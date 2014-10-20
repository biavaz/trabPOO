package com.puc.rio.inf1636.psmbv.gameplay.pecas;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.puc.rio.inf1636.psmbv.gameplay.Coordenada;
import com.puc.rio.inf1636.psmbv.gameplay.Peca;

public class Bispo extends Peca {

	public Bispo(char c) {
		super(c);
		if(c == 'p')
			this.icon = new ImageIcon ("pecas/p_bispo.png").getImage();
		else
			this.icon = new ImageIcon ("pecas/b_bispo.png").getImage();
	}
	
	public ArrayList<Coordenada> getPossibleMovements(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

}
