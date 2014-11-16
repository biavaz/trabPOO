package com.puc.rio.inf1636.psmbv.gameplay.pecas;

import java.util.ArrayList;

import com.puc.rio.inf1636.psmbv.gameplay.Coordenada;
import com.puc.rio.inf1636.psmbv.gameplay.Peca;
import com.puc.rio.inf1636.psmbv.gameplay.Tabuleiro;

public class Rei extends Peca {
	
	private static final long serialVersionUID = 1L;
	private boolean movimentada;

	public Rei(char c) {
		super(c);
		this.setName("Rei");
		this.setMovimentada(false);
		/*if(c == 'p')
			this.icon = new ImageIcon ("pecas/p_rei.png").getImage();
		else
			this.icon = new ImageIcon ("pecas/b_rei.png").getImage();*/
	}

	public ArrayList<Coordenada> getPossibleMovements(int x, int y) {
		
		ArrayList<Coordenada> coords = new ArrayList<Coordenada>();
		Tabuleiro t = Tabuleiro.getInstance();
		
		//coordenadas possiveis, mesma pos x e y corresponde uma coordenada
		
		int vertsy[]={ y-1, y+1, y-1, y+1, y-1,y, y, y+1};
		int vertsx[]={ x+1, x-1, x-1, x, x, x+1, x-1, x+1};
		
		for(int vert = 0; vert<8; vert++){
			
			//validando a posicao
			if (vertsy[vert]>=0 && vertsy[vert]<8 && vertsx[vert]>=0 && vertsx[vert]<8){
			
			
			Peca p = t.getPecaAtCoordenada(vertsx[vert], vertsy[vert]);
						
			//casa livre
			if(p == null){
				coords.add(t.getCoordenada(vertsx[vert], vertsy[vert]));
				
			} 
			//vai comer uma peca do adversario
			else if (p.getColor() != this.color){
				coords.add(t.getCoordenada(vertsx[vert], vertsy[vert]));
				
			}
			}
		}
		
		return coords;
	}

	public boolean getMovimentada() {
		return movimentada;
	}

	public void setMovimentada(boolean movimentada) {
		this.movimentada = movimentada;
	}
	
}
