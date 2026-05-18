package com.spring.demo.Exceptions;

import com.spring.demo.Constants.ExceptionType;

public class JournalEntryNotFoundException extends RuntimeException {

	public JournalEntryNotFoundException() {
		super(ExceptionType.ENTRYNOTFOUND.getMessage());
	}

	public JournalEntryNotFoundException(String message) {
		super(message);

	}

}
