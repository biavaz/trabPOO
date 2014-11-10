package com.puc.rio.inf1636.psmbv.main;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import com.puc.rio.inf1636.psmbv.gameplay.pecas.Rainha;
//Serializer s = new Serializer("lastgame");
//s.writeObjectOnSession(new Trab());

public class Trab implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static Tabuleiro t = null;
	
	private static JFrame f = new JFrame ("TRABALHO INF1636 - XADREZ");
	
	
	
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
	
		for(Coordenada c1 : movimentosPossiveis)
			t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());
		
		if (c.getPeca()!=null && c.getPeca().getColor() == t.getClickedCoordenada().getPeca().getColor()){
			t.paintImmediately(f.getBounds());
			t.setTurno(Turno.TurnoBrancoMovimentar);
			for(Coordenada c1 : c.getMoves())
				t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());
			return c;
		}
		
		
		if(!movimentosPossiveis.contains(c))
			throw new RuntimeException("Movimento invalido");
		
		///fim do jogo - branco vencedor
		if(c.getPeca()!= null && c.getPeca().getName() == "Rei"){
			System.out.println("Parabens jogador BRANCO");
			f.dispose();
			}
		
		//verificar se o rei da cor fica ameaçado
		/*if(!reisalvo('b')){
			System.out.println("Movimento Indisponivel, Rei ficará amreaçado");
			t.setTurno(Turno.TurnoBrancoEscolher);
			return null;
		}*/
		
		//peao branco espeical 
		if(t.getClickedCoordenada().getPeca()!=null && t.getClickedCoordenada().getPeca().getName()=="Peao" && c.getCoordVert()==0){
			System.out.println("peao branco vira dama");
			t.getClickedCoordenada().setPeca(new Rainha ('b'));	
			t.paintPeaoEspecial(t.getClickedCoordenada());
		}
			
		t.moveTo(t.getClickedCoordenada(), c.getCoordHorz(), c.getCoordVert());
		t.repaint();
		t.setTurno(Turno.TurnoPretoEscolher);
		return null;
	}
	
	private static boolean reisalvo(char c) {
		int x=0;
		int y=0;
		String rei = new String("Rei");
		for(x=0; x<7; x++){
			for (y=0; y<7; y++)
				if(t.getPecaAtCoordenada(x, y)!= null && rei.equals(t.getPecaAtCoordenada(x, y).getName()) && t.getPecaAtCoordenada(x, y).getColor() == c ){
					System.out.println("achou peca " + t.getPecaAtCoordenada(x, y).getName());
					System.out.println("achou coords " + x+ " " + y);
					break;
				}
		}
		System.out.println("rei coods" + x + " " + y);
		return false;
	}

	public static Coordenada movimentarPecaPreta(Coordenada c){
		List<Coordenada> movimentosPossiveis = t.getClickedCoordenada().getMoves();

		
		if (c.getPeca()!=null && c.getPeca().getColor() == t.getClickedCoordenada().getPeca().getColor()){
			t.paintImmediately(f.getBounds());
			t.setTurno(Turno.TurnoPretoMovimentar);
			for(Coordenada c1 : c.getMoves())
				t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());
			return c;
		}
		
		if(!movimentosPossiveis.contains(c))
			throw new RuntimeException("Movimento invalido");
		
		//fim do jogo - preto vencedor
		if(c.getPeca()!= null && c.getPeca().getName() == "Rei"){
			System.out.println("Parabens jogador PRETO");
			f.dispose();
			}
		
		//peao preto espeical 
		if(t.getClickedCoordenada().getPeca()!=null && t.getClickedCoordenada().getPeca().getName()=="Peao" && c.getCoordVert()==7){
			System.out.println("peao preto vira dama");
			t.getClickedCoordenada().setPeca(new Rainha ('p'));	
			t.paintPeaoEspecial(t.getClickedCoordenada());
		}
		
		t.moveTo(t.getClickedCoordenada(), c.getCoordHorz(), c.getCoordVert());
		t.repaint();
	
		t.setTurno(Turno.TurnoBrancoEscolher);
		return null;
	}

	
	public static void main(String[] args) {

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
				System.out.println("Digite o nome do arquivo que deseja salvar");
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  
				String entrada = null;
				try {
					entrada = in.readLine();
				} catch (IOException e) {
					
				}  
				System.out.println("Saving game");
				Serializer s = new Serializer(entrada);
				s.writeObjectOnSession(t);				
			}
		});
	    
	    JMenuItem loadMenuItem = new JMenuItem("Load");
	    loadMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Loading last game");
				System.out.println("Digite o nome do arquivo que deseja abrir");
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  
				String entrada = null;
				try {
					entrada = in.readLine();
				} catch (IOException e) {
					
				} 
				try {
					
					FileReader fr = new FileReader(entrada);
					Serializer s = new Serializer(entrada);
					Tabuleiro tab = (Tabuleiro) s.getObjectOnSession();
					t.setMatrizPecas(tab.getMatrizPecas());
					t.setTurno(tab.getTurno());
					t.setClickedCoordenada(tab.getClickedCoordenada());
					t.repaint();	
				} catch (FileNotFoundException e) {
					System.out.println("ARQUIVO INEXISTENTE");
				}
				
				
				
			}
		});
	    fileMenu.add(saveMenuItem);
	    fileMenu.add(loadMenuItem);

	    f.setJMenuBar(menuBar);
	    
		t = Tabuleiro.getInstance();	
		f.add(t);
		f.setSize(500,535);
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

}
