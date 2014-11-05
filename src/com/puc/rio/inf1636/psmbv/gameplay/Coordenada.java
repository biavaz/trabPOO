package com.puc.rio.inf1636.psmbv.gameplay;

import java.io.Serializable;
import java.util.ArrayList;

public class Coordenada implements Serializable{

	private static final long serialVersionUID = 1L;
	private int coordVert;
	private int coordHorz;
	private Peca p;
	
	public Coordenada(int x, int y){
		if(x > 7 || x < 0 || y > 7 || y < 0)
			throw new IllegalArgumentException("Coordenadas inv‡lidas");
		
		this.setCoordHorz(x);
		this.coordVert = y;
		
		this.setPeca(null);
	}
	
	public Coordenada(int x, int y, Peca p){
		if(x > 7 || x < 0 || y > 7 || y < 0)
			throw new IllegalArgumentException("Coordenadas inv‡lidas");
		
		this.setCoordHorz(x);
		this.coordVert = y;
		
		this.setPeca(p);
	}
	
	public ArrayList<Coordenada> getMoves(){
		if(this.p == null){
			System.out.println("Coordenada sem pe�a");
			return null;
		}
		return p.getPossibleMovements(this.coordHorz, this.coordVert);
		
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
	
	@Override
	public String toString(){
		String coord = "X = " + this.getCoordHorz() + " Y " +
				"= " + this.getCoordVert() + " Peça = " + ((this.getPeca()!= null)?this.getPeca().getName(): " vazio") + " Cor = " +((this.getPeca()!= null)?this.getPeca().getColor(): " vazio") ;
	
		return coord;
	}
	
	@Override
	public boolean equals(Object arg0) {
		Coordenada c = (Coordenada) arg0;
		return this.getCoordHorz() == c.getCoordHorz() && this.getCoordVert() == c.getCoordVert();
	}

}
