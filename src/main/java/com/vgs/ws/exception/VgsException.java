package com.vgs.ws.exception;

import com.vgs.ws.Constants;


/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class VgsException extends Exception {

	private static final long serialVersionUID = -1L;

	
	
	private VGSError vgsError;
	
	/**
	 * @return the vgsError
	 */
	public VGSError getVgsError() {
		return vgsError;
	}

	/**
	 * @param vgsError the vgsError to set
	 */
	public void setVgsError(VGSError vgsError) {
		this.vgsError = vgsError;
	}

	/**
	 * @param vgsError
	 */
	public VgsException(VGSError vgsError) {
		super();
		this.vgsError = vgsError;
	}

	/**
	 * @param vgsError
	 */
	public VgsException(VGSError vgsError, Throwable cause) {
		super(cause);
		this.vgsError = vgsError;
	}
	
	/**
	 * @param cause
	 */
	public VgsException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public VgsException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 */
	public VgsException(String message) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public VgsException(String message, Throwable cause) {
		// TODO Auto-generated constructor stub
	}

}
