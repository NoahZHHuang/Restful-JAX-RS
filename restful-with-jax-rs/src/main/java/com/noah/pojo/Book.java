package com.noah.pojo;

public class Book {

	private String id;
	private String title;
	private Double price;
	
	public Book(){
		
	}
	
	public Book(String id, String title, Double price){
		this.id = id;
		this.title = title;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
	
	
}
