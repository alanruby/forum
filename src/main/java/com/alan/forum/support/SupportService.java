package com.alan.forum.support;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.auth.ApplicationUserRepository;
import com.alan.forum.auth.ApplicationUserService;
import com.alan.forum.exception.ApiRequestException;
import com.alan.forum.posting.Posting;
import com.alan.forum.posting.PostingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SupportService {

private final PostingRepository postingRepository;
private final SupportRepository supportRepository;
private final ApplicationUserRepository applicationUserRepository;
private final ApplicationUserService applicationUserService;
	
	
	void addSupport(Long postingId, String username) {
		ApplicationUser user = (ApplicationUser) applicationUserService.loadUserByUsername(username);
		Posting posting = (Posting) postingRepository.findById(postingId).get();

		supportRepository.findSupportByApplicationUserAndPosting(user,posting).ifPresent(s -> {
			throw new ApiRequestException("You've already supported this post.");
		});
		
		SupportId supportId = new SupportId (user.getId(), postingId);
		
		Support support = new Support(supportId, user, posting,LocalDateTime.now());
		
		
		user.addSupport(support);
		
		applicationUserRepository.save(user);
	}
	

}
