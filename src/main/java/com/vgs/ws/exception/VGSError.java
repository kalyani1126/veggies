/**
 * 
 */
package com.vgs.ws.exception;

import com.vgs.ws.Constants;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public enum VGSError {

	// Account Errors
	SERVICE_SUCCESS("1000", "Success.", Constants.OK), 
	SERVICE_FAILED("1001","Service Failed.", Constants.NOT_FOUND),
	INVALID_COUNTRY_CODE("1002", "Invalid country code is specified.", Constants.BAD_REQUEST), 
	INVALID_TIMEZONE_CODE("1003","Invalid timezone code is specified.", Constants.BAD_REQUEST), 
	INVALID_LANGUAGE_CODE("1004", "Invalid language is specified.", Constants.BAD_REQUEST),
	INVALID_USER_ID("1005", "Invalid user ID is specified.", Constants.BAD_REQUEST),
	INVALID_USERID_VALUE("1006", "Invalid user ID value is specified.", Constants.BAD_REQUEST), 
	INVALID_EMAIL("1007", "Invalid Email address.", Constants.BAD_REQUEST), 
	INVALID_REQUEST("1008", "Invalid request.", Constants.BAD_REQUEST),
	MISSING_USER_ID("1009", "User ID of type ICS_ID is not specified.", Constants.BAD_REQUEST),
	ACCOUNT_ALREADY_EXISTS("1010", "Account already exists.", Constants.BAD_REQUEST), 
	ACCOUNT_DEACTIVATED("1011", "Account already deactivated.", Constants.BAD_REQUEST),
	
	// Common Errors
	INTERNAL_SERVER_ERROR("1100", "Internal server error", Constants.SERVER_ERROR), 
	SERVICE_UNAVAILABLE(			"1101", "Service Unavailable", Constants.SERVICE_UNAVIALABLE), 
	INVALID_XML(			"1102", "INVALID_XML/JSON", Constants.BAD_REQUEST),
	// Etc..

	//Authentication
	MISSING_AUTH_HEADER("1150", "Missing Authentication header.", Constants.UNAUTHORIZED),
	HTTP_HEADER_NOT_SUPPORTED("1151", "Http header not supported.", Constants.UNAUTHORIZED),
	UNAUTHORIZED_USER("1151", "Http header not supported.", Constants.UNAUTHORIZED),
	//Device 
	DEVICE_INFO_NOT_FOUND("1200", "Device info not found.", Constants.BAD_REQUEST),
	
	// Image Messages
	UPLOADED_IMAGE_SUCCESS("1250", "Image Uploaded Successfully.", Constants.OK);

	private final String ecode;
	private final String emsg;
	private final int httpStatusCode;

	/**
	 * @param code
	 * @param msg
	 * @param httpStatusCode
	 */
	private VGSError(String ecode, String emsg, int httpStatusCode) {
		this.ecode = ecode;
		this.emsg = emsg;
		this.httpStatusCode = httpStatusCode;
	}

	/**
	 * @return the ecode
	 */
	public String getEcode() {
		return ecode;
	}

	/**
	 * @return the emsg
	 */
	public String getEmsg() {
		return emsg;
	}

	/**
	 * @return the httpStatusCode
	 */
	public int getHttpStatusCode() {
		return httpStatusCode;
	}

}
