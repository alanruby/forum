package com.alan.forum.support;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class SupportId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4744550664234473086L;

	@Column(name = "application_user_id")
    private Long applicationUserId;

    @Column(name = "posting_id")
    private Long postingId;
   

}
