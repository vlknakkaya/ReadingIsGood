package com.readingisgood.controller.rest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingisgood.exception.EntityNotFoundException;
import com.readingisgood.model.converter.BookDTOConverter;
import com.readingisgood.model.dto.BookDTO;
import com.readingisgood.model.dto.BookStockDTO;
import com.readingisgood.model.entity.Book;
import com.readingisgood.service.BookService;
import com.readingisgood.util.ErrorCodes;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private BookService bookService;
	@Autowired
	private BookDTOConverter bookDTOConverter;
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	private static final String PATH = "/book";
	
	/**
	 * Tests BookController.getAllBooks() method
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetAllBooks() throws Exception {
		this.mockMvc.perform(get(PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	/**
	 * Tests BookController.getBookById(long) method with valid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetBookByValidId() throws Exception {
		List<Book> entities = bookService.findAll();
		if (!entities.isEmpty()) {
			Book entity = entities.get(0);
			
			this.mockMvc.perform(get(PATH + "/" + entity.getId()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.id").value(entity.getId()))
					.andExpect(jsonPath("$.name").value(entity.getName()))
					.andExpect(jsonPath("$.price").value(entity.getPrice()))
					.andExpect(jsonPath("$.stock").value(entity.getStock()));
		}
		
		entities = null;
	}

	/**
	 * Tests BookController.getBookById(long) method with invalid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetBookByInvalidId() throws Exception {
		long id = Long.MAX_VALUE;
		
		this.mockMvc.perform(get(PATH + "/" + id))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(ErrorCodes.ENTITY_NOT_FOUND));
	}
	
	/**
	 * Tests BookController.updateBook(long, BookDTO) method
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateBook() throws Exception {
		BookDTO dto = new BookDTO(0, "test_book", 5, 10);
		Book entity = bookService.save(bookDTOConverter.convertToEntity(dto));
		
		dto.setName("new_test_book_name");
		
		this.mockMvc.perform(put(PATH + "/" + entity.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value(dto.getName()));
		
		bookService.removeById(entity.getId());
	}
	
	/**
	 * Tests BookController.createBook(BookDTO) method
	 * 
	 * @throws Exception
	 */
	@Test
	void testCreateBook() throws Exception {
		BookDTO dto = new BookDTO(0, "test_book", 5, 10);
		
		MvcResult result = this.mockMvc.perform(post(PATH)
									.contentType(MediaType.APPLICATION_JSON)
									.content(mapper.writeValueAsString(dto)))
									.andExpect(status().isOk())
									.andExpect(content().contentType(MediaType.APPLICATION_JSON))
									.andExpect(jsonPath("$.name").value(dto.getName()))
									.andExpect(jsonPath("$.price").value(dto.getPrice()))
									.andExpect(jsonPath("$.stock").value(dto.getStock()))
									.andReturn();
		
		bookService.removeById(mapper.readValue(result.getResponse().getContentAsString(), Book.class).getId());
	}

	/**
	 * Tests BookController.removeBookById(long) method with valid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testRemoveBookByValidId() throws Exception {
		BookDTO dto = new BookDTO(0, "test_book", 5, 10);
		Book entity = bookService.save(bookDTOConverter.convertToEntity(dto));

		this.mockMvc.perform(delete(PATH + "/" + entity.getId()))
				.andExpect(status().isOk());
		
		assertThrows(EntityNotFoundException.class, () -> bookService.findById(entity.getId()));
	}

	/**
	 * Tests BookController.removeBookById(long) method with invalid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testRemoveBookByInvalidId() throws Exception {
		long id = Long.MAX_VALUE;
		
		this.mockMvc.perform(delete(PATH + "/" + id))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(ErrorCodes.ENTITY_NOT_FOUND));
	}

	/**
	 * Tests BookController.updateBookStock(List<BookStockDTO>) method with valid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateBookStockWithValidId() throws Exception {
		List<Book> entities = bookService.findAll();
		if (!entities.isEmpty()) {
			Book entity = entities.get(0);
			BookStockDTO bookStockDTO = new BookStockDTO(entity.getId(), 50);
			List<BookStockDTO> bookStockDTOs = new ArrayList<>();
			bookStockDTOs.add(bookStockDTO);
			
			this.mockMvc.perform(put(PATH + "/updateStock")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(bookStockDTOs)))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.[0].id").value(entity.getId()))
					.andExpect(jsonPath("$.[0].stock").value(entity.getStock() + bookStockDTO.getStock()));
		}
		
		entities = null;
	}

	/**
	 * Tests BookController.updateBookStock(List<BookStockDTO>) method with invalid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateBookStockWithInvalidId() throws Exception {
		long id = Long.MAX_VALUE;
		BookStockDTO bookStockDTO = new BookStockDTO(id, 50);
		List<BookStockDTO> bookStockDTOs = new ArrayList<>();
		bookStockDTOs.add(bookStockDTO);

		this.mockMvc.perform(put(PATH + "/updateStock")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(bookStockDTOs)))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(ErrorCodes.ENTITY_NOT_FOUND));
	}
	
}
