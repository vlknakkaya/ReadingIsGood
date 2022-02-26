package com.readingisgood.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.readingisgood.exception.StockIsNotEnoughException;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	private long stock;

	public Book() {
		super();
	}

	public Book(String name, double price, long stock) {
		super();
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public synchronized void increaseStock(long count) {
		this.stock += count;
	}

	public synchronized void decreaseStock(long count) {
		if (this.stock < count) {
			throw new StockIsNotEnoughException(this.id, this.stock, count);
		} else {
			this.stock -= count;
		}
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", price=" + price + ", stock=" + stock + "]";
	}

}
