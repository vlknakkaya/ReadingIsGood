package com.readingisgood.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.model.converter.BookDTOConverter;
import com.readingisgood.model.dto.BookDTO;
import com.readingisgood.model.dto.BookStockDTO;
import com.readingisgood.model.entity.Book;
import com.readingisgood.model.validator.BookDTOValidator;
import com.readingisgood.model.validator.BookStockDTOValidator;
import com.readingisgood.service.BookService;

@RestController
@RequestMapping("/book")
@Validated
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private BookDTOConverter bookDTOConverter;
	@Autowired
	private BookDTOValidator bookDTOValidator;
	@Autowired
	private BookStockDTOValidator bookStockDTOValidator;

	@InitBinder(value = "bookDTO")
	void initBookDTOValidator(WebDataBinder binder) {
		binder.setValidator(bookDTOValidator);
	}

	@InitBinder(value = "bookStockDTO")
	void initBookStockDTOValidator(WebDataBinder binder) {
		binder.setValidator(bookStockDTOValidator);
	}

	/**
	 * Returns all Books
	 * 
	 * @return BookDTO list that represents all Books
	 */
	@GetMapping
	public List<BookDTO> getAllBooks() {
		return bookDTOConverter.convertToDTOList(bookService.findAll());
	}

	/**
	 * Returns Book that has given id
	 * 
	 * @param id Book id
	 * @return BookDTO that represents founded Book
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	@GetMapping("/{id}")
	public BookDTO getBookById(@PathVariable @Min(value = 1, message = "id must be greater than 0") long id) {
		return bookDTOConverter.convertToDTO(bookService.findById(id));
	}

	/**
	 * Updates Book that has given id with given Book properties
	 * 
	 * @param id      Book id
	 * @param bookDTO
	 * @return BookDTO that represents updated Book
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	@PutMapping("/{id}")
	public BookDTO updateBook(@PathVariable @Min(value = 1, message = "id must be greater than 0") long id, @RequestBody @Valid BookDTO bookDTO) {
		Book entity = bookService.findById(id);

		if (StringUtils.hasText(bookDTO.getName())) {
			entity.setName(bookDTO.getName());
		}

		if (StringUtils.hasText(String.valueOf(bookDTO.getPrice()))) {
			entity.setPrice(bookDTO.getPrice());
		}

		if (StringUtils.hasText(String.valueOf(bookDTO.getStock()))) {
			entity.setStock(bookDTO.getStock());
		}

		return bookDTOConverter.convertToDTO(bookService.save(entity));
	}

	/**
	 * Creates new Book by given Book properties
	 * 
	 * @param bookDTO
	 * @return BookDTO that represents created Book
	 */
	@PostMapping
	public BookDTO createBook(@RequestBody @Valid BookDTO bookDTO) {
		Book newEntry = bookDTOConverter.convertToEntity(bookDTO);

		return bookDTOConverter.convertToDTO(bookService.save(newEntry));
	}

	/**
	 * Removes Book that has given id
	 * 
	 * @param id Book id
	 */
	@DeleteMapping("/{id}")
	public void removeBookById(@PathVariable @Min(value = 1, message = "id must be greater than 0") long id) {
		bookService.removeById(id);
	}

	/**
	 * Updates Book stocks by given properties
	 * 
	 * @param bookStockDTOs
	 * @return BookDTO list that represents updated Books
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	@PutMapping("/updateStock")
	public List<BookDTO> updateBookStock(@RequestBody @Valid List<BookStockDTO> bookStockDTOs) {
		List<Book> books = new ArrayList<>();
		Book book;

		for (BookStockDTO bookStockDTO : bookStockDTOs) {
			book = bookService.findById(bookStockDTO.getBookId());
			book.increaseStock(bookStockDTO.getStock());
			books.add(book);
		}

		return bookDTOConverter.convertToDTOList(bookService.saveAll(books));
	}

}
