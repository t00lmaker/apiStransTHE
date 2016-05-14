package com.equalsp.stransthe;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
		Calendar c = new GregorianCalendar();
		c.setTime(creationDate);
		// expires 1 min before...
		c.add(Calendar.MINUTE, minutes - 1);
		Date time = new Date();
		return time.compareTo(c.getTime()) >= 0;
	}

}
