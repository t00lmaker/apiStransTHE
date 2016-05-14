package com.equalsp.stransthe;

import static com.equalsp.stransthe.Utils.dateFormated;
import static com.equalsp.stransthe.Utils.inputStreamToString;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import static com.equalsp.stransthe.Utils.*;

public class GerenciadorToken {
	
	public static final String URL_SERVICE = "https://api.inthegra.strans.teresina.pi.gov.br/v1/";

	private String email;
	private String senha;
	private String key;
	
	private String token;
	
	
	public GerenciadorToken(String email, String senha, String key) {
		this.email = email;
		this.senha = senha;
		this.key = key;
	}

	public String autenticar() throws Exception{
		String requestJson = "{\"email\": \""+email+"\",\"password\": \""+senha+"\"}";
		
		HttpURLConnection connection = criarConnection("signin");
		setBody(requestJson, connection);
		connection.connect();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 404){
			throw new IllegalArgumentException("A url "+URL_SERVICE+" é inválida. (Erro 404)");
		}else if(responseCode == 500){
			throw new RuntimeException("Erro interno do servidor da API. (Erro 500)");
		}
		String responseJson = inputStreamToString(connection.getInputStream());
		connection.disconnect();
		return responseJson;
	}

	private HttpURLConnection criarConnection(String _url)
			throws MalformedURLException, IOException, ProtocolException {
		
		URL url = new URL(URL_SERVICE+_url);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setConnectTimeout(15000);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept-Language", "en");
		connection.setRequestProperty("Date", dateFormated(new Date()));
		connection.setRequestProperty("X-Api-Key","49ea6f5525a34e71bdd7b4f8a92adaac");
		return connection;
	}
	
	public static void main(String[] args) throws Exception {
		GerenciadorToken g = new GerenciadorToken("luanpontes2@gmail.com", "naul1991", "49ea6f5525a34e71bdd7b4f8a92adaac");
		System.out.println(g.autenticar());
	}
	
	
}
