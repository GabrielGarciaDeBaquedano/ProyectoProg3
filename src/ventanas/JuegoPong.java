package ventanas;

import java.awt.Color;

import java.awt.Font;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import com.sun.glass.events.KeyEvent;

import objetosJuego.Circulo;
import objetosJuego.Figura;
import objetosJuego.Rectangulo;
import utils.Audio;
import utils.FisicaPong;
import utils.FisicaPong.Polar;


public class JuegoPong {

	/* -------------------------
	  		  VARIABLES
	   ------------------------- */
	private static final long MSGS_POR_FRAME = 20; 
	private static double VEL_JUEGO = 1.4;          
	private static final double RADIO_BOLA = 20;   
	private static final double RADIO_POWERUP = 25;
	private static final double VEL_MIN = 300;     
	private static final double VEL_RANGO = 200;   
	private static final double VEL_PALA = 500;    
	private static boolean ESTADO_JUEGO = true;
	protected static long tiempoFin;
	protected static long tiempoInicio;
	
	
	private static final boolean DEBUG_CHOQUES = false;  
	
	
	public static void main(String[] args) {
		JuegoPong juego = new JuegoPong();
		juego.jugar();
	}
	
	// -- Declaracion de herramientas que se van a usar --
	
	private VentanaPong vent;       
	private Circulo bola;
	private VentanaPong v;
	private MainPong v1;
	private Random random;
	private ArrayList<Circulo> bolas;
	public static ArrayList<Integer> puntuacion;
	public static Rectangulo pala1;          
	public static Rectangulo pala2;          
	private ArrayList<Figura> elementos;
	private Font letra = new Font("Arial", 100, 30);
	
	
	/**
	 * Inicia el juego.
	 */
	public void jugar() {
		random = new Random();
		
		crearJuego();
		mover();
	}	
	
	/**
	 * Crea todos los elementos del juego (pelota, palas)
	 * Los a�ade a el ArrayList elementos para luego pintarlos.
	 */
	public void crearJuego() {
		
		tiempoInicio = System.currentTimeMillis();
		vent = new VentanaPong( 600, 450, "Juego de pong" );
		bolas = new ArrayList<Circulo>();
		elementos = new ArrayList<Figura>();
		
		bola = new Circulo( RADIO_BOLA, 500, 400, Color.black );
		bola.setVX( (random.nextDouble()-0.5) * VEL_RANGO );
		bola.setVX( bola.getVX()<0 ? bola.getVX()-VEL_MIN : bola.getVX()+VEL_MIN ); 
		bola.setVY( (random.nextDouble()-0.5) * VEL_RANGO );
		bola.setVY( bola.getVY()<0 ? bola.getVY()-VEL_MIN : bola.getVY()+VEL_MIN ); 
		bolas.add( bola );
		elementos.add( bola );	
		
		pala1 = new Rectangulo( 20, 100, 60, vent.getAltura()/2, Color.green, 0);
		pala2 = new Rectangulo( 20, 100, vent.getAnchura()-60, vent.getAltura()/2, Color.blue, 0);
		elementos.add( pala1 );
		elementos.add( pala2 );
	
	}
	
