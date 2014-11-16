package com.puc.rio.inf1636.psmbv.gameplay;
import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;

import javax.swing.*;

/*PADRÃO SINGLETON: 
 * - variavel estatica referindo-se a instancia.
 * - construtor da classe privado.
 * -método getInstance()
 * */ 
public class Tabuleiro extends JPanel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final int LARGURA_CASA = 60;
	private static final int ALTURA_CASA = 60;
	
	private static Tabuleiro INSTANCE = null;
	private MatrizPecas matrizPecas = null;
	private Turno turno;
	private Coordenada clickedCoordenada;
		
	private Tabuleiro(){
		this.matrizPecas = new MatrizPecas();
	}
	
	public static Tabuleiro getInstance(){
		if(INSTANCE == null){
			INSTANCE = new Tabuleiro();
		}
		return INSTANCE;
	}
	
	public MatrizPecas getMatrizPecas() {
		return matrizPecas;
	}

	public void setMatrizPecas(MatrizPecas matrizPecas) {
		this.matrizPecas = matrizPecas;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Coordenada getClickedCoordenada() {
		return clickedCoordenada;
	}

	public void setClickedCoordenada(Coordenada clickedCoordenada) {
		this.clickedCoordenada = clickedCoordenada;
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
							g.drawImage(p.getIcon(), LARGURA_CASA*x, ALTURA_CASA*y, LARGURA_CASA, ALTURA_CASA, null);
						}
					} else {
						g.setColor(Color.BLACK);
						Rectangle2D rt1 = new Rectangle2D.Double(LARGURA_CASA*x, y*ALTURA_CASA, LARGURA_CASA, ALTURA_CASA);
						g2d.fill(rt1);
						Coordenada coord = this.matrizPecas.getCoordenada(x, y);
						Peca p = coord.getPeca();
						if(p != null){
							g.drawImage(p.getIcon(), LARGURA_CASA*x, ALTURA_CASA*y, LARGURA_CASA, ALTURA_CASA, null);
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
							g.drawImage(p.getIcon(), LARGURA_CASA*x, ALTURA_CASA*y, LARGURA_CASA, ALTURA_CASA, null);
						}
					} else {
						g.setColor(Color.WHITE);
						Rectangle2D rt1 = new Rectangle2D.Double(LARGURA_CASA*x, y*ALTURA_CASA, LARGURA_CASA, ALTURA_CASA);
						g2d.fill(rt1);
						Coordenada coord = this.matrizPecas.getCoordenada(x, y);
						Peca p = coord.getPeca();
						if(p != null){
							g.drawImage(p.getIcon(), LARGURA_CASA*x, ALTURA_CASA*y, LARGURA_CASA, ALTURA_CASA, null);
						}
					}
				}
			}
		}
		
	
	}
	
	public void paintCoordenada (int x, int y){
		Graphics2D g = (Graphics2D) this.getGraphics();
		g.setColor(Color.YELLOW);
		g.setStroke(new BasicStroke(5));
		Rectangle2D rt1 = new Rectangle2D.Double(LARGURA_CASA*x, y*ALTURA_CASA, LARGURA_CASA, ALTURA_CASA);
		g.draw(rt1);
	}
	
	public void paintPeaoEspecial (Coordenada c){
		Graphics2D g = (Graphics2D) this.getGraphics();
		Peca p = c.getPeca();
		if(p != null){
			g.drawImage(p.getIcon(), LARGURA_CASA * c.getCoordHorz(), ALTURA_CASA * c.getCoordVert(), LARGURA_CASA, ALTURA_CASA, null);
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
	
	public void setCoordenada(Coordenada c){
		this.matrizPecas.setCoordenada(c);
	}
	
	

}

