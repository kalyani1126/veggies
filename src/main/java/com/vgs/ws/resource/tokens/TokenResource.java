package com.vgs.ws.resource.tokens;

import com.vgs.ws.Constants;
import com.vgs.ws.exception.AuthenticationException;
import com.vgs.ws.exception.WSException;
import com.vgs.ws.resource.AccountTokensRequestUnMarshaller;
import com.vgs.ws.resource.CoreResource;
import com.vgs.ws.service.TokensService;
import com.vgs.ws.util.AccountTokens;
import com.vgs.ws.util.BeanLocator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
@Path ("/{userId}/account/tokens")
public class TokenResource extends CoreResource {

    private static final Logger _logger = Logger.getLogger("com.ws.system.log");
    protected static final String APP_ID_HEADER = "X-APP-ID";
    private static final String X_CLIENT_PLATFORM = "X-Client-Platform";
    private static final String X_USER_UID = "X-USER-UID";
    private static final String X_ACCESS_TOKEN = "X-Access-Token";
    private String clientPlatformHeader;
    private String appIdHeader;
    private String accessTokenHeader;
    private String userUidHeader;

    /**
     * @param headers
     * @param userId
     */
    public TokenResource(@Context HttpHeaders headers,
                         @PathParam ("userId") String userId) {
        super(headers, userId);
    }

    /**
     * @param userId
     * @param timeStamp
     * @param appId
     * @param clientPlatform
     *
     * @return
     */
    @GET
    @Produces ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAccountTokens(@PathParam ("userId") final String userId,
                                     @QueryParam ("timestamp") String timeStamp,
                                     @HeaderParam (APP_ID_HEADER) String appId,
                                     @HeaderParam (X_CLIENT_PLATFORM) String clientPlatform,
                                     @HeaderParam (X_USER_UID) String userUid) {
        this.userId = userId;
        this.appIdHeader = appId;
        this.clientPlatformHeader = clientPlatform;
        this.userUidHeader = userUid;
        RestExecuter re = new RestExecuter() {
            public Response execute() throws Exception {
                _logger.log(Level.INFO, "Enter:getAccountTokens() for [ user = " + userId + ", domain = " + domain + " ]");
                return TokenResource.this.getAccountTokensAux();
            }

            @Override
            public Response errorResponse(int httpStatus, String errorCode, String errorMessage)
                throws Exception {
                return TokenResource.this.sendAccountTokensError(httpStatus, errorCode, errorMessage);
            }
        };
        return super.executeRequest(re, null);
    }

    /**
     * @param userId
     * @param request
     * @param timeStamp
     * @param appId
     * @param clientPlatform
     *
     * @return
     */
    @POST
    @Produces ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAccountTokensWithDevInfo(@PathParam ("userId") final String userId,
                                                final InputStream request,
                                                @QueryParam ("timestamp") String timeStamp,
                                                @HeaderParam (APP_ID_HEADER) String appId,
                                                @DefaultValue ("UTF-8") @HeaderParam ("Content-Encoding") String charset,
                                                @HeaderParam (X_CLIENT_PLATFORM) String clientPlatform,
                                                @HeaderParam (X_USER_UID) String userUid) {
        this.requestXML = Streams.streamToString(request, charset);
        this.userId = userId;
        this.appIdHeader = appId;
        this.clientPlatformHeader = clientPlatform;
        this.userUidHeader = userUid;
        RestExecuter re = new RestExecuter() {
            @Override
            public Response execute() throws Exception {
                return TokenResource.this.getAccountTokensWithDevInfoAux();
            }

            @Override
            public Response errorResponse(int httpStatus, String errorCode, String errorMessage)
                throws Exception {
                return TokenResource.this.sendAccountTokensError(httpStatus,
                                                                 errorCode, errorMessage);
            }
        };
        return super.executeRequest(re, null);
    }

