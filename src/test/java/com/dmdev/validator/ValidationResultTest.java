package com.dmdev.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidationResultTest {

	private ValidationResult validationResult;

	@BeforeEach
	void prepare() {
		validationResult = new ValidationResult();
	}

	@Test
	void isValid() {
		Assertions.assertThat(validationResult.isValid())
				.isTrue();
	}

	@Test
	void isNotValid() {
		validationResult.add(Error.of("invalid", "dummy"));
		Assertions.assertThat(validationResult.isValid())
				.isFalse();
	}

}
