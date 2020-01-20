package ventanas;

public class Partida {
	
	public int codPartida;
	protected int idJugador;
	protected int idJuego;
	protected int puntuacion;
	protected long tiempoPartida;
	protected long fechaPartida;
	
	public Partida(int codPartida, int idJugador, int idJuego, int puntuacion, long tiempoPartida, long fechaPartida) {
		super();
		this.codPartida = codPartida;
		this.idJugador = idJugador;
		this.idJuego = idJuego;
		this.puntuacion = puntuacion;
		this.tiempoPartida = tiempoPartida;
		this.fechaPartida = fechaPartida;
	}
	
	public Partida() {
		
	}

	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

	public int getIdJuego() {
		return idJuego;
	}

	public void setIdJuego(int idJuego) {
		this.idJuego = idJuego;
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
