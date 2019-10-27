package objetosJuego;
import java.awt.Color;

import ventanas.VentanaPong;


/**
 * @author Jon Churruca Zabala
 * Clase rectangulo que extiende de figura. Seran las palas del juego
 *
 */
public class Rectangulo extends Figura {
	
	private static Color COLOR_POR_DEFECTO = Color.green;  
	
	
	
	private double tamX;   
	private double tamY;   
	private int marcador;

	
	/* -----------------------------------------------------------------------------------
	   CONSTRUCTORES, GETTERS & SETTERS Y METODOS PROPIOS Y DE LA CLASE FIGURA (@OVERRIDE). 
	   -----------------------------------------------------------------------------------	*/
	
	public Rectangulo() {
		super( COLOR_POR_DEFECTO );
		
	}
	
	
	public Rectangulo( double anc, double alt, double x, double y, Color color, int marcador ) {
		super( x, y, color);
		this.tamX = anc;
		this.tamY = alt;
		this.marcador = marcador;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	
	public Rectangulo( Rectangulo rectangulo ) {
		this( rectangulo.tamX, rectangulo.tamY, rectangulo.x, rectangulo.y, rectangulo.color, rectangulo.marcador );
		vX = rectangulo.vX;
		vY = rectangulo.vY;
	}
	
	
	public double getAnchura() {
		return tamX;
	}

	
	public void setAnchura(double anc) {
		this.tamX = anc;
	}

	
	public double getAltura() {
		return tamY;
	}
	

	public int getMarcador() {
		return marcador;
	}

	public void setMarcador(int marcador) {
		this.marcador = marcador;
	}

	
	public void setAltura(double alt) {
		this.tamY = alt;
	}

	
	@Override
	public void dibuja( VentanaPong v ) {
		v.dibujaRect( x-tamX/2, y-tamY/2, tamX, tamY, 2f, Color.black, color );
	}
		
	
	public boolean seSaleEnVerticalArriba( VentanaPong v ) {
		return y-tamY/2<=0;
	}
	public boolean seSaleEnVerticalAbajo(VentanaPong v) {
		 return y+tamY/2>=v.getAltura();
	}

	
	@Override
	public boolean seSaleEnHorizontal( VentanaPong v ) {
		return x-tamX/2<=0 || x+tamX/2>=v.getAnchura();
	}

	@Override
	public String toString() {
		return x + "," + y + " (" + tamX + "," + tamY + ")";
	}
	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rectangulo) {
			Rectangulo p2 = (Rectangulo) obj;  // Cast de obj a rectángulo2 (lo entenderemos mejor al ver herencia)
			return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de los rectángulos this y p2
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		Rectangulo r1 = new Rectangulo( 0, 0,  0,  0 , Color.black, 0);
		if (r1.equals("hola")) {
			System.out.println( "Eres un string" );
		}
	}

	@Override
	public boolean seSaleEnVertical(VentanaPong v) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
