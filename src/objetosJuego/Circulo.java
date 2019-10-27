package objetosJuego;
import java.awt.Color;

import ventanas.VentanaPong;


/**
 * @author Jon Churruca Zabala
 * Esta clase circulo va a ser la pelota del juego y extiende de la clase figura.
 *
 */
public class Circulo extends Figura {
	
	private static final Color COLOR_POR_DEFECTO = Color.blue;  
	
	private double radio;  
	
	/* -----------------------------------------------------------------------------------
	   CONSTRUCTORES, GETTERS & SETTERS Y METODOS PROPIOS Y DE LA CLASE FIGURA (@OVERRIDE). 
	   -----------------------------------------------------------------------------------	*/
	
	public Circulo() {
		super( COLOR_POR_DEFECTO );
	
	}
	
	
	public Circulo( double radio, double x, double y, Color color ) {
		super( x, y, color );
		this.radio = radio;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	
	public Circulo( Circulo circulo ) {
		this( circulo.radio, circulo.x, circulo.y, circulo.color );
		vX = circulo.vX;
		vY = circulo.vY;
	}
	
	
	public double getRadio() {
		return radio;
	}

	
	public void setRadio(double radio) {
		this.radio = radio;
	}

	double rot = 0.0;
	
	public void dibuja( VentanaPong v ) {
		v.dibujaImagen( "/Pong/balon.png", x, y, radio/200, rot, 1.0f );
		rot += 0.05;
		v.dibujaCirculo( x, y, radio, 2f, color );
	}
	
	
	@Override
	public boolean seSaleEnVertical( VentanaPong v ) {
		return y-radio<=0 || y+radio>=v.getAltura();
	}

	
	@Override
	public boolean seSaleEnHorizontal( VentanaPong v ) {
		return x-radio<=0 || x+radio>=v.getAnchura();
	}

	
	public int salidaVertical( VentanaPong v ) {
		if (y-radio<=0) return +1;
		else if (y+radio>=v.getAltura()) return -1;
		else return 0;
	}

	
	public int salidaHorizontal( VentanaPong v ) {
		if (x-radio<=0) return +1;
		else if (x+radio>=v.getAnchura()) return -1;
		else return 0;
	}

	@Override
	public String toString() {
		return super.toString() + " (" + radio + ")";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Circulo)) {
			return false;
		}
		Circulo p2 = (Circulo) obj;  
		return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de los círculos this y p2
	}
	
}
