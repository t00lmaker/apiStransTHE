package com.equalsp.stransthe;

public abstract class Localizacao {
	
	public double getDistancia(Localizacao outra) {
		return 0;
	}
	
	public double getDistanciaManhathan(Localizacao outra) {
		return 0;
	}
	
	public abstract double getLong();
	
	public abstract double getLat();

}
