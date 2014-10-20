import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Images extends JPanel {
	
	public Image bispob, cavalob, damab, peaob, reib, torreb;
	public Image bispop, cavalop, damap, peaop, reip, torrep;
	public boolean imagesLoaded = false;
	
	public void loadImages() {
		bispob = new ImageIcon ("pecas/b_bispo.png").getImage();
		cavalob = new ImageIcon ("pecas/b_cavalo.png").getImage();
		damab = new ImageIcon ("pecas/b_dama.png").getImage();
		peaob = new ImageIcon ("pecas/b_peao.png").getImage();
		reib = new ImageIcon ("pecas/b_rei.png").getImage();
		torreb = new ImageIcon ("pecas/b_torre.png").getImage();
		
		bispop = new ImageIcon ("pecas/p_bispo.png").getImage();
		cavalop = new ImageIcon ("pecas/p_cavalo.png").getImage();
		damap = new ImageIcon ("pecas/p_dama.png").getImage();
		peaop = new ImageIcon ("pecas/p_peao.png").getImage();
		reip = new ImageIcon ("pecas/p_rei.png").getImage();
		torrep = new ImageIcon ("pecas/p_torre.png").getImage();
		
		imagesLoaded = true;
	}

}
