package ventanas;

public class Puntuacion implements Comparable<Puntuacion> {
	private String nombreUsuario;
	private int puntos;
	private double tiempo;
	public Puntuacion(String nombreUsuario, int puntos, double tiempo) {
		this.nombreUsuario = nombreUsuario;
		this.puntos = puntos;
		this.tiempo = tiempo;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public double getTiempo() {
		return tiempo;
	}
	public void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}
	@Override
	public String toString() {
		return nombreUsuario + ": " + puntos + " (" + tiempo + ")";
	}
	@Override
	public int compareTo(Puntuacion p) {
		int dif = p.puntos - puntos;  
		if (dif==0) {
			dif = (int) Math.round( tiempo*1000 - p.tiempo*1000 ); 
		}
		if (dif==0) {
			dif = nombreUsuario.compareTo( p.nombreUsuario );
		}
		return dif;
	}	
}
