package com.equalsp.stransthe;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SerializedInthegraService extends CachedInthegraService {

	public SerializedInthegraService(InthegraAPI delegate, long tempoExpiracao, TimeUnit unit) {
		super(delegate, tempoExpiracao, unit);
	}

	@Override
	public void initialize() throws IOException {
		if (System.currentTimeMillis() > expireAt) {
			lock.lock();
			try {
				boolean successfullyLoaded = loadFromFile();
				if (!successfullyLoaded) {
					refreshCache();
					expireAt = System.currentTimeMillis() + timeoutInMillis;
					saveCacheToFile();
				}
			} finally {
				lock.unlock();
			}
		}
	}
	
	private void saveCacheToFile() throws IOException {
		Gson gson = new GsonBuilder().create();
		JsonObject cachedJsonObject = new JsonObject();
		
		cachedJsonObject.addProperty("expireAt", gson.toJson(expireAt));
		
		JsonArray linhasParadasJsonArray = new JsonArray();
		for (Linha linha : cacheLinhaParadas.keySet()) {
			JsonObject linhaParadaJsonObject = new JsonObject();
			String linhaJson = gson.toJson(linha);
			
			List<Parada> paradas = cacheLinhaParadas.get(linha);
			JsonArray paradasJsonArray = new JsonArray();
			for (Parada parada : paradas) {
				String paradaJson = gson.toJson(parada);
				paradasJsonArray.add(paradaJson);
			}
			linhaParadaJsonObject.addProperty("linha", linhaJson);
			linhaParadaJsonObject.add("paradas", paradasJsonArray);
			
			linhasParadasJsonArray.add(linhaParadaJsonObject);
		}
		cachedJsonObject.add("linhasParadas", linhasParadasJsonArray);
		//cachedJsonObject.add("linhasParadas", gson.toJsonTree(cacheLinhaParadas));
		
		JsonArray paradasLinhasJsonArray = new JsonArray();
		for (Parada parada : cacheParadaLinhas.keySet()) {
			JsonObject paradaLinhasJsonObject = new JsonObject();
			String paradaJson = gson.toJson(parada);
			
			List<Linha> linhas = cacheParadaLinhas.get(parada);
			JsonArray linhasJsonArray = new JsonArray();
			for (Linha linha : linhas) {
				String linhaJson = gson.toJson(linha);
				linhasJsonArray.add(linhaJson);
			}
			paradaLinhasJsonObject.addProperty("parada", paradaJson);
			paradaLinhasJsonObject.add("linhas", linhasJsonArray);
			
			paradasLinhasJsonArray.add(paradaLinhasJsonObject);
		}
		cachedJsonObject.add("paradasLinhas", paradasLinhasJsonArray);
		//cachedJsonObject.add("paradasLinhas", gson.toJsonTree(cacheParadaLinhas));

		String cacheJson = gson.toJson(cachedJsonObject);
		Path path = Paths.get("cachedInthegraService.json");
		Files.deleteIfExists(path);
		Files.createFile(path);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(cacheJson);
		}
	}
	
	private boolean loadFromFile() throws IOException {
		Path path = Paths.get("cachedInthegraService.json");
		
		if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) ){
			String fileContent = new String(Files.readAllBytes(path));
			Gson gson = new GsonBuilder().create();
			JsonObject cacheJson = gson.fromJson(fileContent, JsonObject.class);
			expireAt = cacheJson.get("expireAt").getAsLong();
			
			if (System.currentTimeMillis() > expireAt) {
				return false;
			} else {
				cacheLinhaParadas.clear();
				cacheParadaLinhas.clear();
				
				JsonArray linhasParadas = cacheJson.getAsJsonArray("linhasParadas");
				for (int i = 0; i < linhasParadas.size(); ++i) {
					JsonObject jsonObject = linhasParadas.get(i).getAsJsonObject();
					Linha linha = gson.fromJson(jsonObject.get("linha").getAsString(), Linha.class);
					JsonArray paradasJsonArray = jsonObject.getAsJsonArray("paradas");
					
					List<Parada> paradasDaLinha = new ArrayList<>();
					for (int j = 0; j < paradasJsonArray.size(); j++) {
						JsonElement paradaObejct = paradasJsonArray.get(j);
						Parada parada = gson.fromJson(paradaObejct.getAsString(), Parada.class);
						paradasDaLinha.add(parada);
					}
					
					cacheLinhaParadas.put(linha, paradasDaLinha);
				}
				
				JsonArray paradasLinhas = cacheJson.getAsJsonArray("paradasLinhas");
				for (int i = 0; i < paradasLinhas.size(); ++i) {
					JsonObject jsonObject = paradasLinhas.get(i).getAsJsonObject();
					
					Parada parada = gson.fromJson(jsonObject.get("parada").getAsString(), Parada.class);
					JsonArray linhasJsonArray = jsonObject.getAsJsonArray("linhas");
					
					List<Linha> linhasDaParada = new ArrayList<>();
					for (int j = 0; j < linhasJsonArray.size(); j++) {
						JsonElement paradaObejct = linhasJsonArray.get(j);
						Linha linha = gson.fromJson(paradaObejct.getAsString(), Linha.class);
						linhasDaParada.add(linha);
					}
					cacheParadaLinhas.put(parada, linhasDaParada);
				}
				System.out.println("ok");
				return true;
			}
		} else {
			return false;
		}
	}
}