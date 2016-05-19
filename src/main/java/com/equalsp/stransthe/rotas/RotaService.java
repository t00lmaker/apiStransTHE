package com.equalsp.stransthe.rotas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.equalsp.stransthe.CachedInthegraService;
import com.equalsp.stransthe.Linha;
import com.equalsp.stransthe.Localizacao;
import com.equalsp.stransthe.Parada;

/**
 * 
 * @author Erick Passos
 *
 */
public class RotaService {

	private CachedInthegraService cachedService;
	
	/*
	 * API PUBLICA
	 * construtor
	 * busca de rotas (3 opções)
	 * busca de paradas próximas
	 */

	public RotaService(CachedInthegraService cachedService) {
		super();
		this.cachedService = cachedService;
	}

	public Set<Rota> getRotas(PontoDeInteresse a, PontoDeInteresse b, double distanciaPe) throws IOException {
		List<Parada> origens = getParadasProximas(a, distanciaPe, 5);
//		System.out.println("paradas origem: " + origens.size());
		List<Parada> destinos = getParadasProximas(b, distanciaPe, 5);
//		System.out.println("paradas destino: " + origens.size());
		Set<Rota> rotas = getRotas(origens, destinos);
		for (Rota rota : rotas) {
			AdicionarTrechosPedestre(rota, a, b);
		}
		return rotas;
	}
	
	public Set<Rota> getRotas(Parada origem, Parada destino) throws IOException {
		List<Parada> origens = new ArrayList<Parada>();
		origens.add(origem);
		List<Parada> destinos = new ArrayList<Parada>();
		destinos.add(destino);
		return getRotas(origens, destinos);
	}

	public Set<Rota> getRotas(List<Parada> origens, List<Parada> destinos) throws IOException {
		Set<Rota> rotas = new TreeSet<Rota>();
		for (Parada origem : origens) {
			List<Linha> linhasOrigem = getLinhas(origem);
			//System.out.println("linhas da parada origem: " + linhasOrigem.size());
			for (Linha linha : linhasOrigem) {
				Parada destino = LinhaPassaApos(linha, origem, destinos);
				if (destino != null) {
					rotas.add(criaRota(linha, origem, destino));
				}
			}
		}
		return rotas;
	}
	
	private List<Parada> getParadasProximas(Localizacao a, double distanciaMaxima, int quantidade) throws IOException {
		List<Parada> proximas = new ArrayList<Parada>();
		for (Parada p : getParadas()) {
			if (a.getDistancia(p) <= distanciaMaxima) {
				proximas.add(p);
			}
			if (proximas.size() >= quantidade)
				break;
		}
		// Ordenar por proximidade a 'a'
		Collections.sort(proximas, new ComparadorPorProximidade(a));
		return proximas;
	}
	
	/*
	 * FIM DE API PÚBLICA
	 */

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

	private Rota criaRota(Linha linha, Parada origem, Parada destino) {
		Rota r = new Rota();
		Trecho t = new Trecho();
		t.setDestino(destino);
		t.setOrigem(origem);
		t.setLinha(linha);
		r.getTrechos().add(t);
		return r;
	}
	
	private Parada LinhaPassaApos(Linha linha, Parada origem, List<Parada> destinos) throws IOException {
		List<Parada> paradasLinha = getParadas(linha);
		boolean passouOrigem = false;
		for (Parada parada : paradasLinha) {
			if (parada.equals(origem)) {
				//System.out.println("passou origem...");
				passouOrigem = true;
			} else if (passouOrigem && destinos.contains(parada)) {
				return parada;
			}
		}
		return null;
	}

	private List<Parada> getParadas() throws IOException {
		return cachedService.getParadas();
	}

	private List<Parada> getParadas(Linha l) throws IOException {
		return cachedService.getParadas(l);
	}

	private List<Linha> getLinhas(Parada p) throws IOException {
		return cachedService.getLinhas(p);
	}

}
