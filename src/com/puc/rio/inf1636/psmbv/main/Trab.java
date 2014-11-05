package com.puc.rio.inf1636.psmbv.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.puc.rio.inf1636.psmbv.gameplay.Coordenada;
import com.puc.rio.inf1636.psmbv.gameplay.Peca;
import com.puc.rio.inf1636.psmbv.gameplay.Tabuleiro;
import com.puc.rio.inf1636.psmbv.gameplay.Turno;
//Serializer s = new Serializer("lastgame");
//s.writeObjectOnSession(new Trab());

public class Trab implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static Tabuleiro t = null;
	
	public static void main(String[] args) {
		JFrame f = new JFrame ("TRABALHO INF1636 - XADREZ");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();

	    // File Menu, F - Mnemonic
	    JMenu fileMenu = new JMenu("File");
	    menuBar.add(fileMenu);

	    // File->New, N - Mnemonic
	    JMenuItem saveMenuItem = new JMenuItem("Save");
	    saveMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				//implementar o cancelamento do moviemnto antes de salvar o jogo
				System.out.println("Saving game");
				Serializer s = new Serializer("lastgame");
				s.writeObjectOnSession(t);				
			}
		});
	    
	    JMenuItem loadMenuItem = new JMenuItem("Load");
	    loadMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Loading last game");
				Serializer s = new Serializer("lastgame");
				Tabuleiro tab = (Tabuleiro) s.getObjectOnSession();
				t.setMatrizPecas(tab.getMatrizPecas());
				t.setTurno(tab.getTurno());
				t.setClickedCoordenada(tab.getClickedCoordenada());
				t.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent event) {
						super.mouseClicked(event);
						handleMouseClick(event);
					}
				});
				t.repaint();
			}
		});
	    fileMenu.add(saveMenuItem);
	    fileMenu.add(loadMenuItem);

	    f.setJMenuBar(menuBar);
	    
		t = Tabuleiro.getInstance();	
		f.add(t);
		f.setSize(500,515);
		f.setVisible(true);
		t.setTurno(Turno.TurnoBrancoEscolher);
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
			switch(t.getTurno()){
				case TurnoBrancoEscolher:
					t.setClickedCoordenada(escolherPecaBranca(c));
					break;
				case TurnoPretoEscolher:
					t.setClickedCoordenada(escolherPecaPreta(c));
					break;
				case TurnoBrancoMovimentar:
					t.setClickedCoordenada(movimentarPecaBranca(c));
					break;
				case TurnoPretoMovimentar:
					t.setClickedCoordenada(movimentarPecaPreta(c));
					break;
				default:
					break;
			}
		} catch (RuntimeException ex){
			if(ex.getMessage() == null)
				ex.printStackTrace();
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
		
		t.setTurno(Turno.TurnoBrancoMovimentar);
		
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
		
		t.setTurno(Turno.TurnoPretoMovimentar);
		
		return c;
	}
	
	public static Coordenada movimentarPecaBranca(Coordenada c){
		List<Coordenada> movimentosPossiveis = t.getClickedCoordenada().getMoves();
	
		if(c.equals(t.getClickedCoordenada())){
			t.repaint();
			t.setTurno(Turno.TurnoBrancoEscolher);
			return null;
		}
		if(!movimentosPossiveis.contains(c))
			throw new RuntimeException("Movimento invalido");
		
		t.moveTo(t.getClickedCoordenada(), c.getCoordHorz(), c.getCoordVert());
		t.repaint();
		t.setTurno(Turno.TurnoPretoEscolher);
		return null;
	}
	
	public static Coordenada movimentarPecaPreta(Coordenada c){
		List<Coordenada> movimentosPossiveis = t.getClickedCoordenada().getMoves();

		if(c.equals(t.getClickedCoordenada())){
			t.repaint();
			t.setTurno(Turno.TurnoPretoEscolher);
			return null;
		}
		if(!movimentosPossiveis.contains(c))
			throw new RuntimeException("Movimento invalido");
		
		t.moveTo(t.getClickedCoordenada(), c.getCoordHorz(), c.getCoordVert());
		t.repaint();
		t.setTurno(Turno.TurnoBrancoEscolher);
		return null;
	}
}
