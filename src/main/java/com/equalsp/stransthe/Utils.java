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
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Alguns métodos utilitários. 
 * 
 * @author toolmaker
 *
 */
class Utils {
	
	private static final String DATA_FORMAT = "E, dd MMM yyyy HH:mm:ss 'GMT'";

	/**
	 * Retorna retorna um String a partir de um InputStream. 
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	static String inputStreamToString(InputStream is) throws IOException {
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
	
	static String dateFormated(Date data){
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
	static void setBody(String body, HttpURLConnection connection) 
				throws IOException, UnsupportedEncodingException {
		DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
		stream.write(body.getBytes("UTF-8"));
		stream.flush();
		stream.close();
	}
	
	static boolean expired(Date date, int seconds) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.SECOND, seconds);
		Date time = new Date();
		return time.compareTo(c.getTime()) >= 0;
	}
	
//	public static void main(String[] args) {
//		System.out.println(dateFormated(new Date()));
//	}
//	
//	public static Map<String, String> jsonInMap(String json){
//		Type type = new TypeToken<Map<String, String>>(){}.getType();
//		return new Gson().fromJson(json, type);
//	}
}
