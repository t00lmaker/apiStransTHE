package com.equalsp.stransthe;

import java.io.IOException;
import java.util.List;

public interface InthegraAPI {

	void initialize() throws IOException;

	List<Linha> getLinhas() throws IOException;

	List<Linha> getLinhas(String busca) throws IOException;

	List<Parada> getParadas() throws IOException;

	List<Parada> getParadas(String busca) throws IOException;

	List<Parada> getParadas(Linha linha) throws IOException;

	List<Linha> getLinhas(Parada parada) throws IOException;

	List<Veiculo> getVeiculos() throws IOException;

	List<Veiculo> getVeiculos(Linha linha) throws IOException;

}