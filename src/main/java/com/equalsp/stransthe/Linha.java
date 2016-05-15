package com.equalsp.stransthe;

import java.util.Date;
import java.util.List;

public class Linha {

	private String CodigoLinha;

	private String Denomicao;

	private String Origem;

	private String Retorno;

	private boolean Circular;

	private List<Veiculo> Veiculos;

	private List<Parada> paradas;

	public String getCodigoLinha() {
		return CodigoLinha;
	}

	public void setCodigoLinha(String codigoLinha) {
		CodigoLinha = codigoLinha;
	}

	public String getDenomicao() {
		return Denomicao;
	}

	public void setDenomicao(String denomicao) {
		Denomicao = denomicao;
	}

	public String getOrigem() {
		return Origem;
	}

	public void setOrigem(String origem) {
		this.Origem = origem;
	}

	public String getRetorno() {
		return Retorno;
	}

	public void setRetorno(String retorno) {
		this.Retorno = retorno;
	}

	public boolean isCircular() {
		return Circular;
	}

	public void setCircular(boolean circular) {
		this.Circular = circular;
	}

	private Date ultimaAtualizacaoVeiculos;

	public List<Veiculo> getVeiculos() throws Exception {
		// só atualiza veículos no primeiro acesso, depois de 30 em 30s
		if (ultimaAtualizacaoVeiculos == null || Utils.expired(ultimaAtualizacaoVeiculos, 30)) {
			ultimaAtualizacaoVeiculos = new Date();
			Veiculos = InthegraAPI.getVeiculos(this);
		}
		return Veiculos;
	}

	List<Veiculo> getVeiculosFromJson() {
		return Veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		Veiculos = veiculos;
	}

	public List<Parada> getParadas() throws Exception {
		// paradas de linha só é preciso buscar uma vez...
		if (paradas == null) {
			paradas = InthegraAPI.getParadas(this);
		}
		return paradas;
	}

	public void setParadas(List<Parada> paradas) {
		this.paradas = paradas;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Linha) {
			Linha other = (Linha) obj;
			return CodigoLinha.equals(other.CodigoLinha);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int number = 17;
		number = 31 * number + CodigoLinha.hashCode();
		number = 31 * number + Denomicao.hashCode();
		number = 31 * number + Retorno.hashCode();
		number = 31 * number + Origem.hashCode();
		return number;
	}

}
