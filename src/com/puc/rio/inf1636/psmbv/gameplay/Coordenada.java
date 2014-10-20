package com.puc.rio.inf1636.psmbv.gameplay;

import java.util.ArrayList;

public class Coordenada {
	private int coordVert;
	private int coordHorz;
	private Peca p;
	
	public Coordenada(int x, int y){
		if(x > 7 || x < 0 || y > 7 || y < 0)
			throw new IllegalArgumentException("Coordenadas inválidas");
		
		this.setCoordHorz(x);
		this.coordVert = y;
		
		this.setPeca(null);
	}
	
	public Coordenada(int x, int y, Peca p){
		if(x > 7 || x < 0 || y > 7 || y < 0)
			throw new IllegalArgumentException("Coordenadas inválidas");
		
		this.setCoordHorz(x);
		this.coordVert = y;
		
		this.setPeca(p);
	}
	
	public ArrayList<Coordenada> getMoves(){
		if(this.p == null){
			System.out.println("Coordenada sem peça");
			return null;
		}
		return p.getPossibleMovements(this.coordVert, this.coordHorz);
		
	}

	public int getCoordVert() {
		return coordVert;
	}

	public void setCoordVert(int coordVert) {
		this.coordVert = coordVert;
	}

	public int getCoordHorz() {
		return coordHorz;
	}

	public void setCoordHorz(int coordHorz) {
		this.coordHorz = coordHorz;
	}

	public Peca getPeca() {
		return p;
	}

	public void setPeca(Peca p) {
		this.p = p;
	}
	
	public boolean isOccupied(){
		return this.p == null;
	}

}
