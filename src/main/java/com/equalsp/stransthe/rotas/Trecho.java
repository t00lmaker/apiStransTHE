package com.equalsp.stransthe.rotas;

import java.io.Serializable;

import com.equalsp.stransthe.Linha;
import com.equalsp.stransthe.Localizacao;

public class Trecho implements Serializable {

	private static final long serialVersionUID = 8101310540608896475L;

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
		return origem.getDistancia(destino);
	}
	
	public long getTempo() {
		long tempo = (long) getDistancia();
		if (linha == null)
			tempo *= 4;
		return tempo;
	}

}
