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
		double diffLat = getLat() - outra.getLat();
		double diffLong = getLong() - outra.getLong();
		return Math.sqrt(diffLat * diffLat + diffLong * diffLong);
	}

	public double getDistanciaManhathan(Localizacao outra) {
		double diffLat = getLat() - outra.getLat();
		double diffLong = getLong() - outra.getLong();
		return Math.abs(diffLat) + Math.abs(diffLong);
	}

}
