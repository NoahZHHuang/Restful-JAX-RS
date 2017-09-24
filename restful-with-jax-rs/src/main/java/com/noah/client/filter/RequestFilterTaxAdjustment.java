package com.noah.client.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import com.google.gson.Gson;
import com.noah.pojo.Book;

public class RequestFilterTaxAdjustment implements ClientRequestFilter {

	public static final double TAX_RATE = 0.2;
	public static final Gson gson = new Gson();
	
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		System.out.println("This is a client request filter for tax adjustment.");
		String method = requestContext.getMethod();
		String uri = requestContext.getUri().toString();
		if(method.equalsIgnoreCase("POST") && uri.endsWith("/secure/book") && requestContext.hasEntity()){
			Book book = gson.fromJson((String)requestContext.getEntity(), Book.class);
			book.setPrice(book.getPrice()*(1+TAX_RATE));
			requestContext.setEntity(gson.toJson(book));
		}
	}

}
