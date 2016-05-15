package com.equalsp.stransthe;

import java.io.IOException;

public class InthegraException extends IOException {

	private static final long serialVersionUID = -2109859310947295074L;

	private final Erro erro;

	public InthegraException(Erro erro) {
		this.erro = erro;
	}

	public Erro getErro() {
		return erro;
	}

	@Override
	public String getMessage() {
		return getErro().getMessage();
	}
}
