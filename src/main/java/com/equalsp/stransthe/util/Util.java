package com.equalsp.stransthe.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.equalsp.stransthe.InthegraAPI;
import com.equalsp.stransthe.Linha;
import com.equalsp.stransthe.Parada;

public class Util {
	
	private static final int distanciaProximaEmMetros = 250;

	/**
	 * Retorna as linhas que podem ser utilizadas para se locomover de um local a outro.
	 * 
	 * Observação: Este método é muito lento (~ 4 min) porque para cada linha é
	 * executada a requisição para recuperar as paradas. Como já foi comentado
	 * pelo Erick, seria uma boa guardar esse tipo de informação em um bd lite.
	 * 
	 * @param origem - do trajeto
	 * @param destino - do trajeto
	 * @return - um mapa com as linhas encontradas e as paradas mais próximas
	 * @throws Exception - caso não consiga se comunicar com a API do STRANS
	 */
	public static HashMap<Linha, List<Parada>> getLinhas(Local origem, Local destino) throws Exception {
		List<Parada> paradasProximasDaOrigem = getParadasProximas(origem, distanciaProximaEmMetros);
		List<Parada> paradasProximasDoDestino = getParadasProximas(destino, distanciaProximaEmMetros);
		
		List<Linha> todasAsLinhas = InthegraAPI.getLinhas();
		HashMap<Linha, List<Parada>> linhas = new HashMap<Linha, List<Parada>>();
		
		for (Linha linha : todasAsLinhas) {
			List<Parada> paradasDaLinha = linha.getParadas();
			if (!paradasDaLinha.isEmpty()) {
				for (Parada paradaOrigem : paradasProximasDaOrigem) {
					if (paradasDaLinha.contains(paradaOrigem)) {
						if (!Collections.disjoint(paradasDaLinha, paradasProximasDoDestino)) {							
							List<Parada> paradas = linhas.containsKey(linha) ? linhas.get(linha) : new ArrayList<Parada>();
							paradas.add(paradaOrigem);
							linhas.put(linha, paradas);
						}
					}
				}
			}
		}
		
		return linhas;
	}
	
	/**
	 * Retorna a melhor parada, em números de opções de linhas, para se
	 * locomover de um local a outro.
	 * 
	 * Observação: Este método é muito lento (~ 4 min) porque para cada linha é
	 * executada a requisição para recuperar as paradas. Como já foi comentado
	 * pelo Erick, seria uma boa guardar esse tipo de informação em um bd lite.
	 * 
	 * Problema: Ainda não diferença no sentido do trajeto. 
	 * Bairro -> Centro ou Centro -> Bairro
	 * 
	 * @param origem - do trajeto
	 * @param destino - do trajeto
	 * @return - a parada mais próxima com mais opções de linhas para o trajeto informado
	 * @throws Exception - caso não consiga se comunicar com a API do STRANS
	 */
	public static Parada getMelhorParada(Local origem, Local destino) throws Exception {
		List<Parada> paradasProximasDaOrigem = getParadasProximas(origem, distanciaProximaEmMetros);
		List<Parada> paradasProximasDoDestino = getParadasProximas(destino, distanciaProximaEmMetros);
		
		List<Linha> linhas = InthegraAPI.getLinhas();
		HashMap<Parada, Integer> paradas = new HashMap<Parada, Integer>();
		
		Parada melhorParada = null;
		int maiorQuantidade = 0;
		
		for (Linha linha : linhas) {
			List<Parada> paradasDaLinha = linha.getParadas();
			if (!paradasDaLinha.isEmpty()) {
				for (Parada paradaOrigem : paradasProximasDaOrigem) {
					if (paradasDaLinha.contains(paradaOrigem)) {
						if (!Collections.disjoint(paradasDaLinha, paradasProximasDoDestino)) {
							int quantidadeParada = paradas.containsKey(paradaOrigem) ? paradas.get(paradaOrigem) : 0;
							quantidadeParada = quantidadeParada+1;
							paradas.put(paradaOrigem, quantidadeParada);
							
							if (quantidadeParada > maiorQuantidade) {
								maiorQuantidade = quantidadeParada;
								melhorParada = paradaOrigem;
							}
						}
					}
				}
			}
		}

		return melhorParada;
	}
	
	

	/**
	 * Retorna as paradas próximas de um local
	 * 
	 * @param local - inicial para a busca de paradas
	 * @param distanciaMaximaEmMetros - distância máxima para uma parada ser considerada próxima
	 * @return - lista das paradas mais próximas ao local indicado
	 * @throws Exception - caso não consiga se comunicar com a API do STRANS
	 */
	public static List<Parada> getParadasProximas(Local local, int distanciaMaximaEmMetros) throws Exception {
		List<Parada> paradas = InthegraAPI.getParadas();
		List<Parada> paradasProximas = new ArrayList<Parada>();
		
		for (Parada parada : paradas) {
			Local localParada = new Local(parada.getLat(), parada.getLong());
			double distanciaEmKm = getDistanciaEmKm(local, localParada);
			double distanciaEmMetros = distanciaEmKm*1000;
			
			if (distanciaEmMetros < distanciaMaximaEmMetros) {
				paradasProximas.add(parada);
			}
		}
		return paradasProximas;
	}
	
	/**
	 * Cálcula a distância, em kilômetros e em linha reta, entre dois locais
	 * utilizando a Fórmula de Haversine.
	 * 
	 * COMO VISTO EM :
	 * http://stackoverflow.com/questions/27928/calculate-distance-between-two-
	 * latitude-longitude-points-haversine-formula
	 * 
	 * @param localA
	 * @param localB
	 * @return - A distância em kilômetros do Local A ao Local B, em linha reta
	 */
	private static double getDistanciaEmKm(Local localA, Local localB) {
		double AVERAGE_RADIUS_OF_EARTH = 6371;

		double latDistance = Math.toRadians(localA.getLatitude() - localB.getLatitude());
	    double lngDistance = Math.toRadians(localA.getLongitude() - localB.getLongitude());

	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	      + Math.cos(Math.toRadians(localA.getLatitude())) * Math.cos(Math.toRadians(localB.getLatitude()))
	      * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    return (AVERAGE_RADIUS_OF_EARTH * c);
	}
}