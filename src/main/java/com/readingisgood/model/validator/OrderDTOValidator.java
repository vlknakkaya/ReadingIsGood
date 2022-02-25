package com.readingisgood.model.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.readingisgood.model.dto.OrderDTO;
import com.readingisgood.util.ErrorCodes;

@Component
public class OrderDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return OrderDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		OrderDTO orderDTO = (OrderDTO) target;
		
		if (!StringUtils.hasText(String.valueOf(orderDTO.getCustomerId()))) {
			errors.rejectValue("customerId", ErrorCodes.MUST_BE_GIVEN, "Customer ID must be given");
		} else if (orderDTO.getCustomerId() < 0) {
			errors.rejectValue("customerId", ErrorCodes.OUT_OF_LIMIT, "Customer ID can not be lower than 0");
		}
		
		if (ObjectUtils.isEmpty(orderDTO.getCart())) {
			errors.rejectValue("cart", ErrorCodes.MUST_BE_GIVEN, "Cart must be given");
		}
		
		if (!StringUtils.hasText(String.valueOf(orderDTO.getDate()))) {
			errors.rejectValue("date", ErrorCodes.MUST_BE_GIVEN, "Date must be given");
		}
		
	}

}
