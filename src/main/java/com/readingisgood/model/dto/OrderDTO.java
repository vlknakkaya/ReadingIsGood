package com.readingisgood.model.dto;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderDTO {

	private long id;
	private long customerId;
	private List<CartItemDTO> cart;
	private double totalAmount;
    @JsonFormat(pattern="yyyy-MM-dd")
	private Date date;
	private String status;

	public OrderDTO() {
		super();
	}

	public OrderDTO(long id, long customerId, List<CartItemDTO> cart, double totalAmount, Date date, String status) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.cart = cart;
		this.totalAmount = totalAmount;
		this.date = date;
		this.status = status;
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

	public List<CartItemDTO> getCart() {
		return cart;
	}

	public void setCart(List<CartItemDTO> cart) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
