package utils;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class RatonPulsado implements EventoRaton {
	private MouseEvent me;
	public RatonPulsado( MouseEvent me ) {
		this.me = me;
	}
	
	public long getTime() {
		return me.getWhen();
	}

	public Point getPosicion() {
		return me.getPoint();
	}

	public String toString() {
		return "RatonPulsado: (" + getPosicion().getX() + "," + getPosicion().getY() + ")";
	}
}
