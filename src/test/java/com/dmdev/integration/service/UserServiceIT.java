package com.dmdev.integration.service;

import static com.dmdev.integration.util.TestObjectUtils.IVAN;
import static org.junit.jupiter.api.Assertions.*;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.dto.UserDto;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.exception.ValidationException;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.service.UserService;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class UserServiceIT extends IntegrationTestBase {

	private UserService userService;

	@BeforeEach
	void prepare() {
		userService = new UserService();
	}

	@Test
	void login() {
		Assertions.assertThat(userService.login(IVAN.getEmail(), IVAN.getPassword())).isPresent();
		Assertions.assertThat(userService.login(IVAN.getEmail(), "dummy")).isEmpty();
		Assertions.assertThat(userService.login("dummy", IVAN.getPassword())).isEmpty();

	}

	@Test
	void create() {
		UserDto userDto = UserDto.builder()
				.name("Ivan")
				.birthday(LocalDate.parse("1990-01-10"))
				.email("ivan_new@gmail.com")
				.gender(Gender.MALE)
				.role(Role.ADMIN)
				.build();

		CreateUserDto createUserDto = CreateUserDto.builder()
				.name("Ivan")
				.birthday("1990-01-10")
				.email("ivan_new@gmail.com")
				.gender(Gender.MALE.toString())
				.role(Role.ADMIN.toString())
				.password("111")
				.build();

		UserDto newUserDto = userService.create(createUserDto);

		Assertions.assertThat(userDto.getName()).isEqualTo(newUserDto.getName());
		Assertions.assertThat(userDto.getBirthday()).isEqualTo(newUserDto.getBirthday());
		Assertions.assertThat(userDto.getEmail()).isEqualTo(newUserDto.getEmail());
		Assertions.assertThat(userDto.getGender()).isEqualTo(newUserDto.getGender());
		Assertions.assertThat(userDto.getRole()).isEqualTo(newUserDto.getRole());
	}

	@Test
	@DisplayName("email is existed")
	void createFailed() {
		CreateUserDto createUserDto = CreateUserDto.builder()
				.name(IVAN.getName())
				.birthday(IVAN.getBirthday().toString())
				.email(IVAN.getEmail())
				.gender(IVAN.getGender().toString())
				.role(IVAN.getRole().toString())
				.password(IVAN.getPassword())
				.build();

		assertThrows(
				JdbcSQLIntegrityConstraintViolationException.class,
				() -> userService.create(createUserDto)
		);
	}

	@ParameterizedTest
	@MethodSource("getArgumentsForCreateFailedValidation")
	void createFailedValidation(CreateUserDto dto, String errorMessage) {
		var e = assertThrows(ValidationException.class, () -> userService.create(dto));
		Assertions.assertThat(e.getErrors().get(0).getMessage()).isEqualTo(errorMessage);
	}

	static Stream<Arguments> getArgumentsForCreateFailedValidation() {
		return Stream.of(
				Arguments.of(CreateUserDto.builder()
						.name(IVAN.getName())
						.birthday("dummy")
						.email(IVAN.getEmail())
						.gender(IVAN.getGender().toString())
						.role(IVAN.getRole().toString())
						.password(IVAN.getPassword())
						.build(), "Birthday is invalid"),
				Arguments.of(CreateUserDto.builder()
						.name(IVAN.getName())
						.birthday(IVAN.getBirthday().toString())
						.email(IVAN.getEmail())
						.gender("dummy")
						.role(IVAN.getRole().toString())
						.password(IVAN.getPassword())
						.build(), "Gender is invalid"),
				Arguments.of(CreateUserDto.builder()
						.name(IVAN.getName())
						.birthday(IVAN.getBirthday().toString())
						.email(IVAN.getEmail())
						.gender(IVAN.getGender().toString())
						.role("dummy")
						.password(IVAN.getPassword())
						.build(), "Role is invalid")
		);
	}

}
