package com.equalsp.stransthe;

import java.util.List;

public class Testes {

	public static void main(String[] args) throws Exception {
		InthegraService service = new InthegraService("ef5f05bdedd34cada40187761d5daaa7", "erickpassos@gmail.com",  "circ51sp");

		List<Linha> linhas = service.getLinhas("SAO CRISTOVAO");
		Linha linha = linhas.get(0);
		System.out.println(linha.getDenomicao());
		System.out.println("Veiculos: ");
		List<Veiculo> veiculos = service.getVeiculos(linha);
		for (Veiculo veiculo : veiculos) {
			System.out.println(veiculo.getCodigoVeiculo());
		}

		System.out.println("Paradas: ");
		List<Parada> paradas = service.getParadas(linha);
		for (Parada parada : paradas) {
			System.out.println(parada.getDenomicao());
		}
	}
}
