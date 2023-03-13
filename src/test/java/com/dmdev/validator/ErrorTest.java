package com.dmdev.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorTest {

	@Test
	void testError() {
		Error error = Error.of("code", "message");
		Assertions.assertThat(error.getCode()).isEqualTo("code");
		Assertions.assertThat(error.getMessage()).isEqualTo("message");
	}

}
