package com.equalsp.stransthe;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class InthegraService implements InthegraAPI {

	private static final Type LIST_PARADAS = new TypeToken<List<Parada>>() {}.getType();
	private static final Type LIST_LINHAS = new TypeToken<List<Linha>>() {}.getType();
	private static final Type LIST_VEICULOS_LINHAS = new TypeToken<List<VeiculoLinha>>() {}.getType();

	private TokenInfo token;
	private final Credenciais credenciais;
	private final InthegraClient client;

	public InthegraService(InthegraClient client, Credenciais credenciais) {
		this.client = client;
		this.credenciais = credenciais;
	}

	public InthegraService(InthegraClient client, String email, String senha) {
		this(client, new Credenciais(email, senha));
	}

	public InthegraService(String key, String email, String senha) {
		this(new InthegraClient().setApiKey(key), new Credenciais(email, senha));
	}

	@Override
	public void initialize() throws IOException {
		updateToken();
	}

	private void updateToken() throws IOException {
		if (token == null || token.isExpired()) {
			token = client.post("/signin", credenciais, TokenInfo.class);
			client.setApiToken(token.getToken());
		}
	}

	@Override
	public List<Linha> getLinhas() throws IOException {
		return getLinhas(null);
	}

	@Override
	public List<Linha> getLinhas(String busca) throws IOException {
		return client.get(getPathComBusca("/linhas", busca), LIST_LINHAS);
	}

	@Override
	public List<Parada> getParadas() throws IOException {
		return getParadas((String)null);
	}

	@Override
	public List<Parada> getParadas(String busca) throws IOException {
		return client.get(getPathComBusca("/paradas", busca), LIST_PARADAS);
	}

	@Override
	public List<Parada> getParadas(Linha linha) throws IOException {
		ParadaLinha pl = client.get(getPathComBusca("/paradasLinha", linha.getCodigoLinha()), ParadaLinha.class);
		return pl.getParadas();
	}

	@Override
	public List<Veiculo> getVeiculos() throws IOException {
		List<Veiculo> v = new ArrayList<Veiculo>();
		List<VeiculoLinha> linhas = client.get("/veiculos", LIST_VEICULOS_LINHAS);
		for (VeiculoLinha veiculoLinha : linhas) {
			v.addAll(veiculoLinha.getLinha().getVeiculos());
		}
		return v;
	}

	@Override
	public List<Veiculo> getVeiculos(Linha linha) throws IOException {
		VeiculoLinha vl = client.get(getPathComBusca("/veiculosLinha", linha.getCodigoLinha()), VeiculoLinha.class);
		return vl.getLinha().getVeiculos();
	}

	private String getPathComBusca(String path, String busca) throws IOException {
		return busca == null ? path : path + "?busca=" + URLEncoder.encode(busca, "UTF-8");
	}

}