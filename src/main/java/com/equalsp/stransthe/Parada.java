package com.equalsp.stransthe;

import java.util.HashSet;
import java.util.Set;

public class Parada {

	private String CodigoParada;

	private String Denomicao;

	private String Endereco;

	private double Lat;

	private double Long;

	private Set<Linha> linhas = new HashSet<Linha>();

	public Set<Linha> getLinhas() {
		return linhas;
	}

	public void setLinhas(Set<Linha> linhas) {
		this.linhas = linhas;
	}

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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Parada) {
			Parada other = (Parada) obj;
			return CodigoParada.equals(other.CodigoParada);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int number = 17;
		number = 31 * number + CodigoParada.hashCode();
		number = 31 * number + Denomicao.hashCode();
		return number;
	}

}
