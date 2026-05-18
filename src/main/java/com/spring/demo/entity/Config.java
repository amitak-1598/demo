package com.spring.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document("journal_config")
@Getter
@Setter
public class Config {
	private String key;
	private String value;

}
