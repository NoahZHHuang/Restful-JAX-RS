package com.noah.client.filter;

import java.util.List;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.internal.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.noah.pojo.Book;

public class RestfulClient {

	private static final String REST_UNSECURE_URL = "http://localhost:8080/unsecure/book";
	private static final String REST_SECURE_URL = "http://localhost:8080/secure/book";

	private static final Client client = ClientBuilder.newClient();

	private static final Gson gson = new Gson();

	static {
		client.register(RequestFilterTaxAdjustment.class);
	}

	public static List<Book> getAllBooks() {
		String booksInJson = client.target(REST_UNSECURE_URL).path("/all").request().get(String.class);
		TypeToken<List<Book>> token = new TypeToken<List<Book>>() {
		};
		List<Book> books = gson.fromJson(booksInJson, token.getType());
		return books;
	}

	public static Book getBookById(String id) {
		String bookInJson = client.target(REST_UNSECURE_URL).path("/{id}").resolveTemplate("id", id).request()
				.get(String.class);
		return gson.fromJson(bookInJson, Book.class);
	}

	public static List<Book> getAllBooksAysn() throws Exception {
		Future<String> futureTask = client.target(REST_UNSECURE_URL).path("/all_asyn").request().async()
				.get(String.class);
		System.out.println("Doing some other things");
		Thread.sleep(2000L);
		System.out.println("2 secs pass, sth is done!");
		String booksInJson = futureTask.get();
		TypeToken<List<Book>> token = new TypeToken<List<Book>>() {
		};
		List<Book> books = gson.fromJson(booksInJson, token.getType());
		return books;
	}

	public static List<Book> getAllBooksAysnWithCallback() throws Exception {
		Future<String> futureTask = client.target(REST_UNSECURE_URL).path("/all_asyn").request().async()
				.get(new InvocationCallback<String>() {
					@Override
					public void completed(String booksInJson) {
						System.out.println("Process is completed, result is:" + booksInJson);
					}

					@Override
					public void failed(Throwable throwable) {
						throwable.printStackTrace();
					}
				});
		String booksInJson = futureTask.get();
		TypeToken<List<Book>> token = new TypeToken<List<Book>>() {
		};
		List<Book> books = gson.fromJson(booksInJson, token.getType());
		return books;
	}

	public static JSONObject addBook(Book book, String userName, String pwd){
		String result = client.target(REST_SECURE_URL).request().header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader(userName, pwd)).post(Entity.entity(gson.toJson(book), MediaType.APPLICATION_JSON), String.class);
		return (JSONObject) JSONValue.parse(result);
	}
	
	public static JSONObject deleteBookById (String id, String userName, String pwd){
		String result = client.target(REST_SECURE_URL).queryParam("id", id).request().header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader(userName, pwd)).delete(String.class);
		return (JSONObject) JSONValue.parse(result);
	}
	
	public static JSONObject updateBookById(Book book, String userName, String pwd){
		String result = client.target(REST_SECURE_URL).request().header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader(userName, pwd)).put(Entity.entity(gson.toJson(book), MediaType.APPLICATION_JSON),String.class);
		return (JSONObject) JSONValue.parse(result);
	}

	private static String getBasicAuthHeader(String userName, String pwd) {
		return "Basic " + Base64.encodeAsString(userName + ":" + pwd);
	}

}
