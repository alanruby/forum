package com.alan.forum.tool;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.auth.ApplicationUserRepository;
import com.alan.forum.posting.Posting;
import com.alan.forum.posting.PostingRepository;
import com.alan.forum.security.ApplicationUserRole;
import com.alan.forum.support.SupportRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

public abstract class PopulateDB {
	
        @Autowired
        protected ApplicationUserRepository applicationUserRepository;
	    
	    @Autowired
	    protected PostingRepository postingRepository;
	    
	    @Autowired
	    protected SupportRepository supportRepository;
	    
	    protected ObjectMapper objectMapper = new ObjectMapper();
	    
	    protected final Faker faker = new Faker();
	    
	    
	   
	    
	    
	    public void createPostings() throws Exception {
			ApplicationUser user = getUser();
			user.addPosting( new Posting(getDescription(), LocalDateTime.now()));
			applicationUserRepository.save(user);
			
			ApplicationUser user2 = getUser();
			user.addPosting( new Posting(getDescription(), LocalDateTime.now()));
			applicationUserRepository.save(user2);
			
		}
		
		public ApplicationUser getUser() throws Exception {
			String username = String.format(
	                "%s %s",
	                faker.name().firstName(),
	                faker.name().lastName()
	        );
			
			ApplicationUser user =  new ApplicationUser(
					username,
					faker.internet().password(),
	                ApplicationUserRole.STUDENT

	        );
			
			applicationUserRepository.save(user);
			return applicationUserRepository.findUserApplicationByUsername(user.getUsername()).get();
			
			
		}
		
		public String getDescription() {
			String quote = faker.hitchhikersGuideToTheGalaxy().quote();
			return quote.length() >100 ? quote.substring(0,100) : quote;
			
		}

}
