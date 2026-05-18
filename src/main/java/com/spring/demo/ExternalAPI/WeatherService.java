package com.spring.demo.ExternalAPI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.demo.DTO.WeatherResponse;

@FeignClient(name = "weather-service",url = "http://localhost")
public interface WeatherService {

	@GetMapping("/weather")
	ResponseEntity<WeatherResponse> getWeather(java.net.URI uri, @RequestParam("q") String city,
			@RequestParam("units") String units, @RequestParam("appid") String appId);

}
