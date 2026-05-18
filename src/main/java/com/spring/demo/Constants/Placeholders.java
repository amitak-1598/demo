package com.spring.demo.Constants;

import lombok.Getter;

@Getter
public enum Placeholders {
	ALL("<all>", "all"), JAVASCRIPT("<javascript>", "javascript");
	private String key;
	private String value;

	private Placeholders(String key, String value) {
		this.key = key;
		this.value = value;
	}

}
