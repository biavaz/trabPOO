package com.puc.rio.inf1636.psmbv.gameplay;
import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Tabuleiro extends JPanel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static int LARGURA_CASA = 60;
	private static int ALTURA_CASA = 60;
	
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
	
	public boolean xequeMate (char c ){
		int x = 0;
		int y = 0;
		String rei = "Rei";
		int xRei = -1, yRei = -1;
		Coordenada cRei;
		
		List <Coordenada> pecasAtacantes = new ArrayList<Coordenada>();
		//achar o rei adversário
		for (x = 0; x <= 7; x++) {
			for (y = 0; y <= 7; y++)
				if (this.getPecaAtCoordenada(x, y) != null
				&& rei.equals(this.getPecaAtCoordenada(x, y).getName())
				&& this.getPecaAtCoordenada(x, y).getColor() != c) {
					xRei = x;
					yRei = y;
					break;
				}
			if (xRei >= 0 || yRei >= 0)
				break;
		}
		
		cRei = this.getCoordenada(xRei, yRei);
		
		if( xeque (c) ){
			//verificar as pecas que comeriam o rei
			for (x = 0; x <= 7; x++) {
				for (y = 0; y <= 7; y++) {
					if (this.getPecaAtCoordenada(x, y) != null	&& this.getPecaAtCoordenada(x, y).getColor() == c) {
						List<Coordenada> movs = this.getCoordenada(x, y).getMoves();
						if (movs.contains(this.getCoordenada(xRei, yRei))){
								pecasAtacantes.add(this.getCoordenada(x, y));
							}
						}
					}
				}
			//se dais de uma peca diferente derruba o rei, nao tem como fugir: xeque-mate
			if(pecasAtacantes.size()>1)
				return true;
			
			//se o rei ta ameacado e algum movimento dele consigo fugir do xeque: nao eh xeque-mate
			List <Coordenada> movsRei = this.getCoordenada(xRei, yRei).getMoves();
			for(Coordenada coord: movsRei){
				int coordhorz = coord.getCoordHorz();
				int coordvert = coord.getCoordVert();
				Peca coordpeca = coord.getPeca();
				
				this.moveTo(cRei, coordhorz, coordvert);
				if(!xeque(c)){
					this.moveTo(this.getCoordenada(coordhorz, coordvert), xRei, yRei);
					this.setCoordenada(new Coordenada(coordhorz, coordvert, coordpeca));
					return false;
				}
				
				this.moveTo(this.getCoordenada(coordhorz, coordvert), xRei, yRei);
				this.setCoordenada(new Coordenada(coordhorz, coordvert, coordpeca));
			}
			
			//se a peca que esta amecando o rei pode ser comida
			for (x = 0; x <= 7; x++) {
				for (y = 0; y <= 7; y++)
					if (this.getPecaAtCoordenada(x, y) != null
					&& this.getPecaAtCoordenada(x, y).getColor() != c) {
						//se chegou aqui so tem uma peca atacante
						if (this.getCoordenada(x, y).getMoves().contains(pecasAtacantes.get(0))){
							int cX = pecasAtacantes.get(0).getCoordHorz();
							int cY = pecasAtacantes.get(0).getCoordVert();
							Peca cP = pecasAtacantes.get(0).getPeca();
							Coordenada c1 = this.getCoordenada(x, y);
							this.moveTo(this.getCoordenada(x, y), pecasAtacantes.get(0).getCoordHorz(), 
									pecasAtacantes.get(0).getCoordVert());
							
							if(reisalvo((c == 'p')?'b':'p')){
								this.moveTo(c1, x, y);
								this.setCoordenada(new Coordenada(cX, cY, cP));
								return false;				
							}

							this.moveTo(c1, x, y);
							this.setCoordenada(new Coordenada(cX, cY, cP));
						}
					}
			}
			
			
			//verificar se existe um sacrificio
			int xAtaque = pecasAtacantes.get(0).getCoordHorz();
			int yAtaque = pecasAtacantes.get(0).getCoordVert();
		
			for (x = 0; x <= 7; x++) {
				for (y = 0; y <= 7; y++){
					if (this.getPecaAtCoordenada(x, y) != null
					&& this.getPecaAtCoordenada(x, y).getColor() != c 
					&& !this.getPecaAtCoordenada(x, y).getName().equals("Rei")) {
						List <Coordenada> listCoords = this.getCoordenada(x, y).getMoves();
						
						for(Coordenada c1: listCoords){
							//ataque da direita
							if( xAtaque > xRei ){
								//ataque da diagonal direita baixo
								if( yAtaque > yRei ){
									List <Coordenada> movimentos = new ArrayList <Coordenada>();
									int y2=yRei;
									for(int x2= xRei; x2<=xAtaque; x2++){
										movimentos.add(this.getCoordenada(x2, y2));
										y2++;
									}
									if(movimentos.contains(c1))
										return false;
								} else if( yAtaque < yRei ){
									//ataque da diagonal direita cima
									List <Coordenada> movimentos = new ArrayList <Coordenada>();
									int y2=yAtaque;
									for(int x2= xRei; x2<=xAtaque; x2++){
										movimentos.add(this.getCoordenada(x2, y2));
										y2++;
									}
									if(movimentos.contains(c1))
										return false;
								} else {
									//ataque da direita horiziontal
									if(c1.getCoordHorz()>xRei && c1.getCoordHorz()<xAtaque && c1.getCoordVert()==yAtaque)
										return false;
								}
							}
							//ataque da esquerda
							else if( xAtaque < xRei ){
								//ataque da esquerda por baixo
								if( yAtaque > yRei ){
									List <Coordenada> movimentos = new ArrayList <Coordenada>();
									int y2=yRei;
									for(int x2= xAtaque; x2<=xRei; x2++){
										movimentos.add(this.getCoordenada(x2, y2));
										y2++;
									}
									if(movimentos.contains(c1))
										return false;
								} else if( yAtaque < yRei ){
									//ataque da diagonal esquerda cima
									List <Coordenada> movimentos = new ArrayList <Coordenada>();
									int y2=yAtaque;
									for(int x2= xAtaque; x2<=xRei; x2++){
										movimentos.add(this.getCoordenada(x2, y2));
										y2++;
									}
									if(movimentos.contains(c1))
										return false;
								} else {
									//ataque da esquerda horizontal
									if(c1.getCoordHorz()>xAtaque && c1.getCoordHorz()<xRei && c1.getCoordVert()==yAtaque )
										return false;
								}
							} 
							//ataque na vertical
							else {
								if(yAtaque > yRei){
									if(c1.getCoordHorz() == xAtaque && c1.getCoordVert()>yRei && c1.getCoordVert()< yAtaque )
										return false;
								} else {
									if(c1.getCoordHorz() == xAtaque && c1.getCoordVert()<yRei && c1.getCoordVert()>yAtaque )
										return false;
								}
							}
						}
					}
				}
			}
			return true;
		}
		//se nao esta em xeque;
		return false;
	}
	
	public boolean reisalvo(char c) {
		int x = 0;
		int y = 0;
		String rei = "Rei";
		int xRei = -1, yRei = -1;
		for (x = 0; x <= 7; x++) {
			for (y = 0; y <= 7; y++)
				if (this.getPecaAtCoordenada(x, y) != null
				&& rei.equals(this.getPecaAtCoordenada(x, y).getName())
				&& this.getPecaAtCoordenada(x, y).getColor() == c) {
					xRei = x;
					yRei = y;
					break;
				}
			if (xRei >= 0 || yRei >= 0)
				break;
		}
		for (x = 0; x <= 7; x++) {
			for (y = 0; y <= 7; y++) {
				if (this.getPecaAtCoordenada(x, y) != null
						&& this.getPecaAtCoordenada(x, y).getColor() != c) {
					List<Coordenada> movs = this.getCoordenada(x, y).getMoves();
					if (movs.contains(this.getCoordenada(xRei, yRei)))
						return false;
				}
			}
		}
		return true;
	}
	
	public boolean xeque (char c){
		int x = 0;
		int y = 0;
		String rei = "Rei";
		int xRei = -1, yRei = -1;
		
		//achar o rei adversário
		for (x = 0; x <= 7; x++) {
			for (y = 0; y <= 7; y++)
				if (this.getPecaAtCoordenada(x, y) != null
				&& rei.equals(this.getPecaAtCoordenada(x, y).getName())
				&& this.getPecaAtCoordenada(x, y).getColor() != c) {
					xRei = x;
					yRei = y;
					break;
				}
			if (xRei >= 0 || yRei >= 0)
				break;
		}
		
		//verificar se está no caminho de alguma peca minha
		for (x = 0; x <= 7; x++) {
			for (y = 0; y <= 7; y++) {
				if (this.getPecaAtCoordenada(x, y) != null
						&& this.getPecaAtCoordenada(x, y).getColor() == c) {
					List<Coordenada> movs = this.getCoordenada(x, y).getMoves();
					if (movs.contains(this.getCoordenada(xRei, yRei)))
						return true;
				}
			}
		}
		return false;
	}

}

