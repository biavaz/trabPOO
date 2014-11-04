package com.puc.rio.inf1636.psmbv.gameplay.pecas;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.puc.rio.inf1636.psmbv.gameplay.Coordenada;
import com.puc.rio.inf1636.psmbv.gameplay.Peca;
import com.puc.rio.inf1636.psmbv.gameplay.Tabuleiro;

public class Peao extends Peca {

	public Peao(char c) {
		super(c);
		this.setName("Peao");
		if(c == 'p')
			this.icon = new ImageIcon ("pecas/p_peao.png").getImage();
		else
			this.icon = new ImageIcon ("pecas/b_peao.png").getImage();
	}
	
	public ArrayList<Coordenada> getPossibleMovements(int x, int y) {
		ArrayList<Coordenada> coords = new ArrayList<Coordenada>();
		Tabuleiro t = Tabuleiro.getInstance();
		
		if( this.color == 'p'){
			
		
			Peca p = t.getPecaAtCoordenada(x, y+1);
			if(y+1<8){
				if(p == null){
					coords.add(t.getCoordenada(x, y+1));
					
					if( y == 1 ){
						p=t.getPecaAtCoordenada(x, y+2);
						if(p == null)
							coords.add(t.getCoordenada(x, y+2));
					}
				}
			}
			
			
			if(y+1 < 8){
				if(x+1 < 8){
				p = t.getPecaAtCoordenada(x+1, y+1);
				if (p != null && p.getColor() != this.color)
					coords.add(t.getCoordenada(x+1, y+1));
				}
			
				if(x-1>0){
					p = t.getPecaAtCoordenada(x-1, y+1);
					if (p != null && p.getColor() != this.color)
						coords.add(t.getCoordenada(x-1, y+1));
				}
			}
		}
		
		if( this.color == 'b'){
			
			Peca p = t.getPecaAtCoordenada(x, y-1);
			if(y-1>=0){
				if(p == null){
					coords.add(t.getCoordenada(x, y-1));
					if( y == 6 ){
						p=t.getPecaAtCoordenada(x, y-2);
						if(p == null )
							coords.add(t.getCoordenada(x, y-2));
					}
				}
			}
			
			
			if(y-1 >=0){
				if(x-1 >=0){
				p = t.getPecaAtCoordenada(x-1, y-1);
				if (p != null && p.getColor() != this.color)
					coords.add(t.getCoordenada(x-1, y-1));
				}
			
				if(x+1<8){
					p = t.getPecaAtCoordenada(x+1, y-1);
					if (p != null && p.getColor() != this.color)
						coords.add(t.getCoordenada(x+1, y-1));
				}
			}
		}
		return coords;
	}

}
