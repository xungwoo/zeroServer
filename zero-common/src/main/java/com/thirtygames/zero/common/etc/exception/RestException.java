package com.thirtygames.zero.common.etc.exception;

import javax.servlet.http.HttpServletResponse;

import com.thirtygames.zero.common.etc.error.Errors;

public class RestException extends RuntimeException {
	private static final long serialVersionUID = -788933000712756136L;
	private int statusCode = HttpServletResponse.SC_BAD_REQUEST;
    private String message;
    private int errorCode;
 
    public RestException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    
    public RestException(int statusCode, String message, int errorCode) {
    	this.statusCode = statusCode;
    	this.message = message;
    	this.errorCode = errorCode;
    }
    
    public RestException(String message, int errorCode) {
    	this.message = message;
    	this.errorCode = errorCode;
    }

    public RestException(String message) {
        this.message = message;
    }

    public RestException(Errors errorType) {
		this.message = errorType.getMessage();
		this.errorCode = errorType.getCode();
	}

	public RestException(int statusCode, Errors errorType) {
		this.statusCode = statusCode;
		this.message = errorType.getMessage();
		this.errorCode = errorType.getCode();
	}

	public RestException(Errors errorType, String addMessage) {
		this.errorCode = errorType.getCode();
		this.message = errorType.getMessage() + ":" + addMessage;
	}

	public RestException(int statusCode, Errors errorType, String addMessage) {
		this.statusCode = statusCode;
		this.errorCode = errorType.getCode();
		this.message = errorType.getMessage() + ":" + addMessage;
	}

	public int getStatusCode() {
    	return this.statusCode;
    }
    
    @Override
    public String getMessage() {
    	return this.message;
    }
    
    public int getErrorCode() {
    	return this.errorCode;
    }
}