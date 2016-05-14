package com.equalsp.stransthe;

public class InthegraAPI {
	
	private static GerenciadorToken tokenManager;
	
	public static boolean Init(String email, String senha, String key) throws Exception {
		tokenManager = new GerenciadorToken(email, senha, key);
		String tokenJson = tokenManager.autenticar();
		return false;
	}
	
	public static boolean Connected() {
		return false;
	}
	
	

}
