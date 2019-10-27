package objetosJuego;

import java.awt.Point;
import ventanas.VentanaGraficaFlappyCar;
abstract public class ObjetoFlappyCar {
	protected VentanaGraficaFlappyCar ventana;
		protected double posX;
		protected double posY;
		protected int ancho;
		protected int alto;
		protected long tiempoMovimiento;	
		public ObjetoFlappyCar( int posX, int posY, int ancho, int alto, VentanaGraficaFlappyCar ventana ) {
			this.ventana = ventana;
			this.posX = posX;
			this.posY = posY;
			this.ancho = ancho;
			this.alto = alto;
			this.tiempoMovimiento = System.currentTimeMillis();

		}
		public double getX() {
			return posX;
		}
		public double getY() {
			return posY;
		}
		public int getAncho() {
			return ancho;
		}
		public int getAlto() {
			return alto;
		}
		
		public void setPosicion( Point p ) {
			posX = p.getX();
			posY = p.getY();
		}
		public void setPosicion( double posX, double posY ) {
			this.posX = posX;
			this.posY = posY;
		}
		abstract public void quitar();
		abstract public void mover();
		public boolean chocaCon( ObjetoFlappyCar o2 ) {
			boolean choca = !(getX() > o2.getX()+o2.getAncho() ||
					getX() + getAncho() < o2.getX() ||
					getY() > o2.getY()+o2.getAlto() ||
					getY() + getAlto() < o2.getY());
			return choca;
		}
		
		public String toString() {
			return "[objetoFlappyCar (" + posX + "," + posY + ")]";
		}
	}
