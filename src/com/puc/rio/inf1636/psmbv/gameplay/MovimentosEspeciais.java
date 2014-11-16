package com.puc.rio.inf1636.psmbv.gameplay;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.puc.rio.inf1636.psmbv.gameplay.pecas.Rei;
import com.puc.rio.inf1636.psmbv.gameplay.pecas.Torre;

//Implementação do xeque, xeque-mate, rei salvo, roque e EnPassant

public class MovimentosEspeciais {
	
	Tabuleiro t = Tabuleiro.getInstance();
	public static int roqueAtivo = 0;
	public static int enPassantAtivo = 0;
	
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
				if (t.getPecaAtCoordenada(x, y) != null
				&& rei.equals(t.getPecaAtCoordenada(x, y).getName())
				&& t.getPecaAtCoordenada(x, y).getColor() != c) {
					xRei = x;
					yRei = y;
					break;
				}
			if (xRei >= 0 || yRei >= 0)
				break;
		}
		
		cRei = t.getCoordenada(xRei, yRei);
		
		if( xeque (c) ){
			//verificar as pecas que comeriam o rei
			for (x = 0; x <= 7; x++) {
				for (y = 0; y <= 7; y++) {
					if (t.getPecaAtCoordenada(x, y) != null	&& t.getPecaAtCoordenada(x, y).getColor() == c) {
						List<Coordenada> movs = t.getCoordenada(x, y).getMoves();
						if (movs.contains(t.getCoordenada(xRei, yRei))){
								pecasAtacantes.add(t.getCoordenada(x, y));
							}
						}
					}
				}
			//se dais de uma peca diferente derruba o rei, nao tem como fugir: xeque-mate
			if(pecasAtacantes.size()>1)
				return true;
			
			//se o rei ta ameacado e algum movimento dele consigo fugir do xeque: nao eh xeque-mate
			List <Coordenada> movsRei = t.getCoordenada(xRei, yRei).getMoves();
			for(Coordenada coord: movsRei){
				int coordhorz = coord.getCoordHorz();
				int coordvert = coord.getCoordVert();
				Peca coordpeca = coord.getPeca();
				
				t.moveTo(cRei, coordhorz, coordvert);
				if(!xeque(c)){
					t.moveTo(t.getCoordenada(coordhorz, coordvert), xRei, yRei);
					t.setCoordenada(new Coordenada(coordhorz, coordvert, coordpeca));
					return false;
				}
				
				t.moveTo(t.getCoordenada(coordhorz, coordvert), xRei, yRei);
				t.setCoordenada(new Coordenada(coordhorz, coordvert, coordpeca));
			}
			
			//se a peca que esta amecando o rei pode ser comida
			for (x = 0; x <= 7; x++) {
				for (y = 0; y <= 7; y++)
					if (t.getPecaAtCoordenada(x, y) != null
					&& t.getPecaAtCoordenada(x, y).getColor() != c) {
						//se chegou aqui so tem uma peca atacante
						if (t.getCoordenada(x, y).getMoves().contains(pecasAtacantes.get(0))){
							int cX = pecasAtacantes.get(0).getCoordHorz();
							int cY = pecasAtacantes.get(0).getCoordVert();
							Peca cP = pecasAtacantes.get(0).getPeca();
							Coordenada c1 = t.getCoordenada(x, y);
							t.moveTo(t.getCoordenada(x, y), pecasAtacantes.get(0).getCoordHorz(), 
									pecasAtacantes.get(0).getCoordVert());
							
							if(reisalvo((c == 'p')?'b':'p')){
								t.moveTo(c1, x, y);
								t.setCoordenada(new Coordenada(cX, cY, cP));
								return false;				
							}

							t.moveTo(c1, x, y);
							t.setCoordenada(new Coordenada(cX, cY, cP));
						}
					}
			}
			
			
			//verificar se existe um sacrificio
			int xAtaque = pecasAtacantes.get(0).getCoordHorz();
			int yAtaque = pecasAtacantes.get(0).getCoordVert();
		
			for (x = 0; x <= 7; x++) {
				for (y = 0; y <= 7; y++){
					if (t.getPecaAtCoordenada(x, y) != null
					&& t.getPecaAtCoordenada(x, y).getColor() != c 
					&& !t.getPecaAtCoordenada(x, y).getName().equals("Rei")) {
						List <Coordenada> listCoords = t.getCoordenada(x, y).getMoves();
						
						for(Coordenada c1: listCoords){
							//ataque da direita
							if( xAtaque > xRei ){
								//ataque da diagonal direita baixo
								if( yAtaque > yRei ){
									List <Coordenada> movimentos = new ArrayList <Coordenada>();
									int y2=yRei;
									for(int x2= xRei; x2<=xAtaque; x2++){
										movimentos.add(t.getCoordenada(x2, y2));
										y2++;
									}
									if(movimentos.contains(c1))
										return false;
								} else if( yAtaque < yRei ){
									//ataque da diagonal direita cima
									List <Coordenada> movimentos = new ArrayList <Coordenada>();
									int y2=yAtaque;
									for(int x2= xRei; x2<=xAtaque; x2++){
										movimentos.add(t.getCoordenada(x2, y2));
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
										movimentos.add(t.getCoordenada(x2, y2));
										y2++;
									}
									if(movimentos.contains(c1))
										return false;
								} else if( yAtaque < yRei ){
									//ataque da diagonal esquerda cima
									List <Coordenada> movimentos = new ArrayList <Coordenada>();
									int y2=yAtaque;
									for(int x2= xAtaque; x2<=xRei; x2++){
										movimentos.add(t.getCoordenada(x2, y2));
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
				if (t.getPecaAtCoordenada(x, y) != null
				&& rei.equals(t.getPecaAtCoordenada(x, y).getName())
				&& t.getPecaAtCoordenada(x, y).getColor() == c) {
					xRei = x;
					yRei = y;
					break;
				}
			if (xRei >= 0 || yRei >= 0)
				break;
		}
		for (x = 0; x <= 7; x++) {
			for (y = 0; y <= 7; y++) {
				if (t.getPecaAtCoordenada(x, y) != null
						&& t.getPecaAtCoordenada(x, y).getColor() != c) {
					List<Coordenada> movs = t.getCoordenada(x, y).getMoves();
					if (movs.contains(t.getCoordenada(xRei, yRei)))
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
				if (t.getPecaAtCoordenada(x, y) != null
				&& rei.equals(t.getPecaAtCoordenada(x, y).getName())
				&& t.getPecaAtCoordenada(x, y).getColor() != c) {
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
				if (t.getPecaAtCoordenada(x, y) != null
						&& t.getPecaAtCoordenada(x, y).getColor() == c) {
					List<Coordenada> movs = t.getCoordenada(x, y).getMoves();
					if (movs.contains(t.getCoordenada(xRei, yRei)))
						return true;
				}
			}
		}
		return false;
	}
	
	protected List<Coordenada> getTorresRoque (char c){
		int x, y;
		int numTorres=0;
		List <Coordenada> torres = new ArrayList <Coordenada> ();
		for (x = 0; x <= 7; x++) {
			for (y = 0; y <= 7; y++)
				if (t.getPecaAtCoordenada(x, y) != null
				&& t.getPecaAtCoordenada(x, y).getName().equals("Torre")
				&& t.getPecaAtCoordenada(x, y).getColor() == c) {
					if(numTorres==0){
						Torre torre = (Torre) t.getPecaAtCoordenada(x, y);
						if(torre.getMovimentada()==false){
							torres.add(t.getCoordenada(x, y));
							numTorres++;
						}
					}
					else{
						Torre torre = (Torre) t.getPecaAtCoordenada(x, y);
						if(torre.getMovimentada()==false){
							torres.add(t.getCoordenada(x, y));
							numTorres++;
						}
					}	
				}
			if (numTorres>1)
				break;
		}
		
		return torres;
	}
	
	public boolean roque(JFrame f, final char color){
		int xRei=-1, yRei=-1;
		Rei r = null;
		List <Coordenada> numTorres = null;
		List <Coordenada> torres = getTorresRoque(color);
						
		if(getCoordenadaRei(color)!=null){
			r = (Rei) getCoordenadaRei(color).getPeca();
			xRei=getCoordenadaRei(color).getCoordHorz();
			yRei=getCoordenadaRei(color).getCoordVert();
		}
		if(torres.size()==0 || r.getMovimentada()== true || xeque(color)){
			JOptionPane.showMessageDialog(f, "ROQUE INDISPONIVEL",
						"Xadrez", JOptionPane.ERROR_MESSAGE);
			return false;
		}
				
		
		else{
			t.paintImmediately(f.getBounds());
			
			numTorres = verificaTorres (torres, xRei, yRei);								
			
			if(numTorres.size()>0){
					JOptionPane.showMessageDialog(f, "CLIQUE NA TORRE QUE DESEJA EXECUTAR O ROQUE",
						"Xadrez", JOptionPane.INFORMATION_MESSAGE);
				roqueAtivo=1;
				return true;
			}
			
			else {
				JOptionPane.showMessageDialog(f, "ROQUE INDISPONIVEL",
						"Xadrez", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
	}

	
	private Coordenada getCoordenadaRei(char color) {
		int x, y, xRei=-1, yRei=-1;
		
		for (x = 0; x <= 7; x++) {
			for (y = 0; y <= 7; y++)
				if (t.getPecaAtCoordenada(x, y) != null
				&& t.getPecaAtCoordenada(x, y).getName().equals("Rei")
				&& t.getPecaAtCoordenada(x, y).getColor() == color) {
					xRei = x;
					yRei = y;
					break;
				}
			if (xRei >= 0 || yRei >= 0)
				break;
		}
		if(xRei>=0 && yRei>=0)
			return t.getCoordenada(xRei, yRei);
		else
			return null;
	}

	private List<Coordenada> verificaTorres(List<Coordenada> torres, int xRei, int yRei) {
	
		int haPeca=0;
		int x;
		List <Coordenada> numTorres = new ArrayList <Coordenada> ();
		if(torres.get(0).getCoordHorz()>xRei){
			haPeca=0;
			for(x=5; x<7; x++){
				if(t.getCoordenada(x, yRei).getPeca()!=null){
					haPeca++;
				}
			}
			if(haPeca==0){
				t.paintCoordenada(torres.get(0).getCoordHorz(), torres.get(0).getCoordVert());
				numTorres.add(t.getCoordenada(torres.get(0).getCoordHorz(), torres.get(0).getCoordVert()));
				}
		}
			
		
		else if(torres.get(0).getCoordHorz()<xRei){
			haPeca=0;
			for(x=1; x<4; x++){
				if(t.getCoordenada(x, yRei).getPeca()!=null){
					haPeca++;
				}
			}
			if(haPeca==0){
				t.paintCoordenada(torres.get(0).getCoordHorz(), torres.get(0).getCoordVert());
				numTorres.add(t.getCoordenada(torres.get(0).getCoordHorz(), torres.get(0).getCoordVert()));
			}
		}
		
		
		if(torres.size()==2){
			haPeca=0;
			if(torres.get(1).getCoordHorz()>xRei){
				for(x=5; x<7; x++){
					if(t.getCoordenada(x, yRei).getPeca()!=null){
						haPeca++;
					}
				}
				if(haPeca==0){
				t.paintCoordenada(torres.get(1).getCoordHorz(), torres.get(1).getCoordVert());
				numTorres.add(t.getCoordenada(torres.get(1).getCoordHorz(), torres.get(1).getCoordVert()));
				}
			}
		}
				
		else if(torres.get(1).getCoordHorz()<xRei){
			haPeca=0;
			for(x=1; x<4; x++){
				if(t.getCoordenada(x, yRei).getPeca()!=null){
					haPeca++;
				}
			}
			if(haPeca==0){
				t.paintCoordenada(torres.get(1).getCoordHorz(), torres.get(1).getCoordVert());
				numTorres.add(t.getCoordenada(torres.get(1).getCoordHorz(), torres.get(1).getCoordVert()));
			}
		}
		return numTorres;
	}

	public void trocaRoque(Coordenada c, char color) {
		roqueAtivo=0;
		Coordenada rei = getCoordenadaRei(color);
		List <Coordenada> torres = verificaTorres(getTorresRoque(color),rei.getCoordHorz(), rei.getCoordVert());

		 if(c.getPeca()!=null){
			if(torres.contains(c)){
				
				System.out.println("ROQUE");
				
				int coordhorz = rei.getCoordHorz();
				int coordvert = rei.getCoordVert();
				Peca coordpeca = c.getPeca();
				
				t.moveTo(rei, c.getCoordHorz(), c.getCoordVert());
				t.setCoordenada(new Coordenada(coordhorz, coordvert, coordpeca));
				t.repaint();
				 if(color == 'p')
					 t.setTurno(Turno.TurnoBrancoEscolher);
				 else
					 t.setTurno(Turno.TurnoPretoEscolher);
				
			 }
			 else{
				 t.repaint();
				 System.out.println("Movimento Indisponivel");
				 if(color == 'b')
					 t.setTurno(Turno.TurnoBrancoEscolher);
				 else
					 t.setTurno(Turno.TurnoPretoEscolher);
			 }
		}
		 else{
			 t.repaint();
			 System.out.println("Movimento Indisponivel");
			 if(color == 'b')
				 t.setTurno(Turno.TurnoBrancoEscolher);
			 else
				 t.setTurno(Turno.TurnoPretoEscolher);
		 }
		
			
	}
		

	public void enPassant(JFrame f){
		
	}

	public boolean verificaEnPassant(Coordenada clickedCoordenada, char c) {
		if(c == 'b'){
			if(clickedCoordenada.getCoordVert()== 3){
				if(t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca()!=null
						&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getName().equals("Peao")
						&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getColor()=='p')
					return true;
				
				if(t.getCoordenada(clickedCoordenada.getCoordHorz()+1, clickedCoordenada.getCoordVert()).getPeca()!=null
						&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getName().equals("Peao")
						&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getColor()=='p')
					return true;
			}
		}
		
		if(c == 'p'){
			if(clickedCoordenada.getCoordVert()== 4){
				if(t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca()!=null
						&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getName().equals("Peao")
						&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getColor()=='b')
					return true;
				
				if(t.getCoordenada(clickedCoordenada.getCoordHorz()+1, clickedCoordenada.getCoordVert()).getPeca()!=null
						&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getName().equals("Peao")
						&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getColor()=='b')
					return true;
			}
		}
		return false;
	}

	public List <Coordenada> coordEnPassant(Coordenada clickedCoordenada, char c) {
		
		List <Coordenada> coords = new ArrayList <Coordenada>();
		if(verificaEnPassant(clickedCoordenada, c)){
			if(c == 'b'){
				if(clickedCoordenada.getCoordVert()== 3){
					if(t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca()!=null
							&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getName().equals("Peao")
							&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getColor()=='p'){
						coords.add(t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()-1));
						
					}
					
					if(t.getCoordenada(clickedCoordenada.getCoordHorz()+1, clickedCoordenada.getCoordVert()).getPeca()!=null
							&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getName().equals("Peao")
							&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getColor()=='p'){
						t.paintCoordenada(clickedCoordenada.getCoordHorz(), clickedCoordenada.getCoordVert());
						coords.add(t.getCoordenada(clickedCoordenada.getCoordHorz()+1, clickedCoordenada.getCoordVert()-1));
					}
				}
			}
			
			if(c == 'p'){
				if(clickedCoordenada.getCoordVert()== 3){
					if(t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca()!=null
							&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getName().equals("Peao")
							&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getColor()=='b'){
						coords.add(t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()+1));
					}
						
					
					if(t.getCoordenada(clickedCoordenada.getCoordHorz()+1, clickedCoordenada.getCoordVert()).getPeca()!=null
							&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getName().equals("Peao")
							&& t.getCoordenada(clickedCoordenada.getCoordHorz()-1, clickedCoordenada.getCoordVert()).getPeca().getColor()=='b'){
						coords.add(t.getCoordenada(clickedCoordenada.getCoordHorz()+1, clickedCoordenada.getCoordVert()+1));
					}
				}
			
			}
			return coords;
		}
		else
			return null;
	}
	

}
