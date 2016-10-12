package com.vgs.ws.resource;

import com.vgs.ws.exception.VGSError;
import com.vgs.ws.exception.WSException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public abstract class CoreResource {

    private static final Logger _logger = Logger.getLogger("com.ws.system.log");
    //HTTP Headers
    protected static final String DEFAULT_AUTH_HEADER = "Authorization";
    protected static final String HEADER_AUTHORIZATION_DOMAIN = "Authorization-Domain";
    protected static final String DEFAULT_CHARSET = "UTF-8";
    protected static final String HTTP_AUTH_BASIC = "Basic";
    
    protected String userPasswordFromHeader;
    protected String addressTypeFromHeader;
    protected String domain;
    protected JAXBElement<?> requestElement;
    protected String userId;
    protected String requestXML;
    
    //HTTP Context
    protected @Context    UriInfo uriInfo;
    protected @Context    Request request;
    protected @Context    HttpHeaders headers;
    protected @HeaderParam ("Content-Encoding")    String charset;
        

    public CoreResource(HttpHeaders headers, String userId) {
        this.headers = headers;
        this.userId = userId;
    }

    @Context
    public void setUriInfo(UriInfo ui) {
        this.uriInfo = ui;
    }

    public UriInfo getUriInfo() {
        return this.uriInfo;
    }

    @Context
    public void setRequest(Request req) {
        this.request = req;
    }

    public Request getRequest() {
        return this.request;
    }

    @Context
    public void setHttpHeaders(HttpHeaders hdrs) {
        this.headers = hdrs;
    }

    public HttpHeaders getHttpHeaders() {
        return this.headers;
    }

    protected interface RestExecuter {

        public Response execute() throws Exception;

        public Response errorResponse(int httpStatus, String errorCode, String errorMessage)
            throws Exception;
    };

    protected String getCharset() {
        return charset == null ? DEFAULT_CHARSET : charset;
    }

    /**
     * This method {@code #RestExecuter.executeRequest} thru which all the REST requests are routed 
     * and allows common logic such as logging & exception handling to be centralized at one place.
     *
     * @param re RestExecuter
     *
     * @return Response
     */
    protected Response executeRequest(RestExecuter re, String requestBody) {
        Response response = null;
        String uriPath = null;
        String httpMethod = null;
        long t1 = 0;
        long t2 = 0;
        try {
            t1 = System.currentTimeMillis();
            uriPath = this.uriInfo.getPath();
            httpMethod = this.request.getMethod();
            log(Level.INFO, uriPath, httpMethod, getHeaders(),this.requestXML);
            List<String> authHeaders = headers.getRequestHeader(DEFAULT_AUTH_HEADER);
            List<String> domainList = headers.getRequestHeader(HEADER_AUTHORIZATION_DOMAIN);

            if (domainList == null || domainList.isEmpty() || authHeaders == null || authHeaders.isEmpty()) {
                throw new WSException(VGSError.MISSING_AUTH_HEADER, "Authorization-Domain is missing");
            }
            this.domain = domainList.get(0);
            processAuthHeader(authHeaders.get(0));
            setUserFromRequestor();
            response = re.execute();
            t2 = System.currentTimeMillis();

            if (response.getEntity() != null) {
                log(Level.INFO, uriPath, httpMethod, response.getEntity().toString(), t2 - t1);
            } else {
                log(Level.INFO, uriPath, httpMethod, "", t2 - t1);
            }
        } catch (WSException ex) {
            VGSError error = ex.getVgsError();
            _logger.error("Error executing request - VGSError." + error, ex);
            try {
                response =
                    re.errorResponse(error.getHttpStatusCode(), error.getEcode(), error.getEmsg());
            } catch (Exception exc) {
                _logger.error("Exception building error response", exc);
                response = buildResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            _logger.error("Exception building error response", e);
            response = buildResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    /**
     * @param httpStatusCode int
     * @param responseBody String
     *
     * @return Response
     */
    protected Response buildResponse(int httpStatusCode, String responseBody) {
        return Response.status(httpStatusCode).entity(responseBody).type(
            MediaType.APPLICATION_XML).build();
    }

    /**
     * @param httpStatusCode int
     *
     * @return Response
     */
    protected Response buildResponse(int httpStatusCode) {
        return buildResponse(httpStatusCode, null);
    }

    /**
     * Process the Authorization header to extract authentication information
     *
     * @param authHeaderValue The value of the Authorization header.
     *
     * @return the userId string from the Authorization header.
     *
     * @throws Exception if http auth method is not supported.
     */
    protected String processAuthHeader(String authHeaderValue) throws WSException {
        try {
            String authType = authHeaderValue.split(" ")[0];
            String userIdPassFromHeader = authHeaderValue.split(" ")[1];
            // Now decode the user id and password if the auth type is Basic
            if ((authType != null) && authType.equals(HTTP_AUTH_BASIC)) {

                String userIdPassStr = decodeToString(userIdPassFromHeader);
                String userIdFromHeader = null;
                String userIdPq[] = userIdPassStr.split(":");
                if (userIdPq.length >= 1) {
                    userPasswordFromHeader = userIdPq[1];
                    // Splitting the decoded value to obtain the user id , pw
                    // and addresstype.
                    String idType[] = userIdPq[0].split("\\|");
                    if (idType.length > 1) {
                        userIdFromHeader = idType[1];
                        this.addressTypeFromHeader = idType[0];
                    } else {
                        userIdFromHeader = userIdPq[0];
                    }
                }
                _logger.info("Userid from URL and Authorization header : " + userIdFromHeader);
                return userIdFromHeader;
            } else {
                throw new WSException(VGSError.HTTP_HEADER_NOT_SUPPORTED,
                                           "The header values specified are not correct");
            }
        } catch (NullPointerException exc) {
            throw new WSException(VGSError.UNAUTHORIZED_USER, exc);
        }
    }

    private String decodeToString(String base64) {
        return new String(Base64.getDecoder().decode(base64));
    }

    private void setUserFromRequestor() throws WSException {
        if ((this.userId != null) && this.userId.equalsIgnoreCase("@requestor")) {
            List<String> authHeaders = headers.getRequestHeader(DEFAULT_AUTH_HEADER);
            String authHeaderValue = authHeaders.get(0);
            this.userId = processAuthHeader(authHeaderValue);
            _logger.info("setUserFromRequestor():Mapping @requestor from request URI to userId:" + this.userId);
        }
    }

    protected String getHeaders() {
        String headerLogString = "";
        try {
            MultivaluedMap<String, String> headerMap = this.headers.getRequestHeaders();
            for (Iterator<String> iter = headerMap.keySet().iterator(); iter.hasNext(); ) {
                String headerName = iter.next();
                List<String> headerValues = headerMap.get(headerName);
                String headerValueString = "";
                for (String headerValue : headerValues) {
                    headerValueString +=
                        headerValueString.equals("") ? headerValue : "|" + headerValue;
                }
                String headerString = headerName + "=" + headerValueString;
                headerLogString +=
                    headerLogString.equals("") ? headerString : ", " + headerString;
            }
        } catch (NullPointerException exc) {
            _logger.error("Error while retrieving the header values", exc);
        }
        return headerLogString;
    }

    protected static class Streams {

        public static String streamToString(InputStream is, String charset) {
            try {
                ByteArrayOutputStream e = new ByteArrayOutputStream();
                copy((InputStream) is, (OutputStream) e);
                return e.toString(charset);
            } catch (IOException var3) {
                //Logger.get().exception(LogType.ERROR, (String)null, (String)null, "Stream read failure", var3);
                return null;
            }
        }

        public static long copy(InputStream is, OutputStream os) {
            byte[] buffer = new byte[65536];
            long result = 0L;
            try {
                int count1;
                while ((count1 = is.read(buffer)) != -1) {
                    os.write(buffer, 0, count1);
                    result += (long) count1;
                }
            } catch (Exception exc) {
                //return result = 0L
            }
            return result;
        }
    }

    private void log(Level level, String uriPath, String httpMethod, String headers, String requestXML) {
        _logger.log(level, "[PATH:" + uriPath + "][httpMethod:" + httpMethod + "][headers:" + headers + "][request:" + requestXML + "]");
    }

    private void log(Level level, String uriPath, String httpMethod, String requestXML, long time) {
        _logger.log(level, "[PATH:" + uriPath + "][httpMethod:" + httpMethod + "][request:" + requestXML + "]" + "[TIME:" + time + "]");
    }
}
