package com.readingisgood.model.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.readingisgood.model.dto.CartItemDTO;
import com.readingisgood.util.ErrorCodes;

@Component
public class CartItemDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CartItemDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CartItemDTO cartItemDTO = (CartItemDTO) target;

		if (!StringUtils.hasText(String.valueOf(cartItemDTO.getBookId()))) {
			errors.rejectValue("bookId", ErrorCodes.MUST_BE_GIVEN, "Book ID must be given");
		} else if (cartItemDTO.getBookId() < 1) {
			errors.rejectValue("bookId", ErrorCodes.OUT_OF_LIMIT, "Book ID can not be lower than 1");
		}

		if (!StringUtils.hasText(String.valueOf(cartItemDTO.getCount()))) {
			errors.rejectValue("count", ErrorCodes.MUST_BE_GIVEN, "Count must be given");
		} else if (cartItemDTO.getCount() < 1) {
			errors.rejectValue("count", ErrorCodes.OUT_OF_LIMIT, "Count can not be lower than 1");
		}
	}

}
