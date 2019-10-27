package utils;

import java.awt.Point;

import java.awt.event.MouseEvent;

public class RatonDrag implements EventoRaton {
	private MouseEvent meFinalDrag;
	private long timeInicioDrag;
	private Point puntoInicioDrag;
	public RatonDrag( long timeInicioDrag, Point puntoInicioDrag, MouseEvent meFinalDrag ) {
		this.meFinalDrag = meFinalDrag;
		this.timeInicioDrag = timeInicioDrag;
		this.puntoInicioDrag = puntoInicioDrag;
	}
	
	public long getTime() {
		return meFinalDrag.getWhen();
	}
	// Devuelve posici�n de final de drag

	public Point getPosicion() {
		return meFinalDrag.getPoint();
	}
	
	// Devuelve momento de inicio del drag
	
	public long getTimeIni() {
		return timeInicioDrag;
	}
	// Devuelve posici�n de inicio del drag
	
	public Point getPosicionIni() {
		return puntoInicioDrag;
	}

	public String toString() {
		return "RatonDrag: (" + getPosicionIni().getX() + "," + getPosicionIni().getY() + ")" 
				+ "-->(" + getPosicion().getX() + "," + getPosicion().getY() + ")";
	}	
}
