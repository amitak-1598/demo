package com.spring.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.demo.DTO.LoginRequest;
import com.spring.demo.DTO.LoginResponse;
import com.spring.demo.Service.UserDetailServiceImpl;
import com.spring.demo.Service.UserService;
import com.spring.demo.Utility.JwtUtil;
import com.spring.demo.entity.User;

@RestController
@RequestMapping("/public")
public class PublicController {

//	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;

	private AuthenticationManager authenticationManager;

	private JwtUtil jwtUtil;

//	
//
//	@Autowired
	private UserService userService;

	public PublicController(UserDetailServiceImpl userDetailServiceImpl, UserService userService,
			AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.userDetailServiceImpl = userDetailServiceImpl;
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;

	}

	@PostMapping("/create-user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return new ResponseEntity<>(this.userService.createUser(user), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			UserDetails userdetails = userDetailServiceImpl.loadUserByUsername(loginRequest.getUsername());
			String token = jwtUtil.generateToken(userdetails.getUsername());
			return ResponseEntity.status(HttpStatus.OK).body(LoginResponse.builder().token(token).status(HttpStatus.OK).build());
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or Password is not correct");

	}

	@GetMapping("/health")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Server is Healthy", HttpStatus.OK);
	}

}
