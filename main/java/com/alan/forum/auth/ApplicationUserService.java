package com.alan.forum.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alan.forum.exception.ApiRequestException;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {
	
	
	private final ApplicationUserRepository applicationUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final static String USER_NOT_FOUND_MSG = "Username %s not found";
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return applicationUserRepository
				.findUserApplicationByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,username)));
	}


	public void signUpUser(ApplicationUser applicationUser) {
		
		applicationUserRepository
                .findUserApplicationByUsername(applicationUser.getUsername())
                .ifPresent(s -> {
                    throw new ApiRequestException("Username has already been taken");
                });
		
        String encodedPassword = passwordEncoder
                .encode(applicationUser.getPassword());

        applicationUser.setPassword(encodedPassword);

        applicationUserRepository.save(applicationUser);

		
	}

}
