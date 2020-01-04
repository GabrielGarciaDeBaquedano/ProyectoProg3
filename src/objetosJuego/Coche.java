package objetosJuego;

import java.awt.Point;

import ventanas.FlappyCar;
import ventanas.VentanaGraficaFlappyCar;
import img.*;
 public class Coche extends ObjetoFlappyCar{
	protected ObjetoGraficoRotable og;
	protected double velHaciaArriba = 0D;
	protected boolean estoyMuerto = false;
	public Coche(int posX, int posY, VentanaGraficaFlappyCar ventana) {
		super(posX, posY, FlappyCar.pxAnchoCoche*2, FlappyCar.pxAltoCoche*2, ventana);		
		og = new ObjetoGraficoRotable( Img.getURLRecurso( "CocheVista1.gif" ), true, FlappyCar.pxAnchoCoche*2, FlappyCar.pxAltoCoche*2, 0 );
		og.setName( "Car" );
		og.setRectanguloDeChoque( FlappyCar.pxAnchoCoche/2, FlappyCar.pxAltoCoche/2, og.getAnchuraObjeto()-FlappyCar.pxAnchoCoche/2, og.getAlturaObjeto()-FlappyCar.pxAltoCoche/2 );
		ventana.addObjeto( og, new Point( posX, posY ) );
	}
	public ObjetoGraficoRotable getObjetoGrafico() {
		return og;
	}
	public boolean estoyMuerto(){
		return this.estoyMuerto;
	}	
	public void quitar() {
		muero();
		ventana.removeObjeto(og);
	
	}	
	public void muero() {
		estoyMuerto = true;
	}
	public void mover() {
		if (!estoyMuerto) {
			long tiempoCambio = System.currentTimeMillis() - tiempoMovimiento;
			tiempoMovimiento = System.currentTimeMillis();
			velHaciaArriba = velHaciaArriba + FlappyCar.Aceleracion * tiempoCambio / 1000;  
			posY = posY - tiempoCambio * velHaciaArriba / 1000;
			int posYNueva = (int) Math.round( posY );
			if (og.getY() != posYNueva) {
				
				if (posYNueva > FlappyCar.pxAlturaVent){ 
					muero();
					quitar();	
					
				}else if(posYNueva < -50){  
					muero();
					quitar();
					
				}else {  
					og.setLocation( og.getX(), posYNueva );
				}
			}
		}
	}
	
	public void saltar() {
		velHaciaArriba = FlappyCar.VelSalto;
	}	
}
