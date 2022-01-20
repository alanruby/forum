package com.alan.forum.support;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.posting.Posting;

public interface SupportRepository extends JpaRepository<Support, Long> {
	
	Optional<Support> findSupportByApplicationUserAndPosting(ApplicationUser applicationUser, Posting posting);

}
