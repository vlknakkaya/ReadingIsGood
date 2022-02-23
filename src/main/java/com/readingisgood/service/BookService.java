package com.readingisgood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readingisgood.exception.EntityNotFoundException;
import com.readingisgood.model.entity.Book;
import com.readingisgood.repository.BookRepository;

@Service
public class BookService {

	private BookRepository bookRepository;

	@Autowired
	public BookService(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	public Book findById(long id) {
		return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book", "id", id));
	}

	public Book findByName(String name) {
		return bookRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Book", "name", name));
	}

	public List<Book> findByPrice(double price) {
		return bookRepository.findByPrice(price);
	}

	public List<Book> findByPriceBetween(double min, double max) {
		return bookRepository.findByPriceBetween(min, max);
	}

	public List<Book> findByStock(long stock) {
		return bookRepository.findByStock(stock);
	}

	public List<Book> findByStockBetween(long min, long max) {
		return bookRepository.findByStockBetween(min, max);
	}

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public Book save(Book book) {
		return bookRepository.save(book);
	}

	public List<Book> saveAll(List<Book> books) {
		return bookRepository.saveAll(books);
	}

	public void removeById(long id) {
		bookRepository.deleteById(id);
	}

}
