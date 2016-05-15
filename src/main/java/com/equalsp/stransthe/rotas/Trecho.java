package com.equalsp.stransthe.rotas;

import com.equalsp.stransthe.Linha;
import com.equalsp.stransthe.Localizacao;

public class Trecho {

	private Linha linha;

	private Localizacao origem;

	private Localizacao destino;

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Localizacao getOrigem() {
		return origem;
	}

	public void setOrigem(Localizacao origem) {
		this.origem = origem;
	}

	public Localizacao getDestino() {
		return destino;
	}

	public void setDestino(Localizacao destino) {
		this.destino = destino;
	}
	
	public double getDistancia() {
		return origem.getDistanciaManhathan(destino);
	}
	
	public long getTempo() {
		long tempo = (long) getDistancia();
		if (linha == null)
			tempo *= 4;
		return tempo;
	}

}
