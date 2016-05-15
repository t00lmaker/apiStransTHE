package com.equalsp.stransthe.rotas;

import java.util.Comparator;

import com.equalsp.stransthe.Localizacao;

public class ComparadorPorProximidade implements Comparator<Localizacao> {

	private Localizacao referencia;

	public ComparadorPorProximidade(Localizacao referencia) {
		super();
		this.referencia = referencia;
	}

	@Override
	public int compare(Localizacao o1, Localizacao o2) {
		double dist1 = o1.getDistancia(referencia);
		double dist2 = o2.getDistancia(referencia);
		double diff = dist1 - dist2;
		return (int) diff;
	}

}
