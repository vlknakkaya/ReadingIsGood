package com.readingisgood.util;

public enum OrderStatus {

	PENDING(0, "pending"), SHIPPING(1, "shipping"), DELIVERED(2, "delivered");

	private OrderStatus(int id, String statusText) {
		this.id = id;
		this.statusText = statusText;
	}

	private int id;
	private String statusText;

	public int getId() {
		return id;
	}

	public String getStatusText() {
		return statusText;
	}

}
