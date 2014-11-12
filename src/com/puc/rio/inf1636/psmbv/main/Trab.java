package com.puc.rio.inf1636.psmbv.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.puc.rio.inf1636.psmbv.gameplay.Coordenada;
import com.puc.rio.inf1636.psmbv.gameplay.MatrizPecas;
import com.puc.rio.inf1636.psmbv.gameplay.Peca;
import com.puc.rio.inf1636.psmbv.gameplay.Tabuleiro;
import com.puc.rio.inf1636.psmbv.gameplay.Turno;
import com.puc.rio.inf1636.psmbv.gameplay.pecas.Rainha;

public class Trab implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Tabuleiro t = null;

	private static JFrame f = new JFrame("TRABALHO INF1636 - XADREZ");

	public static void handleMouseClick(MouseEvent click) {
		int x = click.getPoint().x;
		int y = click.getPoint().y;

		Coordenada c = t.getCoordenadaByXY(x, y);
		System.out.println(c.toString());
		try {
			switch (t.getTurno()) {
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
		} catch (RuntimeException ex) {
			if (ex.getMessage() == null)
				ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}

	public static Coordenada escolherPecaBranca(Coordenada c) {
		Peca p = c.getPeca();
		if (p == null || p.getColor() == 'p')
			throw new RuntimeException("Selecione uma peça branca.");
		if (c.getMoves().size() == 0)
			throw new RuntimeException("Nao existem movimentos para essa peca");

		for (Coordenada c1 : c.getMoves())
			t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());

		t.setTurno(Turno.TurnoBrancoMovimentar);

		return c;
	}

	public static Coordenada escolherPecaPreta(Coordenada c) {
		Peca p = c.getPeca();
		if (p == null || p.getColor() == 'b')
			throw new RuntimeException("Selecione uma peça preta.");
		if (c.getMoves().size() == 0)
			throw new RuntimeException("Nao existem movimentos para essa peca");

		for (Coordenada c1 : c.getMoves())
			t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());

		t.setTurno(Turno.TurnoPretoMovimentar);

		return c;
	}

	public static Coordenada movimentarPecaBranca(Coordenada c) {
		List<Coordenada> movimentosPossiveis = t.getClickedCoordenada()
				.getMoves();

		for (Coordenada c1 : movimentosPossiveis)
			t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());

		// clicar na propria peca
		// clicar na propria peca novamente
		if (c.equals(t.getClickedCoordenada())) {
			if (c.equals(t.getClickedCoordenada())) {
				t.repaint();
				t.setTurno(Turno.TurnoBrancoEscolher);
				return null;
			}
		}

		// clicar na peca da mesma cor
		if (c.getPeca() != null
				&& c.getPeca().getColor() == t.getClickedCoordenada().getPeca()
						.getColor()) {
			t.paintImmediately(f.getBounds());
			t.setTurno(Turno.TurnoBrancoMovimentar);
			for (Coordenada c1 : c.getMoves())
				t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());
			return c;
		}

		if (!movimentosPossiveis.contains(c))
			throw new RuntimeException("Movimento invalido");

		// /fim do jogo - branco vencedor
		if (c.getPeca() != null && c.getPeca().getName().equals("Rei")) {
			System.out.println("Parabens jogador BRANCO");
			f.dispose();
		}

		// peao branco espeical
		if (t.getClickedCoordenada().getPeca() != null
				&& t.getClickedCoordenada().getPeca().getName().equals("Peao")
				&& c.getCoordVert() == 0) {
			System.out.println("peao branco vira dama");
			t.getClickedCoordenada().setPeca(new Rainha('b'));
			t.paintPeaoEspecial(t.getClickedCoordenada());
		}
		int x = t.getClickedCoordenada().getCoordHorz();
		int y = t.getClickedCoordenada().getCoordVert();

		t.moveTo(t.getClickedCoordenada(), c.getCoordHorz(), c.getCoordVert());

		// verificar se o rei da cor fica ameaçado
		 if(!reisalvo('b')){
		 System.out.println("Movimento Indisponivel, Rei ficará amreaçado");
		 t.moveTo(c,x,y); 
		 t.repaint();
		 t.setTurno(Turno.TurnoBrancoEscolher);
		 return null;
		 }
		 

		t.repaint();
		
		//verificar se ocorreu XEQUE MATE
		if(xequeMate('b')){
			JOptionPane.showMessageDialog(f, "XEQUE MATE - PARABENS JOGADOR BRANCO", "Xadrez",
					JOptionPane.INFORMATION_MESSAGE);
			f.dispose();
		}
		
		//verificar se ocorreu XEQUE
		if(xeque('b')){
			JOptionPane.showMessageDialog(f, "XEQUE", "Xadrez", JOptionPane.INFORMATION_MESSAGE);
		}
		
		t.setTurno(Turno.TurnoPretoEscolher);
		return null;
	}


	public static Coordenada movimentarPecaPreta(Coordenada c) {
		List<Coordenada> movimentosPossiveis = t.getClickedCoordenada()
				.getMoves();

		// clicar na propria peca novamente
		if (c.equals(t.getClickedCoordenada())) {
			if (c.equals(t.getClickedCoordenada())) {
				t.repaint();
				t.setTurno(Turno.TurnoBrancoEscolher);
				return null;
			}
		}

		// clicar em uma peca da mesma cor
		if (c.getPeca() != null
				&& c.getPeca().getColor() == t.getClickedCoordenada().getPeca()
						.getColor()) {
			t.paintImmediately(f.getBounds());
			t.setTurno(Turno.TurnoPretoMovimentar);
			for (Coordenada c1 : c.getMoves())
				t.paintCoordenada(c1.getCoordHorz(), c1.getCoordVert());
			return c;
		}

		if (!movimentosPossiveis.contains(c))
			throw new RuntimeException("Movimento invalido");

		// fim do jogo - preto vencedor
		if (c.getPeca() != null && c.getPeca().getName().equals("Rei")) {
			System.out.println("Parabens jogador PRETO");
			f.dispose();
		}

		// peao preto espeical
		if (t.getClickedCoordenada().getPeca() != null
				&& t.getClickedCoordenada().getPeca().getName().equals("Peao")
				&& c.getCoordVert() == 7) {
			System.out.println("peao preto vira dama");
			t.getClickedCoordenada().setPeca(new Rainha('p'));
			t.paintPeaoEspecial(t.getClickedCoordenada());
		}

		int x = t.getClickedCoordenada().getCoordHorz();
		int y = t.getClickedCoordenada().getCoordVert();

		t.moveTo(t.getClickedCoordenada(), c.getCoordHorz(), c.getCoordVert());

		// verificar se o rei da cor fica ameaçado
		 if(!reisalvo('p')){
		 System.out.println("Movimento Indisponivel, Rei ficará amreaçado");
		 t.moveTo(c,x,y); 
		 t.repaint(); 
		 t.setTurno(Turno.TurnoPretoEscolher);
		 return null;
		 }
		 
		t.repaint();
		
		//verificar se ocorreu XEQUE MATE
		if(xequeMate('p')){
			JOptionPane.showMessageDialog(f, "XEQUE MATE - PARABENS JOGADOR PRETO", "Xadrez",
					JOptionPane.INFORMATION_MESSAGE);
			f.dispose();
		}
		
		//verificar se ocorreu XEQUE
		if(xeque('p')){
			JOptionPane.showMessageDialog(f, "XEQUE", "Xadrez",
					JOptionPane.INFORMATION_MESSAGE);
		}
		t.setTurno(Turno.TurnoBrancoEscolher);
		return null;
	}

	
	private static boolean reisalvo(char c) {
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
	
	private static boolean xeque (char c){
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

	
	@SuppressWarnings("null")
	private static boolean xequeMate (char c ){
		int x = 0;
		int y = 0;
		String rei = "Rei";
		int xRei = -1, yRei = -1;
		
		ArrayList <Peca> pecasAtacantes = null;
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
		
		
		if( xeque (c) ){
			//verificar as pecas que comeriam o rei
			for (x = 0; x <= 7; x++) {
				for (y = 0; y <= 7; y++) {
					if (t.getPecaAtCoordenada(x, y) != null	&& t.getPecaAtCoordenada(x, y).getColor() == c) {
						List<Coordenada> movs = t.getCoordenada(x, y).getMoves();
						if (movs.contains(t.getCoordenada(xRei, yRei))){
								pecasAtacantes.add(t.getPecaAtCoordenada(x, y));
							}
								
						}
							
					}
					
				}
		

		//se duas pecas diferentes comem o rei, nao tem como fugir: xeque-mate
		if(pecasAtacantes.size()>1)
			return true;
		
		
		
		//se o rei ta ameacado e algum movimento dele consigo fugir do xeque: nao eh xeque-mate
		List <Coordenada> movsRei = t.getCoordenada(xRei, yRei).getMoves();
		for(Coordenada coord: movsRei){
			int coordhorz= coord.getCoordHorz();
			int coordvert = coord.getCoordVert();
			
			t.moveTo(coord, xRei, yRei);
			if(!xeque(c)){
				t.moveTo(t.getCoordenada(xRei, yRei), coordhorz, coordvert);
				return false;
			}
			t.moveTo(t.getCoordenada(xRei, yRei), coordhorz, coordvert);		
		}
		
		//se a peca que esta amecando o rei pode ser comido
		for (x = 0; x <= 7; x++) {
			for (y = 0; y <= 7; y++)
				if (t.getPecaAtCoordenada(x, y) != null
				&& t.getPecaAtCoordenada(x, y).getColor() != c) {
					if (t.getCoordenada(x, y).getMoves().contains(pecasAtacantes.listIterator(0)))
						return false;				
				}
		}
		
		return true;
	}
		
		//se nao esta em xeque;
		return false;
	}
	
	public static void main(String[] args) {

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();

		// File Menu, F - Mnemonic
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// File->New, N - Mnemonic
		
		
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(f, "Deseja começar um novo jogo?",
				        "Xadrez", JOptionPane.OK_CANCEL_OPTION);
				if(result == JOptionPane.OK_OPTION){
					MatrizPecas m = new MatrizPecas();
					t.setMatrizPecas(m);
					t.repaint();
					t.setTurno(Turno.TurnoBrancoEscolher);
				}
			}
		});
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// implementar o cancelamento do moviemnto antes de salvar o
				// jogo

				String entrada = (String) JOptionPane.showInputDialog(f,
						"Digite o nome do arquivo:", "Xadrez",
						JOptionPane.PLAIN_MESSAGE);
				if (entrada != null && entrada.length() > 0) {
					Serializer s = new Serializer(entrada);
					s.writeObjectOnSession(t);
					JOptionPane.showMessageDialog(f, "Jogo Salvo!", "Xadrez",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(f, "Nome inválido", "Xadrez",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JMenuItem loadMenuItem = new JMenuItem("Load");
		loadMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String entrada = (String) JOptionPane.showInputDialog(f,
						"Digite o nome do arquivo:", "Xadrez",
						JOptionPane.PLAIN_MESSAGE);
				try {

					Serializer s = new Serializer(entrada);
					Tabuleiro tab = (Tabuleiro) s.getObjectOnSession();
					t.setMatrizPecas(tab.getMatrizPecas());
					t.setTurno(tab.getTurno());
					t.setClickedCoordenada(tab.getClickedCoordenada());
					t.repaint();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(f, "ARQUIVO INEXISTENTE",
							"Xadrez", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(loadMenuItem);

		f.setJMenuBar(menuBar);

		t = Tabuleiro.getInstance();
		f.add(t);
		f.setSize(500, 535);
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
