package com.equalsp.stransthe;

public class Veiculo {

	private String CodigoVeiculo;

	private double Lat;

	private double Long;

	private String Hora;

	public String getCodigoVeiculo() {
		return CodigoVeiculo;
	}

	public void setCodigoVeiculo(String codigoVeiculo) {
		CodigoVeiculo = codigoVeiculo;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		this.Lat = lat;
	}

	public double getLong() {
		return Long;
	}

	public void setLong(double longi) {
		this.Long = longi;
	}

	public String getHora() {
		return Hora;
	}

	public void setHora(String hora) {
		this.Hora = hora;
	}
}
