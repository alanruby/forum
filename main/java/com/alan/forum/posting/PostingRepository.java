package com.alan.forum.posting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
	
	 @Query(value="" +
	            "select description,posting.created_at, count(support.posting_id) as upvotes, posting.id " +
	            "from posting left join  support on support.posting_id= posting.id group by posting.id "
	            ,nativeQuery = true
	    )
	 List<Object[]> getPostingWithUpvotes();
	 
	 @Query(value="" +
	            "select description,posting.created_at, count(support.posting_id) as upvotes, posting.id " +
	            "from posting left join  support on support.posting_id= posting.id where support.application_user_id=:user_id " +
	            "group by posting.id" ,nativeQuery = true
	    )
	 List<Object[]> getPostingWithUpvotesByUser(@Param("user_id") Long user_id);

}
