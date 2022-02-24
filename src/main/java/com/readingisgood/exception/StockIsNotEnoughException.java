package com.readingisgood.exception;

public class StockIsNotEnoughException extends RuntimeException {

	private static final long serialVersionUID = -7137192596347707920L;

	private final String message;

	public StockIsNotEnoughException(long bookId, long stock, long neededStock) {
		message = String.format("There is not enough stock for Book(%s): (current: %s, needed: %s)", bookId, stock,
				neededStock);
	}

	@Override
	public String getMessage() {
		return message;
	}

}
