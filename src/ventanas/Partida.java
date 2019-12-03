package ventanas;

import java.util.Date;

public class Partida {
	
	protected int codPartida;
	protected int puntuacion;
	protected double tiempoPartida;
	protected Date fechaPartida;
	
	public Partida(int codPartida, int puntuacion, double tiempoPartida, Date fechaPartida) {
		super();
		this.codPartida = codPartida;
		this.puntuacion = puntuacion;
		this.tiempoPartida = tiempoPartida;
		this.fechaPartida = fechaPartida;
	}

	public int getCodPartida() {
		return codPartida;
	}

	public void setCodPartida(int codPartida) {
		this.codPartida = codPartida;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public double getTiempoPartida() {
		return tiempoPartida;
	}

	public void setTiempoPartida(double tiempoPartida) {
		this.tiempoPartida = tiempoPartida;
	}

	public Date getFechaPartida() {
		return fechaPartida;
	}

	public void setFechaPartida(Date fechaPartida) {
		this.fechaPartida = fechaPartida;
	}
	
	
}
