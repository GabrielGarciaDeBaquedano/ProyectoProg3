package ventanas;

public class Jugador {
	
	protected int idJugador;
	protected String nombreJugador;
	
	public Jugador(int idJugador, String nombreJugador) {
		
		this.idJugador = idJugador;
		this.nombreJugador = nombreJugador;
		
	}

	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

	public String getNombreJugador() {
		return nombreJugador;
	}

	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}
}
