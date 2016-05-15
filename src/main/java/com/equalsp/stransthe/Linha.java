package com.equalsp.stransthe;

public class Linha {

	private String CodigoLinha;

	private String Denomicao;

	private String Origem;

	private String Retorno;

	private boolean Circular;

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
		return CodigoLinha == null ? 0 : CodigoLinha.hashCode();
	}

}
