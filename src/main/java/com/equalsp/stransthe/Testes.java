package com.equalsp.stransthe;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Testes {

	public static void main(String[] args) throws Exception {
		InthegraAPI service = new InthegraService("ef5f05bdedd34cada40187761d5daaa7", "erickpassos@gmail.com",  "circ51sp");

		List<Linha> linhas = service.getLinhas("FREI SERAFIM");
		Linha linha = linhas.get(0);
		System.out.println(linha.getDenomicao());
		/*System.out.println("Veiculos: ");
		List<Veiculo> veiculos = service.getVeiculos(linha);
		for (Veiculo veiculo : veiculos) {
			System.out.println(veiculo.getCodigoVeiculo());
		}*/

		System.out.println("Paradas: ");
		List<Parada> paradas = service.getParadas("FREI");
		for (Parada parada : paradas) {
			System.out.println(parada.getCodigoParada() + ": " + parada.getDenomicao());
		}

		CachedInthegraService cachedService = new CachedInthegraService(service, 1, TimeUnit.DAYS);
		List<Parada> paradasFreiSerafim1 = cachedService.getParadas("AV. FREI SERAFIM 1");
		for (Parada parada : paradasFreiSerafim1) {
			System.out.println(parada.getDenomicao());
		}

		Parada paradaFreiSerafim1 = paradasFreiSerafim1.get(0);
		System.out.println("Linhas da parada " + paradaFreiSerafim1);
		for (Linha l : cachedService.getLinhas(paradaFreiSerafim1)) {
			System.out.println(l.getDenomicao());
		}
	}
}
