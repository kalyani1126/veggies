package com.vgs.ws.exception;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class WSException extends VgsException {
    
    private int errorCode;
    private String errorMessage;

    public WSException() {
        this.errorCode = -1;
        this.log();
    }

    public WSException(Throwable cause) {
        super(cause);
        this.errorCode = -1;
        this.log();
    }

    public WSException(int errorCode) {
        this.errorCode = errorCode;
        this.log();
    }

    public WSException(String message) {
        super(message);
        this.errorCode = -1;
        this.log();
    }

    public WSException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = -1;
        this.log();
    }

    public WSException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.log();
    }

    public WSException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.log();
    }

    public WSException(int errorCode, String errorMessage, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.log();
    }

    public WSException(int errorCode, String errorMessage, String additionalText) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.log();
    }

    public WSException(int errorCode, String errorMessage, String additionalText, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.log();
    }

    public WSException(VGSError vgsError) {
        super(vgsError);
        this.log();
    }
    
    public WSException(VGSError vgsError, String eMessage) {
        super(vgsError);
        this.errorMessage = eMessage;
        this.log();
    }
    
    public WSException(VGSError vgsError, Throwable cause) {
        super(vgsError, cause);
        this.log();
    }
    
    private void log() {
        if (this.errorCode != -1) {
            this.getLogger().log(Level.FINEST, "WSException", this);
        }
    }

    protected Logger getLogger() {
        return Logger.getGlobal();
    }

    public String errorText() {
        if (this.errorCode >= 0) {
            if (this.errorCode == 0) {
                return "Successful";
            }

            if (null != this.errorMessage) {
                return this.errorMessage;
            }
        }
        return "-1 Unknown error";
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.getErrorMessage();
    }

	public String getErrorMessage() {
		StringBuilder message = new StringBuilder(this.errorText());
		return message.append(this.variableText()).toString();
	}

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessageText() {
        return this.errorMessage;
    }

    public void setMessageText(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private StringBuilder variableText() {
        if (this.errorMessage == null ) {
        	return new StringBuilder("UnKnown Error");
        }else{
            StringBuilder buffer = new StringBuilder();
            if (this.errorMessage != null) {
                buffer.append("  (");
                buffer.append(this.errorMessage);
                buffer.append(")");
            }

            return buffer;
        }
    }

    public String toString() {
        String theText = this.errorText();
        if (this.errorMessage == null) {
            return theText;
        } else {
            if (theText != null) {
                theText = theText + "  (" + this.errorMessage + ")";
            }

            return theText;
        }
    }

    public WSException success() {
        this.errorCode = 0;
        return this;
    }

    public WSException atpError(String errorMessage, int errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        return this;
    }

    public WSException unhandledException(String s) {
        this.errorCode = 1200;
        this.errorMessage = s;
        return this;
    }

    public WSException tokenExpired(String s) {
        this.errorCode = 1201;
        this.errorMessage = s;
        return this;
    }

    public WSException tokenMismatch(String s) {
        this.errorCode = 381;
        this.errorMessage = s;
        return this;
    }

}
