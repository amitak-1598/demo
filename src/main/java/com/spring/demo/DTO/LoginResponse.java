package com.spring.demo.DTO;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponse {
	private String token;
	private HttpStatus status;

}
