package com.equalsp.stransthe;

import java.util.List;

import com.equalsp.stransthe.modelo.Linha;
import com.equalsp.stransthe.modelo.Veiculo;
import com.google.gson.Gson;

public class InthegraAPI {

	private static GerenciadorToken tokenManager;

	public static boolean init(String email, String senha, String key) throws Exception {
		tokenManager = new GerenciadorToken(email, senha, key);
		return tokenManager.autenticar();
	}

	public List<Linha> getLinhas() {
		return null;
	}

	public List<Linha> getLinhas(String termo) {
		return null;
	}

	public static List<Veiculo> getVeiculos(String linha) {
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(InthegraAPI.init("luanpontes2@gmail.com", "naul1991", "49ea6f5525a34e71bdd7b4f8a92adaac"));
	}

}
