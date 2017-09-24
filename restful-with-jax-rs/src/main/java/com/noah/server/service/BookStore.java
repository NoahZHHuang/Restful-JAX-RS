package com.noah.server.service;

import java.util.ArrayList;
import java.util.List;

import com.noah.pojo.Book;

public class BookStore {

	private static List<Book> bookRepository = new ArrayList<>();

	static {
		reset();
	}

	public static void reset() {
		bookRepository.clear();
		Book b1 = new Book("ID_1", "Java Code Design", 15.6);
		Book b2 = new Book("ID_2", "JavaScript Code Design", 15.6);
		Book b3 = new Book("ID_3", "SQL Desgin", 15.6);
		Book b4 = new Book("ID_4", "Big Data Desgin", 15.6);
		bookRepository.add(b1);
		bookRepository.add(b2);
		bookRepository.add(b3);
		bookRepository.add(b4);
	}

	public static List<Book> getBookRepository() {
		return bookRepository;
	}

	public static void setBookRepository(List<Book> bookRepository) {
		BookStore.bookRepository = bookRepository;

	}

}
