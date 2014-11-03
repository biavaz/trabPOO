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
		
		/*
			
		for (int i=0; i<8; i++){
			if(i%2 == 0) {
				g.setColor(Color.WHITE);
				Rectangle2D rt1 = new Rectangle2D.Double(0,i*87.5,87.5,87.5);
				g2d.fill(rt1);
				
				Rectangle2D rt3 = new Rectangle2D.Double(175,i*87.5,87.5,87.5);
				g2d.fill(rt3);
				
				Rectangle2D rt5 = new Rectangle2D.Double(350,i*87.5,87.5,87.5);
				g2d.fill(rt5);
				
				Rectangle2D rt7 = new Rectangle2D.Double(525,i*87.5,87.5,87.5);
				g2d.fill(rt7);
							
				g.setColor(Color.BLACK);
				
				Rectangle2D rt2 = new Rectangle2D.Double(87.5,i*87.5,87.5,87.5);
				g2d.fill(rt2);
				
				Rectangle2D rt4 = new Rectangle2D.Double(262.5,i*87.5,87.5,87.5);
				g2d.fill(rt4);
				
				Rectangle2D rt6 = new Rectangle2D.Double(437.5,i*87.5,87.5,87.5);
				g2d.fill(rt6);
				
				Rectangle2D rt8 = new Rectangle2D.Double(612,i*87.5,87.5,87.5);
				g2d.fill(rt8);
				
			}
			else {
				g.setColor(Color.BLACK);
				Rectangle2D rt1 = new Rectangle2D.Double(0,i*87.5,87.5,87.5);
				g2d.fill(rt1);
				
				Rectangle2D rt3 = new Rectangle2D.Double(175,i*87.5,87.5,87.5);
				g2d.fill(rt3);
				
				Rectangle2D rt5 = new Rectangle2D.Double(350,i*87.5,87.5,87.5);
				g2d.fill(rt5);
				
				Rectangle2D rt7 = new Rectangle2D.Double(525,i*87.5,87.5,87.5);
				g2d.fill(rt7);
							
				g.setColor(Color.WHITE);
				
				Rectangle2D rt2 = new Rectangle2D.Double(87.5,i*87.5,87.5,87.5);
				g2d.fill(rt2);
				
				Rectangle2D rt4 = new Rectangle2D.Double(262.5,i*87.5,87.5,87.5);
				g2d.fill(rt4);
				
				Rectangle2D rt6 = new Rectangle2D.Double(437.5,i*87.5,87.5,87.5);
				g2d.fill(rt6);
				
				Rectangle2D rt8 = new Rectangle2D.Double(612,i*87.5,87.5,87.5);
				g2d.fill(rt8);
				
			}
		}
		
		Images i = new Images();
		if(!i.imagesLoaded)
			i.loadImages();
		
		//PECAS PRETAS
		g.drawImage(i.torrep, 10, 10, 60, 60, null);
		g.drawImage(i.cavalop, 100, 10, 60, 60, null);
		g.drawImage(i.bispop, 190 , 10, 60, 60, null);
		g.drawImage(i.reip, 280, 10, 60, 60, null);
		g.drawImage(i.damap, 360 , 10, 60, 60, null);
		g.drawImage(i.bispop, 450, 10, 60, 60, null);
		g.drawImage(i.cavalop, 540, 10, 60, 60, null);
		g.drawImage(i.torrep, 630 , 10, 60, 60, null);
		
		g.drawImage(i.peaop, 10, 100, 60, 60, null);
		g.drawImage(i.peaop, 100, 100, 60, 60, null);
		g.drawImage(i.peaop, 190 , 100, 60, 60, null);
		g.drawImage(i.peaop, 280, 100, 60, 60, null);
		g.drawImage(i.peaop, 360 , 100, 60, 60, null);
		g.drawImage(i.peaop, 450, 100, 60, 60, null);
		g.drawImage(i.peaop, 540, 100, 60, 60, null);
		g.drawImage(i.peaop, 630 , 100, 60, 60, null);
		
		//PECAS BRANCAS
		g.drawImage(i.torreb, 10, 620, 60, 60, null);
		g.drawImage(i.cavalob, 100, 620, 60, 60, null);
		g.drawImage(i.bispob, 190 , 620, 60, 60, null);
		g.drawImage(i.reib, 280, 620, 60, 60, null);
		g.drawImage(i.damab, 360 , 620, 60, 60, null);
		g.drawImage(i.bispob, 450, 620, 60, 60, null);
		g.drawImage(i.cavalob, 540, 620, 60, 60, null);
		g.drawImage(i.torreb, 630 , 620, 60, 60, null);
		
		g.drawImage(i.peaob, 10, 540, 60, 60, null);
		g.drawImage(i.peaob, 100, 540, 60, 60, null);
		g.drawImage(i.peaob, 190 , 540, 60, 60, null);
		g.drawImage(i.peaob, 280, 540, 60, 60, null);
		g.drawImage(i.peaob, 360 , 540, 60, 60, null);
		g.drawImage(i.peaob, 450, 540, 60, 60, null);
		g.drawImage(i.peaob, 540, 540, 60, 60, null);
		g.drawImage(i.peaob, 630 , 540, 60, 60, null);
		*/
	}

	public Coordenada getCoordenada(int x, int y) {
		return matrizPecas.getCoordenada(x, y);
	}

	public void moveTo(Coordenada c, int x, int y) {
		this.matrizPecas.moveTo(c, x, y);
	}
		
}
