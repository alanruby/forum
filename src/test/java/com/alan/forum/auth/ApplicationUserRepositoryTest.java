package com.alan.forum.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.alan.forum.security.ApplicationUserRole;

@DataJpaTest
class ApplicationUserRepositoryTest {
	
	private final static String PASSWORD = "123";
	private final static String NAME = "Bob";
	
	@Autowired
	private ApplicationUserRepository underTest;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		underTest.deleteAll();
	}

	@Test
	void testFindUserApplicationByUsernameExists() {
		
		ApplicationUser user =  new ApplicationUser(
				NAME,
				PASSWORD,
                ApplicationUserRole.STUDENT

        );
		
		underTest.save(user);
		
		boolean expected = underTest.findUserApplicationByUsername(NAME).isPresent();
		assertThat(expected).isTrue();
	}
	
	@Test
	void testFindUserApplicationByUsernameDoesNotExists() {
		
		
		boolean expected = underTest.findUserApplicationByUsername(NAME).isPresent();
		assertThat(expected).isFalse();
	}

}
