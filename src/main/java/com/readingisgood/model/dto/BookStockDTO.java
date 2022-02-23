package com.readingisgood.model.dto;

public class BookStockDTO {
	
	private long bookId;
	private long stock;
	
	public BookStockDTO() {
		super();
	}

	public BookStockDTO(long bookId, long stock) {
		super();
		this.bookId = bookId;
		this.stock = stock;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}
	
}
