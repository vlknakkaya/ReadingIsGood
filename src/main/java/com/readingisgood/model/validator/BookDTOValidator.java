package com.readingisgood.model.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.readingisgood.model.dto.BookDTO;
import com.readingisgood.service.BookService;
import com.readingisgood.util.ErrorCodes;

@Component
public class BookDTOValidator implements Validator {

	@Autowired
	private BookService bookService;

	@Override
	public boolean supports(Class<?> clazz) {
		return BookDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BookDTO bookDTO = (BookDTO) target;

		if (StringUtils.hasText(bookDTO.getName()) && bookDTO.getName().trim().length() == 0) {
			errors.rejectValue("name", ErrorCodes.CANNOT_BE_EMPTY, "Name field can not be empty");
		}

		if (StringUtils.hasText(bookDTO.getName()) && bookService.findByName(bookDTO.getName()) != null) {
			errors.rejectValue("name", ErrorCodes.ALREADY_EXIST, "The book is already exist: " + bookDTO.getName());
		}

		if (StringUtils.hasText(String.valueOf(bookDTO.getPrice())) && bookDTO.getPrice() < 0.0) {
			errors.rejectValue("price", ErrorCodes.OUT_OF_LIMIT, "Price can not be lower than 0");
		}

		if (StringUtils.hasText(String.valueOf(bookDTO.getStock())) && bookDTO.getStock() < 0) {
			errors.rejectValue("stock", ErrorCodes.OUT_OF_LIMIT, "Stock can not be lower than 0");
		}
	}

}
