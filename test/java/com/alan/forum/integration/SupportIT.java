package com.alan.forum.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.posting.Posting;
import com.alan.forum.support.Support;
import com.alan.forum.tool.PopulateDB;

@SpringBootTest
@TestPropertySource(
		locations = "classpath:resources/application-it.properties"
)

@AutoConfigureMockMvc
public class SupportIT extends PopulateDB {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void addSupport() throws Exception {
		ApplicationUser user = getUser();
		
		Posting posting = new Posting(getDescription(),LocalDateTime.now());
		
		user.addPosting(posting);
		applicationUserRepository.save(user);
		
		List<Posting> postings = postingRepository.findAll();
		
		Posting posting_saved = postings.stream().filter(s -> s.getDescription().equals(posting.getDescription())).findFirst().get();
		
		ResultActions resultActionsReg = mockMvc
				.perform(post("/api/v1/supports/"+posting_saved.getId())
						.with(user(user.getUsername()).password(user.getPassword())
						.authorities(new SimpleGrantedAuthority("student:write"))).contentType(MediaType.APPLICATION_JSON));

		
		resultActionsReg.andExpect(status().isOk());
		
		List<Support> supports = supportRepository.findAll();

		
		Boolean foundSupport =  supports.stream().filter(s -> 
			s.getApplicationUser().getId().equals(user.getId()) && 
			s.getPosting().getId().equals(posting_saved.getId())).findFirst().isPresent();
		
		 assertThat(foundSupport).isTrue();
	}

}
