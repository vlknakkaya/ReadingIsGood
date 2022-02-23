package com.readingisgood.exception;

import java.util.Date;

public class ErrorResponse {

	private int errorCode;
	private String errorMessage;
	private Date date;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ErrorResponse [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", date=" + date + "]";
	}

}
