package com.alan.forum.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alan.forum.posting.Posting;
import com.alan.forum.security.ApplicationUserRole;
import com.alan.forum.support.Support;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity(name = "ApplicationUser")
@Table(name = "application_user")
public class ApplicationUser implements UserDetails{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1577439253955613050L;
	@SequenceGenerator(
			name="application_user_sequence",
			sequenceName = "application_user_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "application_user_sequence"
	)
	private Long id;
	@Enumerated(EnumType.STRING)
	private ApplicationUserRole applicationUserRole;
	
	@Transient
	private  Set<? extends GrantedAuthority> grantedAuthorities;
	
	private String password; 
	private String username;
	
	@OneToMany(mappedBy = "applicationUser",fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.REMOVE}, orphanRemoval = true)
	private List<Posting> postings = new ArrayList<>();
	
	public ApplicationUser(String username, String password, ApplicationUserRole applicationUserRole) {
		this.applicationUserRole = applicationUserRole;
		this.password = password;
		this.username = username;
		grantedAuthorities = applicationUserRole.getGrantedAuthorities();
	}
	
	public ApplicationUser(Long id, String username, String password) {
		this.password = password;
		this.username = username;
		this.id = id;
	}
	
	public ApplicationUser(String username, String password) {
		this.password = password;
		this.username = username;
	}
	
	
	
	@OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            mappedBy = "applicationUser",
            fetch = FetchType.EAGER
    )
    private List<Support> supports = new ArrayList<>();
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return applicationUserRole.getGrantedAuthorities();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "ApplicationUser [password=" + password + ", username=" + username + "]";
	}
	
	public void addPosting(Posting posting) {
		System.out.println("ooooooi 2");
		if(!this.postings.contains(posting)) {
			System.out.println("ooooooi 3");
			if (this.postings.add(posting)) {
				System.out.println("ooooooi 4");
				posting.setApplicationUser(this);
			}
		}
	}
	
	public void addSupport(Support support) {
        if (!supports.contains(support)) {
        	supports.add(support);
        }
    }

	
	
	public List<Posting> getPostings() {
		return postings;
	}


}
