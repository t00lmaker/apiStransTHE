package com.equalsp.stransthe.modelo;

import java.util.List;

public class ParadaLinha {
	private Linha linha;
	private List<Parada> paradas;

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public List<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(List<Parada> paradas) {
		this.paradas = paradas;
	}

}
