package com.equalsp.stransthe;

public class InthegraAPI {
	
	private static GerenciadorToken tokenManager;
	
	private static TokenInfo currentToken = null;
	
	public static boolean Init(String email, String senha, String key) throws Exception {
		tokenManager = new GerenciadorToken(email, senha, key);
		String tokenJson = tokenManager.autenticar();
		// TODO desserialize from json
		currentToken = new TokenInfo("", 10 * 60);
		return !currentToken.isExpired();
	}
	
	public static boolean Connected() {
		return currentToken != null && !currentToken.isExpired();
	}
	
	

}
