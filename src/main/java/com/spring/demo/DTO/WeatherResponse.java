package com.spring.demo.DTO;

import java.util.List;

import lombok.Data;

@Data
public class WeatherResponse {

	private List<Weather> weather;
	private Main main;

	@Data
	public static class Weather {
		private String description;
	}

	@Data
	public static class Main {
		private double temp;
	}
}