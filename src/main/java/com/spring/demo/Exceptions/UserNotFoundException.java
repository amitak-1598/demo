package com.spring.demo.Exceptions;

import com.spring.demo.Constants.ExceptionType;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException() {
		super(ExceptionType.USERNOTFOUND.getMessage());

	}

	public UserNotFoundException(String message) {
		super(message);
	}

}
