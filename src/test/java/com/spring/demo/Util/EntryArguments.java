package com.spring.demo.Util;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.spring.demo.entity.Journal;

public class EntryArguments implements ArgumentsProvider {

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
		return Stream.of(Arguments.of("niteshsaini1296@gmail.com",
				Journal.builder().content("Good Morning").title("Greet").build()));

	}

}
