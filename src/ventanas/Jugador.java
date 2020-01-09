package ventanas;

public class Jugador {
	
	protected String nombreJugador, contrasenya;
	protected int idusuario;
	
	
	public Jugador(int idusuario,String nombreJugador, String contrasenya) {
		this.idusuario = idusuario;
		this.nombreJugador = nombreJugador;
		this.contrasenya = contrasenya;
	}
	
	public Jugador() {
		
	}

	public String getNombreJugador() {
		return nombreJugador;
	}

	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	
	
}
