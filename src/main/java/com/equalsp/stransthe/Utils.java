package com.equalsp.stransthe;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Alguns métodos utilitários. 
 * 
 * @author toolmaker
 *
 */
public class Utils {
	
	private static final String DATA_FORMAT = "E, dd MMM yyyy HH:mm:ss 'GMT'";

	/**
	 * Retorna retorna um String a partir de um InputStream. 
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	
	public static String dateFormated(Date data){
		SimpleDateFormat formatter = new SimpleDateFormat(DATA_FORMAT,Locale.US);
		return formatter.format(data);
	}
	
	
	/**
	 * Seta o conteúdo (body) passado por parametro
	 * na conexão também passada. 
	 * 
	 * @param body
	 * @param connection
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void setBody(String body, HttpURLConnection connection) 
				throws IOException, UnsupportedEncodingException {
		DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
		stream.write(body.getBytes("UTF-8"));
		stream.flush();
		stream.close();
	}
	
	public static void main(String[] args) {
		System.out.println(dateFormated(new Date()));
	}
	
	public static Map<String, String> jsonInMap(String json){
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		return new Gson().fromJson(json, type);
	}
}
