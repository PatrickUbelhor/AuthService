package com.patrickubelhor.service.auth.exception;

public class ServerConfigurationException extends RuntimeException {
	
	public ServerConfigurationException(Throwable cause) {
		super("Server configuration error. Please contact administrator.", cause);
	}
	
}
