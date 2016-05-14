package com.equalsp.stransthe;

import java.util.ArrayList;
import java.util.List;

/**
 * Oferece acesso à API REST Inthegra do sistema de transporte coletivo da cidade de Teresina-PI.
 * Registro na API em https://inthegra.strans.teresina.pi.gov.br
 * @author Erick Passos
 *
 */
public class InthegraAPI {

	private static InthegraAgent tokenManager;

	/**
	 * [obrigatório] Inicializa a API antes de se fazerem buscas. 
	 * @param email - cadastrado ao se fazer o registro na API.
	 * @param senha - cadastrada ao se fazer o registro na API.
	 * @param key - chave de aplicação (cadastrada através site da API). 
	 * @return true - caso a autenticação funcione.
	 * @throws Exception - caso ocorra um erro (email, senha, ou problema técnico).
	 */
	public static boolean init(String email, String senha, String key) throws Exception {
		tokenManager = new InthegraAgent(email, senha, key);
		return tokenManager.autenticar();
	}

	/**
	 * Busca dados de todas as linhas cadastradas. 
	 * @return lista com todas as linhas.
	 * @throws Exception - caso não inicializado corretamente.
	 */
	public static List<Linha> getLinhas() throws Exception {
		if (tokenManager == null) {
			throw new Exception("Chame init(email, senha, key) antes...");
		}
		return tokenManager.getLinhas();
	}

	/**
	 * Busca dados de linhas com base na descrição enviada. 
	 * @return lista com as linhas que satisfazem o critério de busca.
	 * @throws Exception - caso não inicializado corretamente.
	 */
	public static List<Linha> getLinhas(String busca) throws Exception {
		if (tokenManager == null) {
			throw new Exception("Chame init(email, senha, key) antes...");
		}
		return tokenManager.getLinhas(busca);
	}

	/**
	 * Busca dados de todas as paradas cadastradas. 
	 * @return lista com todas as paradas.
	 * @throws Exception - caso não inicializado corretamente.
	 */
	public static List<Parada> getParadas() throws Exception {
		if (tokenManager == null) {
			throw new Exception("Chame init(email, senha, key) antes...");
		}
		return tokenManager.getParadas();
	}

	/**
	 * Busca dados de paradas com base na descrição enviada. 
	 * @return lista com as paradas que satisfazem o critério de busca.
	 * @throws Exception - caso não inicializado corretamente.
	 */
	public static List<Parada> getParadas(String busca) throws Exception {
		if (tokenManager == null) {
			throw new Exception("Chame init(email, senha, key) antes...");
		}
		return tokenManager.getParadas(busca);
	}

	/**
	 * Busca dados das paradas de uma linha específica. 
	 * @return lista com todas as paradas de uma linha.
	 * @throws Exception - caso não inicializado corretamente.
	 */
	public static List<Parada> getParadas(Linha linha) throws Exception {
		if (tokenManager == null) {
			throw new Exception("Chame init(email, senha, key) antes...");
		}
		ParadaLinha p = tokenManager.getParadasLinha(linha.getCodigoLinha());
		return p.getParadas();
	}

	/**
	 * Busca dados atualizados de todos os veículos cadastrados. 
	 * @return lista com todos os veículos.
	 * @throws Exception - caso não inicializado corretamente.
	 */
	public static List<Veiculo> getVeiculos() throws Exception {
		if (tokenManager == null) {
			throw new Exception("Chame init(email, senha, key) antes...");
		}
		List<Veiculo> v = new ArrayList<Veiculo>();
		List<VeiculoLinha> linhas = tokenManager.getVeiculos();
		for (VeiculoLinha veiculoLinha : linhas) {
			v.addAll(veiculoLinha.getLinha().getVeiculos());
		}
		return v;
	}

	/**
	 * Busca dados atualizados dos veículos de uma linha específica. 
	 * @return lista com todos os veículos de uma linha.
	 * @throws Exception - caso não inicializado corretamente.
	 */
	public static List<Veiculo> getVeiculos(Linha linha) throws Exception {
		if (tokenManager == null) {
			throw new Exception("Chame init(email, senha, key) antes...");
		}
		return tokenManager.getVeiculosLinha(linha.getCodigoLinha()).getLinha().getVeiculosFromJson();
	}

}
