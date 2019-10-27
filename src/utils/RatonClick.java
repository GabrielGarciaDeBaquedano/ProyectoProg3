package utils;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class RatonClick implements EventoRaton {
	private MouseEvent me;
	public RatonClick( MouseEvent me ) {
		this.me = me;
	}
	
	public long getTime() {
		return me.getWhen();
	}
	
	public Point getPosicion() {
		return me.getPoint();
	}
	
	public String toString() {
		return "RatonClick: (" + getPosicion().getX() + "," + getPosicion().getY() + ")";
	}
}