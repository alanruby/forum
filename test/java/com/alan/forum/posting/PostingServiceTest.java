package com.alan.forum.posting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.configurationprocessor.json.JSONException;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.auth.ApplicationUserRepository;
import com.alan.forum.auth.ApplicationUserService;

@ExtendWith(MockitoExtension.class)
class PostingServiceTest {
	
	private final static Long ID = 100L;
	private final static String PASSWORD = "123";
	private final static String NAME = "Bob";
	private final static String DESCRIPTION = "Description";
	
	@Mock private ApplicationUserService applicationUserService;
	@Mock private ApplicationUserRepository applicationUserRepository;
	@Mock private PostingRepository postingRepository;
	
	private PostingService underTest;

	@BeforeEach
	void setUp() throws Exception {
		underTest = new PostingService(applicationUserService, applicationUserRepository, postingRepository);
	}

	@Test
	void testAddPosting() {
		Posting posting = new Posting();
		
		posting.setDescription(DESCRIPTION);
		
		ApplicationUser user = new ApplicationUser(NAME, PASSWORD);
		
		given(applicationUserService.loadUserByUsername(NAME)).willReturn(user);
		
		underTest.addPosting(posting,NAME);
		
		ArgumentCaptor<ApplicationUser> userArgumentCaptor =
                ArgumentCaptor.forClass(ApplicationUser.class);

        verify(applicationUserRepository)
                .save(userArgumentCaptor.capture());

        ApplicationUser capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
	}

	@Test
	void testGetPostingWithUpvotes() throws JSONException {
		underTest.getPostingWithUpvotes();
		verify(postingRepository).getPostingWithUpvotes();
	}
	
	@Test
	void testGetPostingWithUpvotesByUser() throws JSONException {
		ApplicationUser user = new ApplicationUser(ID,NAME, PASSWORD);
		given(applicationUserService.loadUserByUsername(NAME)).willReturn(user);
		underTest.getPostingWithUpvotesByUser(NAME);
		verify(postingRepository).getPostingWithUpvotesByUser(ID);
	}

}
