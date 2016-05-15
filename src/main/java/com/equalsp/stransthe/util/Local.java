package com.equalsp.stransthe.util;

public class Local {

	private Double Latitude;
	
	private Double Longitude;

	public Local(Double latitude, Double longitude) {
		super();
		Latitude = latitude;
		Longitude = longitude;
	}

	public Double getLatitude() {
		return Latitude;
	}

	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}

	public Double getLongitude() {
		return Longitude;
	}

	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}
}