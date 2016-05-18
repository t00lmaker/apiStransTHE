package com.equalsp.stransthe;

public class Veiculo extends Localizacao {

	private static final long serialVersionUID = 6368022552134294563L;

	private String CodigoVeiculo;

	private String Hora;

	public String getCodigoVeiculo() {
		return CodigoVeiculo;
	}

	public void setCodigoVeiculo(String codigoVeiculo) {
		CodigoVeiculo = codigoVeiculo;
	}

	public String getHora() {
		return Hora;
	}

	public void setHora(String hora) {
		this.Hora = hora;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Veiculo) {
			Veiculo other = (Veiculo) obj;
			return CodigoVeiculo.equals(other.CodigoVeiculo);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return CodigoVeiculo == null ? 0 : CodigoVeiculo.hashCode();
	}
}
