package com.spring.demo.ExternalAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.spring.demo.Cache.AppCache;
import com.spring.demo.Constants.Keys;
import com.spring.demo.Constants.Placeholders;
import com.spring.demo.DTO.Quote;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QuoteService {
	@Value("${qoute.api.key}")
	public String apiKey;
	private static final String HOST = "quotes-api12.p.rapidapi.com";
	private RestTemplate restTemplate;
	private AppCache appCache;
	public QuoteService(RestTemplate restTemplate, AppCache appCache) {
		this.restTemplate = restTemplate;
		this.appCache = appCache;
	}

	public ResponseEntity<Quote> getQuote() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-rapidapi-host", HOST);
		headers.add("x-rapidapi-key", apiKey);
		String finalAPI = appCache.appCache.get(Keys.QUOTE_KEY.getKey())
				.replace(Placeholders.ALL.getKey(), Placeholders.ALL.getValue())
				.replace(Placeholders.JAVASCRIPT.getKey(), Placeholders.JAVASCRIPT.getValue());
		// wrap into entity
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(finalAPI, HttpMethod.GET, entity, Quote.class);

	}

}
