package com.readingisgood.model.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.readingisgood.model.dto.AuthRequestDTO;
import com.readingisgood.util.ErrorCodes;

@Component
public class AuthRequestDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AuthRequestDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AuthRequestDTO authRequestDTO = (AuthRequestDTO) target;

		if (!StringUtils.hasText(authRequestDTO.getUsername())) {
			errors.rejectValue("username", ErrorCodes.MUST_BE_GIVEN, "username must be given");
		} else if (authRequestDTO.getUsername().trim().length() == 0) {
			errors.rejectValue("username", ErrorCodes.CANNOT_BE_EMPTY, "username field can not be empty");
		}

		if (!StringUtils.hasText(authRequestDTO.getPassword())) {
			errors.rejectValue("password", ErrorCodes.MUST_BE_GIVEN, "password must be given");
		} else if (authRequestDTO.getPassword().trim().length() == 0) {
			errors.rejectValue("password", ErrorCodes.CANNOT_BE_EMPTY, "password field can not be empty");
		}
	}

}
