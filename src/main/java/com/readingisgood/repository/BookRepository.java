package com.readingisgood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingisgood.model.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByName(String name);

	List<Book> findByPrice(double price);

	List<Book> findByPriceBetween(double min, double max);

	List<Book> findByStock(long stock);

	List<Book> findByStockBetween(long min, long max);

}
