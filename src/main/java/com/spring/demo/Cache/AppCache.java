package com.spring.demo.Cache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import com.spring.demo.Repository.ConfigRepository;
import com.spring.demo.entity.Config;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

	private ConfigRepository configRepository;

	public AppCache(ConfigRepository configRepository) {
		this.configRepository = configRepository;

	}

	public Map<String, String> appCache;

	@PostConstruct
	public void init() {
		// initilize and reinitialize appCache
		appCache = new HashMap<>();
		// load config from db
		List<Config> configs = configRepository.findAll();
		for (Config config : configs) {
			appCache.put(config.getKey(), config.getValue());
		}

	}

}
