package com.puc.rio.inf1636.psmbv.graphic;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.puc.rio.inf1636.psmbv.gameplay.Peca;

public class ImageHelper {
	public static Image getIconFromPeca(Peca p){
		String nomeArq = p.getColor() + "_" + p.getName().toLowerCase();
		return new ImageIcon ("pecas/"+nomeArq+".png").getImage();
	}
}
