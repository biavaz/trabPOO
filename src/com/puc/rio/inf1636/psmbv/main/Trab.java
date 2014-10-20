package com.puc.rio.inf1636.psmbv.main;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.puc.rio.inf1636.psmbv.gameplay.Coordenada;
import com.puc.rio.inf1636.psmbv.gameplay.Tabuleiro;


public class Trab {
	private static Tabuleiro t = null;
	
	public static void main(String[] args) {
		JFrame f = new JFrame ("TRABALHO INF1636 - XADREZ");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t = Tabuleiro.getInstance();	
		f.add(t);
		f.setSize(480,500);
		f.setVisible(true);
		
		Coordenada c = t.getCoordenada(0, 0);
		t.moveTo(c, 4, 4);
		
		t.repaint();
		
		c = t.getCoordenada(4, 4);
		
		ArrayList<Coordenada> coords = c.getMoves();
		for(Coordenada cx : coords){
			System.out.println(cx.getCoordHorz() +"-"+ cx.getCoordVert());
		}
	}
	
}
