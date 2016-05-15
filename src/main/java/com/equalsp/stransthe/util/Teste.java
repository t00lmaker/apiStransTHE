package com.equalsp.stransthe.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.equalsp.stransthe.InthegraAPI;
import com.equalsp.stransthe.Linha;
import com.equalsp.stransthe.Parada;

public class Teste {
	public static void main(String[] args) throws Exception {
		InthegraAPI.init("erickpassos@gmail.com", "circ51sp", "ef5f05bdedd34cada40187761d5daaa7");

		Local local1 = new Local(-5.069351, -42.759172);
		Local local2 = new Local(-5.088717, -42.810846);

		long startTime = System.currentTimeMillis();
		HashMap<Linha,List<Parada>> linhas = Util.getLinhas(local1, local2);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
		System.out.println("Primeira execução: " + seconds + "s");

		startTime = System.currentTimeMillis();
		Parada melhorParada = Util.getMelhorParada(local1, local2);
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
		System.out.println("Segunda execução: " + seconds + "s");
		
		for (Entry<Linha, List<Parada>> entry : linhas.entrySet()) {
			Linha linha = entry.getKey();
			System.out.println("----");
			System.out.println(linha.getCodigoLinha());
			List<Parada> paradas = entry.getValue();
			for (Parada parada : paradas) {
				System.out.println(parada.getDenomicao());
				System.out.println(parada.getLat() + " " + parada.getLong());
				System.out.println("--");
			}
		}
		
		System.out.println("==========");
		System.out.println(melhorParada.getDenomicao());
		System.out.println(melhorParada.getLat() + " " + melhorParada.getLong());
		System.out.println("");

	}
}
