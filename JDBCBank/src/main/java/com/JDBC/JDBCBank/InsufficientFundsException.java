package com.JDBC.JDBCBank;

public class InsufficientFundsException extends Throwable {

	public InsufficientFundsException(String errorMessage) {
		super(errorMessage);
	}
}
