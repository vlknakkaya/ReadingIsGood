package com.readingisgood.model.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.readingisgood.model.dto.BookDTO;
import com.readingisgood.util.ErrorCodes;

@Component
public class BookDTOValidator implements Validator {

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

		if (StringUtils.hasText(String.valueOf(bookDTO.getPrice())) && bookDTO.getPrice() < 0.0) {
			errors.rejectValue("price", ErrorCodes.OUT_OF_LIMIT, "Price can not be lower than 0");
		}

		if (StringUtils.hasText(String.valueOf(bookDTO.getStock())) && bookDTO.getStock() < 0) {
			errors.rejectValue("stock", ErrorCodes.OUT_OF_LIMIT, "Stock can not be lower than 0");
		}
	}

}
