package com.equalsp.stransthe.rotas;

import java.util.ArrayList;
import java.util.List;

public class Rota implements Comparable<Rota> {

	private boolean priorizaDistancia = false;

	public Rota(boolean priorizaDistancia) {
		super();
		this.priorizaDistancia = priorizaDistancia;
	}

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

	@Override
	public int compareTo(Rota o) {
		if (priorizaDistancia) {
			double diff = getDistanciaTotal() - o.getDistanciaTotal();
			return (int) diff;
		}
		else {
			long diff = getTempoTotal() - o.getTempoTotal();
			return (int) diff;
		}
	}

}
