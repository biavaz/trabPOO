package com.puc.rio.inf1636.psmbv.gameplay;

import java.awt.Image;

import com.puc.rio.inf1636.psmbv.gameplay.pecas.Bispo;
import com.puc.rio.inf1636.psmbv.gameplay.pecas.Cavalo;
import com.puc.rio.inf1636.psmbv.gameplay.pecas.Peao;
import com.puc.rio.inf1636.psmbv.gameplay.pecas.Rainha;
import com.puc.rio.inf1636.psmbv.gameplay.pecas.Rei;
import com.puc.rio.inf1636.psmbv.gameplay.pecas.Torre;

public abstract class Peca implements Moveable{
	
	protected Image icon;
	protected char color;
	
	public Peca(char color){
		this.color = color;
	}

	public static Peca createByInicialPosition(int x, int y) {
		Peca p = null;
		if(y == 0){
			switch(x){
			case 0:
			case 7:
				p = new Torre('p');
				break;
			case 1:
			case 6:
				p = new Cavalo('p');
				break;
			case 2:
			case 5:
				p = new Bispo('p');
				break;
			case 3:
				p = new Rainha('p');
				break;
			case 4:
				p = new Rei('p');
				break;
			default: 
				p = null;
			}
		}
		if(y == 1){
			p = new Peao('p');
		}
		if(y == 6){
			p = new Peao('b');
		}
		
		if(y == 7){
			switch(x){
			case 0:
			case 7:
				p = new Torre('b');
				break;
			case 1:
			case 6:
				p = new Cavalo('b');
				break;
			case 2:
			case 5:
				p = new Bispo('b');
				break;
			case 3:
				p = new Rainha('b');
				break;
			case 4:
				p = new Rei('b');
				break;
			default: 
				p = null;
			}
		}
			
		return p;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public char getColor() {
		return color;
	}

	public void setColor(char color) {
		this.color = color;
	}

}
