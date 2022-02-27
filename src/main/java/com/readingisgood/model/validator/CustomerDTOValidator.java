package com.readingisgood.model.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.readingisgood.model.dto.CustomerDTO;
import com.readingisgood.model.entity.Customer;
import com.readingisgood.service.CustomerService;
import com.readingisgood.util.ErrorCodes;

@Component
public class CustomerDTOValidator implements Validator {

	@Autowired
	private CustomerService customerService;

	@Override
	public boolean supports(Class<?> clazz) {
		return CustomerDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CustomerDTO customerDTO = (CustomerDTO) target;

		if (StringUtils.hasText(customerDTO.getEmail())) {
			try {
				Customer entity = customerService.findByEmail(customerDTO.getEmail());

				if (entity != null && !customerDTO.getEmail().equals(entity.getEmail())) {
					errors.rejectValue("email", ErrorCodes.ALREADY_EXIST,
							"The customer is already exist: " + customerDTO.getEmail());
				}
			} catch (Exception e) {
				// there is no need an action
			}

			if (!customerDTO.getEmail().contains("@")) {
				errors.rejectValue("email", ErrorCodes.NEEDED_SPECIAL_CHARACTER,
						"Email should has '@' character: " + customerDTO.getEmail());
			}
		}

		if (StringUtils.hasText(customerDTO.getName()) && customerDTO.getName().trim().length() == 0) {
			errors.rejectValue("name", ErrorCodes.CANNOT_BE_EMPTY, "Name field can not be empty");
		}

		if (StringUtils.hasText(customerDTO.getAddress()) && customerDTO.getAddress().trim().length() == 0) {
			errors.rejectValue("address", ErrorCodes.CANNOT_BE_EMPTY, "Address field can not be empty");
		}

	}

}
