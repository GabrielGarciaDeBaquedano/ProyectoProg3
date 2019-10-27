package objetosJuego;

import java.awt.Point;
import utils.FlappyCar;
import utils.VentanaGrafica;
import objetosJuego.ObjetoGrafico;
import ventanas.VentanaGraficaFlappyCar;
import img.Img;
public class ColumnaCars extends ObjetoFlappyCar {		
	protected GrupoObjetos listaOG = new GrupoObjetos();
	protected boolean fuera = false;
 public ColumnaCars( int numCol, boolean arriba, int pixelHueco, int posX, VentanaGraficaFlappyCar ventana ) {
			super( posX, 0, FlappyCar.pxAnchoCoche, 0, ventana );
			if (arriba) {
				posY = 0;
				alto = pixelHueco;
				int posYColumnas = pixelHueco - FlappyCar.pxAltoCoche;
				do {
					ObjetoGrafico og = new ObjetoGrafico( Img.getURLRecurso( "columna3.png" ), true, FlappyCar.pxAnchoCoche, FlappyCar.pxAltoCoche);
					ventana.addObjeto( og, new Point( posX, posYColumnas ) );
					listaOG.anyadir( og );
					og.setName( "Arriba-" + numCol + "->" + posYColumnas );
					posYColumnas -= FlappyCar.pxDistCuadrados;
				} while (posYColumnas >= -FlappyCar.pxDistCuadrados);
			} else {
				posY = pixelHueco;
				alto = ventana.getAltoPanelGrafico() - pixelHueco;
				int posYEscudos = pixelHueco;
				do {
					ObjetoGrafico og = new ObjetoGrafico( Img.getURLRecurso( "columna3.png" ), 
							true, FlappyCar.pxAnchoCoche, FlappyCar.pxAltoCoche );
					ventana.addObjeto( og, new Point( posX, posYEscudos ) );
					listaOG.anyadir( og );
					og.setName( "Abajo-" + numCol + "->" + posYEscudos );
					posYEscudos += FlappyCar.pxDistCuadrados;
				} while (posYEscudos < ventana.getAltoPanelGrafico());
			}
		}
		public GrupoObjetos getObjetosGraficos() {
			return listaOG;
		}
	public boolean columnaFuera(){
		return fuera;
	}
	public void quitar() {
		for (int i=0; i<listaOG.tamanyo(); i++) {
			ObjetoGrafico og = (ObjetoGrafico) listaOG.coger(i);
			ventana.removeObjeto(og);
			fuera = true;
		}
	}
	public void mover() {
		long tiempoCambio = System.currentTimeMillis() - tiempoMovimiento;
		tiempoMovimiento = System.currentTimeMillis();
		posX = posX - tiempoCambio * FlappyCar.getVelAvance() / 1000D;
		int posXNueva = (int) Math.round( posX );
		if (((ObjetoGrafico)listaOG.coger(0)).getX() != posXNueva) {
			if (posXNueva < -FlappyCar.pxAnchoCoche){  
				quitar();
			} else {  
				for (int i=0; i<listaOG.tamanyo(); i++) {
					ObjetoGrafico og = (ObjetoGrafico) listaOG.coger(i);
					og.setLocation( posXNueva, og.getY() );
				}
			}
		}
	}
}
