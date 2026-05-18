package com.spring.demo.ExternalAPI;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.demo.Cache.AppCache;
import com.spring.demo.Constants.ExceptionType;
import com.spring.demo.Constants.Keys;
import com.spring.demo.Constants.MessageType;
import com.spring.demo.DTO.WeatherResponse;
import com.spring.demo.Service.RedisService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeatherServiceImpl {

	@Value("${weatherAppId}")
	private String appId;
	private WeatherService weatherService;
	private AppCache appCache;
	private RedisService weatherRedisService;

	public WeatherServiceImpl(WeatherService weatherService, AppCache appCache, RedisService weatherRedisService) {
		this.appCache = appCache;
		this.weatherService = weatherService;
		this.weatherRedisService = weatherRedisService;

	}

	public ResponseEntity<?> getWeather(String city) {
		// get data from cache
		WeatherResponse weather = weatherRedisService.getDetails(Keys.REDISWEATHERKEY.getKey().concat(city),WeatherResponse.class);
		if (weather != null) {
			log.info(MessageType.REDISSUCCESS.getMessage());
			return new ResponseEntity<>(weather, HttpStatus.OK);
		}
		String finalUrl = appCache.appCache.get(Keys.WEATHER_KEY.getKey());
		System.out.println("finalUr " +finalUrl);
		if (finalUrl == null || finalUrl.isBlank()) {
			throw new RuntimeException(ExceptionType.URLNOTFOUND.getMessage());
		}
		var response = weatherService.getWeather(URI.create(finalUrl), city, "metric", appId);
		// set into redis
		weatherRedisService.setDetails(response.getBody(), Keys.REDISWEATHERKEY.getKey().concat(city));
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

}
