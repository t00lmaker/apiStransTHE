package com.equalsp.stransthe;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class InthegraClient {

	public static final String API_VERSION = "v1";
	public static final String URL_SERVICE = "https://api.inthegra.strans.teresina.pi.gov.br/" + API_VERSION;

	private static final String HEADER_USER_AGENT = "User-Agent";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final String HEADER_CONTENT_LENGTH = "Content-Length";
	private static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
	private static final String HEADER_DATE = "Date";
	private static final String HEADER_API_KEY = "X-Api-Key";
	private static final String HEADER_API_TOKEN = "X-Auth-Token";

	private static final String METHOD_GET = "GET";
	private static final String METHOD_POST = "POST";

	private static final String CHARSET_UTF8 = "UTF-8";
	private static final String USER_AGENT = "InthegraJava/0.1.0";
	private static final String CONTENT_TYPE_JSON = "application/json";
	private static final String LANGUAGE = "en";
	private static final String DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss 'GMT'";

	protected Gson gson = new Gson();

	private String userAgent = USER_AGENT;
	private String language = LANGUAGE;
	private int bufferSize = 8192;
	private String apiKey;
	private String apiToken;

	public String getApiKey() {
		return apiKey;
	}

	public InthegraClient setApiKey(String apiKey) {
		if (apiKey == null) {
			throw new IllegalArgumentException("apiKey não pode ser null");
		}
		this.apiKey = apiKey;
		return this;
	}

	public String getApiToken() {
		return apiToken;
	}

	public InthegraClient setApiToken(String apiToken) {
		if (apiKey == null) {
			throw new IllegalArgumentException("apiToken não pode ser null");
		}
		this.apiToken = apiToken;
		return this;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public InthegraClient setUserAgent(String userAgent) {
		this.userAgent = userAgent;
		return this;
	}

	public String getLanguage() {
		return language;
	}

	public InthegraClient setLanguage(String language) {
		this.language = language;
		return this;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public InthegraClient setBufferSize(int bufferSize) {
		if (bufferSize < 1) {
			throw new IllegalArgumentException("bufferSize deve ser maior que 0");
		}

		this.bufferSize = bufferSize;
		return this;
	}

	public <T> T get(String path, Type returnType) throws IOException {
		return request(METHOD_GET, path, null, returnType);
	}

	public <T> T get(String path, Object params, Type returnType) throws IOException {
		return request(METHOD_GET, path, params, returnType);
	}

	public <T> T post(String path, Object params, Type returnType) throws IOException {
		return request(METHOD_POST, path, params, returnType);
	}

	protected <T> T request(String method, String path, Object params, Type returnType) throws IOException {
		HttpURLConnection connection = createConnection(method, path);
		if (params != null) {
			sendParams(connection, params);
		}

		int code = connection.getResponseCode();
		if (isOk(code)) {
			return parseJson(getStream(connection), returnType);
		}

		if (isEmpty(code)) {
			return null;
		}

		throw createException(getStream(connection), code, connection.getResponseMessage());
	}

	protected HttpURLConnection createConnection(String method, String path) throws IOException {
		URL url = new URL(URL_SERVICE + path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		return configureConnection(connection);
	}

	protected HttpURLConnection configureConnection(HttpURLConnection connection) {
		connection.setUseCaches(false);
		connection.setRequestProperty(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
		connection.setRequestProperty(HEADER_USER_AGENT, getUserAgent());
		connection.setRequestProperty(HEADER_ACCEPT_LANGUAGE, getLanguage());
		connection.setRequestProperty(HEADER_DATE, dateFormated(new Date()));
		if (getApiKey() != null) {
			connection.setRequestProperty(HEADER_API_KEY, getApiKey());
		}
		if (getApiToken() != null) {
			connection.setRequestProperty(HEADER_API_TOKEN, getApiToken());
		}
		return connection;
	}

	protected void sendParams(HttpURLConnection connection, Object params) throws IOException {
		connection.setDoOutput(true);
		if (params != null) {
			byte[] data = toJson(params).getBytes(CHARSET_UTF8);
			connection.setFixedLengthStreamingMode(data.length);
			BufferedOutputStream output = new BufferedOutputStream(connection.getOutputStream(), getBufferSize());
			try {
				output.write(data);
				output.flush();
			} finally {
				try {
					output.close();
				} catch (IOException ignored) {
					// Ignored
				}
			}
		} else {
			connection.setFixedLengthStreamingMode(0);
			connection.setRequestProperty(HEADER_CONTENT_LENGTH, "0");
		}
	}

	protected IOException createException(InputStream inputStream, int code, String status) throws IOException {
		if (isError(code)) {
			Erro erro = parseJson(inputStream, Erro.class);
			if (erro != null) {
				return new InthegraException(erro);
			}
		}

		String message;
		if (status != null && status.length() > 0) {
			message = status + " (" + code + ')';
		} else {
			message = "Unknown error occurred (" + code + ')';
		}
		return new IOException(message);
	}

	protected boolean isOk(int code) {
		return code == HTTP_OK;
	}

	protected boolean isEmpty(int code) {
		return code == HTTP_NO_CONTENT;
	}

	protected boolean isError(int code) {
		switch (code) {
		case HTTP_BAD_REQUEST:
		case HTTP_UNAUTHORIZED:
		case HTTP_FORBIDDEN:
			return true;
		default:
			return false;
		}
	}

	protected InputStream getStream(HttpURLConnection request) throws IOException {
		if (request.getResponseCode() < HTTP_BAD_REQUEST)
			return request.getInputStream();
		else {
			InputStream stream = request.getErrorStream();
			return stream != null ? stream : request.getInputStream();
		}
	}

	protected String toJson(Object object) throws IOException {
		try {
			return gson.toJson(object);
		} catch (JsonParseException e) {
			throw new IOException("Parse exception converting object to JSON", e);
		}
	}

	protected <V> V parseJson(InputStream stream, Type type) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, CHARSET_UTF8), getBufferSize());
		try {
			return gson.fromJson(reader, type);
		} finally {
			reader.close();
		}
	}

	protected String dateFormated(Date data) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		return formatter.format(data);
	}

}
