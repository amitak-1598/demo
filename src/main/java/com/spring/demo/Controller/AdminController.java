package com.spring.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.demo.Service.UserService;
import com.spring.demo.entity.User;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/get-users")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
	}
}
