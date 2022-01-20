package com.alan.forum.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.auth.ApplicationUserRepository;
import com.alan.forum.posting.Posting;
import com.alan.forum.posting.PostingRepository;
import com.alan.forum.tool.PopulateDB;

@DataJpaTest
class SupportRepositoryTest extends PopulateDB {
	
	@Autowired
	private PostingRepository postingRepository;
	
	@Autowired
    protected ApplicationUserRepository applicationUserRepository;
	
	@Autowired
	private SupportRepository underTest;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		underTest.deleteAll();
		postingRepository.deleteAll();
		applicationUserRepository.deleteAll();
	}

	@Test
	void testFindSupportByApplicationUserAndPosting() throws Exception {
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
		
		Support support_saved = underTest.findSupportByApplicationUserAndPosting(user,postingSaved).get();
		
		assertThat(support_saved.getApplicationUser().getId()).isEqualTo(user.getId());
		assertThat(support_saved.getPosting().getId()).isEqualTo(postingSaved.getId());
	}

}
