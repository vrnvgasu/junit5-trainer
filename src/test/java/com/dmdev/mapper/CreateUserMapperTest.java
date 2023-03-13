package com.dmdev.mapper;

import static com.dmdev.integration.util.TestObjectUtils.IVAN;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateUserMapperTest {

	private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

	@Test
	void map() {
		CreateUserDto dto = CreateUserDto.builder()
				.name(IVAN.getName())
				.birthday(IVAN.getBirthday().toString())
				.email(IVAN.getEmail())
				.gender(IVAN.getGender().toString())
				.role(IVAN.getRole().toString())
				.password(IVAN.getPassword())
				.build();

		User user = createUserMapper.map(dto);
		Assertions.assertThat(user.getName()).isEqualTo(IVAN.getName());
		Assertions.assertThat(user.getBirthday()).isEqualTo(IVAN.getBirthday());
		Assertions.assertThat(user.getEmail()).isEqualTo(IVAN.getEmail());
		Assertions.assertThat(user.getGender()).isEqualTo(IVAN.getGender());
		Assertions.assertThat(user.getRole()).isEqualTo(IVAN.getRole());
		Assertions.assertThat(user.getPassword()).isEqualTo(IVAN.getPassword());
		Assertions.assertThat(user.getId()).isNull();
	}

}
