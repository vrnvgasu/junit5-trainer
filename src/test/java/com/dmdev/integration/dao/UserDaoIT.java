package com.dmdev.integration.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dmdev.dao.UserDao;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.integration.util.TestObjectUtils;
import java.time.LocalDate;
import java.util.Optional;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserDaoIT extends IntegrationTestBase {

	private UserDao userDao;

	@BeforeEach
	void prepare() {
		userDao = UserDao.getInstance();
	}

	@Test
	void findAll() {
		assertThat(userDao.findAll())
				.hasSize(5);
	}

	@Test
	void findById() {
		Optional<User> optional = userDao.findById(1);
		assertThat(optional)
				.isPresent();
		assertThat(optional.get()).isEqualTo(TestObjectUtils.IVAN);
	}

	@Test
	void save() {
		User user = User.builder()
				.name("Test")
				.birthday(LocalDate.parse("1990-01-10"))
				.email("test@gmail.com")
				.password("111")
				.role(Role.ADMIN)
				.gender(Gender.MALE)
				.build();
		User savedUser = userDao.save(user);

		assertThat(user.getId()).isNotNull();
		assertThat(user).isEqualTo(savedUser);
	}

	@Test
	@DisplayName("Try to save existing email")
	void saveFailed() {
		assertThrows(JdbcSQLIntegrityConstraintViolationException.class, () -> userDao.save(TestObjectUtils.IVAN));
	}

	@Test
	void findByEmailAndPassword() {
		assertThat(userDao.findByEmailAndPassword(
				TestObjectUtils.IVAN.getEmail(),
				TestObjectUtils.IVAN.getPassword()
		)).isPresent()
				.isEqualTo(Optional.of(TestObjectUtils.IVAN));
		assertThat(userDao.findByEmailAndPassword(TestObjectUtils.IVAN.getEmail(), "dummy")).isEmpty();
		assertThat(userDao.findByEmailAndPassword("dummy", TestObjectUtils.IVAN.getPassword())).isEmpty();
	}

	@Test
	void delete() {
		assertThat(userDao.delete(1)).isTrue();
	}

	@Test
	@DisplayName("Try to delete not existing user")
	void deleteFailed() {
		assertThat(userDao.delete(9999)).isFalse();
	}

	@Test
	void update() {
		User user = User.builder()
				.name("Ivan1")
				.birthday(LocalDate.parse("1991-01-10"))
				.email("ivan1@gmail.com")
				.password("1111")
				.role(Role.USER)
				.gender(Gender.FEMALE)
				.id(1)
				.build();

		assertThat(userDao.findById(1).get()).isNotEqualTo(user);
		userDao.update(user);
		assertThat(userDao.findById(1).get()).isEqualTo(user);
	}

}
