package com.puc.rio.inf1636.psmbv.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		f.setSize(500,515);
		f.setVisible(true);
		t.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent event) {
				super.mouseClicked(event);
				
				int x = event.getPoint().x;
				int y = event.getPoint().y;
				
				Coordenada c = t.getCoordenadaByXY(x, y);
				System.out.println(c.toString());
			}
		});
		/*
		 * Teste de movimentacao.
		Coordenada c = t.getCoordenada(0, 0);
		t.moveTo(c, 4, 4);
		
		t.repaint();
		
		c = t.getCoordenada(4, 4);
		
		ArrayList<Coordenada> coords = c.getMoves();
		for(Coordenada cx : coords){
			System.out.println(cx.getCoordHorz() +"-"+ cx.getCoordVert());
		}
		*/
	}
	
}
