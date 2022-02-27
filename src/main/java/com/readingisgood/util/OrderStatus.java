package com.readingisgood.util;

/**
 * Enum class for Order status.
 * 
 * Current status ids and texts:
 * 0 - preparing
 * 1 - shipping
 * 2 - delivered
 */
public enum OrderStatus {

	PREPARING(0, "PREPARING"),
	SHIPPING(1, "SHIPPING"),
	DELIVERED(2, "DELIVERED");

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

	public static OrderStatus getStatusById(int id) {
		for (OrderStatus status : values()) {
			if (status.getId() == id) {
				return status;
			}
		}

		return PREPARING; // default
	}

}
