package objetosJuego;

import java.awt.Color;

import utils.FisicaPong;
import ventanas.VentanaPong;

public abstract class Figura {
		
	
	protected double x;      
	protected double y;      
	protected double vX;     
	protected double vY;     
	protected Color color;   	
	
	/* ---------------------------------------------------------------------------------------
	         CONSTRUCTORES, GETTERS Y SETTERS Y METODOS (TODO ABSTRACTO, CLASE PADRE). 
	   ---------------------------------------------------------------------------------------  */
	
	
	public Figura( Color c ) {
		this.color = c;
		
	}
	
	public Figura( double x, double y, Color c ) {
		this.x = x;
		this.y = y;
		this.color = c;
	}

	
	public double getX() {
		return x;
	}

	
	public void setX(double x) {
		this.x = x;
	}

	
	public double getY() {
		return y;
	}

	
	public void setY(double y) {
		this.y = y;
	}
	
	
	public double getVX() {
		return vX;
	}

	
	public void setVX(double vx) {
		this.vX = vx;
	}

	
	public double getVY() {
		return vY;
	}

	
	public void setVY(double vy) {
		this.vY = vy;
	}

	
	public void incXY( double incX, double incY ) {
		x += incX;
		y += incY;
	}

	
	public Color getColor() {
		return color;
	}

	
	public void setColor(Color color) {
		this.color = color;
	}

	
	/**
	 * @param segs
	 * Uso del metodo calcEspacio de la clase Fisica para calcular la posicion en el movimiento
	 */
	public void mueve( double segs ) {
		x = FisicaPong.calcEspacio( x, vX, segs );
		y = FisicaPong.calcEspacio( y, vY, segs );
	}
	
	
	public abstract void dibuja( VentanaPong v );

	
	public abstract boolean seSaleEnVertical( VentanaPong v );

	
	public abstract boolean seSaleEnHorizontal( VentanaPong v );

	@Override
	public String toString() {
		return x + " , " + y;
	}
	
}
