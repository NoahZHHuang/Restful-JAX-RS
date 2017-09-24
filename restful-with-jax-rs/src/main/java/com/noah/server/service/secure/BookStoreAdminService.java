package com.noah.server.service.secure;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.noah.pojo.Book;
import com.noah.server.permission.Role;
import com.noah.server.service.BookStore;

@RolesAllowed(Role.ADMIN_USER)
@Path("/")
public class BookStoreAdminService {

	private static Gson gson = new Gson();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBook(String bookInJson){
		BookStore.getBookRepository().add(gson.fromJson(bookInJson, Book.class));
		JSONObject result = new JSONObject();
		result.put("status", true);
		result.put("count", BookStore.getBookRepository().size());
		return Response.ok(result.toJSONString()).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBookById(@QueryParam("id") String id){
		List<Book> booksRemain = BookStore.getBookRepository().stream().filter(book-> !book.getId().equalsIgnoreCase(id)).collect(Collectors.toList());
		BookStore.setBookRepository(booksRemain);
		JSONObject result = new JSONObject();
		result.put("status", true);
		result.put("count", BookStore.getBookRepository().size());
		return Response.ok(result.toJSONString()).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBook(String bookInJson){
		Book bookToUpdate = gson.fromJson(bookInJson, Book.class);
		BookStore.getBookRepository().stream().filter(book->book.getId().equalsIgnoreCase(bookToUpdate.getId())).forEach(book->{
			book.setTitle(bookToUpdate.getTitle());
			book.setPrice(bookToUpdate.getPrice());
		});;
		JSONObject result = new JSONObject();
		result.put("status", true);
		return Response.ok(result.toJSONString()).build();
	}
	
}
