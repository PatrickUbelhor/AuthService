package com.patrickubelhor.service.auth.exception;

public class UnauthorizedException extends RuntimeException {
	
	public UnauthorizedException() {
		super("Invalid token");
	}
	
	
	public UnauthorizedException(String message) {
		super(message);
	}
	
}
