package com.equalsp.stransthe;

public class Credenciais {

	private final String email;
	private final String password;

	public Credenciais(String email, String password) {
		if (email == null) {
			throw new IllegalArgumentException("Email não pode ser null");
		}
		if (password == null) {
			throw new IllegalArgumentException("password não pode ser null");
		}
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
