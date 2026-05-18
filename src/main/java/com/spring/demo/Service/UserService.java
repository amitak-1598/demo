package com.spring.demo.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.demo.Constants.ExceptionType;
import com.spring.demo.Constants.MessageType;
import com.spring.demo.Exceptions.UserNotCreatedException;
import com.spring.demo.Exceptions.UserNotFoundException;
import com.spring.demo.Repository.UserRepository;
import com.spring.demo.entity.User;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER"));
		try {
			User createdUser = userRepository.save(user);
			if (createdUser != null) {
				log.info("User Created Successfully.");
				return user;
			}
		} catch (Exception e) {
			log.error(ExceptionType.USERNOTCREATED.getMessage(), e);

		}
		throw new UserNotCreatedException();

	}

	public List<User> getAllUsers() {
		return userRepository.findAll();

	}

	public User getUser(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			return user;

		}
		log.error(ExceptionType.USERNOTFOUND.getMessage());
		throw new UserNotFoundException();

	}

	public User getUserById(ObjectId id) {
		Optional<User> users = userRepository.findById(id);
		if (users.isPresent()) {
			return users.get();
		}
		log.error(ExceptionType.USERNOTFOUND.getMessage());
		throw new UserNotFoundException();

	}

	public boolean updateUser(String username, User newUserData) {
		// get user
		User user = userRepository.findByUsername(username);
		if (user != null) {
			// user found
			user.setUsername(!newUserData.getUsername().equals("") ? newUserData.getUsername() : user.getUsername());
			user.setPassword(!newUserData.getPassword().equals("") ? passwordEncoder.encode(newUserData.getPassword())
					: user.getPassword());

			// update the user
			userRepository.save(user);
			log.info(MessageType.USERUPDATED.getMessage());
			return true;
		} else {
			throw new UserNotFoundException();
		}

	}

	public boolean deleteUser(String username) {
		if (this.userRepository.deleteByUsername(username)) {
			log.info(MessageType.USERDELETED.getMessage());
			return true;

		}
		log.error(MessageType.USERNOTDELETED.getMessage());
		return false;

	}

}
