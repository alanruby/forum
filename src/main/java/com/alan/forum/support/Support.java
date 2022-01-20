package com.alan.forum.support;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.posting.Posting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Support")
@Table(name = "support")
public class Support {

	    @EmbeddedId
	    private SupportId id;
	    
	    @ManyToOne
	    @MapsId("applicationUserId")
	    @JoinColumn(
	            name = "application_user_id",
	            foreignKey = @ForeignKey(
	                    name = "support_application_user_id_fk"
	            )
	    )
	    private ApplicationUser applicationUser;
	    
	    
	    @ManyToOne
	    @MapsId("postingId")
	    @JoinColumn(
	            name = "posting_id",
	            foreignKey = @ForeignKey(
	                    name = "support_posting_id_fk"
	            )
	    )
	    private Posting posting;

	    @Column(
	            name = "created_at",
	            nullable = false,
	            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
	    )
	    private LocalDateTime createdAt;

		public Support(SupportId id, ApplicationUser applicationUser, Posting posting, LocalDateTime createdAt) {
			this.id = id;
			this.applicationUser = applicationUser;
			this.posting = posting;
			this.createdAt = createdAt;
		}

		public Support(ApplicationUser applicationUser, Posting posting, LocalDateTime createdAt) {
			this.applicationUser = applicationUser;
			this.posting = posting;
			this.createdAt = createdAt;
		}

		public Support(ApplicationUser applicationUser, Posting posting) {
			this.applicationUser = applicationUser;
			this.posting = posting;
		}
}
