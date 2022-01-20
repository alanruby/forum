package com.alan.forum.posting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alan.forum.auth.ApplicationUser;
import com.alan.forum.support.Support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "Posting")
@Table(name = "posting")
@AllArgsConstructor
public class Posting {
	
	@Id
	@SequenceGenerator(
			name="posting_sequence",
			sequenceName = "posting_sequence",
			allocationSize = 1
	)
	
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "posting_sequence"
	)
	
	@Column(
		name = "id",
		updatable = false
	)
	private Long id;
	
	@Transient
	private Long upvotes;
	
	@Column(
		name = "description",
		nullable = false
	)
	private String description;
	
	@ManyToOne
	@JoinColumn(
		name = "application_user_id",
		nullable = false,
		referencedColumnName = "id",
		foreignKey = @ForeignKey(name="application_user_posting_fk")
	)
	private ApplicationUser applicationUser;
	
	@Column(
			name = "created_at",
			nullable = false,
			columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
		)
	private LocalDateTime createdAt;
	
	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "posting",
            fetch = FetchType.EAGER
    )
    private List<Support> supports = new ArrayList<>();

	public Posting(String description, ApplicationUser applicationUser, LocalDateTime createdAt) {
		this.description = description;
		this.applicationUser = applicationUser;
		this.createdAt = createdAt;
	}
	
	public Posting(String description, LocalDateTime createdAt) {
		this.description = description;
		this.createdAt = createdAt;
	}

	public Posting(Long id, String description, LocalDateTime createdAt) {
		this.id = id;
		this.description = description;
		this.createdAt = createdAt;
	}
	
	public void addEnrolment(Support support) {
        if (!supports.contains(support)) {
        	supports.add(support);
        }
    }

	public Posting(Long id, Long upvotes, String description, LocalDateTime createdAt) {
		this.id = id;
		this.upvotes = upvotes;
		this.description = description;
		this.createdAt = createdAt;
	}
}
