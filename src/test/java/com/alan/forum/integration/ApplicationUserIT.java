package com.alan.forum.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.auth.ApplicationUserRepository;
import com.alan.forum.security.ApplicationUserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@SpringBootTest
@TestPropertySource(
		locations = "classpath:resources/application-it.properties"
)

@AutoConfigureMockMvc
public class ApplicationUserIT {
	
		private final static String PASSWORD = "afdasfs";
	
		@Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @Autowired
	    private ApplicationUserRepository applicationUserRepository;
	    
	    private final Faker faker = new Faker();
	    
	
	@Test
	void registerNewuserApplication() throws Exception {
		String username = String.format(
                "%s %s",
                faker.name().firstName(),
                faker.name().lastName()
        );
		
		ApplicationUser user =  new ApplicationUser(
				username,
				PASSWORD,
                ApplicationUserRole.STUDENT

        );
		
		ResultActions resultActions = mockMvc
                .perform(post("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)));
		
		 resultActions.andExpect(status().isOk());
	      
		 boolean expected = applicationUserRepository.findUserApplicationByUsername(username).isPresent();
			assertThat(expected).isTrue();
		
	}
}
