package com.equalsp.stransthe;

import java.util.ArrayList;
import java.util.List;

import com.equalsp.stransthe.modelo.Linha;
import com.equalsp.stransthe.modelo.Parada;
import com.equalsp.stransthe.modelo.ParadaLinha;
import com.equalsp.stransthe.modelo.Veiculo;
import com.equalsp.stransthe.modelo.VeiculoLinha;

public class InthegraAPI {

	private static GerenciadorToken tokenManager;

	public static boolean init(String email, String senha, String key) throws Exception {
		tokenManager = new GerenciadorToken(email, senha, key);
		return tokenManager.autenticar();
	}

	public List<Linha> getLinhas() throws Exception {
		return tokenManager.getLinhas();
	}

	public List<Linha> getLinhas(String busca) throws Exception {
		return tokenManager.getLinhas(busca);
	}

	public List<Parada> getParadas() throws Exception {
		return tokenManager.getParadas();
	}

	public List<Parada> getParadas(String busca) throws Exception {
		return tokenManager.getParadas(busca);
	}

	
	public List<Parada> getParadas(Linha linha) throws Exception {
		ParadaLinha p = tokenManager.getParadasLinha(linha.getCodigo());
		return p.getParadas();
	}

	public static List<Veiculo> getVeiculos() throws Exception {
		List<Veiculo> v = new ArrayList<Veiculo>();
		List<VeiculoLinha> linhas = tokenManager.getVeiculos();
		for (VeiculoLinha veiculoLinha : linhas) {
			v.addAll(veiculoLinha.getLinha().getVeiculos());
		}
		return v;
	}

	public static List<Veiculo> getVeiculos(Linha linha) throws Exception {
		return tokenManager.getVeiculosLinha(linha.getCodigo()).getLinha().getVeiculos();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(InthegraAPI.init("luanpontes2@gmail.com", "naul1991", "49ea6f5525a34e71bdd7b4f8a92adaac"));
	}

}
