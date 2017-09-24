package com.noah.server.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class AuthenticationException extends WebApplicationException {

	public AuthenticationException(String message) {
		super(Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).entity(message).build());
	}
}
