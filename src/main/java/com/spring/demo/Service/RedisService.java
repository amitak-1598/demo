package com.spring.demo.Service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.spring.demo.Constants.MessageType;
import com.spring.demo.DTO.WeatherResponse;
import com.spring.demo.DTO.WeatherResponse.Weather;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class RedisService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public <T> T getDetails(String key, Class<T> entityClass) {
		String response = redisTemplate.opsForValue().get(key);
		if (response == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response, entityClass);

	}

	public boolean setDetails(Object object, String key) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String weatherValue = mapper.writeValueAsString(object);
			redisTemplate.opsForValue().set(key, weatherValue, 300, TimeUnit.SECONDS);
			log.info(MessageType.REDISDBSTATUS.getMessage());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.error(MessageType.REDISERROR.getMessage());
		return false;
	}

}
