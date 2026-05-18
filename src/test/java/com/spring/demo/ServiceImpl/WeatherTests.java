package com.spring.demo.ServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.demo.ExternalAPI.WeatherServiceImpl;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class WeatherTests {

	@Autowired
	private WeatherServiceImpl weatherServiceImpl;

	@Test
	void getWeather() {
		var response = weatherServiceImpl.getWeather("delhi");
		log.info("weather response \n {}", response);

	}

}
