package com.equalsp.stransthe;

import static com.equalsp.stransthe.Utils.dateFormated;
import static com.equalsp.stransthe.Utils.inputStreamToString;
import static com.equalsp.stransthe.Utils.setBody;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.equalsp.stransthe.modelo.Linha;
import com.equalsp.stransthe.modelo.Parada;
import com.equalsp.stransthe.modelo.ParadaLinha;
import com.equalsp.stransthe.modelo.VeiculoLinha;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GerenciadorToken {

	public static final String URL_SERVICE = "https://api.inthegra.strans.teresina.pi.gov.br/v1/";

	private String email;
	private String senha;
	private String key;

	private TokenInfo token;

	public GerenciadorToken(String email, String senha, String key) {
		this.email = email;
		this.senha = senha;
		this.key = key;
	}

	public boolean autenticar() throws Exception {
		String requestJson = "{\"email\": \"" + email + "\",\"password\": \"" + senha + "\"}";

		HttpURLConnection connection = criarConnection("signin", "POST");
		setBody(requestJson, connection);
		connection.connect();

		int responseCode = connection.getResponseCode();
		if (responseCode == 404) {
			throw new IllegalArgumentException("A url " + URL_SERVICE + " é inválida. (Erro 404)");
		} else if (responseCode == 500) {
			throw new RuntimeException("Erro interno do servidor da API. (Erro 500)");
		}
		String responseJson = inputStreamToString(connection.getInputStream());
		connection.disconnect();
		Gson g = new Gson();
		this.token = g.fromJson(responseJson, TokenInfo.class);
		return (token != null);
	}

	private void updateToken() throws Exception {
		if (token.isExpired())
			autenticar();
	}

	// Provisorio, esse metodo nao deve ta aqui, TODO remover
	public List<Parada> getParadas() throws Exception {
		updateToken();
		HttpURLConnection connection = criarConnection("paradas", "GET");
		String responseJson = inputStreamToString(connection.getInputStream());
		System.out.println(responseJson);
		Type listType = new TypeToken<List<Parada>>() {
		}.getType();
		List<Parada> yourList = new Gson().fromJson(responseJson, listType);
		connection.disconnect();
		return yourList;
	}

	// Provisorio, esse metodo nao deve ta aqui, TODO remover
	public List<Parada> getParadas(String busca) throws Exception {
		updateToken();
		HttpURLConnection connection = criarConnection("paradas?busca=" + busca, "GET");
		String responseJson = inputStreamToString(connection.getInputStream());
		System.out.println(responseJson);
		Type listType = new TypeToken<List<Parada>>() {
		}.getType();
		List<Parada> yourList = new Gson().fromJson(responseJson, listType);
		connection.disconnect();
		return yourList;
	}

	// Provisorio, esse metodo nao deve ta aqui, TODO remover
	public ParadaLinha getParadasLinha(String linha) throws Exception {
		updateToken();
		HttpURLConnection connection = criarConnection("paradasLinha?busca=" + linha, "GET");
		String responseJson = inputStreamToString(connection.getInputStream());
		System.out.println(responseJson);
		ParadaLinha yourList = new Gson().fromJson(responseJson, ParadaLinha.class);
		connection.disconnect();
		return yourList;
	}

	// Provisorio, esse metodo nao deve ta aqui, TODO remover
	public List<Linha> getLinhas() throws Exception {
		updateToken();
		HttpURLConnection connection = criarConnection("linhas", "GET");
		String responseJson = inputStreamToString(connection.getInputStream());
		Type listType = new TypeToken<List<Linha>>() {
		}.getType();
		System.out.println(responseJson);
		List<Linha> yourList = new Gson().fromJson(responseJson, listType);
		connection.disconnect();
		return yourList;
	}

	// Provisorio, esse metodo nao deve ta aqui, TODO remover
	public List<Linha> getLinhas(String busca) throws Exception {
		updateToken();
		HttpURLConnection connection = criarConnection("linhas?busca=" + busca, "GET");
		String responseJson = inputStreamToString(connection.getInputStream());
		Type listType = new TypeToken<List<Linha>>() {
		}.getType();
		System.out.println(responseJson);
		List<Linha> yourList = new Gson().fromJson(responseJson, listType);
		connection.disconnect();
		return yourList;
	}

	// Provisorio, esse metodo nao deve ta aqui, TODO remover
	public List<VeiculoLinha> getVeiculos() throws Exception {
		updateToken();
		HttpURLConnection connection = criarConnection("veiculos", "GET");
		String responseJson = inputStreamToString(connection.getInputStream());
		Type listType = new TypeToken<List<VeiculoLinha>>() {
		}.getType();
		System.out.println(responseJson);
		List<VeiculoLinha> yourList = new Gson().fromJson(responseJson, listType);
		connection.disconnect();
		return yourList;
	}

	// Provisorio, esse metodo nao deve ta aqui, TODO remover
	public VeiculoLinha getVeiculosLinha(String linha) throws Exception {
		updateToken();
		HttpURLConnection connection = criarConnection("veiculosLinha?busca=" + linha, "GET");
		String responseJson = inputStreamToString(connection.getInputStream());
		System.out.println(responseJson);
		VeiculoLinha yourList = new Gson().fromJson(responseJson, VeiculoLinha.class);
		connection.disconnect();
		return yourList;
	}

	private HttpURLConnection criarConnection(String _url, String method)
			throws MalformedURLException, IOException, ProtocolException {

		URL url = new URL(URL_SERVICE + _url);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setConnectTimeout(15000);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept-Language", "en");
		connection.setRequestProperty("Date", dateFormated(new Date()));
		connection.setRequestProperty("X-Api-Key", "49ea6f5525a34e71bdd7b4f8a92adaac");
		if (token != null)
			connection.setRequestProperty("X-Auth-Token", token.getToken());

		return connection;
	}

	public static void main(String[] args) throws Exception {
		GerenciadorToken g = new GerenciadorToken("luanpontes2@gmail.com", "naul1991",
				"49ea6f5525a34e71bdd7b4f8a92adaac");
		g.autenticar();
		// g.getParadas();
		g.getLinhas("dirceu");

	}

}
