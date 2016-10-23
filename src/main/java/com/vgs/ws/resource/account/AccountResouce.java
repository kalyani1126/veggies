/**
 * 
 */
package com.vgs.ws.resource.account;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.vgs.ws.resource.CoreResource;

/**
 * @author Santosh.Kumar
 * Date : Oct 19, 2016
 */

@Path("/account/{account.id}")
public class AccountResouce extends CoreResource{

	private static final Logger logger = Logger.getLogger("com.ws.system.log");
	protected static final String APP_ID_HEADER = "X-APP-ID";
    private static final String X_CLIENT_PLATFORM = "X-Client-Platform";
    private static final String X_USER_UID = "X-USER-UID";
    private String clientPlatformHeader;
    private String appIdHeader;
    private String userUidHeader;
	/**
	 * @param headers
	 * @param userId
	 */
	public AccountResouce(@Context HttpHeaders headers, @PathParam ("userId") String userId) {
		super(headers, userId);
		// TODO Auto-generated constructor stub
	}

	@GET
    @Produces ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAccount(@PathParam ("userId") final String userId,
                                     @HeaderParam (APP_ID_HEADER) String appId,
                                     @HeaderParam (X_CLIENT_PLATFORM) String clientPlatform,
                                     @HeaderParam (X_USER_UID) String userUid) {
        this.userId = userId;
        this.appIdHeader = appId;
        this.clientPlatformHeader = clientPlatform;
        this.userUidHeader = userUid;
        RestExecuter re = new RestExecuter() {
            public Response execute() throws Exception {
                logger.log(Level.INFO, "Enter:getAccount() for [ user = " + userId + ", domain = " + domain + " ]");
                return AccountResouce.this.getAccountProx();
            }

            @Override
            public Response errorResponse(int httpStatus, String errorCode, String errorMessage)
                throws Exception {
                return AccountResouce.this.sendAccountError(httpStatus, errorCode, errorMessage);
            }
        };
        return super.executeRequest(re, null);
    }

	/**
	 * @param httpStatus
	 * @param errorCode
	 * @param errorMessage
	 * @return
	 */
	protected Response sendAccountError(int httpStatus, String errorCode,
			String errorMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	protected Response getAccountProx() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
