package com.spring.demo.Constants;

import lombok.Getter;

@Getter
public enum Keys {
	QUOTE_KEY("quote"),
	WEATHER_KEY("weather_api"),
	REDISWEATHERKEY("weather of ");

	private String key;

	Keys(String key) {
		this.key = key;
	}

}
