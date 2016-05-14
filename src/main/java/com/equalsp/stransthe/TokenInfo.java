package com.equalsp.stransthe;

import java.util.Date;

class TokenInfo {
	private String token;
	private Date creationDate = new Date();
	private int minutes;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	public boolean isExpired() {
		// expired is in seconds...
		return Utils.expired(creationDate, (minutes - 1) * 60);
	}

}
