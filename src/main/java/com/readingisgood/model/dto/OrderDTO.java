package com.readingisgood.model.dto;

import java.sql.Date;
import java.util.HashMap;

public class OrderDTO {

	private long id;
	private long customerId;
	private HashMap<Long, Integer> cart;
	private double totalAmount;
	private Date date;

	public OrderDTO() {
		super();
	}

	public OrderDTO(long id, long customerId, HashMap<Long, Integer> cart, double totalAmount, Date date) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.cart = cart;
		this.totalAmount = totalAmount;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public HashMap<Long, Integer> getCart() {
		return cart;
	}

	public void setCart(HashMap<Long, Integer> cart) {
		this.cart = cart;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
