package com.puc.rio.inf1636.psmbv.gameplay.pecas;

import java.util.ArrayList;

import com.puc.rio.inf1636.psmbv.gameplay.Coordenada;
import com.puc.rio.inf1636.psmbv.gameplay.Peca;
import com.puc.rio.inf1636.psmbv.gameplay.Tabuleiro;

public class Rainha extends Peca {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Rainha(char c) {
		super(c);
		this.setName("Rainha");
		/*if(c == 'p')
			this.icon = new ImageIcon ("pecas/p_dama.png").getImage();
		else
			this.icon = new ImageIcon ("pecas/b_dama.png").getImage();*/
	}
	
	//movimento bispo + movimento torre
	public ArrayList<Coordenada> getPossibleMovements(int x, int y) {
		ArrayList<Coordenada> coords = new ArrayList<Coordenada>();
		Tabuleiro t = Tabuleiro.getInstance();
		
		
		//movimento bispo
		int cont=0;
		for(int vert = y+1; vert<8; vert++){
			cont++;
			if(x+cont < 8 ){
				Peca p = t.getPecaAtCoordenada(x+cont, vert);
				if(p == null) 
					coords.add(t.getCoordenada(x+cont, vert));
				else if (p.getColor() != this.color) {
					coords.add(t.getCoordenada(x+cont, vert));
					break;
				}
				else
					break;
			}
		}
		
		cont=0;
		for(int vert = y-1; vert>=0; vert--){
			cont++;
			if(x+cont < 8 ){
				Peca p = t.getPecaAtCoordenada(x+cont, vert);
				if(p == null) 
					coords.add(t.getCoordenada(x+cont, vert));
				else if (p.getColor() != this.color) {
					coords.add(t.getCoordenada(x+cont, vert));
					break;
				}
				else
					break;
			}
		}
		
		cont=x;
		for(int vert = y-1; vert>=0; vert--){
			cont--;
			if(cont>=0){
				Peca p = t.getPecaAtCoordenada(cont, vert);
				if(p == null) 
					coords.add(t.getCoordenada(cont, vert));
				else if (p.getColor() != this.color) {
					coords.add(t.getCoordenada(cont, vert));
					break;
				}
				else
					break;
			}
		}
			
		cont=x;
		for(int vert = y+1; vert<8; vert++){
			cont--;
			if(cont>=0){
				Peca p = t.getPecaAtCoordenada(cont, vert);
				if(p == null) 
					coords.add(t.getCoordenada(cont, vert));
				else if (p.getColor() != this.color) {
					coords.add(t.getCoordenada(cont, vert));
					break;
				}
				else
					break;
			}
		}
		
		//movimento torre
		
		for(int vert = y+1; vert<8; vert++){
			Peca p = t.getPecaAtCoordenada(x, vert);
			if(p == null) 
				coords.add(t.getCoordenada(x, vert));
			else if (p.getColor() != this.color){
				coords.add(t.getCoordenada(x, vert));
				break;
			}
			else
				break;
		}
		for(int vert = y-1; vert>=0; vert--){
			Peca p = t.getPecaAtCoordenada(x, vert);
			if(p == null) 
				coords.add(t.getCoordenada(x, vert));
			else if (p.getColor() != this.color){
				coords.add(t.getCoordenada(x, vert));
				break;
			}
			else
				break;
		}
		
		for(int horz = x+1; horz<8; horz++){
			Peca p = t.getPecaAtCoordenada(horz, y);
			if(p == null )
				coords.add(t.getCoordenada(horz, y));
			
			else if (p.getColor() != this.color){
				coords.add(t.getCoordenada(horz, y));
				break;
			}
			else
				break;
		}
		for(int horz = x-1; horz>=0; horz--){
			Peca p = t.getPecaAtCoordenada(horz, y);
			if(p == null )
				coords.add(t.getCoordenada(horz, y));
			
			else if (p.getColor() != this.color){
				coords.add(t.getCoordenada(horz, y));
				break;
			}
			else
				break;
		}
		return coords;
	}

}
