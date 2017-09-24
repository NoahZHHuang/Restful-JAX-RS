package com.noah.server.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.xml.bind.DatatypeConverter;

import com.noah.server.exception.AuthenticationException;
import com.noah.server.permission.User;
import com.noah.server.permission.UserStore;

@Priority(Priorities.AUTHENTICATION)
public class RequestFilterBasicAuth implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("This is the server request filter for authentication.");
		String authentication = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authentication == null || !authentication.startsWith("Basic ")) {
			throw new AuthenticationException("Authentication error, credentials are required.");
		}
		authentication = authentication.substring("Basic ".length());
		String[] values = new String(DatatypeConverter.parseBase64Binary(authentication), Charset.forName("ASCII"))
				.split(":");
		if (values.length < 2) {
			throw new AuthenticationException("Authentication error, credentials format is not correct.");
		}
		String userName = values[0];
		String password = values[1];
		System.out.println("credentials:" + userName + " " + password);
		User user = UserStore.getUser(userName);
		if(user == null){
			throw new AuthenticationException("Authentication error, user does not exist.");
		}
		if(!user.getPwd().equals(password)){
			throw new AuthenticationException("Authentication error, pwd is not correct.");
		}
		requestContext.setSecurityContext(new MySecurityContext(user));
	}

	private class MySecurityContext implements SecurityContext {

		private final User user;

		public MySecurityContext(User user) {
			this.user = user;
		}

		@Override
		public Principal getUserPrincipal() {
			return new Principal() {
				@Override
				public String getName() {
					return user.getUserName();
				}
			};
		}

		@Override
		public boolean isUserInRole(String role) {
			return role.equals(user.getRole());
		}

		@Override
		public boolean isSecure() {
			return true;
		}

		@Override
		public String getAuthenticationScheme() {
			return "Basic";
		}

	}

}
