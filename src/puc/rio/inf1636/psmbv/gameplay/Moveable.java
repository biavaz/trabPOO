package com.puc.rio.inf1636.psmbv.gameplay;

import java.util.ArrayList;

public interface Moveable {
	public ArrayList<Coordenada> getPossibleMovements(int x, int y);
}
