package com.dmdev.integration.util;

import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestObjectUtils {

	public static final User IVAN = User.builder()
			.id(1)
			.name("Ivan")
			.birthday(LocalDate.parse("1990-01-10"))
			.email("ivan@gmail.com")
			.password("111")
			.role(Role.ADMIN)
			.gender(Gender.MALE)
			.build();

	public static final User PETR = User.builder()
			.id(2)
			.name("Petr")
			.birthday(LocalDate.parse("1995-10-19"))
			.email("petr@gmail.com")
			.password("123")
			.role(Role.USER)
			.gender(Gender.MALE)
			.build();

}