	/**
	 * Movimiento de las palas 
	 * Las reajusta si se van a salir
	 */
	public void muevePalas() {
		
		pala1.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
		pala2.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
		
		if (pala1.seSaleEnVerticalAbajo(vent)) {
			pala1.setY(vent.getAltura() - pala1.getAltura() / 2 - 10);
		}
		
		if (pala1.seSaleEnVerticalArriba(vent)) {
			pala1.setY(pala1.getAltura() / 2 + 10);
		}
		
		if (pala2.seSaleEnVerticalAbajo(vent)) {
			pala2.setY(vent.getAltura() - pala2.getAltura() / 2 - 10);
		}
		
		if (pala2.seSaleEnVerticalArriba(vent)) {
			pala2.setY(pala2.getAltura() / 2 + 10);
		}
	}
	
	
	// Bucle de movimiento
	private void mover() {
		Random random = new Random();
		vent.setDibujadoInmediato( false ); 
		boolean hayGol = false;
		while (!vent.estaCerrada()) {
		
			gestionTeclado(vent);
			
			if (DEBUG_CHOQUES) { 
				vent.borra(); 
				pala1.dibuja( vent );
				pala2.dibuja( vent );
			} 
			
			if (ESTADO_JUEGO) {
				muevePalas();
			}
			
			else {}
			
			for (Circulo bola : bolas) {
				
				if (DEBUG_CHOQUES) { 
					vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.orange, 20 );
				}
				
				if (ESTADO_JUEGO) {
					bola.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
				}
				
				else {}
				// Control de salida de pantalla
				if (bola.seSaleEnHorizontal( vent )) {
					
					if (bola.salidaHorizontal(vent) == 1) {
						pala2.setMarcador(pala2.getMarcador() + 1);
						
					}
					else if (bola.salidaHorizontal(vent) == -1) {
						pala1.setMarcador(pala1.getMarcador() + 1);
						
					}
					
					hayGol = true;
					bola.setX(vent.getAnchura() / 2);
					bola.setY(vent.getAltura() / 2);
					bola.setVY( (random.nextDouble()-0.5) * VEL_RANGO );
					bola.setVY( bola.getVY()<0 ? bola.getVY()-VEL_MIN : bola.getVY()+VEL_MIN );
					bola.setVX( (random.nextDouble()-0.5) * VEL_RANGO );
					bola.setVX( bola.getVX()<0 ? bola.getVX()-VEL_MIN : bola.getVX()+VEL_MIN );
					pala1.setY(vent.getAltura() / 2);
					pala2.setY(vent.getAltura() / 2);
					
					if (DEBUG_CHOQUES) {vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.green, 20 ); }  	
				}
				if (bola.seSaleEnVertical( vent )) {
					bola.setVY( -bola.getVY() );
					if (DEBUG_CHOQUES) {vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.gray, 20 ); } 
				}
				// Choque bola y palas
				boolean hayChoque = false;
				if (choqueLateralPala(bola, pala1) || choqueLateralPala(bola, pala2)) {
					hayChoque = true;
					bola.setVX( -bola.getVX() );
					if (DEBUG_CHOQUES) {vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.green, 20 ); }  // En modo depuración, dibuja el cambio de velocidad tras choque
				} else if (choqueVerticalPala(bola, pala1) || choqueVerticalPala(bola, pala2)) {
					hayChoque = true;
					bola.setVY( -bola.getVY() );
					if (DEBUG_CHOQUES) {vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.gray, 20 ); }  // En modo depuración, dibuja el cambio de velocidad tras choque
				} else if (choqueExtremoPala(bola, pala1) || choqueExtremoPala(bola, pala2)) {  
					hayChoque = true;
					if (choqueExtremoPala(bola, pala1)) calculaReboteEsquina( bola, pala1 );
					else calculaReboteEsquina( bola, pala2 );
					if (DEBUG_CHOQUES) {vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.cyan, 20 ); }  // En modo depuración, dibuja el cambio de velocidad tras choque
				}
				if (hayChoque) { 
					reajustaBola( bola ); 
					Audio.lanzaAudioEnHilo("src/wav/choque.wav");
					
				}
			}
			
			// Dibujado
		
			vent.borra();
			vent.dibujaTexto(vent.getAnchura() / 2 - 45, 45, Integer.toString(pala1.getMarcador()), letra, Color.black);
			vent.dibujaTexto(vent.getAnchura() / 2 + 25, 45, Integer.toString(pala2.getMarcador()), letra, Color.black);
			dibujaBordes();
			for (Figura figura : elementos) {
				figura.dibuja( vent );
				if (figura instanceof Rectangulo) {
					
				}
				if (figura instanceof Circulo) {
					Circulo bola = (Circulo) figura;
					if (DEBUG_CHOQUES) { vent.dibujaFlecha( figura.getX(), figura.getY(), figura.getX()+figura.getVX()/4, figura.getY()+figura.getVY()/4, 1.0f, Color.magenta, 20 ); }  // En modo depuración, dibuja vector de velocidad de la bola
					
				}
			}
			
			if (ESTADO_JUEGO) {
				vent.repaint();
				vent.espera( MSGS_POR_FRAME );
				
				if (hayGol) {
					
					hayGol = false;
					Audio.lanzaAudioEnHilo("src/wav/gol.wav");
					vent.repaint();
					vent.espera(MSGS_POR_FRAME * 40);
					
				}
			}
			
			else {
				
				vent.dibujaTexto(vent.getAnchura() / 2 - 120, vent.getAltura() / 2 - 150, "JUEGO    EN PAUSA", letra, Color.black);
				vent.repaint();
				vent.espera( MSGS_POR_FRAME);
			}
			
			// Dibujado de Game Over cuando se acaba el juego
			if (vent.isTeclaPulsada(KeyEvent.VK_ESCAPE)) {
				
				tiempoFin = System.currentTimeMillis();
				long tiempoJuego =  tiempoFin-tiempoInicio;
				Partida partida = new Partida();
				partida.setPuntuacion(pala1.getMarcador()-pala2.getMarcador());
				partida.setTiempoPartida(tiempoJuego);
				partida.setFechaPartida(System.currentTimeMillis());
				partida.setIdJuego(2);
				partida.setIdJugador(MenuLogin.getIdUsuarioEnUso());
				BD.insertarPartida(partida);
				System.out.println("Partida insertada");
				vent.borra();
				vent.dibujaRect(0, 0, vent.getAnchura(), vent.getAltura(), 40, Color.BLACK, Color.WHITE);
				vent.dibujaImagen("src/img/gameover.png", vent.getAnchura() / 2, vent.getAltura() / 2, 1, 0, 1);
				vent.repaint();
				vent.espera(MSGS_POR_FRAME * 40);
				vent.acaba();
				
				
			}
		}
		
		// Inicia el hilo del partido
		Thread r = new Thread() {
			@Override
			public void run() {
				PartidosPong.main(null);
			}
		}; r.start();
	}
	
	/**
	 * @param vent
	 * Gestion del teclado de las palas, de la velocidad del juego y del Start/Stop
	 */
	private void gestionTeclado(VentanaPong vent) {
		
		if (vent.isTeclaPulsada(KeyEvent.VK_Q)) {
			ESTADO_JUEGO = false;
			vent.setMensaje("Pausa");
		}
		
		if (vent.isTeclaPulsada(KeyEvent.VK_E)) {
			ESTADO_JUEGO = true;
			vent.setMensaje("En marcha");
		}
		
		if (vent.isTeclaPulsada( KeyEvent.VK_PLUS )) {
			VEL_JUEGO *= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + VEL_JUEGO );
		}
		if (vent.isTeclaPulsada( KeyEvent.VK_MINUS )) {
			VEL_JUEGO /= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + VEL_JUEGO );
		}
		
		if (vent.isTeclaPulsada( KeyEvent.VK_UP )) {
			pala2.setVY( -VEL_PALA );
		} else if (vent.isTeclaPulsada( KeyEvent.VK_DOWN )) {
			pala2.setVY( VEL_PALA );
		} else {
			pala2.setVY( 0 );
		}
		
		if (vent.isTeclaPulsada( KeyEvent.VK_W )) {
			pala1.setVY( -VEL_PALA );
		} else if (vent.isTeclaPulsada( KeyEvent.VK_S )) {
			pala1.setVY( VEL_PALA );
		} else {
			pala1.setVY( 0 );
		}
	}
	
	/**
	 * Dibuja los bordes del campo de futbol en la ventana
	 */
	private void dibujaBordes() {
		
		vent.dibujaRect( 0, 0, vent.getAnchura(), vent.getAltura(), 5, Color.black );
		vent.dibujaLinea( vent.getAnchura()/2, 0, vent.getAnchura()/2, vent.getAltura(), 5 / 2, Color.black );
		vent.dibujaCirculo(vent.getAnchura() / 2, vent.getAltura() / 2, 70, 5/2, Color.black);
		vent.dibujaCirculo(0, vent.getAltura() / 2, 120, 5/2, Color.black);
		vent.dibujaCirculo(vent.getAnchura(), vent.getAltura() / 2, 120, 5/2, Color.black);
	}
	
	
	/**
	 * @param bola
	 * @param pala
	 * @return true si la pelota se choca con la parte horizontal de la pala
	 */
	private boolean choqueLateralPala( Circulo bola, Rectangulo pala ) {
		if (bola.getY() >= pala.getY()-pala.getAltura()/2 &&
			bola.getY() <= pala.getY()+pala.getAltura()/2) {  
			if (bola.getX()-bola.getRadio()<=pala.getX()+pala.getAnchura()/2 &&
				bola.getX()+bola.getRadio()>=pala.getX()-pala.getAnchura()/2 ) {
				return true;
			}
		}
		return false;
	}
	/**
	 * @param bola
	 * @param pala
	 * @return true si la pelota choca con la parte vertical de la pala
	 */
	private boolean choqueVerticalPala( Circulo bola, Rectangulo pala ) {
		if (bola.getX() >= pala.getX()-pala.getAnchura()/2 &&
			bola.getX() <= pala.getX()+pala.getAnchura()/2) {  
			if (bola.getY()-bola.getRadio()<=pala.getY()+pala.getAltura()/2 &&
				bola.getY()+bola.getRadio()>=pala.getY()-pala.getAltura()/2 ) {
				return true;
			}
		}
		return false;
	}
	private boolean choqueSuperiorPala( Circulo bola, Rectangulo pala ) {
		return choqueVerticalPala(bola, pala) && bola.getY() < pala.getY();
	}
	private boolean choqueInferiorPala( Circulo bola, Rectangulo pala ) {
		return choqueVerticalPala(bola, pala) && bola.getY() > pala.getY();
	}
	
	
	/**
	 * @param bola
	 * @param pala
	 * @return true si la pelota choca con el extremo de la pala
	 */
	private boolean choqueExtremoPala(Circulo bola, Rectangulo pala) {
		double dist1 = FisicaPong.distancia( bola.getX(), bola.getY(), pala.getX()+pala.getAnchura()/2, pala.getY()+pala.getAltura()/2 );
		double dist2 = FisicaPong.distancia( bola.getX(), bola.getY(), pala.getX()+pala.getAnchura()/2, pala.getY()-pala.getAltura()/2 );
		double dist3 = FisicaPong.distancia( bola.getX(), bola.getY(), pala.getX()-pala.getAnchura()/2, pala.getY()+pala.getAltura()/2 );
		double dist4 = FisicaPong.distancia( bola.getX(), bola.getY(), pala.getX()-pala.getAnchura()/2, pala.getY()-pala.getAltura()/2 );
		if (dist1<=bola.getRadio() || dist2<=bola.getRadio() || dist3<=bola.getRadio() || dist4<=bola.getRadio()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param bola
	 * @param pala
	 * Calcula el rebote en la esquina
	 */
	private void calculaReboteEsquina( Circulo bola, Rectangulo pala ) {
		for (double anch : new double[] {-1.0, 1.0}) {
			for (double alt : new double[] {-1.0, 1.0}) {  
				Point2D esquina = new Point2D.Double( pala.getX()+anch*pala.getAnchura()/2, pala.getY()+alt*pala.getAltura()/2 );
				double dist = FisicaPong.distancia( bola.getX(), bola.getY(), esquina.getX(), esquina.getY() );
				if (dist<=bola.getRadio()) {  
					Point2D vectorImpacto = new Point2D.Double( esquina.getX()-bola.getX(), esquina.getY()-bola.getY() );
					Polar vectorImpactoP = new Polar( vectorImpacto );
					double anguloDesv = vectorImpactoP.getArgumento();
					Point2D vectorVel = new Point2D.Double( bola.getVX(), bola.getVY() );
					Polar vectorVelP = new Polar( vectorVel );
					vectorVelP.rotar( -anguloDesv );
					Point2D velRotada = vectorVelP.toPoint();
					velRotada.setLocation( -velRotada.getX(), velRotada.getY() );  
					Polar velRotadaP = new Polar( velRotada );
					velRotadaP.rotar( anguloDesv ); 
					Point2D velTrasRebote = velRotadaP.toPoint();
					bola.setVX( velTrasRebote.getX() );
					bola.setVY( velTrasRebote.getY() );
					return;
				}
			}
		}
	}

	
	/**
	 * @param bola
	 * Reajusta la bola con cada choque (con la pala o con los bordes de la ventana)
	 */
	private void reajustaBola(Circulo bola) {
		if (DEBUG_CHOQUES) { 
			vent.dibujaCirculo( bola.getX(), bola.getY(), 4, 1.5f, Color.orange ); 
			vent.dibujaCirculo( bola.getX(), bola.getY(), 25, 1.5f, Color.orange ); 
		}
		if (bola.seSaleEnHorizontal( vent )) {
			bola.setVX( Math.abs(bola.getVX()) * bola.salidaHorizontal( vent ) );  
		if (choqueVerticalPala(bola, pala1)) {  
			
		}
			if ((choqueSuperiorPala(bola, pala1) && pala1.getVY()<0)       
			    || (choqueInferiorPala(bola, pala1) && pala1.getVY()>0)) { 
				bola.setVY( bola.getVY() + pala1.getVY() );  
			}
			
			if (bola.getY() < pala1.getY()) bola.setY(pala1.getY()-pala1.getAltura()/2-bola.getRadio()-0.01);
			else bola.setY(pala1.getY()+pala1.getAltura()/2+bola.getRadio()+0.01);
		}
		if (choqueVerticalPala(bola, pala2)) {  
			
			if ((choqueSuperiorPala(bola, pala2) && pala2.getVY()<0)       
				|| (choqueInferiorPala(bola, pala2) && pala2.getVY()>0)) { 
				bola.setVY( bola.getVY() + pala2.getVY() );
			}
			
			if (bola.getY() < pala2.getY()) bola.setY(pala2.getY()-pala2.getAltura()/2-bola.getRadio()-0.01);
			else bola.setY(pala2.getY()+pala2.getAltura()/2+bola.getRadio()+0.01);
		}
		if (bola.seSaleEnVertical( vent )) {
			bola.setVY( Math.abs(bola.getVY()) * bola.salidaVertical( vent ) );  
		}
		if (choqueLateralPala(bola, pala1)) { 
			if (bola.getVX() < 0) bola.setX(pala1.getX()-pala1.getAnchura()/2-bola.getRadio()-0.01);
			else bola.setX(pala1.getX()+pala1.getAnchura()/2+bola.getRadio()+0.01);
		}
		if (choqueLateralPala(bola, pala2)) { 
			if (bola.getVX() < 0) bola.setX(pala2.getX()-pala2.getAnchura()/2-bola.getRadio()-0.01);
			else bola.setX(pala2.getX()+pala2.getAnchura()/2+bola.getRadio()+0.01);
		}

		while (choqueExtremoPala(bola, pala1) || choqueExtremoPala(bola, pala2)) {
			bola.setX( bola.getX()+bola.getVX()*0.001 );
			bola.setY( bola.getY()+bola.getVY()*0.001 );
		}
		if (DEBUG_CHOQUES) {  
			bola.dibuja( vent );
			vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.blue, 20 );
			vent.repaint();
			while (!vent.estaCerrada() && vent.getRatonPulsado()==null) ; 
			while (!vent.estaCerrada() && vent.getRatonPulsado()!=null) ;		
		}
	}

}