    @DELETE
    @Produces ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response logout(@PathParam ("userId") final String userId,
                           @QueryParam ("timestamp") String timeStamp,
                           @HeaderParam (APP_ID_HEADER) String appId,
                           @HeaderParam (X_CLIENT_PLATFORM) String clientPlatform,
                           @HeaderParam (X_ACCESS_TOKEN) String accessToken) {
        this.userId = userId;
        this.appIdHeader = appId;
        this.clientPlatformHeader = clientPlatform;
        this.accessTokenHeader = accessToken;
        
        RestExecuter re = new RestExecuter() {
            @Override
            public Response execute() throws Exception {
                return TokenResource.this.logoutAux();
            }

            @Override
            public Response errorResponse(int httpStatus, String errorCode, String errorMessage)
                throws Exception {
                return TokenResource.this.sendAccountTokensError(httpStatus,
                                                                 errorCode, errorMessage);
            }
        };
        return super.executeRequest(re, null);
    }

    /**
     * @return Response
     *
     * @throws WSException
     */
    private Response logoutAux() throws WSException {
        Map<String, String> header = currentHeaders();
        header.put(AccountTokens.V_TOKEN, accessTokenHeader);
        AccountTokensMarshaller accountTokensMarshaller = new AccountTokensMarshaller();
        String xml = "";
        try {
            String msg = Constants.SUCCESS_MSG;
            String code = Constants.SUCCESS_CODE;
            getTokensService().invalidateToken(userId, userPasswordFromHeader, header);
            xml = accountTokensMarshaller.deleteTokensResponse(code, msg);
            return buildResponse(Constants.OK, xml);
        } catch (AuthenticationException e) {
            _logger.log(Level.ERROR, "Error executing getAccountTokensWithDevInfoAux request", e);
            int errorType = Constants.BAD_REQUEST;
            if(null != e.getErrorCode()) {
                errorType = handleHttpStatus(e);
                xml = accountTokensMarshaller.deleteTokensResponse(e.getErrorCode(), e.getErrorMessage());
            } else {
                xml =
                    accountTokensMarshaller.deleteTokensResponse(Integer.toString(e.getAuthStatus().getCode()), e.getAuthStatus().getMessage());
            }
            return buildResponse(errorType, xml);
        } catch (Throwable e) {
            _logger.log(Level.ERROR, "Unhandled Error executing getAccountTokensWithDevInfoAux request", e);
            return buildResponse(Constants.SERVER_ERROR);
        }
    }

    /**
     * @return Response
     *
     * @throws WSException
     */
    private Response getAccountTokensWithDevInfoAux() throws WSException {

        AccountTokensMarshaller accountTokensMarshaller = new AccountTokensMarshaller();
        try {
            String msg = Constants.SUCCESS_MSG;
            String code = Constants.SUCCESS_CODE;

            AccountTokensRequestUnMarshaller accountTokensRequestUnMarshaller = new AccountTokensRequestUnMarshaller();
            AccountTokens accountTokensRequestBody = accountTokensRequestUnMarshaller.readAccountTokensRequest(requestXML, userId);
            Map<String, String> header = currentHeaders();
            header.put(AccountTokens.DEVICE_INFO, accountTokensRequestBody.getDeviceinfo());
            AccountTokens accountTokens = getTokensService().getAccountTokens(userId, userPasswordFromHeader, header);
            String xml =
                accountTokensMarshaller.writeTokensResponse(accountTokens, code, msg);
            return buildResponse(Constants.OK, xml);
        } catch (AuthenticationException e) {
            _logger.log(Level.ERROR, "Error executing getAccountTokensWithDevInfoAux request", e);
            String xml = "";
            int errorType = Constants.BAD_REQUEST;
            if(null != e.getErrorCode()) {
                errorType = handleHttpStatus(e);
                xml =
                    accountTokensMarshaller.writeTokensResponse(e.getErrorCode(), e.getErrorMessage());
            } else {
                xml =
                    accountTokensMarshaller.writeTokensResponse(Integer.toString(e.getAuthStatus().getCode()), e.getAuthStatus().getMessage());
            }
            return buildResponse(errorType, xml);
        } catch (WSException e) {
            _logger.log(Level.ERROR, "Error executing getAccountTokensWithDevInfoAux request", e);
            String xml =
                accountTokensMarshaller.writeTokensResponse(Integer.toString(e.getErrorCode()), e.getErrorMessage());
            return buildResponse(Constants.BAD_REQUEST, xml);
        } catch (Throwable e) {
            _logger.log(Level.ERROR, "Unhandled Error executing getAccountTokensWithDevInfoAux request", e);
            return buildResponse(Constants.BAD_REQUEST_SEMANTICS);
        }
    }

