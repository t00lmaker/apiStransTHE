package com.equalsp.stransthe.rotas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.equalsp.stransthe.Linha;
import com.equalsp.stransthe.Localizacao;
import com.equalsp.stransthe.Parada;

public class RotaService {

	private boolean priorizaDistancia = false;

	public RotaService() {
		super();
	}

	public RotaService(boolean priorizaDistancia) {
		this();
		this.priorizaDistancia = priorizaDistancia;
	}

	// obter paradas proximas de A e B, descobrir linhas que saem das proximas
	// de a, até as proximas de B
	public Set<Rota> getRotas(PontoDeInteresse a, PontoDeInteresse b) {
		List<Parada> origens = maisProximas(a, 500, 3);
		List<Parada> destinos = maisProximas(b, 500, 3);
		Set<Rota> rotas = getRotas(origens, destinos);
		for (Rota rota : rotas) {
			AdicionarTrechosPedestre(rota, a, b);
		}
		return rotas;
	}

	private void AdicionarTrechosPedestre(Rota rota, PontoDeInteresse a, PontoDeInteresse b) {
		Trecho inicial = new Trecho();
		inicial.setOrigem(a);
		inicial.setDestino(rota.getTrechos().get(0).getOrigem());

		Trecho f = new Trecho();
		f.setDestino(b);
		f.setOrigem(rota.getTrechos().get(0).getOrigem());

		rota.getTrechos().add(0, inicial);
		rota.getTrechos().add(f);
	}

	private Set<Rota> getRotas(List<Parada> origens, List<Parada> destinos) {
		Set<Rota> rotas = new TreeSet<Rota>();
		for (Parada origem : origens) {
			List<Linha> linhasOrigem = getLinhas(origem);
			for (Linha linha : linhasOrigem) {
				Parada destino = LinhaPassaApos(linha, origem, destinos);
				if (destino != null) {
					rotas.add(criaRota(linha, origem, destino));
				}
			}
		}
		return rotas;
	}

	private Rota criaRota(Linha linha, Parada origem, Parada destino) {
		Rota r = new Rota(priorizaDistancia);
		Trecho t = new Trecho();
		t.setDestino(destino);
		t.setOrigem(origem);
		t.setLinha(linha);
		r.getTrechos().add(t);
		return r;
	}

	// retornar a primeira parada da linha que é destino e ocorre após ter
	// passado pela origem
	private Parada LinhaPassaApos(Linha linha, Parada origem, List<Parada> destinos) {
		List<Parada> paradasLinha = getParadas(linha);
		boolean passouOrigem = false;
		for (Parada parada : paradasLinha) {
			if (parada.equals(origem)) {
				passouOrigem = true;
			} else if (passouOrigem && destinos.contains(parada)) {
				return parada;
			}
		}
		return null;
	}

	private List<Parada> maisProximas(Localizacao a, double distanciaMaxima, int quantidade) {
		List<Parada> proximas = new ArrayList<Parada>();
		for (Parada p : getParadas()) {
			if (a.getDistanciaManhathan(p) <= distanciaMaxima) {
				proximas.add(p);
			}
			if (proximas.size() >= quantidade)
				break;
		}
		// TODO ordenar por mais proxima...
		return proximas;
	}

	private List<Parada> getParadas() {
		return new ArrayList<Parada>();
	}

	private List<Parada> getParadas(Linha l) {
		return new ArrayList<Parada>();
	}

	private List<Linha> getLinhas(Parada p) {
		return new ArrayList<Linha>();
	}

}
