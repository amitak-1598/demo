package com.spring.demo.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spring.demo.Repository.JournalRepository;
import com.spring.demo.Repository.UserRepository;
import com.spring.demo.Service.JournalService;
import com.spring.demo.Service.UserService;
import com.spring.demo.Util.EntryArguments;
import com.spring.demo.entity.Journal;
import com.spring.demo.entity.User;

public class JournalServiceTests {

	@InjectMocks
	private UserService userService;
	@InjectMocks
	private JournalService journalService;

	@Mock
	private UserRepository userRepository;
	@Mock
	private JournalRepository journalRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	@ParameterizedTest
	@ArgumentsSource(EntryArguments.class)
	public void saveEntry(String username, Journal entry) {
		when(journalRepository.save(entry)).thenReturn(entry);
		when(userRepository.findByUsername(username))
				.thenReturn(User.builder().username(username).password("test").roles(new ArrayList<>()).build());
		User user = userRepository.findByUsername(username);
		assertNotNull(user);

	}

}
