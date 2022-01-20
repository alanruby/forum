package com.alan.forum.support;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.auth.ApplicationUserRepository;
import com.alan.forum.auth.ApplicationUserService;
import com.alan.forum.exception.ApiRequestException;
import com.alan.forum.posting.Posting;
import com.alan.forum.posting.PostingRepository;

@ExtendWith(MockitoExtension.class)
class SupportServiceTest {
	
	private final static Long ID =1L;
	private final static String NAME = "Bob";
	private final static String PASSWORD = "123";
	
	@Mock private PostingRepository postingRepository;
	@Mock private SupportRepository supportRepository;
	@Mock private ApplicationUserRepository applicationUserRepository;
	@Mock private ApplicationUserService applicationUserService;
	
	private SupportService underTest;

	@BeforeEach
	void setUp() throws Exception {
		underTest = new SupportService(postingRepository, supportRepository, applicationUserRepository, applicationUserService);
	}


	@Test
	void testAddSupportThrowPostingAlreadySupported() {
		ApplicationUser user = new ApplicationUser(ID, NAME, PASSWORD);
		given(applicationUserService.loadUserByUsername(NAME)).willReturn(user);
		
		Posting posting = new Posting();
		Support support = new Support();
		Optional<Posting> posting_opt = Optional.of(posting);
		Optional<Support> support_opt = Optional.of(support);
		
		given(postingRepository.findById(ID)).willReturn(posting_opt);
		given(supportRepository.findSupportByApplicationUserAndPosting(user,posting)).willReturn(support_opt);
		
		assertThatThrownBy(() -> underTest.addSupport(ID,NAME))
        	.isInstanceOf(ApiRequestException.class)
         	.hasMessageContaining("You've already supported this post.");
		 
		verify(applicationUserRepository, never()).save(any());
		
	}
	
	@Test
	void testAddSupport() {
		ApplicationUser user = new ApplicationUser(ID, NAME, PASSWORD);
		given(applicationUserService.loadUserByUsername(NAME)).willReturn(user);
		
		Posting posting = new Posting();
		Optional<Posting> posting_opt = Optional.of(posting);
		
		given(postingRepository.findById(ID)).willReturn(posting_opt);
		given(supportRepository.findSupportByApplicationUserAndPosting(user,posting)).willReturn(Optional.empty());
		
		 underTest.addSupport(ID,NAME);
		 
		verify(applicationUserRepository).save(any());
		
	}
}
