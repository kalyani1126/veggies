package com.vgs.ws;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public final class Constants {

	private Constants() {
	}

	/**
	 * Constant code for success.
	 */
	public static String SUCCESS_CODE = "4000";
	/**
	 * Constant message for success.
	 */
	public static String SUCCESS_MSG = "Success";

	public static final int UNKNOWN_ERROR = -1;

	public static final int OK = 200;
	public static final int CREATED = 201;
	public static final int ACCEPTED = 202;
	public static final int NO_CONTENT = 204;

	public static final int HTTP_REDIRECT = 301;

	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int FORBIDDEN = 403;
	public static final int NOT_FOUND = 404;
	public static final int METHOD_NOT_ALLOWED = 405;
	public static final int NOT_ACCEPTABLE = 406;
	public static final int HTTP_REQUEST_TIME_OUT = 408;
	public static final int CONFLICT = 409;
	public static final int UNSUPPORTED_MEDIA_TYPE = 415;
	public static final int BAD_REQUEST_SEMANTICS = 422;

	public static final int UNKNOWN_HOST_EXCEPTION = 450;
	public static final int SOCKET_TIMEOUT_EXCEPTION = 451;
	public static final int JSON_EXCEPTION = 452;
	public static final int CONNECTION_EXCEPTION = 453;
	public static final int INSTANTIATION_FAILED = 454;
	public static final int TOKEN_FAILED = 455;
	public static final int DELEGATE_TOKEN_FAILED = 456;

	public static final int SERVER_ERROR = 500;
	public static final int SERVICE_UNAVIALABLE = 503;

}
