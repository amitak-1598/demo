package com.spring.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import com.spring.demo.Cache.AppCache;
import com.spring.demo.Constants.MessageConstants;
import com.spring.demo.Constants.MessageType;

@RestController
@RequestMapping("/utility")
public class UtilityController {
	private AppCache appCache;

	public UtilityController(AppCache appCache) {
		this.appCache = appCache;

	}

	@GetMapping("/load/config")
	public ResponseEntity<Map<String, String>> loadConfig() {
		appCache.init();
		Map<String, String> response = new HashMap<>();
		response.put(MessageConstants.MESSAGE, MessageType.CONFIGSUCCESS.getMessage());
		response.put(MessageConstants.STATUS, MessageType.CONFIGSUCCESS.getStatus() + "");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
