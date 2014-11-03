package com.puc.rio.inf1636.psmbv.gameplay;

import java.util.LinkedList;

public class MatrizPecas {
	
	private LinkedList<LinkedList<Coordenada>> vertList = new LinkedList<LinkedList<Coordenada>>();
	
	public MatrizPecas(){
		for(int i = 0; i < 8; i++){
			LinkedList<Coordenada> horzList = null;
			if(i == 0 || i == 7 || i == 1 || i == 6)
				horzList = setRow(i);
			else 
				horzList = setEmptyRow(i);
			
			vertList.add(horzList);
		}
	}

	private LinkedList<Coordenada> setEmptyRow(int y) {
		LinkedList<Coordenada> l = new LinkedList<Coordenada>();
		for(int x = 0; x<8; x++){
			l.add(new Coordenada(x, y, null));
		}
		return l;
	}

	private LinkedList<Coordenada> setRow(int y) {
		LinkedList<Coordenada> l = new LinkedList<Coordenada>();
		for(int x = 0; x<8; x++){
			Peca p = Peca.createByInicialPosition(x, y);
			l.add(new Coordenada(x, y, p));
		}
		return l;
	}
	
	public Coordenada getCoordenada(int x, int y){
		return this.vertList.get(y).get(x);
	}

	public void moveTo(Coordenada c, int x, int y) {
		Coordenada c1 = this.vertList.get(c.getCoordVert()).set(c.getCoordHorz(), new Coordenada(c.getCoordVert(), c.getCoordHorz(), null));
		c1.setCoordHorz(x);
		c1.setCoordVert(y);
		
		this.vertList.get(y).set(x, c1);
	}

}
