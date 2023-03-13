package com.dmdev.validator;

import static com.dmdev.integration.util.TestObjectUtils.IVAN;

import com.dmdev.dto.CreateUserDto;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CreateUserValidatorTest {

	private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

	@ParameterizedTest
	@MethodSource("getArgumentsForValidateTest")
	void validate(CreateUserDto dto, boolean result) {
		ValidationResult validationResult = createUserValidator.validate(dto);
		Assertions.assertThat(validationResult.isValid()).isEqualTo(result);
	}

	static Stream<Arguments> getArgumentsForValidateTest() {
		return Stream.of(
				Arguments.of(CreateUserDto.builder()
						.name(IVAN.getName())
						.birthday(IVAN.getBirthday().toString())
						.email(IVAN.getEmail())
						.gender(IVAN.getGender().toString())
						.role(IVAN.getRole().toString())
						.password(IVAN.getPassword())
						.build(), true),
				Arguments.of(CreateUserDto.builder()
						.name(IVAN.getName())
						.birthday("dummy")
						.email(IVAN.getEmail())
						.gender(IVAN.getGender().toString())
						.role(IVAN.getRole().toString())
						.password(IVAN.getPassword())
						.build(), false),
				Arguments.of(CreateUserDto.builder()
						.name(IVAN.getName())
						.birthday(IVAN.getBirthday().toString())
						.email(IVAN.getEmail())
						.gender("dummy")
						.role(IVAN.getRole().toString())
						.password(IVAN.getPassword())
						.build(), false),
				Arguments.of(CreateUserDto.builder()
						.name(IVAN.getName())
						.birthday(IVAN.getBirthday().toString())
						.email(IVAN.getEmail())
						.gender(IVAN.getGender().toString())
						.role("dummy")
						.password(IVAN.getPassword())
						.build(), false)
		);
	}

}
