package com.patrickubelhor.service.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tokens")
public class TokenListing {
	
	/*
	 * TODO: this should not be unique. A user should have one token per user-agent.
	 *  This way, a user can request to revoke tokens per device.
	 */
	@Id
	private Long userId;
	
	@Column(unique = true)
	@Size(min = 1)
	private String token;
	
	
	public TokenListing() {}
	
	
	public TokenListing(Long userId, String token) {
		this.userId = userId;
		this.token = token;
	}
	
	
	public String getToken() {
		return token;
	}
	
	
	public Long getUserId() {
		return userId;
	}
	
	
	public void setToken(String token) {
		this.token = token;
	}
	
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
