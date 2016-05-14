package com.equalsp.stransthe;

import static com.equalsp.stransthe.Utils.dateFormated;
import static com.equalsp.stransthe.Utils.inputStreamToString;
import static com.equalsp.stransthe.Utils.setBody;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

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

	public boolean autenticar() throws Exception{
		String requestJson = "{\"email\": \""+email+"\",\"password\": \""+senha+"\"}";
		
		HttpURLConnection connection = criarConnection("signin", "POST");
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
		Map<String, String> response = Utils.jsonInMap(responseJson);
		this.token = response.get("token"); 
		return (token != null);
	}
	
	//Provisorio, esse metodo nao deve ta aqui, TODO remover
	public String getParadas() throws Exception {
		HttpURLConnection connection = criarConnection("paradas", "GET");
		
		return null;
	} 
	

	private HttpURLConnection criarConnection(String _url, String method)
			throws MalformedURLException, IOException, ProtocolException {
		
		URL url = new URL(URL_SERVICE+_url);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
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
		g.autenticar();
		System.out.println(g.token);
	}
	
	
}
