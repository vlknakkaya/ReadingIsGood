package com.readingisgood.model.entity;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.readingisgood.util.OrderStatus;

@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ElementCollection
	private Map<Book, Integer> cart = new HashMap<>();

	@Column(nullable = false)
	private double totalAmount = 0.0;

    @JsonFormat(pattern="yyyy-MM-dd")
	@Column(nullable = false)
	private Date date;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status = OrderStatus.PREPARING;

	public Order() {
		super();
	}

	public Order(Customer customer, Map<Book, Integer> cart, Date date, OrderStatus status) {
		super();
		this.customer = customer;
		this.cart = cart;
		this.date = date;
		this.status = status;
		calculateTotalAmount();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Map<Book, Integer> getCart() {
		return cart;
	}

	public void setCart(Map<Book, Integer> cart) {
		this.cart = cart;
		calculateTotalAmount();
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

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	private void calculateTotalAmount() {
		double totalAmountVal = 0;
		
		for (Map.Entry<Book, Integer> entry : this.cart.entrySet()) {
			totalAmountVal += entry.getKey().getPrice() * entry.getValue();
		}
		
		this.totalAmount = totalAmountVal;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", cart=" + cart + ", totalAmount=" + totalAmount
				+ ", date=" + date + ", status=" + status.getStatusText() + "]";
	}

}
