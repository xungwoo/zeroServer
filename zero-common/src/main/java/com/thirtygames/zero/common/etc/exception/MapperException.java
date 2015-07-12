package com.thirtygames.zero.common.etc.exception;



public class MapperException extends RestException {
	private static final long serialVersionUID = 6997350300978294189L;

	public MapperException(String message) {
		super(message);
	}

	public MapperException(int statusCode, String message) {
		super(statusCode, message);
	}

}