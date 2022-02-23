package com.readingisgood.model.entity;

import java.sql.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToMany
	@JoinTable(name = "customer_orders", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
	private Customer customer;

	private Map<Book, Integer> cart;

	private double totalAmount;
	
	private Date date;

	public Order() {
		super();
	}

	public Order(Customer customer, Map<Book, Integer> cart, double totalAmount, Date date) {
		super();
		this.customer = customer;
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

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", cart=" + cart + ", totalAmount=" + totalAmount
				+ ", date=" + date + "]";
	}

}
