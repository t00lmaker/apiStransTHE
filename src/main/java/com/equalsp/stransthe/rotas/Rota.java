package com.equalsp.stransthe.rotas;

import java.util.ArrayList;
import java.util.List;

public class Rota {

	private List<Trecho> trechos = new ArrayList<Trecho>();

	public List<Trecho> getTrechos() {
		return trechos;
	}

	public long getTempoTotal() {
		long tempo = 0;
		for (Trecho trecho : trechos) {
			tempo += trecho.getTempo();
		}
		return tempo;
	}

	public double getDistanciaTotal() {
		double distancia = 0;
		for (Trecho trecho : trechos) {
			distancia += trecho.getDistancia();
		}
		return distancia;
	}

}
