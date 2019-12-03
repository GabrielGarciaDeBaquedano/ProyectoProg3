package ventanas;

public class Juego {
	
	protected int idJuego;
	protected String nombreJuego;
	
	public int getIdJuego() {
		return idJuego;
	}

	public void setIdJuego(int idJuego) {
		this.idJuego = idJuego;
	}

	public String getNombreJuego() {
		return nombreJuego;
	}

	public void setNombreJuego(String nombreJuego) {
		this.nombreJuego = nombreJuego;
	}

	public Juego(int idJuego, String nombreJuego) {
		
		this.idJuego = idJuego;
		this.nombreJuego = nombreJuego;
	}

}
