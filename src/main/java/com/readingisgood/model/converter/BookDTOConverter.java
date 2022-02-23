package com.readingisgood.model.converter;

import org.springframework.stereotype.Component;

import com.readingisgood.model.dto.BookDTO;
import com.readingisgood.model.entity.Book;

@Component
public class BookDTOConverter implements DTOConverter<Book, BookDTO> {

	@Override
	public Book convertToEntity(BookDTO dto) {
		if (dto == null) {
			return null;
		}

		Book entity = new Book();
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setStock(dto.getStock());

		return entity;
	}

	@Override
	public BookDTO convertToDTO(Book entity) {
		if (entity == null) {
			return null;
		}

		BookDTO dto = new BookDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPrice(entity.getPrice());
		dto.setStock(entity.getStock());

		return dto;
	}

}
