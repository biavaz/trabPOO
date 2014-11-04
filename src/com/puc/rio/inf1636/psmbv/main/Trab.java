package com.puc.rio.inf1636.psmbv.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
				for(Coordenada c1 :c.getMoves())
					t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());
				/*try {
					for(Coordenada c1 :c.getMoves())
						t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());
					super.mouseClicked(event);
					x = event.getPoint().x;
					y = event.getPoint().y;
					
					ArrayList<Coordenada> coords = c.getMoves();
					for(Coordenada cx : coords){
						if(cx.getCoordHorz() == x && cx.getCoordVert() == y){
							t.moveTo(c, x, y);
							t.repaint();
						}
					}
					
				
				}
				catch(Exception e){
				}*/
					
				
			}
		});
		
		
		//Teste de movimentacao.
		Coordenada c = t.getCoordenada(1, 7);
		t.moveTo(c, 3, 4);
		
		t.repaint();
		
		c = t.getCoordenada(3, 4);
		
		ArrayList<Coordenada> coords = c.getMoves();
		for(Coordenada cx : coords){
			System.out.println(cx.getCoordHorz() +"-"+ cx.getCoordVert());
		}
	
	}
	
}
