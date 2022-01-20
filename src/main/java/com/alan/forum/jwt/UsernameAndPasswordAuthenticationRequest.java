package com.alan.forum.jwt;

public class UsernameAndPasswordAuthenticationRequest {
	
	private String username;
	private String password;
	
	public UsernameAndPasswordAuthenticationRequest() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String usernameString) {
		this.username = usernameString;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
