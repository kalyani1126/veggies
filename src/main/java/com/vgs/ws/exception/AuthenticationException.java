package com.vgs.ws.exception;

/**
 * @author Santosh.Kumar
 * Date : Oct 8, 2016
 */
public class AuthenticationException extends Exception {

	private String errorCode;
	private String errorMessage;
    protected AuthStatusError authStatus;
    
	/**
	 * @param message
	 */
	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(String errorCode, String errorMessage,Throwable cause ) {
		super(errorMessage, cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * 
	 */
	public AuthenticationException() {
		super();
	}

	private static final long	serialVersionUID	= 8951069451828841255L;

	/**
	 * @param message
	 * @param cause
	 */
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public AuthenticationException(Throwable cause) {
		super(cause);
	}
	
	public AuthenticationException(Throwable cause, AuthStatusError authStatus) {	    
        this(cause);
        this.authStatus = authStatus;
    }
	
	/**
     * @param message
     * @param cause
     */
    public AuthenticationException(String message, Throwable cause, AuthStatusError authStatus) {
        this(message, cause);
        this.authStatus = authStatus;
    }
    
    /**
     * @param message
     */
    public AuthenticationException(String message, AuthStatusError authStatus) {
        this(message);
        this.authStatus = authStatus;
    }

    /**
     * @param message
     */
    public AuthenticationException(AuthStatusError authStatus) {
        this.authStatus = authStatus;
    }
    
    /**
     * @return Returns the authStatus.
     */
    public AuthStatusError getAuthStatus() {
        return authStatus;
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
