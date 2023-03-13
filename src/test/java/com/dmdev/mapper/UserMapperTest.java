package com.dmdev.mapper;

import static com.dmdev.integration.util.TestObjectUtils.IVAN;

import com.dmdev.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserMapperTest {

	private final UserMapper userMapper = UserMapper.getInstance();

	@Test
	void map() {
		UserDto dto = UserDto.builder()
				.id(IVAN.getId())
				.name(IVAN.getName())
				.birthday(IVAN.getBirthday())
				.email(IVAN.getEmail())
				.gender(IVAN.getGender())
				.role(IVAN.getRole())
				.build();

		Assertions.assertThat(userMapper.map(IVAN)).isEqualTo(dto);
	}

}
