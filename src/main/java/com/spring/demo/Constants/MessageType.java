package com.spring.demo.Constants;

import lombok.Getter;

@Getter
public enum MessageType {
	USERUPDATED("User has been updated successfully", 200), USERDELETED("User has been deleted successfully", 200),
	USERNOTDELETED("User has not been deleted", 400), ENTRYSAVED("Entry saved successfully", 200),
	ENTRYDELETE("Entry has been deleted successfully", 200), ENTRYFETCHED("Entry fetched from db Successfully.", 200),
	CONFIGSUCCESS("Configuration Loaded Successfully", 200), CONFIGLOADERROR("Error while loading configuration", 400),
	REDISSUCCESS("Response fetched from redis", 200), REDISDBSTATUS("Data Set into Redis Successfully", 200),
	REDISERROR("Error while Storing data into redis", 400),
	REDISFETCHERROR("Error while Fetching data from redis", 400);

	;

	private String message;
	private int status;

	private MessageType(String message, int status) {
		this.message = message;
		this.status = status;

	}

}
