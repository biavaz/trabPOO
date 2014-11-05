package com.puc.rio.inf1636.psmbv.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;

import com.puc.rio.inf1636.psmbv.gameplay.Coordenada;
import com.puc.rio.inf1636.psmbv.gameplay.Peca;
import com.puc.rio.inf1636.psmbv.gameplay.Tabuleiro;
import com.puc.rio.inf1636.psmbv.gameplay.Turno;


public class Trab {
	private static Tabuleiro t = null;
	private static Turno turno;
	private static Coordenada clickedCoordenada;
	
	public static void main(String[] args) {
		JFrame f = new JFrame ("TRABALHO INF1636 - XADREZ");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t = Tabuleiro.getInstance();	
		f.add(t);
		f.setSize(500,515);
		f.setVisible(true);
		turno = Turno.TurnoBrancoEscolher;
		t.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				super.mouseClicked(event);
				handleMouseClick(event);
			}
		});

	}
	
	public static void handleMouseClick(MouseEvent click){
		int x = click.getPoint().x;
		int y = click.getPoint().y;
		
		Coordenada c = t.getCoordenadaByXY(x, y);
		System.out.println(c.toString());
		try{
			switch(turno){
				case TurnoBrancoEscolher:
					clickedCoordenada = escolherPecaBranca(c);
					break;
				case TurnoPretoEscolher:
					clickedCoordenada = escolherPecaPreta(c);
					break;
				case TurnoBrancoMovimentar:
					clickedCoordenada = movimentarPecaBranca(c);
					break;
				case TurnoPretoMovimentar:
					clickedCoordenada = movimentarPecaPreta(c);
					break;
				default:
					break;
			}
		} catch (RuntimeException ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public static Coordenada escolherPecaBranca(Coordenada c) {
		Peca p = c.getPeca();
		if(p == null || p.getColor() == 'p')
			throw new RuntimeException("Selecione uma peça branca.");
		if(c.getMoves().size() == 0)
			throw new RuntimeException("Nao existem movimentos para essa peca");
		
		for(Coordenada c1 : c.getMoves())
			t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());
		
		turno = Turno.TurnoBrancoMovimentar;
		
		return c;
	}
	
	public static Coordenada escolherPecaPreta(Coordenada c) {
		Peca p = c.getPeca();
		if(p == null || p.getColor() == 'b')
			throw new RuntimeException("Selecione uma peça preta.");
		if(c.getMoves().size() == 0)
			throw new RuntimeException("Nao existem movimentos para essa peca");
		
		for(Coordenada c1 : c.getMoves())
			t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());
		
		turno = Turno.TurnoPretoMovimentar;
		
		return c;
	}
	
	public static Coordenada movimentarPecaBranca(Coordenada c){
		List<Coordenada> movimentosPossiveis = clickedCoordenada.getMoves();
	
		if(c.equals(clickedCoordenada)){
			t.repaint();
			turno = Turno.TurnoBrancoEscolher;
			return null;
		}
		if(!movimentosPossiveis.contains(c))
			throw new RuntimeException("Movimento invalido");
		
		t.moveTo(clickedCoordenada, c.getCoordHorz(), c.getCoordVert());
		t.repaint();
		turno = Turno.TurnoPretoEscolher;
		return null;
	}
	
	public static Coordenada movimentarPecaPreta(Coordenada c){
		List<Coordenada> movimentosPossiveis = clickedCoordenada.getMoves();

		if(c.equals(clickedCoordenada)){
			t.repaint();
			turno = Turno.TurnoPretoEscolher;
			return null;
		}
		if(!movimentosPossiveis.contains(c))
			throw new RuntimeException("Movimento invalido");
		
		t.moveTo(clickedCoordenada, c.getCoordHorz(), c.getCoordVert());
		t.repaint();
		turno = Turno.TurnoBrancoEscolher;
		return null;
	}
}
