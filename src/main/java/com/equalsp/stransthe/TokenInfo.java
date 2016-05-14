package com.equalsp.stransthe;

import java.util.Date;

public class TokenInfo {
	private String token;
	private Date creationDate = new Date();
	private long expirationSeconds;

	public TokenInfo(String token, long expirationSeconds) {
		this.token = token;
		this.expirationSeconds = expirationSeconds;
	}

	public String getToken() {
		return token;
	}

	public boolean isExpired() {
		return false;
	}

}
