package com.equalsp.stransthe;

import java.io.Serializable;

public class Parada extends Localizacao implements Serializable {

	private static final long serialVersionUID = 1779630573477740939L;

	private static final long serialVersionUID = 62474138535072904L;

	private String CodigoParada;

	private String Denomicao;

	private String Endereco;

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
		return CodigoParada == null ? 0 : CodigoParada.hashCode();
	}
	
	@Override
	public String toString() {
		return getDenomicao() + " (" + getLat() + "," + getLong() + ")";
	}

}
