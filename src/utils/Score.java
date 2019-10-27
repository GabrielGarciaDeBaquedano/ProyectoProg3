package utils;

import java.io.Serializable;
public class Score implements Serializable {
	private static final long serialVersionUID = 1L;
	private int puntuacionMaxima;
	public Score(int puntuacionMaxima) {
		super();
		this.puntuacionMaxima = puntuacionMaxima;
	}
	public Score() {
		super();
	}
	public int getPuntuacionMaxima() {
		return puntuacionMaxima;
	}
	public void setPuntuacionMaxima(int puntuacionMaxima) {
		this.puntuacionMaxima = puntuacionMaxima;
	}
	
}