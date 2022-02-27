package com.readingisgood.util;

public final class ErrorCodes {

	private ErrorCodes() {
	}

	public static final int ENTITY_NOT_FOUND = 101;
	public static final int DATA_INTEGRITY_ERROR = 102;
	public static final int STOCK_IS_NOT_ENOUGH = 103;
	public static final int VALIDATION_ERROR = 104;
	public static final int BAD_CREDENTIALS = 105;

	public static final String MUST_BE_GIVEN = "201";
	public static final String CANNOT_BE_EMPTY = "202";
	public static final String ALREADY_EXIST = "203";
	public static final String OUT_OF_LIMIT = "204";
	public static final String NEEDED_SPECIAL_CHARACTER = "205";

}
