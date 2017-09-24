package com.noah;


import java.util.List;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;

import org.json.simple.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.noah.client.filter.RestfulClient;
import com.noah.pojo.Book;
import com.noah.server.JettyServer;
import com.noah.server.service.BookStore;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CURL_Test {
	
	@BeforeClass
	public static void startUp() throws Exception{
		JettyServer.start();
	}
	
	@Before
	public void resetData(){
		BookStore.reset();
	}
	
	@AfterClass
	public static void tearDown() throws Exception{
		JettyServer.stop();
	}
	
	@Test
	public void testGet(){
		List<Book> books = RestfulClient.getAllBooks();
		assertThat(books.size(), equalTo(4));
	}
	
	@Test
	public void testtGetBookById(){
		Book book = RestfulClient.getBookById("ID_2");
		assertThat(book.getTitle(), equalTo("JavaScript Code Design"));
	}
	
	@Ignore
	@Test
	public void testGetAllAsyn() throws Exception{
		List<Book> books = RestfulClient.getAllBooksAysn();
		assertThat(books.size(), equalTo(4));
	}
	
	@Ignore
	@Test
	public void testGetAllAsynWithCallback() throws Exception{
		List<Book> books = RestfulClient.getAllBooksAysnWithCallback();
		assertThat(books.size(), equalTo(4));
	}
	
	@Ignore
	@Test(expected= NotFoundException.class)
	public void testGetSthNotExist(){
		RestfulClient.getBookById("ID_Not_Exist");
	}
	
	@Test
	public void testPost(){
		Book book = new Book("ID_New", "New_Book", 100.25);
		JSONObject result = RestfulClient.addBook(book, "Noah", "NoahPwd");
		assertThat(result.get("status"), equalTo(true));
		assertThat(Integer.valueOf(result.get("count").toString()), equalTo(5));
		Book bookAfterPost = RestfulClient.getBookById("ID_New");
		assertThat(bookAfterPost.getPrice(), equalTo(120.3));
	}
	
	@Test
	public void testDelete(){
		JSONObject result = RestfulClient.deleteBookById("ID_1", "Noah", "NoahPwd");
		assertThat(result.get("status"), equalTo(true));
		assertThat(Integer.valueOf(result.get("count").toString()), equalTo(3));
	}
	
	@Test
	public void testPut(){
		Book book = RestfulClient.getBookById("ID_1");
		assertThat(book.getTitle(), equalTo("Java Code Design"));
		assertThat(book.getPrice(), equalTo(15.6));
		book.setTitle("New Java Code Design");
		book.setPrice(115.8);
		RestfulClient.updateBookById(book, "Noah", "NoahPwd");
		Book newBook = RestfulClient.getBookById("ID_1");
		assertThat(newBook.getTitle(), equalTo("New Java Code Design"));
		assertThat(newBook.getPrice(), equalTo(115.8));
	}
	
	@Test(expected = ForbiddenException.class)
	public void testDeleteUnauthenticated(){
		RestfulClient.deleteBookById("ID_!", "Allie", "AlliePwd");
	}
	
	
	@Test(expected = NotAuthorizedException.class)
	public void testDeletePwdIncorrect(){
		RestfulClient.deleteBookById("ID_!", "Noah", "NoahWrongPwd");
	}
	
	
	
	
	
	
	
	
	
	
	

}
