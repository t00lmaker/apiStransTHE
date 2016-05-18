package com.equalsp.stransthe;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.equalsp.stransthe.rotas.PontoDeInteresse;
import com.equalsp.stransthe.rotas.Rota;
import com.equalsp.stransthe.rotas.RotaService;
import com.equalsp.stransthe.rotas.Trecho;

public class Testes {

	public static void main(String[] args) throws Exception {
		InthegraAPI service = new InthegraService("aa91935448534d519da1cda34d0b1ee4", "c2387331@trbvn.com", "c2387331@trbvn.com");
		DesktopFileHanlder fileHanlder = new DesktopFileHanlder();
		CachedInthegraService cachedService = new CachedInthegraService(service, fileHanlder, 1, TimeUnit.DAYS);

		/*

		List<Linha> linhas = service.getLinhas("FREI SERAFIM");
		Linha linha = linhas.get(0);
		System.out.println(linha.getDenomicao());
		System.out.println("Veiculos: ");
		List<Veiculo> veiculos = service.getVeiculos(linha);
		for (Veiculo veiculo : veiculos) {
			System.out.println(veiculo.getCodigoVeiculo());
		}

		System.out.println("Paradas: ");
		List<Parada> paradas = service.getParadas("FREI");
		for (Parada parada : paradas) {
			System.out.println(parada.getCodigoParada() + ": " + parada.getDenomicao());
		}*/

		List<Parada> paradasFreiSerafim1 = cachedService.getParadas("AV. FREI SERAFIM 1");
		for (Parada parada : paradasFreiSerafim1) {
			System.out.println(parada.getDenomicao());
		}

		Parada paradaFreiSerafim1 = paradasFreiSerafim1.get(0);
		System.out.println("Linhas da parada " + paradaFreiSerafim1);
		for (Linha l : cachedService.getLinhas(paradaFreiSerafim1)) {
			System.out.println(l.getDenomicao());
		}
//		CachedInthegraService cachedService = new CachedInthegraService(service, 1, TimeUnit.DAYS);
//		List<Parada> paradasFreiSerafim1 = cachedService.getParadas("AV. FREI SERAFIM 1");
//		for (Parada parada : paradasFreiSerafim1) {
//			System.out.println(parada.getDenomicao());
//		}

//		Parada paradaFreiSerafim1 = paradasFreiSerafim1.get(0);
//		System.out.println("Linhas da parada " + paradaFreiSerafim1);
//		for (Linha l : cachedService.getLinhas(paradaFreiSerafim1)) {
//			System.out.println(l.getDenomicao());
//		}
		
//		RotaService rotaService = new RotaService(cachedService);
//		PontoDeInteresse a = new PontoDeInteresse(-5.080375, -42.775798);
//		PontoDeInteresse b = new PontoDeInteresse(-5.089095, -42.810302);
//		Set<Rota> rotas = rotaService.getRotas(a, b, 200);
//		for (Rota rota : rotas) {
//			Trecho t = rota.getTrechos().get(1);
//			System.out.println(t.getLinha().getCodigoLinha() + " - " + t.getLinha().getDenomicao());
//			System.out.println(t.getOrigem());
//			System.out.println(t.getDestino());
//			System.out.println(rota.getDistanciaTotal());
//		}
//		
//		System.out.println();
//		System.out.println("Parada a parada:");
//		Parada p2 = paradasFreiSerafim1.get(paradasFreiSerafim1.size()-1);
//		Set<Rota> rotas2 = rotaService.getRotas(paradaFreiSerafim1, p2);
//		for (Rota rota : rotas2) {
//			Trecho t = rota.getTrechos().get(0);
//			System.out.println(t.getLinha().getCodigoLinha() + " - " + t.getLinha().getDenomicao());
//			System.out.println(t.getOrigem());
//			System.out.println(t.getDestino());
//			System.out.println(rota.getDistanciaTotal());
//		}
	}
}
