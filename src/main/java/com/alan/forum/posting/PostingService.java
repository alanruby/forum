package com.alan.forum.posting;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.auth.ApplicationUserRepository;
import com.alan.forum.auth.ApplicationUserService;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostingService {
	
	private final ApplicationUserService applicationUserService;
	private final ApplicationUserRepository applicationUserRepository;
	private final PostingRepository postingRepository;
	
	
	void addPosting(Posting posting, String username) {
		ApplicationUser user = (ApplicationUser) applicationUserService.loadUserByUsername(username);
		posting.setCreatedAt(LocalDateTime.now());
    	user.addPosting(posting);

    	applicationUserRepository.save(user);
	}
	
	List<JSONObject> getPostingWithUpvotes() throws JSONException {
		return getPostingWithUpvotesResponse(postingRepository.getPostingWithUpvotes());
	}
	
	List<JSONObject> getPostingWithUpvotesByUser(String username) throws JSONException {
		ApplicationUser user = (ApplicationUser) applicationUserService.loadUserByUsername(username);
		return getPostingWithUpvotesResponse(postingRepository.getPostingWithUpvotesByUser(user.getId()));
	}
	
	private List<JSONObject> getPostingWithUpvotesResponse(List<Object[]> postings) throws JSONException {
		List<JSONObject> listOfLists = Lists.newArrayList();
		
		if(postings != null && !postings.isEmpty()){
		   for (Object[] object : postings) {
			   JSONObject json = new JSONObject();
			   json.put("description", object[0].toString());
			   json.put("createdAt", object[1].toString());
			   json.put("upvotes", object[2].toString());
			   json.put("id", object[3].toString());
			   listOfLists.add(json);
		   }
		}
		return listOfLists;
	}
	
	

}



