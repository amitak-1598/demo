

package com.spring.demo.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.demo.DTO.Quote;
import com.spring.demo.DTO.WeatherResponse;
import com.spring.demo.ExternalAPI.QuoteService;
import com.spring.demo.ExternalAPI.WeatherServiceImpl;
import com.spring.demo.Service.UserService;
import com.spring.demo.entity.User;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

	private UserService userService;
	private QuoteService quoteService;
	private WeatherServiceImpl weatherService;

	public UserController(UserService userService, QuoteService quoteService, WeatherServiceImpl weatherService) {
		this.userService = userService;
		this.quoteService = quoteService;
		this.weatherService = weatherService;

	}

	@GetMapping
	public ResponseEntity<?> getUser() {
		// get user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);

	}

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		// get user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		this.userService.updateUser(username, user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUserByUsername() {
		// get user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		this.userService.deleteUser(username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@GetMapping("/welcome")
	public ResponseEntity<?> welcome() {
		ResponseEntity<Quote> qoute = quoteService.getQuote();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		String message = "Hi " + username + ", \n" + qoute.getBody().getJoke();
		return new ResponseEntity<>(message, qoute.getStatusCode());
	}

	@GetMapping("/weather")
	public ResponseEntity<?> getWeather(@RequestParam String city) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		var response = weatherService.getWeather(city);
		WeatherResponse weather = (WeatherResponse) response.getBody();
		String message = "Hii, " + username + ", its feels like " + weather.getWeather().get(0).getDescription()
				+ " with temperature " + weather.getMain().getTemp()+" in "+city;
		Map<String, String> sendResponse = new HashMap<>();
		sendResponse.put("message", message);
		sendResponse.put("status", response.getStatusCode().toString());
		return new ResponseEntity<>(sendResponse, response.getStatusCode());

	}

}
