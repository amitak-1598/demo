package com.spring.demo.ServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	void redisTest() {
		redisTemplate.opsForValue().set("test", "Nitesh Kumar Saini");

	}

}
