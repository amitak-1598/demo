package com.spring.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.demo.Service.UserService;
import com.spring.demo.entity.User;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UserService userService;

	@PostMapping("/create-user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return new ResponseEntity<>(this.userService.createUser(user), HttpStatus.CREATED);
	}
	@GetMapping("/health")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Server is Healthy", HttpStatus.OK);
	}

}
