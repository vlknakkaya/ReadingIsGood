package com.readingisgood.model.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.readingisgood.model.dto.BookStockDTO;
import com.readingisgood.util.ErrorCodes;

@Component
public class BookStockDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return BookStockDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BookStockDTO bookStockDTO = (BookStockDTO) target;

		if (!StringUtils.hasText(String.valueOf(bookStockDTO.getBookId()))) {
			errors.rejectValue("bookId", ErrorCodes.MUST_BE_GIVEN, "Book ID must be given");
		} else if (bookStockDTO.getBookId() < 1) {
			errors.rejectValue("bookId", ErrorCodes.OUT_OF_LIMIT, "Book ID can not be lower than 1");
		}

		if (!StringUtils.hasText(String.valueOf(bookStockDTO.getStock()))) {
			errors.rejectValue("stock", ErrorCodes.MUST_BE_GIVEN, "Stock must be given");
		} else if (bookStockDTO.getStock() < 1) {
			errors.rejectValue("stock", ErrorCodes.OUT_OF_LIMIT, "Stock can not be lower than 1");
		}
	}

}
