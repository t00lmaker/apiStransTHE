package com.equalsp.stransthe;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import static com.equalsp.stransthe.Utils.*;

/**
 * Classe responsavel por gerenciamento 
 * de requisições para a api. Ela cuida 
 * para que o token de atualização esteja 
 * sempre valido. 
 * 
 * @author toolmaker
 *
 */
public class GerenciadorDeRequisicoes {

	private static final String URL_SERVICE = "https://api.inthegra.strans.teresina.pi.gov.br/v1/";
	
	public String postRequest(String _url, Map<String,String> params) throws Exception{
		String requestJson = "{\"email\": \"luanpontes2@gmail.com\",\"password\": \"naul1991\"}";
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
		setBody(requestJson, connection);
		connection.connect();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 404){
			throw new IllegalArgumentException("A url "+_url+" é inválida. (Erro 404)");
		}else if(responseCode == 500){
			throw new RuntimeException("Erro interno do servidor da API. (Erro 500)");
		}
		String responseJson = inputStreamToString(connection.getInputStream());
		connection.disconnect();
		return responseJson;
	}
	

	

}
