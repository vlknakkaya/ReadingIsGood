package com.readingisgood.model.dto;

public class CartItemDTO {

	private long bookId;
	private int count;

	public CartItemDTO() {
		super();
	}

	public CartItemDTO(long bookId, int count) {
		super();
		this.bookId = bookId;
		this.count = count;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
