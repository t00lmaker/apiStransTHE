package com.equalsp.stransthe;

public abstract class Localizacao {

	private double Lat;

	private double Long;

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		Lat = lat;
	}

	public double getLong() {
		return Long;
	}

	public void setLong(double l) {
		Long = l;
	}

	public double getDistancia(Localizacao outra) {
		double R = 6378.137; // raio da terra em KM
		double dLat = (getLat() - outra.getLat()) * Math.PI / 180 ;
		double dLon = (getLong() - outra.getLong()) * Math.PI / 180;
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	    Math.cos(getLat() * Math.PI / 180) * Math.cos(outra.getLat() * Math.PI / 180) *
	    Math.sin(dLon/2) * Math.sin(dLon/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double d = R * c;
	    return d * 1000; // metros
	}

}
