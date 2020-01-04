package ventanas;

public class Partida {
	
	public int codPartida;
	protected String nombreJugador;
	protected String nombreJuego;
	protected int puntuacion;
	protected long tiempoPartida;
	protected long fechaPartida;
	
	public Partida(int codPartida, String nombreJugador, String nombreJuego, int puntuacion, long tiempoPartida, long fechaPartida) {
		super();
		this.codPartida = codPartida;
		this.nombreJugador = nombreJugador;
		this.nombreJuego = nombreJuego;
		this.puntuacion = puntuacion;
		this.tiempoPartida = tiempoPartida;
		this.fechaPartida = fechaPartida;
	}

	public String getNombreJugador() {
		return nombreJugador;
	}

	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}

	public String getNombreJuego() {
		return nombreJuego;
	}

	public void setNombreJuego(String nombreJuego) {
		this.nombreJuego = nombreJuego;
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

	public void setTiempoPartida(long tiempoPartida) {
		this.tiempoPartida = tiempoPartida;
	}

	public long getFechaPartida() {
		return fechaPartida;
	}

	public void setFechaPartida(long fechaPartida) {
		this.fechaPartida = fechaPartida;
	}
	
	
}
