package com.spring.demo.Service;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.demo.entity.User;

public class ArgumentProviderImpl implements ArgumentsProvider {

	@InjectMocks
	PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
		return Stream.of(Arguments.of("niteshsaini1296@gmail.com",
				User.builder().username("Nitesh").password("rawPass1").build())

		);
	}

}
