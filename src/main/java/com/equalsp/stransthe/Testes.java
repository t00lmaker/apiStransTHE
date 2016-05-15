package com.equalsp.stransthe;

import java.util.ArrayList;
import java.util.List;

public class Testes {

	public static void main(String[] args) throws Exception {
		InthegraAPI.init("erickpassos@gmail.com", "circ51sp", "ef5f05bdedd34cada40187761d5daaa7");
		
		List<Veiculo> v = InthegraAPI.getVeiculos();
		System.out.println(v.size());
//		List<String> boas = new ArrayList<String>();
//		List<String> ruins = new ArrayList<String>();
//		List<Linha> linhas = InthegraAPI.getLinhas();
//		for (Linha linha : linhas) {
//			
//			try {
//				linha.getVeiculos();
//				boas.add(linha.getCodigoLinha());
//			} catch (Exception e) {
//				ruins.add(linha.getCodigoLinha());
//				System.out.println(linha.getCodigoLinha() + " - " + linha.getDenomicao());
//			}
//		}
//		
//		System.out.println("ruins: " + ruins.size());
//		for (String linha : ruins) {
//			System.out.println(linha);
//		}
//		System.out.println("\nboas: " + boas.size());
//		for (String linha : boas) {
//			System.out.println(linha);
//		}
	}
}
