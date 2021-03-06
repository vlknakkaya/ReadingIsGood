package com.readingisgood.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.readingisgood.model.entity.Order;
import com.readingisgood.util.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByCustomerId(long customerId);
	
	Page<Order> findByCustomerId(long customerId, Pageable pageable);
	
	List<Order> findByTotalAmount(double totalAmount);

	List<Order> findByTotalAmountBetween(double min, double max);
	
	List<Order> findByDate(Date date);

	List<Order> findByDateBetween(Date startDate, Date endDate);
	
	List<Order> findByStatus(OrderStatus status);
	
}
