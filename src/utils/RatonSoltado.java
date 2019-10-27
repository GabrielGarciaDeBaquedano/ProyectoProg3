package utils;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class RatonSoltado implements EventoRaton {
	private MouseEvent me;
	public RatonSoltado( MouseEvent me ) {
		this.me = me;
	}

	public long getTime() {
		return me.getWhen();
	}

	public Point getPosicion() {
		return me.getPoint();
	}

	public String toString() {
		return "RatonSoltado: (" + getPosicion().getX() + "," + getPosicion().getY() + ")";
	}
}
