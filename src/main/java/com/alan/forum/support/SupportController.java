package com.alan.forum.support;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/supports")
@AllArgsConstructor
public class SupportController {
	
	private SupportService supportService;
	
	@PostMapping(path="{postingId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void addSupport(@PathVariable("postingId") Long postingId, Authentication authentication) {
		
		supportService.addSupport(postingId, authentication.getName());
		
	}

}