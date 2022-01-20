package com.alan.forum.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alan.forum.security.ApplicationUserRole;
import com.alan.forum.exception.ApiRequestException;


@ExtendWith(MockitoExtension.class)
class ApplicationUserServiceTest {
	
	private final static String USER_NOT_FOUND_MSG = "Username %s not found";
	private final static String USERNAME_HAS_ALREADY_BEEN_TAKEN = "Username has already been taken";
	private final static String PASSWORD = "123";
	private final static String NAME = "Bob";
	@Mock
	private ApplicationUserRepository applicationUserRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	
	private ApplicationUserService underTest;

	@BeforeEach
	void setUp() throws Exception {
		underTest = new ApplicationUserService(applicationUserRepository,passwordEncoder);
	}

	@Test
	void testLoadUserByUsernameExists() {
		Optional<ApplicationUser> user = Optional.of(new ApplicationUser());
		given(applicationUserRepository.findUserApplicationByUsername(anyString())).willReturn(user);
		underTest.loadUserByUsername(anyString());
		verify(applicationUserRepository).findUserApplicationByUsername(anyString());
	}
	
	
	
	@Test
	void testLoadUserByUsernameDoesNotExists() {
		assertThatThrownBy(() -> underTest.loadUserByUsername(NAME))
			.isInstanceOf(UsernameNotFoundException.class)
			.hasMessageContaining(String.format(USER_NOT_FOUND_MSG,NAME));
		verify(applicationUserRepository).findUserApplicationByUsername(anyString());
	}
	
	@Test
	void testSignUpUser() {
		ApplicationUser user = new ApplicationUser(NAME, PASSWORD,ApplicationUserRole.STUDENT);
		given(passwordEncoder.encode(anyString())).willReturn(anyString());
		
		underTest.signUpUser(user);
		
		ArgumentCaptor<ApplicationUser> userArgumentCaptor = 
				ArgumentCaptor.forClass(ApplicationUser.class);
		verify(applicationUserRepository).save(userArgumentCaptor.capture());
		
	}

	@Test
	void testSignUpUserExists() {

		ApplicationUser user = new ApplicationUser(NAME, PASSWORD,ApplicationUserRole.STUDENT);
		Optional<ApplicationUser> userOpt = Optional.of(user);
		
		given(applicationUserRepository.findUserApplicationByUsername(NAME)).willReturn(userOpt);
		
		assertThatThrownBy(() -> underTest.signUpUser(user))
			.isInstanceOf(ApiRequestException.class)
			.hasMessageContaining(String.format(USERNAME_HAS_ALREADY_BEEN_TAKEN));
		verify(applicationUserRepository, never()).save(user);
		
	}
}
