package com.spring.demo.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ExceptionType {
	USERNOTFOUND("User not found with the given id", 404),
	ENTRYNOTFOUND("No Entry is found with the given id", 404),
	USERNOTCREATED("User not created in DB", 404),
	URLNOTFOUND("Url is not null or empty", 404);
	

	ExceptionType(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;

	}

	private final String message;
	private final int statusCode;

}
