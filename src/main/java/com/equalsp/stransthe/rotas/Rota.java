package com.equalsp.stransthe.rotas;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Rota implements Serializable, Comparable<Rota> {

	private static final long serialVersionUID = -5293540601998247559L;
	
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
		double diff = getDistanciaTotal() - o.getDistanciaTotal();
		return (int) diff;

	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.00"); 
		StringBuilder builder = new StringBuilder();
		for (Trecho trecho : trechos) {
			if (trecho.getLinha() != null) {
				builder.append("Linha: " + trecho.getLinha().getCodigoLinha() + " ");	
			}
		}
		builder.append("- Distância inicial: " + df.format(getTrechos().get(0).getDistancia()) + "m");
		
		return builder.toString();
	}
}
