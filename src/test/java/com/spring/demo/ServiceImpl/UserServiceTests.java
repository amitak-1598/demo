package com.spring.demo.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.spring.demo.Repository.UserRepository;
import com.spring.demo.Service.ArgumentProviderImpl;
import com.spring.demo.Service.UserService;
import com.spring.demo.entity.User;

public class UserServiceTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public User userCreateTest() {
		// create user
		User user = User.builder().username("niteshsaini1296@gmail.com").password("test").build();
		when(userRepository.save(user)).thenReturn(user);
		User response = userService.createUser(user);
		Assertions.assertNotNull(response);
		return user;
	}

	@Disabled
	@Test
	public void getAllUsers() {
		List<User> users = new ArrayList<>();
		User user1 = userCreateTest();
		User user2 = userCreateTest();
		users.add(user1);
		users.add(user2);

		when(userRepository.findAll()).thenReturn(users);
		List<User> returnUsers = userService.getAllUsers();
		Assertions.assertNotNull(returnUsers);
	}

	@ParameterizedTest
	@ValueSource(strings = { "niteshsaini1296@gmail.com" })
	public void getUserByUsername(String username) {
		when(userRepository.findByUsername(username))
				.thenReturn(User.builder().username(username).password("test").roles(new ArrayList<>()).build());
		User user = userService.getUser(username);
		Assertions.assertNotNull(user);

	}

	@ParameterizedTest
	@ValueSource(strings = { "69e3619472351720ffb12b1d" })
	public void getUserById(String id) {
		ObjectId objectId = new ObjectId(id);
		List<String> roles = new ArrayList<>();
		roles.add("USER");

		when(userRepository.findById(objectId)).thenReturn(Optional
				.of(User.builder().username("niteshsaini1296@gmail.com").password("test").roles(roles).build()));
		User user = userService.getUserById(objectId);
		Assertions.assertNotNull(user);

	}

	@ParameterizedTest
	@ArgumentsSource(ArgumentProviderImpl.class)
	public void updateUser(String username, User newUserDetail) {
		when(userRepository.findByUsername(username))
				.thenReturn(User.builder().username(username).password("test").roles(new ArrayList<>()).build());
		boolean response = userService.updateUser(username, newUserDetail);
		assertTrue(response);

	}

	@ParameterizedTest
	@ValueSource(strings = "niteshsaini1296@gmail.com")
	public void deleteUserByUsername(String username) {
		when(userRepository.deleteByUsername(username)).thenReturn(true);
		boolean response = userService.deleteUser(username);
		assertTrue(response);

	}

}
