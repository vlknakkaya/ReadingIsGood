package com.readingisgood.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	@ExceptionHandler(value = StockIsNotEnoughException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<ErrorResponse> handleStockIsNotEnoughException(StockIsNotEnoughException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorCodes.STOCK_IS_NOT_ENOUGH);
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setDate(new Date());

		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorCodes.VALIDATION_ERROR);
		errorResponse.setErrorMessage("[Validation Error] " + ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining("; ")));
		errorResponse.setDate(new Date());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorCodes.BAD_CREDENTIALS);
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setDate(new Date());

		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorCodes.VALIDATION_ERROR);
		errorResponse.setErrorMessage("[Validation Error] " + ex.getMessage());
		errorResponse.setDate(new Date());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