    /**
     * @return Response
     *
     * @throws WSException
     */
    private Response getAccountTokensAux() throws WSException {
        _logger.log(Level.INFO, "Enter: getAccountTokensAux() for user :" + userId);
        AccountTokensMarshaller accountTokensMarshaller = new AccountTokensMarshaller();
        try {
            String msg = Constants.SUCCESS_MSG;
            String code = Constants.SUCCESS_CODE;
            AccountTokens accountTokens =
                getTokensService().getAccountTokens(this.userId, userPasswordFromHeader, currentHeaders());
            String xml =
                accountTokensMarshaller.writeTokensResponse(accountTokens, code, msg);
            return buildResponse(Constants.OK, xml);
        } catch (AuthenticationException e) {
            _logger.log(Level.ERROR, "Error executing getAccountTokensAux request", e);
            String xml = "";
            int errorType = Constants.BAD_REQUEST;
            if(null != e.getErrorCode()) {
                errorType = handleHttpStatus(e);
                xml =
                    accountTokensMarshaller.writeTokensResponse(e.getErrorCode(), e.getErrorMessage());
            } else {
                xml =
                    accountTokensMarshaller.writeTokensResponse(Integer.toString(e.getAuthStatus().getCode()), e.getAuthStatus().getMessage());
            }
            return buildResponse(errorType, xml);
        } catch (WSException e) {
            _logger.log(Level.ERROR, "Error executing getAccountTokensAux request", e);
            String xml =
                accountTokensMarshaller.writeTokensResponse(Integer.toString(e.getErrorCode()), e.getErrorMessage());
            return buildResponse(Constants.BAD_REQUEST_SEMANTICS, xml);
        } catch (Throwable e) {
            _logger.log(Level.ERROR, "Unhandled Error executing getAccountTokensAux request", e);
            return buildResponse(Constants.SERVER_ERROR);
        }
    }

    /**
     * @param httpStatusCode
     * @param errorCode
     * @param errorMessage
     *
     * @return Response
     */
    protected Response sendAccountTokensError(int httpStatusCode, String errorCode,
                                              String errorMessage) {
        AccountTokensMarshaller accountTokensMarshaller = new AccountTokensMarshaller();
        String statusMsg = null;
        try {
            statusMsg = accountTokensMarshaller.writeTokensResponse(errorCode, errorMessage);
        } catch (Exception exc) {
        }
        return buildResponse(httpStatusCode, statusMsg);
    }

    protected Map<String, String> currentHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(AccountTokens.APPID, appIdHeader);
        headers.put(AccountTokens.CLIENTPLATFORM, clientPlatformHeader);
        headers.put(AccountTokens.DOMAIN, domain);
        headers.put(AccountTokens.USERNAME, this.userId);
        return headers;
    }

    public TokensService getTokensService() {
        return BeanLocator.getInstance().getTokensServiceBean();
    }

    private int handleHttpStatus(AuthenticationException exception) {
        int errorType = Constants.UNAUTHORIZED;
        //Have to Segregate http_error_code based on error code  
        return errorType;
    }
}
