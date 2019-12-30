package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import java.util.Date;

import org.omg.CORBA.Current;

import objetosJuego.Asteroide;
import objetosJuego.Disparo;
import objetosJuego.Nave;
//import sun.util.calendar.BaseCalendar.Date;
import utils.Audio;
import utils.Rotable;
import utils.VentanaGrafica;

/** Juego runner ejercicio mejorado
 * Con las colisiones codificadas como interfaz
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class JuegoAsteroids {
	
	static PrintStream estats;

	private static final long MSGS_POR_FRAME = 20;    // 20 msgs por frame = 50 frames por segundo aprox
	private static final int RADIO_AST = 40;           
	private static final long MILIS_NIVEL = 15000;    // Milisegundos de paso de nivel
	private static final double PRB_AST_NIVEL = 0.01; // Subida de probabilidad (suma) de aparición de asteroide por cada subida de nivel
	private static Font fuente = new Font("Arial Black",Font.BOLD,20);

	private static double PROB_NUEVO_AST = 0.02;      // Probabilidad de que aparezca un nuevo asteroide cada frame (2%)
	private static double VEL_JUEGO = 1.0;            // 1.0 = tiempo real. Cuando mayor, más rápido pasa el tiempo y viceversa 
	private static Random random;                     // Generador de aleatorios para creación de asteroides
	private static int puntuacion;                       // Variable para puntuación
	protected static int numDisp;				  // Disparos realizados
	protected static int numImp;					  // Disparos impactados
	protected static float porcentajeImp;			  // Porcentaje de disparos impactados
	protected static long tiempoFin;
	protected static long tiempoInicio;
	
	public static void main(String[] args) {
		JuegoAsteroids juego = new JuegoAsteroids();
		juego.jugar();
	}
	
	// =================================
	// ATRIBUTOS Y MÉTODOS DE INSTANCIA (no static)
	// =================================
	
	private VentanaGrafica vent;                  // Ventana del juego
	private ArrayList<Nave> naves;                // Naves del juego (solo las naves)
	private ArrayList<Asteroide> asteroides;      // Asteroides del juego (solo los asteroides)
	private ArrayList<Disparo> disparos;          // Disparos del juego (solo los disparos)
	private long tiempoNivel;                     // Tiempo inicial del nivel actual (milisegundos)
	Nave nave;
	
	public void jugar() {
		puntuacion = 0;
		random = new Random();
		vent = new VentanaGrafica( 1200, 600, "Asteroids" );
		tiempoInicio = System.currentTimeMillis();
		crearNaves();
		mover();
	}

	// Crea la nave de inicio
	private void crearNaves() {
		naves = new ArrayList<>();
		disparos = new ArrayList<>();
		asteroides = new ArrayList<>();
			Nave nave = new Nave( 600, 300,80.1,.35,.98, 10 );// Nueva nave  
			naves.add( nave );
		}
	
	// Bucle de movimiento (bucle principal de juego)
	private void mover() {
		long tiempoInicial = System.currentTimeMillis();
		tiempoNivel = System.currentTimeMillis();
		vent.setDibujadoInmediato( false );
		while (!vent.estaCerrada() && naves.size()>0) {
			gestionTecladoYMovtoNaves();       // Manejo de teclado
			creacionNuevosElementos();         // Creación procedural de elementos
			movimientoElementos();             // Movimiento y rotación de todos los elementos
			controlDeChoques();                // Control de choques de todas las naves con el resto de elementos
			dibujadoElementos(tiempoInicial);  // Dibujado del fotograma completo
			vent.espera( MSGS_POR_FRAME );     // Ciclo de espera en cada bucle
			subeNivel();                       // Subida de nivel (cuando proceda)
		}
	}

	// Toda la gestión de teclado y su lógica asociada en el juego - movimiento de la nave
	private void gestionTecladoYMovtoNaves() {
		//Derecha
		if (vent.isTeclaPulsada( KeyEvent.VK_RIGHT )) {
			for (Nave nave : naves) {
				if (nave instanceof Rotable) {  // El interfaz funciona como una clase abstracta
					((Rotable)nave).rota( 0.15 );
					}
			}
		 }
		//Izquierda
		if (vent.isTeclaPulsada( KeyEvent.VK_LEFT )) {
			for (Nave nave : naves) {
				if (nave instanceof Rotable) {  // El interfaz funciona como una clase abstracta
					((Rotable)nave).rota( -0.15 );
					}
			}
		}
			// Disparo
					if (vent.isTeclaPulsada( KeyEvent.VK_SPACE )){
						for (Nave nave : naves) {
								if(nave.puedeDisparar()){
									//System.out.println("Funcion");
									Audio.lanzaAudioEnHilo("src/wav/Disparo.wav");
									disparos.add(nave.dispara());
									System.out.println(disparos);
									numDisp++;
								}
						}		
					}
			// Avance
					if (vent.isTeclaPulsada( KeyEvent.VK_UP )) {
						for (Nave nave : naves) {
							nave.setAcelerando(true);
						}
						}
					else{
						for (Nave nave : naves) {							
							nave.setAcelerando(false);
						}
					}

	}

	// Creación de nuevos elementos (en función de los valores aleatorios)
	private void creacionNuevosElementos() {
		// Creación aleatoria de nuevo asteroide
		if (random.nextDouble()<PROB_NUEVO_AST) {
			creaNuevoAsteroide();
		}
	}
	
	// Movimiento y rotación del fotograma de todos los elementos
	private void movimientoElementos() {
		for (Nave nave : naves){
			nave.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
		}
		for (Disparo dis : disparos) {
			dis.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
		}
		// Rotación de los bonus y los asteroides
		for (Asteroide ast : asteroides) {
			ast.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
			ast.rota(0.05);
		}
	}
	
	// Control de choques de todas las naves con el resto de elementos
	private void controlDeChoques() {
		for (Nave nave : naves) {
			for(Asteroide ast : asteroides){
				if(nave.getEnvolvente().chocaCon( ast.getEnvolvente() )) {
							// Puntua cada asteroide que destrozamos
					Audio.lanzaAudioEnHilo("src/wav/derrota.wav");
							System.out.println("choque de nave y ast " + nave +" "+ ast);
							tiempoFin = System.currentTimeMillis();
							vent.espera(2000);
							Thread t = new Thread () {
								public void run() {
										MuerteAsteroids.main( null );
										
								}
							};
							t.start(); 
							vent.acaba();
							double tiempoJuego = (tiempoFin-tiempoInicio)/1000.00;
							System.out.println("Estadisticas:\n"+"Disparos realizados: "+numDisp+"\n"+"Disparos impactados: "+numImp+"\n"+"Porcentaje disparos impactados: "+(numImp/numDisp)*100+"% \n"+"Tiempo de juego: "+tiempoJuego+"s");
							try {
								estats = new PrintStream(new FileOutputStream("estadisticasAsteroids.txt", true));
							} catch (Exception e1) {}
							
							estats.println("ID partida: "+(new Date())+" Nick jugador: "+MenuLogin.getNick()+" Disparos realizados: "+numDisp+" Disparos impactados: "+numImp+" Porcentaje disparos impactados: "+(numImp/numDisp)*100+"% Tiempo de juego: "+tiempoJuego+"s");
							}
						}
					}
				for (Disparo dis : disparos){
					for (Asteroide ast : asteroides) {
						if (dis.getEnvolvente().chocaCon(ast.getEnvolvente() )){
							dis.setVivo(false);
							dis.setX(1900);
							System.out.println("choque de disparo y ast " + dis +" "+ ast);
							Audio.lanzaAudioEnHilo("src/"
									+ "wav/impacto.wav");
							numImp++;
							if(ast.getImp_rest()>1){
								if(ast.getImp_rest()>2){
									puntuacion ++;
								}
								else if(ast.getImp_rest()>1){
									puntuacion += 2;
								}
								Asteroide a = ast.creaAsteroideDividido(.5,5);
								Asteroide b = ast.creaAsteroideDividido(.5,5);
								asteroides.add(a);
								asteroides.add(b);
								asteroides.remove(ast);
							break;
							}
							asteroides.remove(ast);
							puntuacion += 3;
							break;
						}
						
					}
				}
				
			}
	
	// Dibujado de todos los elementos en el fotograma
	private void dibujadoElementos( long tiempoInicial ) {
		vent.borra();
		for (Nave nave: naves){
			nave.dibuja(vent);
		}
		for (Disparo disp : disparos){
			if(disp.getVivo()==true){
				disp.dibuja( vent );
			}
		}
		for (Asteroide ast: asteroides){
			ast.dibuja(vent);
		}
		
		vent.dibujaTexto(30.0, 30.0, "Puntos: "+puntuacion, fuente, Color.BLACK);
		
		vent.repaint();
		vent.setMensaje( "PUNTUACIÓN: " + puntuacion + ". Tiempo de juego: " + (System.currentTimeMillis() - tiempoInicial)/1000 + " segundos." );
	}
	
	// Crea un asteroide nuevo
	private void creaNuevoAsteroide() {
		Asteroide asteroide = new Asteroide( RADIO_AST, Math.random()*1200,Math.random()*600, 1.0, .5,
				 5,3,2);
		asteroides.add(asteroide);
	}
	
	// Sube el nivel cada N segundos (mayor dificultad)
	private void subeNivel() {
		if (System.currentTimeMillis() - tiempoNivel > MILIS_NIVEL) {
			PROB_NUEVO_AST += PRB_AST_NIVEL;     // Sube un x% la probabilidad de asteroide
			tiempoNivel = System.currentTimeMillis();  // Nueva cuenta de tiempo
			System.out.println( String.format( "Nuevo nivel. Asteroide %1$1.1f%%", PROB_NUEVO_AST*100.0) );
		}
	}
	
	public static int getPuntuacion(){
		return puntuacion;
	}

}
