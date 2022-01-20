package com.alan.forum.posting;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.auth.ApplicationUserRepository;
import com.alan.forum.security.ApplicationUserRole;
import com.alan.forum.support.Support;
import com.alan.forum.support.SupportId;
import com.alan.forum.tool.PopulateDB;
import com.github.javafaker.Faker;

@DataJpaTest
class PostingRepositoryTest extends PopulateDB {
	
	@Autowired
	private PostingRepository underTest;
	
	@Autowired
    protected ApplicationUserRepository applicationUserRepository;
	

	@AfterEach
	void tearDown() throws Exception {
		underTest.deleteAll();
		applicationUserRepository.deleteAll();
	}

	@Test
	void testGetPostingWithUpvotes() throws Exception {
		ApplicationUser user = getUser();
		
		String description = getDescription();
		Posting posting = new Posting(description, LocalDateTime.now());
		user.addPosting(posting);
		applicationUserRepository.save(user);
		
		assertThat(underTest.getPostingWithUpvotes().size() > 0).isTrue();
	}
	
	@Test
	void testGetPostingWithUpvotesByUser() throws Exception {
ApplicationUser user = getUser();
		
		String description = getDescription();
		Posting posting = new Posting(description, LocalDateTime.now());
		user.addPosting(posting);
		applicationUserRepository.save(user);
		
		Posting postingSaved = postingRepository.findAll()
				.stream()
				.filter(s->s.getDescription().equals(description)).findFirst().get();
		
		SupportId supportId = new SupportId (user.getId(), postingSaved.getId());
		Support support = new Support(supportId, user, postingSaved,LocalDateTime.now());
		user.addSupport(support);
		applicationUserRepository.save(user);
		
		assertThat(underTest.getPostingWithUpvotesByUser(user.getId()).size()).isEqualTo(1L);
	}
	

}
