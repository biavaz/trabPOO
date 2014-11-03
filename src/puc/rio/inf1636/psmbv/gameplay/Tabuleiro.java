package com.puc.rio.inf1636.psmbv.gameplay;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Tabuleiro extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private static int LARGURA_CASA = 60;
	private static int ALTURA_CASA = 60;
	
	private static Tabuleiro INSTANCE = null;
	private MatrizPecas matrizPecas = null;
		
	private Tabuleiro(){
		this.matrizPecas = new MatrizPecas();
	}
	
	public static Tabuleiro getInstance(){
		if(INSTANCE == null){
			INSTANCE = new Tabuleiro();
		}
		return INSTANCE;
	}
	
	public Peca getPecaAtCoordenada(int x, int y){
		return matrizPecas.getCoordenada(x, y).getPeca();
	}

	public void paintComponent (Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d=(Graphics2D) g;
		
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				if(y%2 == 0){
					if(x%2 == 0) {
						g.setColor(Color.WHITE);
						Rectangle2D rt1 = new Rectangle2D.Double(LARGURA_CASA*x, y*ALTURA_CASA, LARGURA_CASA, ALTURA_CASA);
						g2d.fill(rt1);
						Coordenada coord = this.matrizPecas.getCoordenada(x, y);
						Peca p = coord.getPeca();
						if(p != null){
							g.drawImage(p.icon, LARGURA_CASA*x, ALTURA_CASA*y, LARGURA_CASA, ALTURA_CASA, null);
						}
					} else {
						g.setColor(Color.BLACK);
						Rectangle2D rt1 = new Rectangle2D.Double(LARGURA_CASA*x, y*ALTURA_CASA, LARGURA_CASA, ALTURA_CASA);
						g2d.fill(rt1);
						Coordenada coord = this.matrizPecas.getCoordenada(x, y);
						Peca p = coord.getPeca();
						if(p != null){
							g.drawImage(p.icon, LARGURA_CASA*x, ALTURA_CASA*y, LARGURA_CASA, ALTURA_CASA, null);
						}
					}
				} else {
					if(x%2 == 0) {
						g.setColor(Color.BLACK);
						Rectangle2D rt1 = new Rectangle2D.Double(LARGURA_CASA*x, y*ALTURA_CASA, LARGURA_CASA, ALTURA_CASA);
						g2d.fill(rt1);
						Coordenada coord = this.matrizPecas.getCoordenada(x, y);
						Peca p = coord.getPeca();
						if(p != null){
							g.drawImage(p.icon, LARGURA_CASA*x, ALTURA_CASA*y, LARGURA_CASA, ALTURA_CASA, null);
						}
					} else {
						g.setColor(Color.WHITE);
						Rectangle2D rt1 = new Rectangle2D.Double(LARGURA_CASA*x, y*ALTURA_CASA, LARGURA_CASA, ALTURA_CASA);
						g2d.fill(rt1);
						Coordenada coord = this.matrizPecas.getCoordenada(x, y);
						Peca p = coord.getPeca();
						if(p != null){
							g.drawImage(p.icon, LARGURA_CASA*x, ALTURA_CASA*y, LARGURA_CASA, ALTURA_CASA, null);
						}
					}
				}
			}
		}
		
	
	}

	public Coordenada getCoordenada(int x, int y) {
		return matrizPecas.getCoordenada(x, y);
	}

	public void moveTo(Coordenada c, int x, int y) {
		this.matrizPecas.moveTo(c, x, y);
	}

	public Coordenada getCoordenadaByXY(int x, int y) {
		return matrizPecas.getCoordenada(x/LARGURA_CASA, y/ALTURA_CASA);
	}
		
}

