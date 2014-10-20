import javax.swing.JFrame;


public class Trab {
	
	public static void main(String[] args) {
		JFrame f = new JFrame ("TRABALHO INF1636 - XADREZ");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Tabuleiro t = new Tabuleiro();	
		f.add(t);
		f.setSize(715,735);
		f.setVisible(true);
	}
	
}
