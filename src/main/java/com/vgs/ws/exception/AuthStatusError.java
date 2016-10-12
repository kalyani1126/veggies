
package com.vgs.ws.exception;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */

public enum AuthStatusError {

    UNKNOWN_ERROR(1000, "unknown error"),

    USER_NOT_FOUND(1001, "user not found"),

    INVALID_PASSWORD(1003, "invalid login"),

    INVALID_DEVICE_INFO(1004, "invalid device info"),

    PASSWORD_NOT_FOUND(1005, "password not provided"),

    TOKEN_EXPIRED(1006, "token expired"),

    TOKEN_INVALID(1007, "token invalid"),

    TOKEN_MISSING(1008, "token missing"),
    
    //
    
    MISSING_USER_CREDENTIALS_ERROR(1010, "Missing User Credentials"),

    INVALID_AUTHORIZATION_DOMAIN(1011, "Invalid Authorization Domain"),
    
    SYSTEM_BUSY_ERROR(1100, "System Busy");

    private AuthStatusError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

    /**
     * @return Returns the code.
     */
    public int getCode() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }

}

