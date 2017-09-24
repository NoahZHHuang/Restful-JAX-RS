package com.noah.server.service.unsecure;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.noah.pojo.Book;
import com.noah.server.service.BookStore;

@Path("/")
public class BookStoreOuterService {
	
	private static Gson gson = new Gson();
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBook(){
		String booksInJson = gson.toJson(BookStore.getBookRepository());
		return Response.ok(booksInJson).build();
	}
	
	@Path("/all_asyn")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBookAsyn() throws InterruptedException{
		String booksInJson = gson.toJson(BookStore.getBookRepository());
		Thread.sleep(5000L);
		return Response.ok(booksInJson).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBookById(@PathParam("id") String id){
		List<Book> books =BookStore.getBookRepository().stream().filter(book->book.getId().equalsIgnoreCase(id)).collect(Collectors.toList());
		if(books.size()>0){
			return Response.ok(gson.toJson(books.get(0))).build();
		}else{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
}
