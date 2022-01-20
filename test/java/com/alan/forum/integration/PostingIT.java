package com.alan.forum.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hibernate.hql.internal.ast.tree.BooleanLiteralNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.posting.Posting;
import com.alan.forum.tool.PopulateDB;

@SpringBootTest
@TestPropertySource(
		locations = "classpath:resources/application-it.properties"
)

@AutoConfigureMockMvc
public class PostingIT extends PopulateDB {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void addPosting() throws Exception {
		ApplicationUser user = getUser();
		
		Posting posting = new Posting();
		posting.setDescription(getDescription());

		ResultActions resultActionsReg = mockMvc
				.perform(post("/api/v1/postings")
						.with(user(user.getUsername()).password(user.getPassword())
						.authorities(new SimpleGrantedAuthority("student:write"))).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(posting)));

		
		resultActionsReg.andExpect(status().isOk());
		
		List<Posting> postings = postingRepository.findAll();
		
		Boolean foundPosting =  postings.stream().filter(s -> s.getDescription().equals(posting.getDescription())).findFirst().isPresent();
		
		 assertThat(foundPosting).isTrue();
	}

	
	@Test
	void getPostingWithUpvotes() throws Exception {
		createPostings();

		ResultActions resultActionsReg = mockMvc
				.perform(get("/api/v1/postings")
						.with(user(faker.name().firstName()).password(faker.internet().password())
						.authorities(new SimpleGrantedAuthority("student:read"))));

		
		resultActionsReg.andExpect(status().isOk());
		
		MvcResult result = resultActionsReg.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		
		
		String[] response_arr = getNumberOfPostingInStringResponse(result.getResponse().getContentAsString());
		
		assertThat(postingRepository.findAll().size()).isEqualTo(response_arr.length);
		
	}
	
	private String[] getNumberOfPostingInStringResponse(String response) {
		
		return response.split("},");
		
	}
	
}
