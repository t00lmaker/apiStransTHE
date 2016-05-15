package com.equalsp.stransthe.cache;

import java.util.HashMap;

import com.equalsp.stransthe.InthegraAPI;
import com.equalsp.stransthe.Linha;
import com.equalsp.stransthe.Parada;

public class CacheManager {

	public static HashMap<String, Linha> linhas = new HashMap<String, Linha>();
	public static HashMap<String, Parada> paradas = new HashMap<String, Parada>();
	
	// ineficient... colocar em BD depois (dados sao imutaveis)
	public static void buildCache() throws Exception {
		for (Linha l : InthegraAPI.getLinhas()) {
			linhas.put(l.getCodigoLinha(), l);
			try {
				for (Parada p : l.getParadas()) {
					if (paradas.containsKey(p.getCodigoParada()))
						p = paradas.get(p.getCodigoParada());
					else
						paradas.put(p.getCodigoParada(), p);
					p.getLinhas().add(l);
				}
			} catch (Exception e) {
			}
			// Paradas podem ser objetos novos
			l.getParadas().clear();
		}
		// reconstruindo relacao por parte de linhas...
		for (Parada p : paradas.values()) {
			for (Linha l : p.getLinhas()) {
				l.getParadas().add(p);
			}
		}
		
		// TODO salvar em BD local o cache...
	}
}
