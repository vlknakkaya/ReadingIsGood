package com.readingisgood.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.readingisgood.util.ErrorCodes;

@RestControllerAdvice
public class ExceptionAdvise {

	@ExceptionHandler(value = EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorCodes.ENTITY_NOT_FOUND);
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setDate(new Date());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorCodes.DATA_INTEGRITY_ERROR);
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setDate(new Date());

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorCodes.ENTITY_NOT_FOUND);
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setDate(new Date());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

}
