package com.alan.forum.posting;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/postings")
@AllArgsConstructor
public class PostingController {
	
	private PostingService postingService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void addPosting(@RequestBody Posting posting, Authentication authentication) {
		
		postingService.addPosting(posting, authentication.getName());
		
	}

	@GetMapping
	@PreAuthorize("hasAuthority('student:read')")
	public String getPostingWithUpvotes() throws JSONException {
		
		return postingService.getPostingWithUpvotes().toString();
		
	}
	
	@GetMapping(path="/user")
	@PreAuthorize("hasAuthority('student:read')")
	public String getPostingWithUpvotesByUser(Authentication authentication) throws JSONException {
		
		return postingService.getPostingWithUpvotesByUser(authentication.getName()).toString();
		
	}
}
