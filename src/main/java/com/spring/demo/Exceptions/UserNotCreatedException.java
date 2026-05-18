package com.spring.demo.Exceptions;

import com.spring.demo.Constants.ExceptionType;

public class UserNotCreatedException extends RuntimeException {
	public UserNotCreatedException() {
		super(ExceptionType.USERNOTCREATED.getMessage());
	}

}
