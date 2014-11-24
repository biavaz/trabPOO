package com.puc.rio.inf1636.psmbv.main;

import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.puc.rio.inf1636.psmbv.gameplay.Coordenada;
import com.puc.rio.inf1636.psmbv.gameplay.MatrizPecas;
import com.puc.rio.inf1636.psmbv.gameplay.MovimentosEspeciais;
import com.puc.rio.inf1636.psmbv.gameplay.Peca;
import com.puc.rio.inf1636.psmbv.gameplay.Tabuleiro;
import com.puc.rio.inf1636.psmbv.gameplay.Turno;
import com.puc.rio.inf1636.psmbv.gameplay.pecas.Rainha;
import com.puc.rio.inf1636.psmbv.gameplay.pecas.Torre;

public class Trab implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Tabuleiro t = null;
	private static MovimentosEspeciais m = new MovimentosEspeciais();

	private static JFrame f = new JFrame("TRABALHO INF1636 - XADREZ");
	
	public static void main(String[] args) {

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();

		// File Menu, F - Mnemonic
		JMenu fileMenu = new JMenu("File");
		JMenu fileMenu2 = new JMenu ("Movimentos Especiais");
		menuBar.add(fileMenu);
		menuBar.add(fileMenu2);
		

		// File->New, N - Mnemonic
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				novoJogo();
			}
		});
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salvarJogo();
			}
		});

		JMenuItem loadMenuItem = new JMenuItem("Load");
		loadMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				carregarJogo();
			}
		});
		
		JMenuItem RoqueMenuItem = new JMenuItem("Roque");
		RoqueMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(t.getTurno()== Turno.TurnoBrancoEscolher || t.getTurno() == Turno.TurnoBrancoMovimentar){
					if (m.roque(f, 'b')==false){
						t.repaint();
						t.setTurno(Turno.TurnoBrancoEscolher);
					}
						
				}
				else{
					if (m.roque(f, 'p')==true){
						
						t.repaint();
						t.setTurno(Turno.TurnoPretoEscolher);
					}
						
				}
					
			}
		});
		
		JMenuItem EnPassantMenuItem = new JMenuItem("En Passant");
		EnPassantMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m.enPassant(f);
			}
		});
		
		
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(loadMenuItem);
		fileMenu2.add(RoqueMenuItem);
		fileMenu2.add(EnPassantMenuItem);

		f.setJMenuBar(menuBar);

		t = Tabuleiro.getInstance();
		f.add(t);
		f.setSize(500, 575);
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

	public static void handleMouseClick(MouseEvent click) {
		int x = click.getPoint().x;
		int y = click.getPoint().y;

		Coordenada c = t.getCoordenadaByXY(x, y);
		System.out.println(c.toString());
		try {
			if(MovimentosEspeciais.roqueAtivo==1){
				if(t.getTurno()== Turno.TurnoBrancoEscolher || t.getTurno() == Turno.TurnoBrancoMovimentar){
					m.trocaRoque(c, 'b');
				}
				else{
					m.trocaRoque(c, 'p');
				}
			}
			
			else{
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
		int cX = c.getCoordHorz();
		int cY = c.getCoordVert();
		Peca cP = c.getPeca();
		t.moveTo(t.getClickedCoordenada(), c.getCoordHorz(), c.getCoordVert());

		// verificar se o rei da cor fica ameaçado
		if (!m.reisalvo('b')) {
			JOptionPane.showMessageDialog(f, "Movimento Indisponivel, Rei ficará amreaçado", "Xadrez", 
					JOptionPane.INFORMATION_MESSAGE);
			t.moveTo(c, x, y);
			t.setCoordenada(new Coordenada(cX, cY, cP));
			t.repaint();
			t.setTurno(Turno.TurnoBrancoEscolher);
			return null;
		}
		t.repaint();
		
		//verificar se ocorreu XEQUE MATE
		if(m.xequeMate('b')){
			JOptionPane.showMessageDialog(f, "XEQUE MATE - PARABENS JOGADOR BRANCO", "Xadrez",
					JOptionPane.INFORMATION_MESSAGE);
			f.dispose();
		}
		
		//verificar se ocorreu XEQUE
		else if(m.xeque('b')){
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
			t.repaint();
			t.setTurno(Turno.TurnoPretoEscolher);
			return null;
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
		int cX = c.getCoordHorz();
		int cY = c.getCoordVert();
		Peca cP = c.getPeca();
		t.moveTo(t.getClickedCoordenada(), c.getCoordHorz(), c.getCoordVert());

		// verificar se o rei da cor fica ameaçado
		if (!m.reisalvo('p')) {
			JOptionPane.showMessageDialog(f, "Movimento Indisponivel, Rei ficará amreaçado", "Xadrez", 
					JOptionPane.INFORMATION_MESSAGE);
			t.moveTo(c, x, y);
			t.setCoordenada(new Coordenada(cX, cY, cP));
			t.repaint();
			t.setTurno(Turno.TurnoPretoEscolher);
			return null;
		}
		 
		t.repaint();
		
		//verificar se ocorreu XEQUE MATE
		if(m.xequeMate('p')){
			JOptionPane.showMessageDialog(f, "XEQUE MATE - PARABENS JOGADOR PRETO", "Xadrez",
					JOptionPane.INFORMATION_MESSAGE);
			f.dispose();
		}
		
		//verificar se ocorreu XEQUE
		else if(m.xeque('p')){
			JOptionPane.showMessageDialog(f, "XEQUE", "Xadrez",
					JOptionPane.INFORMATION_MESSAGE);
		}
		t.setTurno(Turno.TurnoBrancoEscolher);
		return null;
	}

	private static void novoJogo() {
		int result = JOptionPane.showConfirmDialog(f, "Deseja começar um novo jogo?",
		        "Xadrez", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			t.setClickedCoordenada(null);
			t.setMatrizPecas(new MatrizPecas());
			t.setTurno(Turno.TurnoBrancoEscolher);
			MovimentosEspeciais.roqueAtivo=0;
			t.repaint();
		}
	}
	
	private static void salvarJogo() {
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

	private static void carregarJogo() {
		String entrada = (String) JOptionPane.showInputDialog(f,
				"Digite o nome do arquivo:", "Xadrez",
				JOptionPane.PLAIN_MESSAGE);
		try {

			Serializer s = new Serializer(entrada);
			Tabuleiro tab = (Tabuleiro) s.getObjectOnSession();
			MovimentosEspeciais.roqueAtivo=0;
			t.setMatrizPecas(tab.getMatrizPecas());
			t.setTurno(tab.getTurno());
			t.setClickedCoordenada(tab.getClickedCoordenada());
			t.repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(f, "ARQUIVO INEXISTENTE",
					"Xadrez", JOptionPane.ERROR_MESSAGE);
		}
	}
}

