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
		List<Parada> destinos = getParadasProximas(b, distanciaPe, 5);
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
		Parada ultimaParada;
		for (Parada origem : origens) {
			List<Linha> linhasOrigem = getLinhas(origem);
			for (Linha linha : linhasOrigem) {
				Parada destino = LinhaPassaApos(linha, origem, destinos);
				if (destino != null) {
					Rota rota = new Rota();
					List<Parada> proximasParadas = ParadasDaLinhaAteDestino(linha, origem, destinos);
					ultimaParada = origem;
					for (Parada parada : proximasParadas) {
						if (ultimaParada != origem) {
							Trecho trecho = new Trecho();
							trecho.setOrigem(ultimaParada);
							trecho.setDestino(parada);
							trecho.setLinha(linha);
							rota.getTrechos().add(trecho);							
						}
						ultimaParada = parada;
					}
					rotas.add(rota);
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
		List<Trecho> trechos = rota.getTrechos();
		inicial.setOrigem(a);
		inicial.setDestino(trechos.get(0).getOrigem());

		Trecho f = new Trecho();
		f.setOrigem(trechos.get(trechos.size()-1).getOrigem());
		f.setDestino(b);

		trechos.add(0, inicial);
		trechos.add(f);
	}

	@SuppressWarnings("unused")
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
	
	private List<Parada> ParadasDaLinhaAteDestino(Linha linha, Parada origem, List<Parada> destinos) throws IOException {
		List<Parada> paradasLinha = getParadas(linha);
		List<Parada> proximasParadas = new ArrayList<>();
		boolean passouOrigem = false;
		boolean passouDestino = false;
		
		for (Parada parada : paradasLinha) {
			if (parada.equals(origem)) {
				passouOrigem = true;
			}
			
			if (destinos.contains(parada)){
				proximasParadas.add(parada);
				passouDestino = true;
			}
			
			if (passouOrigem && !passouDestino) {
				proximasParadas.add(parada);
			}
		}
		

		return proximasParadas;
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
