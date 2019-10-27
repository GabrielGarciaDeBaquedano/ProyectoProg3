package objetosJuego;

import java.awt.Color;

import utils.BoundingCircle;
import utils.Chocable;
import utils.Envolvente;

/** Clase que permite crear y gestionar naves y dibujarlas en una ventana gráfica
 */
public class Disparo extends ObjetoJuego implements Chocable {
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================

	final double VDisp = 12;
	double Vx, Vy;
	boolean vivo;
	private final BoundingCircle BC_DISPARO = new BoundingCircle( x, y, 1 );
	
	
	/** Crea un nuevo disparo
	 * @param x coordenada x del disparo (en pixeles)
	 * @param y coordenada y del disparo (en pixeles)
	 * @param angulo angulo que lleva el disparo
	 * @param VxNave velocidad en x que tiene la nave en ese momento
	 * @param VyNave velocidad en y que tiene la nave en ese momento
	 * @param vidaRestante frames que le quedan al disparo para desaparecer
	 */
	public Disparo( double x, double y, double rot, double VxNave, double VyNave, boolean vivo) {
		super( x, y, rot );
		this.vivo = vivo;
		// suma la velocidad de la nave a los disparos
		Vx=VDisp*Math.cos(rot)+VxNave;
		Vy=VDisp*Math.sin(rot)+VyNave;
	}
	
	public void mueve(double segs){
		super.mueve(segs);
		 x+=Vx; 
		 BC_DISPARO.setX(x);
		 y+=Vy;
		 BC_DISPARO.setY(y);
		 }

	/** Comprueba si la nave es igual a otro objeto dado. Se entiende que dos naves son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Disparo) {
			Disparo p2 = (Disparo) obj;  // Cast de obj a nave2 (lo entenderemos mejor al ver herencia)
			return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de las naves this y p2
		} else {
			return false;
		}
	}

	@Override
	public void dibuja(utils.VentanaGrafica v) {
		v.dibujaCirculo(x, y, 1, 1, Color.black);
		
	}
	
	public double getX(){
		 return x;
		 }

		public double getY(){
		 return y;
		 }

		public boolean getVivo(){
		 return vivo;
		 }
		
		public void setVivo(boolean vivo){
			this.vivo=vivo;
		}

		@Override
		public Envolvente getEnvolvente() {
			return BC_DISPARO;
		}
	
		@Override
		public String toString() {
			return x+" "+y+" "+"1";
		}
		
}
