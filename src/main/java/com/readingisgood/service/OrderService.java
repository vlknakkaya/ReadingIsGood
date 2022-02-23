package com.readingisgood.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readingisgood.exception.EntityNotFoundException;
import com.readingisgood.model.entity.Order;
import com.readingisgood.repository.OrderRepository;
import com.readingisgood.util.OrderStatus;

@Service
public class OrderService {

	private OrderRepository orderRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	public Order findById(long id) {
		return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order", "id", id));
	}

	public List<Order> findByCustomerId(long customerId) {
		return orderRepository.findByCustomerId(customerId);
	}

	public List<Order> findByTotalAmount(double totalAmount) {
		return orderRepository.findByTotalAmount(totalAmount);
	}

	public List<Order> findByTotalAmountBetween(double min, double max) {
		return orderRepository.findByTotalAmountBetween(min, max);
	}

	public List<Order> findByDate(Date date) {
		return orderRepository.findByDate(date);
	}

	public List<Order> findByDateBetween(Date startDate, Date endDate) {
		return orderRepository.findByDateBetween(startDate, endDate);
	}

	public List<Order> findByStatus(OrderStatus status) {
		return orderRepository.findByStatus(status);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	public void removeById(long id) {
		orderRepository.deleteById(id);
	}

}
