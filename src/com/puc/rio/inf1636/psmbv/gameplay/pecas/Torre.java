package com.puc.rio.inf1636.psmbv.gameplay.pecas;

import java.util.ArrayList;

import com.puc.rio.inf1636.psmbv.gameplay.Coordenada;
import com.puc.rio.inf1636.psmbv.gameplay.Peca;
import com.puc.rio.inf1636.psmbv.gameplay.Tabuleiro;

public class Torre extends Peca {

	private static final long serialVersionUID = 1L;
	
	private boolean movimentada;

	public Torre(char c) {
		super(c);
		this.setName("Torre");
		this.setMovimentada(false);
		/*if(c == 'p')
			this.icon = new ImageIcon ("pecas/p_torre.png").getImage();
		else
			this.icon = new ImageIcon ("pecas/b_torre.png").getImage();*/
	}

	public ArrayList<Coordenada> getPossibleMovements(int x, int y) {
		ArrayList<Coordenada> coords = new ArrayList<Coordenada>();
		Tabuleiro t = Tabuleiro.getInstance();
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

	public boolean getMovimentada() {
		return movimentada;
	}

	public void setMovimentada(boolean movimentada) {
		this.movimentada = movimentada;
	}

}
