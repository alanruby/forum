package com.alan.forum.registration;

import org.springframework.stereotype.Service;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.auth.ApplicationUserRepository;
import com.alan.forum.auth.ApplicationUserService;
import com.alan.forum.exception.ApiRequestException;
import com.alan.forum.security.ApplicationUserRole;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {
	

    private final ApplicationUserService applicationUserService;
    private final ApplicationUserRepository applicationUserRepository;

	public void register(RegistrationRequest request) {
		
		
		
		applicationUserRepository.findUserApplicationByUsername( request.getUsername()).ifPresent(s -> {
            throw new ApiRequestException("Username has already been taken");
        });
		

		applicationUserService.signUpUser(
                new ApplicationUser(
                        request.getUsername(),
                        request.getPassword(),
                        ApplicationUserRole.STUDENT

                )
        );
	        
	}

}
