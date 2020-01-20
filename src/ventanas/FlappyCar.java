package ventanas;

import java.util.*;

import img.Img;
import objetosJuego.Coche;
import objetosJuego.ColumnaCars;
import objetosJuego.GrupoObjetosFlappyCar;
import objetosJuego.ObjetoGrafico;
import utils.Constantes;
import utils.EventoRaton;
import utils.EventoVentana;
import utils.FileManager;
import utils.FlappyRecords;
import utils.RatonPulsado;
import utils.Record;
import utils.Score;

import javax.swing.JOptionPane;

public class FlappyCar implements Runnable {
	public static final int pxAlturaVent = 700;
	public static final int pxAnchuraVent = 1280;
	public static final int pxAltoCoche = 45;
	public static final int pxAnchoCoche = 60;
	public static final int pxDistCuadrados = 45;
	public static final int coordXCoche = 50; 
	public static final double Aceleracion = -650.0; 
	public static final double VelSalto = 300;  
	public static final long TIEMPO_AVANCE_NIVEL = 10000;  
	public static final double INI_VELOCIDAD_POR_NIVEL = 200;  
	public static final long INI_TIEMPO_COLS_POR_NIVEL = 2000;  
	public static final int INI_HUECO_ENTRE_COLUMNAS = 200;  
	public static final double INC_VELOCIDAD_POR_NIVEL = 6;  
	public static final long  INC_TIEMPO_COLS_POR_NIVEL = -33; 
	public static final int INC_HUECO_ENTRE_COLUMNAS = -4;  
	public static final double MAX_VELOCIDAD_POR_NIVEL = 360;  
	public static final long MIN_TIEMPO_COLS_POR_NIVEL = 1000;  
	public static final int MIN_HUECO_ENTRE_COLUMNAS = 140;  
	private static double velAvance = INI_VELOCIDAD_POR_NIVEL;
	private static long tiempoEntreColumnas = INI_TIEMPO_COLS_POR_NIVEL;
	private static int huecoEntreColumnas = INI_HUECO_ENTRE_COLUMNAS;
	public static VentanaGraficaFlappyCar v;
	public static int getHuecoEntreColumnas() {
		return huecoEntreColumnas;
	}
	public static void setHuecoEntreColumnas(int huecoEntreColumnas) {
		FlappyCar.huecoEntreColumnas = huecoEntreColumnas;
	}
	public static double getVelAvance() {
		return velAvance;
	}
	public static void setVelAvance( double nuevaVelAvance ) {
		velAvance = nuevaVelAvance;
	}
	public static long getTiempoEntreCols() {
		return tiempoEntreColumnas;
	}
	public static void setTiempoEntreCols( long nuevoTiempo ) {
		tiempoEntreColumnas = nuevoTiempo;
	}
			private static void controlDeJugador( EventoVentana ev, Coche po ) {
				if (ev != null) {
					if (ev instanceof RatonPulsado ) {
						// Si se pulsa
						po.saltar();
					}
				}
			}
			private static boolean moverYQuitar( VentanaGraficaFlappyCar v, Coche po, GrupoObjetosFlappyCar columnas ) {
				po.mover();
				for (int i=0; i<columnas.tamanyo(); i++) {
					columnas.coger(i).mover();
				}
				if (!columnas.esVacio() && 
						((ColumnaCars)columnas.coger(0)).columnaFuera()) {   
					columnas.quitar(0).quitar();
					columnas.quitar(0).quitar();
					return true;
				}
				return false;
			}			
			private static boolean comprobarChoque( Coche po, GrupoObjetosFlappyCar columnas ) {
				for (int i=0; i<columnas.tamanyo(); i++) {
					ColumnaCars c = (ColumnaCars) columnas.coger(i);
					for (int j=0; j<c.getObjetosGraficos().tamanyo(); j++) {
						ObjetoGrafico o = (ObjetoGrafico) c.getObjetosGraficos().coger(j);
						if (po.getObjetoGrafico().chocaCon(o, -10)) { 
							return true;
						}
					}
				}
				return false;
			}
			private static void crearNuevasColumnas( VentanaGraficaFlappyCar v, int huecoEn, int numColumna, GrupoObjetosFlappyCar columnas ) {
				ColumnaCars cArriba = new ColumnaCars( numColumna, true, huecoEn, v.getAnchoPanelGrafico(), v );
				ColumnaCars cAbajo = new ColumnaCars( numColumna, false, huecoEn + getHuecoEntreColumnas(), v.getAnchoPanelGrafico(), v );
				columnas.anyadir( cArriba );
				columnas.anyadir( cAbajo );
			}	
			private static void incrementaNivel() {
				velAvance += INC_VELOCIDAD_POR_NIVEL;
				if (velAvance > MAX_VELOCIDAD_POR_NIVEL) velAvance = MAX_VELOCIDAD_POR_NIVEL;
				tiempoEntreColumnas += INC_TIEMPO_COLS_POR_NIVEL;
				if (tiempoEntreColumnas < MIN_TIEMPO_COLS_POR_NIVEL) tiempoEntreColumnas = MIN_TIEMPO_COLS_POR_NIVEL;
				huecoEntreColumnas += INC_HUECO_ENTRE_COLUMNAS;
				if (huecoEntreColumnas < MIN_HUECO_ENTRE_COLUMNAS) huecoEntreColumnas = MIN_HUECO_ENTRE_COLUMNAS;
			}
			private static void resetNivel(){
				velAvance=INI_VELOCIDAD_POR_NIVEL;
				tiempoEntreColumnas=INI_TIEMPO_COLS_POR_NIVEL;
				huecoEntreColumnas=INI_HUECO_ENTRE_COLUMNAS;
			}
			private static void previoAlJuego(){
				 class VentanaRunnable implements Runnable{
						
						
						public void run(){
							
							VentanaInicioFlappyCar home = new VentanaInicioFlappyCar();
							home.setVisible(true);
							
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							home.dispose();
						}
					}
				 	VentanaRunnable tarea = new VentanaRunnable();  
				 	Thread hilo = new Thread(tarea);
					hilo.start();
			}
			
