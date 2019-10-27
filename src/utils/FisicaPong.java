package utils;

import java.awt.Point;
import java.awt.geom.Point2D;


public class FisicaPong {
	
	
	public static double distancia (Point p1, Point p2) {
		return distancia( p1.x, p1.y, p2.x, p2.y );
	}
	
	
	public static double distancia( double x1, double y1, double x2, double y2 ) {
		return Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) ); 
	}
	
	
	public static double calcEspacio( double espacio, double velocidad, double segs ) {
		return espacio + velocidad * segs;
	}
	
	
	public static Polar cartAPolar( double x, double y ) {
		return new Polar( Math.sqrt( x*x + y*y ), Math.atan2( y, x ) );
	}
	
	
	/**
	 * @author Jon Churruca Zabala
	 * Clase para calcular los choques de la bola en el juego
	 */
	public static class Polar {
		private double modulo;
		private double argumento;
		
		public Polar( double modulo, double argumento ) {
			this.modulo = modulo;
			this.argumento = argumento;
		}
		
		public Polar( Point2D punto ) {
			this.modulo = Math.sqrt( punto.getX()*punto.getX() + punto.getY()*punto.getY() );
			this.argumento = Math.atan2( punto.getY(), punto.getX() );
		}
		public double getArgumento() {
			return argumento;
		}
		public double getModulo() {
			return modulo;
		}
		
		public Point2D toPoint() {
			Point2D ret = new Point2D.Double( modulo*Math.cos(argumento), modulo*Math.sin(argumento) );
			return ret;
		}
		
		public void rotar( double rotacionRad ) {
			argumento += rotacionRad;
		}
		
		public void setModulo( double nuevoModulo ) {
			modulo = nuevoModulo;
		}
		@Override
		public String toString() {
			return "|" + modulo + "| " + argumento + " rad";
		}
	}
	
	/**
	 * @param args
	 * prueba para ver si funciona b
	 */
	public static void main(String[] args) {
		double x = 2;
		double y = -2;
		Polar p = cartAPolar( x, y );
		System.out.println( x + "," + y + " = " + p + "  -  " + p.toPoint() );
	}
	
}
