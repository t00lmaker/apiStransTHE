package com.equalsp.stransthe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CachedInthegraService implements InthegraAPI {

	private static final int ITEM_NOT_FOUND = 130;

	private final InthegraAPI delegate;
	private final long timeoutInMillis;
	private final ReentrantLock lock = new ReentrantLock();
	private long expireAt;

	private Map<Linha, List<Parada>> cacheLinhaParadas = new HashMap<>();
	private Map<Parada, List<Linha>> cacheParadaLinhas = new HashMap<>();

	public CachedInthegraService(InthegraAPI delegate, long tempoExpiracao, TimeUnit unit) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate não pode ser null");
		}
		this.delegate = delegate;
		this.timeoutInMillis = unit.toMillis(tempoExpiracao);
	}

	@Override
	public void initialize() throws IOException {
		if (System.currentTimeMillis() > expireAt) {
			lock.lock();
			try {
				refreshCache();
				expireAt = System.currentTimeMillis() + timeoutInMillis;
			} finally {
				lock.unlock();
			}
		}
	}

	private void refreshCache() throws IOException {
		cacheLinhaParadas.clear();
		cacheParadaLinhas.clear();

		List<Linha> linhas = delegate.getLinhas();
		List<Parada> paradas;
		for (Linha linha : linhas) {
			try {
				paradas = delegate.getParadas(linha);
				cacheLinhaParadas.put(linha, paradas);

				for (Parada parada : paradas) {
					List<Linha> linhasDaParada = cacheParadaLinhas.get(parada);
					if (linhasDaParada == null) {
						linhasDaParada = new ArrayList<>();
						cacheParadaLinhas.put(parada, linhasDaParada);
					}
					linhasDaParada.add(linha);
				}

			} catch (InthegraException e) {
				if (e.getErro().getCode() != ITEM_NOT_FOUND) {
					throw e;
				}
			}
		}
	}

	@Override
	public List<Linha> getLinhas() throws IOException {
		initialize();
		return new ArrayList<>(cacheLinhaParadas.keySet());
	}

	@Override
	public List<Linha> getLinhas(String busca) throws IOException {
		List<Linha> linhas = new ArrayList<>();
		for (Linha linha : getLinhas()) {
			if (linha.getCodigoLinha().equals(busca) || linha.getDenomicao().contains(busca)) {
				linhas.add(linha);
			}
		}
		return linhas;
	}

	@Override
	public List<Parada> getParadas() throws IOException {
		initialize();
		return new ArrayList<>(cacheParadaLinhas.keySet());
	}

	@Override
	public List<Parada> getParadas(String busca) throws IOException {
		List<Parada> paradas = new ArrayList<>();
		for (Parada parada : getParadas()) {
			if (parada.getCodigoParada().equals(busca) || parada.getDenomicao().contains(busca)) {
				paradas.add(parada);
			}
		}
		return paradas;
	}

	@Override
	public List<Parada> getParadas(Linha linha) throws IOException {
		initialize();
		return cacheLinhaParadas.get(linha);
	}

	@Override
	public List<Linha> getLinhas(Parada parada) throws IOException {
		initialize();
		return cacheParadaLinhas.get(parada);
	}

	@Override
	public List<Veiculo> getVeiculos() throws IOException {
		return delegate.getVeiculos();
	}

	@Override
	public List<Veiculo> getVeiculos(Linha linha) throws IOException {
		return delegate.getVeiculos(linha);
	}

}