			private static void menuJuego(){
				MenuWindowFlappyCar menu = new MenuWindowFlappyCar();
				menu.setVisible(true);
				menu.setLocationRelativeTo(null);
			}
			public void run() {	
				long comienzo = System.currentTimeMillis();
				v = new VentanaGraficaFlappyCar(pxAnchuraVent, pxAlturaVent, 0, true, true, false, "FlappyCar" );v.setVisible(true);
				v.setFondoAnimado(new ObjetoGrafico( Img.getURLRecurso( "Fondo8.jpg" ), true ),
				new ObjetoGrafico( Img.getURLRecurso( "Fondo8.jpg" ), true ), 1 );
				int posY = (v.getAltoPanelGrafico() - pxAltoCoche) / 2;   
				Random r = new Random();
				EventoVentana ev = null;
				v.showMessage( "EL JUEGO VA A COMENZAR  (3) " );
				v.readEvento( 1500 );
				v.showMessage( "PREPARADOS... (2)  " );
				v.readEvento( 1500 );
				v.showMessage( "LISTOS...(1)  " );
				v.readEvento( 1500 );
				int nivel = 1;
				do {
					v.showMessage( "YA!!!!" );
					ev = null;
					v.clearObjetos();
					Coche po = new Coche( coordXCoche, posY, v );
					GrupoObjetosFlappyCar columnas = new GrupoObjetosFlappyCar();
					long tiempoJuego = System.currentTimeMillis();
					long tiempoUltimaColumna = tiempoJuego - tiempoEntreColumnas;
					int numColumna = 0;
					int columnasPasadas = 0;
					long tiempoUltimoAvanceNivel = tiempoJuego;
					while (!v.isClosed() && !po.estoyMuerto()) {
						v.rodarFondoAnimado( true );
						long tiempoActual = System.currentTimeMillis();
						controlDeJugador( ev, po );
						if (moverYQuitar( v, po, columnas )) {
							columnasPasadas++;
							v.showMessage( columnasPasadas + " (Nivel " + nivel + ")" );
						}
						if (comprobarChoque( po, columnas )){
							po.muero();
							System.out.println("Columnas pasadas"+ columnasPasadas);
							System.out.println("StartWindow.score: "+ VentanaInicioFlappyCar.score);
							if(columnasPasadas>VentanaInicioFlappyCar.score.getPuntuacionMaxima())
								JOptionPane.showMessageDialog(v, "Acabas de batir el RECORD!!");
								VentanaInicioFlappyCar.score.setPuntuacionMaxima(columnasPasadas);
								int s= VentanaInicioFlappyCar.score.getPuntuacionMaxima();
								Score a = new Score(s);
								FileManager.saveScoreToFile(a);
						}if (tiempoActual - tiempoUltimaColumna > getTiempoEntreCols()) {  
							
							int huecoEn = r.nextInt( v.getAltoPanelGrafico() - getHuecoEntreColumnas() - 20 ) + 10;
							numColumna++;
							tiempoUltimaColumna = tiempoActual;
							crearNuevasColumnas( v, huecoEn, numColumna, columnas );
						}
						if (tiempoActual - tiempoUltimoAvanceNivel > TIEMPO_AVANCE_NIVEL) {
							tiempoUltimoAvanceNivel = tiempoActual;
							incrementaNivel();
							nivel++;
							v.showMessage( "Siguiente Nivel !!! Nivel " + nivel );
						}
						
						if (po.estoyMuerto()==true){ 
							//Partida partida = new Partida(1 , MenuLogin.get(), "FlappyCar", columnasPasadas, System.currentTimeMillis() - comienzo, System.currentTimeMillis());
							//BD.insertarPartida(partida);
							//String nombreUsuario = JOptionPane.showInputDialog("Introduce tu nombre: ");
							//Record nuevo = new Record(nombreUsuario, columnasPasadas);
							ArrayList<Record> aRecords = FlappyRecords.volcarFicheroAArrayList(Constantes.NF_RECORDS);
							//aRecords.add(nuevo);
							FlappyRecords.volcarArrayListAFichero(Constantes.NF_RECORDS, aRecords);
							resetNivel();
							nivel=1;
						}
						ev = v.readEvento( 5 );  
					}
					v.rodarFondoAnimado( false );
					int gradosRot = 0;
					while (gradosRot < 360) {
						gradosRot += 10;
						po.getObjetoGrafico().setRotacionGrados( gradosRot );
						v.esperaUnRato( 20 );
					}
					v.showMessage( "HAS PERDIDO !!!!  PUNTUACION = " + columnasPasadas );
					v.esperaUnRato( 2000 );
					v.borraEventos();
					v.showMessage( "Haz click para seguir jugando!!" );
					ev = null; 
					ev = v.readEvento( 60000 );
				} while (ev != null && (ev instanceof EventoRaton));
					v.finish();
			}
			public static void main(String[] args){		
                                                                   
				
				previoAlJuego();
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
				menuJuego();	
				
				
			}
}

