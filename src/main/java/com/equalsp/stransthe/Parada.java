package com.equalsp.stransthe;

public class Parada {

	private String CodigoParada;

	private String Denomicao;

	private String Endereco;

	private double Lat;

	private double Long;

	public String getEndereco() {
		return Endereco;
	}

	public void setEndereco(String endereco) {
		Endereco = endereco;
	}

	public String getCodigoParada() {
		return CodigoParada;
	}

	public void setCodigoParada(String codigoParada) {
		CodigoParada = codigoParada;
	}

	public String getDenomicao() {
		return Denomicao;
	}

	public void setDenomicao(String denomicao) {
		Denomicao = denomicao;
	}

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

}
