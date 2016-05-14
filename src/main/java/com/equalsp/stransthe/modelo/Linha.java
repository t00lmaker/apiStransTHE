package com.equalsp.stransthe.modelo;

import java.util.List;

public class Linha {
	
	private String codigo;
	
	private String denominacao;
	
	private String origem;
	
	private String retorno;
	
	private boolean circular;
	
	private List<Veiculo> veiculos;

	private List<Parada> paradas;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDenominacao() {
		return denominacao;
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

	public boolean isCircular() {
		return circular;
	}

	public void setCircular(boolean circular) {
		this.circular = circular;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public List<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(List<Parada> paradas) {
		this.paradas = paradas;
	}

}
