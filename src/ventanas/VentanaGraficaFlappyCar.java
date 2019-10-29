package ventanas;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import objetosJuego.ObjetoGrafico;
import utils.EventoVentana;
import utils.RatonClick;
import utils.RatonDrag;
import utils.RatonPulsado;
import utils.RatonSoltado;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.*;
import java.io.File;
import java.io.IOException;



// Clase de utilidad para poder realizar juegos o animaciones
 
public class VentanaGraficaFlappyCar extends JFrame implements WindowListener {	
	private static final long serialVersionUID = 1L;
	private JLabel lMensaje = new JLabel( " " );
	private JLabel lMensajeSombra = new JLabel( " " );
	private JPanel pAreaControl = new JPanel();	
	private JLayeredPane layeredPane = new JLayeredPane();
	
	// lista de eventos pendientes de teclado/rat�n
	private ArrayList<EventoVentana> eventosVentana;
	
	 // posici�n actual del rat�n (null si est� fuera del panel gr�fico)
	private Point posicionRaton = null; 
	
	 // se generan o no los eventos de click y drag?
	private boolean generarClicksYDrags;   
	
	// Tiempo para un caso de animaci�n
	private long tiempoAnimMsg = 500L;
	
	//Msg entre cada paso de refresco de animaci�n
	private long tiempoFrameAnimMsg = tiempoAnimMsg/40L;
	
	//Hilo de la animaci�n
		private HiloAnimacion hilo = null; 
		private ArrayList<Animacion> animacionesPendientes = new ArrayList<Animacion>();

	//JLayeredPane.DEFAULT_LAYER;
	private static final Integer CAPA_FONDO = new Integer(-100); 
	private static final int PX_SOLAPE_FONDOS = 0;
	
			private synchronized void addEvento( EventoVentana ev ) {
				eventosVentana.add( ev );
			}
			private synchronized EventoVentana remEvento( int index ) {
				return eventosVentana.remove( index );
			}
			private synchronized boolean remEvento( EventoVentana ev ) {
				return eventosVentana.remove( ev );
			}
			private synchronized EventoVentana getEvento( int index ) {
				return eventosVentana.get ( index );
			}
	
	//Construye una ventana de juego de tablero,
	 
	public VentanaGraficaFlappyCar( int anchuraVent, int alturaVent, int anchuraPanelControl, boolean tamFijo, boolean cerrable, boolean genCyD, String titulo ) {
		setSize( anchuraVent, alturaVent );
		setTitle( titulo );
		setResizable( !tamFijo );
		this.addWindowListener(this);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaGraficaFlappyCar.class.getResource("logo.png")));
		pAreaControl.setMaximumSize( new Dimension(anchuraPanelControl, 5000));
		pAreaControl.setMinimumSize( new Dimension(anchuraPanelControl, 0));
	// amarillo 
		lMensaje.setBounds( 0, 0, anchuraVent, 35 );
	// azul 
		lMensajeSombra.setBounds( 2, 2, anchuraVent, 35 );
		generarClicksYDrags = genCyD;
		if (cerrable)
			setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
		else
			setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );			
		try {
			EventQueue.invokeAndWait( new Runnable()  {
				@Override
				public void run() {
					eventosVentana = new ArrayList<EventoVentana>();
					setLocationRelativeTo( null );
					setLayeredPane( layeredPane );
					pAreaControl.setOpaque( false );
					lMensaje.setOpaque( false );
					lMensajeSombra.setOpaque( false );
					lMensaje.setFont( new Font( "Arial", Font.BOLD, 30 ));
					lMensajeSombra.setFont( new Font( "Arial", Font.BOLD, 30 ));
					lMensaje.setForeground( new Color(255, 255, 0) );
					lMensajeSombra.setForeground( new Color(0, 0, 128) );
					lMensaje.setHorizontalAlignment( JLabel.CENTER );
					lMensajeSombra.setHorizontalAlignment( JLabel.CENTER );
					layeredPane.add( pAreaControl, JLayeredPane.PALETTE_LAYER );
					layeredPane.add( lMensaje, JLayeredPane.PALETTE_LAYER );
					layeredPane.add( lMensajeSombra, JLayeredPane.PALETTE_LAYER );
					// layout de posicionamiento absoluto
					pAreaControl.setLayout( null ); 
					layeredPane.setFocusable( true );
					layeredPane.requestFocus();
					setVisible( true );
		    		
					layeredPane.addFocusListener( new FocusAdapter() {
						@Override
						public void focusLost(FocusEvent arg0) {
							layeredPane.requestFocus();
						}
					});
					layeredPane.addMouseListener( new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent arg0) {
							boolean anyadirSuelta = true;
							if (generarClicksYDrags) {
								RatonPulsado rp = null;
								for (EventoVentana ev : eventosVentana) {
									// Se ha pulsado un rat�n y ahora se suelta: a ver qu� es
									if (ev instanceof RatonPulsado) {  
										rp = (RatonPulsado) ev;
										anyadirSuelta = false;
										// Igual coordenada: click
										if (rp.getPosicion().equals( arg0.getPoint() )) {  
											addEvento( new RatonClick(arg0) );
											// Dif coordenada: drag
										} else { 
											addEvento( new RatonDrag( rp.getTime(), rp.getPosicion(), arg0 ) );
										}
										break;
									}
								}
								// Quitar evento de pulsaci�n de la cola
								if (rp!=null) {  
									remEvento( rp );
								}
							}
							if (anyadirSuelta) {
								addEvento( new RatonSoltado(arg0) );
							}
							posicionRaton = arg0.getPoint();
						}
						
						public void mousePressed(MouseEvent arg0) {
							addEvento( new RatonPulsado(arg0) );
							posicionRaton = arg0.getPoint();
						}
						
						public void mouseEntered(MouseEvent e) {
							posicionRaton = e.getPoint();
						}
						
						public void mouseExited(MouseEvent e) {
							posicionRaton = null;
						}
					});
					layeredPane.addMouseMotionListener( new MouseMotionListener() {
						
						public void mouseMoved(MouseEvent e) {
							posicionRaton = e.getPoint();
						}
						
						public void mouseDragged(MouseEvent e) {
							posicionRaton = e.getPoint();
						}
					});				
				}
			} );
		} catch (Exception e) {
		}
	}
	
	// Pone el fondo de la ventana de juego con un objeto gr�fico,
	
	public void setFondo( ObjetoGrafico og ) {
		fondoAnimado = false;
		// Quitar posibles fondos anteriores
		for (Component c : layeredPane.getComponentsInLayer( CAPA_FONDO )) {
			layeredPane.remove( c );
		}
		layeredPane.add( og, CAPA_FONDO );
		layeredPane.repaint();
	}
	//Cierra y finaliza la ventana de juego pero no acaba la aplicaci�n
	
	public void finish() {
		if (hilo!=null) { hilo.interrupt(); }
		dispose();
	}
	//Pone el fondo de la ventana de juego con dos objetos gr�ficos,
	 
	public void setFondoAnimado( ObjetoGrafico og1, ObjetoGrafico og2, double pixDespAIzqda ) {
		// Quitar posibles fondos anteriores
		for (Component c : layeredPane.getComponentsInLayer( CAPA_FONDO )) {
			layeredPane.remove( c );
		}
		fondo1 = og1;
		fondo2 = og2;
		og1.setLocation( 0, 0 );
		og2.setLocation( og1.getWidth() - PX_SOLAPE_FONDOS, 0 );   // a la derecha de og1 [solapa un pixel]
		coorX1 = 0;
		coorX2 = og1.getWidth() - PX_SOLAPE_FONDOS;
		layeredPane.add( og1, CAPA_FONDO );
		layeredPane.add( og2, CAPA_FONDO );
		layeredPane.repaint();
		fondoAnimado = true;
		fondoRodando = false;
		this.pixDespAIzqda = pixDespAIzqda;
		if (hilo==null) { hilo = new HiloAnimacion(); hilo.start(); }
	}
		// Atributos de animaci�n de fondo:
		private boolean fondoAnimado = false;
		private boolean fondoRodando = true;
		private double pixDespAIzqda = 0D;
		private double coorX1 = 0D;
		private double coorX2 = 0D;
		private ObjetoGrafico fondo1 = null;
		private ObjetoGrafico fondo2 = null;
	
	//Permite parar o seguir haciendo el desplazamiento lateral del fondo
	
	public void rodarFondoAnimado( boolean seguir ) {
		fondoRodando = seguir;
	}
		
	//Devuelve la posici�n actual del rat�n con respecto al panel gr�fico de la ventana 
	 
	public Point getPosRaton() {
		return posicionRaton;
	}
	
	//Indica si la coordenada est� dentro del panel gr�fico de la ventana
	
	public boolean estaEnVentana( Point p ) {
		return (p.getX() >= 0 && p.getX() < layeredPane.getWidth() &&
				p.getY() >= 0 && p.getY() < layeredPane.getHeight());
	}
	
	// Devuelve el n�mero de pixels de ancho del panel gr�fico de la ventana
	 
	public int getAnchoPanelGrafico() {
		return layeredPane.getWidth();
	}
	
	// Devuelve el n�mero de pixels de alto del panel gr�fico de la ventana
	 
	public int getAltoPanelGrafico() {
		return layeredPane.getHeight();
	}
	
	//Consulta si el usuario ha realizado alg�n evento de rat�n o teclado en la ventana
	 
	public boolean hayEvento() {
		return !eventosVentana.isEmpty();
	}
	
	// Borra los eventos de rat�n o teclado
	 
	public void borraEventos() {
		eventosVentana.clear();
	}
	
	//Devuelve el primer evento pendiente de rat�n o teclado en la ventana
	 
	public EventoVentana getEvento() {
		if (eventosVentana.isEmpty()) return null;
		return remEvento(0);
	}

	//Consulta si el usuario est� haciendo una interacci�n no acabada con el rat�n
	 
	public boolean hayClickODragAMedias() {
		if (!eventosVentana.isEmpty() && 
				(getEvento(0) instanceof RatonPulsado)) {
			for (EventoVentana ev : eventosVentana) {
				if (ev instanceof RatonSoltado) {
					return false;
				}
			}
			return true;
		} else
			return false;
	}
	
	// Espera a que haya un evento en la ventana (de rat�n o teclado) y lo devuelve
	 
	public EventoVentana readEvento( long maxEspera ) {
		long esperaHasta = System.currentTimeMillis()+maxEspera;
		boolean sigoEsperando = true;
		while (sigoEsperando && System.currentTimeMillis() < esperaHasta) {
			sigoEsperando = eventosVentana.isEmpty() && isVisible();
			// Si click/drag mirar si hay algo que hacer
			if (!sigoEsperando && generarClicksYDrags) { 
				if (hayClickODragAMedias())
					// Hay uno a medias, sigo esperando
						sigoEsperando = true;  
			}
			if (sigoEsperando)
				// Espera hasta que el rat�n o el teclado hagan algo
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) { }
		}
		if (generarClicksYDrags && sigoEsperando)   
			if (!eventosVentana.isEmpty() && eventosVentana.get(0) instanceof RatonPulsado) {
				// eventosVentana.remove(0);
				// Tengo un evento a medias... devuelvo null
				return null;  
			}
		if (!isVisible()) return null;
		if (sigoEsperando)
			return null;
		else
			return remEvento(0);
	}
	
	// Espera a que haya un evento en la ventana (de rat�n o teclado) y lo devuelve.<br>
	
	public EventoVentana readEvento() {
		return readEvento( 3153600000000L );  
	}
	
	// Visualiza un mensaje en la l�nea de mensajes
	 
	public void showMessage( String s ) {
		lMensaje.setText( s );
		lMensajeSombra.setText( s );
	}
	
	// Informa si la ventana ha sido cerrada por el usuario
	 
	public boolean isClosed() {
		return !isVisible();
	}
	
	//A�ade al panel gr�fico un objeto de juego, que se visualizar� 
	 
	public void addObjeto( final ObjetoGrafico oj, Point p ) {
		oj.setLocation(p);
		try {
			SwingUtilities.invokeLater( new Runnable() {
				
				public void run() {
					layeredPane.add( oj, new Integer( JLayeredPane.DEFAULT_LAYER ) );
					layeredPane.repaint( oj.getX(), oj.getY(), oj.getAnchuraObjeto(), oj.getAlturaObjeto() );
				}
			});
		} catch (Exception e) {
		}
	}
	
	// Quita de la ventana el objeto gr�fico
	 
	public void removeObjeto( final ObjetoGrafico oj ) {
		try {
			SwingUtilities.invokeLater( new Runnable() {
				
				public void run() {
					layeredPane.remove( oj );
					layeredPane.repaint( oj.getX(), oj.getY(), oj.getAnchuraObjeto(), oj.getAlturaObjeto() );
					layeredPane.validate();  // TODO: chequear si hace falta
				}
			});
		} catch (Exception e) {
		}
	}
	
	// Devuelve el n�mero de objetos activos en la ventana
	 
	public int getNumObjetos() {
		return layeredPane.getComponentCountInLayer( JLayeredPane.DEFAULT_LAYER );
	}

	//Quita de la ventana todos los objetos gr�ficos que hubiera
	
	public void clearObjetos() {
		try {
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					for (Component c : layeredPane.getComponentsInLayer( JLayeredPane.DEFAULT_LAYER )) {
						layeredPane.remove( c );
					}
					layeredPane.repaint();
				}
			});
		} catch (Exception e) {
		}
	}

	// Trae al frente de la ventana el objeto gr�fico.<br>
	 
	public void traeObjetoAlFrente( final ObjetoGrafico oj ) {
		if (oj != null)
			try {
				SwingUtilities.invokeLater( new Runnable() {
					@Override
					public void run() {
						layeredPane.moveToFront(oj);
	
					}
				});
			} catch (Exception e) {
			}
	}

	// Mueve un objeto de juego a la posici�n indicada.
	
	public void setPosGrafico( ObjetoGrafico oj, Point p ) {
		oj.setLocation( p );
	}
	
	//Mueve un objeto de juego a la posici�n indicada

	public void muevePosGrafico( ObjetoGrafico oj, Point p ) {
		if (oj!=null) {
			if (hilo==null) { hilo = new HiloAnimacion(); hilo.start(); }
			Animacion a = new Animacion( oj.getX(), p.getX(), 
					oj.getY(), p.getY(), tiempoAnimMsg, oj );
			if (animacionesPendientes.indexOf(a) == -1)
				// Si el objeto es nuevo se mete en animaciones pendientes
				animacionesPendientes.add( a );
			else {  // Si ya estaba se actualiza esa animaci�n (ojo, puede generar diagonales o cosas raras)
				int pos = animacionesPendientes.indexOf(a);
				animacionesPendientes.get(pos).xHasta = p.getX();
				animacionesPendientes.get(pos).yHasta = p.getY();
				animacionesPendientes.get(pos).msFaltan = tiempoAnimMsg;
			}
		}
	} 

	// Mueve un objeto de juego a la posici�n indicada
	 
	public void muevePosGrafico( ObjetoGrafico oj, Point p, long msg ) {
		if (oj!=null) {
			if (hilo==null) { hilo = new HiloAnimacion(); hilo.start(); }
			Animacion a = new Animacion( oj.getX(), p.getX(), 
					oj.getY(), p.getY(), msg, oj );
			if (animacionesPendientes.indexOf(a) == -1)
				// Si el objeto es nuevo se mete en animaciones pendientes
				animacionesPendientes.add( a );
			else {  // Si ya estaba se actualiza esa animaci�n (ojo, puede generar diagonales o cosas raras)
				int pos = animacionesPendientes.indexOf(a);
				animacionesPendientes.get(pos).xHasta = p.getX();
				animacionesPendientes.get(pos).yHasta = p.getY();
				animacionesPendientes.get(pos).msFaltan = msg;
			}
		}
	}

	// Calcula el tiempo de movimiento de un objeto en su trayectoria lineal,
	 
	public long calcTiempoDeMovimiento( ObjetoGrafico oj, Point p, double vel ) {
		double dist = Math.sqrt( Math.pow( oj.getX()-p.getX(), 2 ) + 
								 Math.pow( oj.getY()-p.getY(), 2 ) );
		return Math.round( dist / vel * 1000 );
	}
	//Para el movimiento del objeto gr�fico indicado. Si
	 public void paraMovimiento( ObjetoGrafico oj ) {
		if (oj == null) return;
		for (Animacion a : animacionesPendientes) {
			if (a.oj.equals( oj )) {
				animacionesPendientes.remove( a );
				return;
			}
		}
	} 

	//Pone los tiempos para realizar las animaciones visuales en pantalla.
	 
	public void setTiempoPasoAnimacion( long tiempoAnimMsg, int numMovtos ) {
		if (tiempoAnimMsg < 100L || numMovtos < 2 || numMovtos > tiempoAnimMsg) 
			return;  // Error: no se hace nada
		this.tiempoAnimMsg = tiempoAnimMsg;
		this.tiempoFrameAnimMsg = tiempoAnimMsg/numMovtos;
	}

	//Devuelve la posici�n actual del objeto gr�fico indicado.<br>
	 
	public Point getPosicion( ObjetoGrafico oj ) {
		if (oj == null) return null;
		java.util.List<Component> l = Arrays.asList( layeredPane.getComponentsInLayer( JLayeredPane.DEFAULT_LAYER ) );
		if (!l.contains( oj )) return null;
		return oj.getLocation();
	}

	//Comprueba si hay alg�n objeto gr�fico en la posici�n indicada.<br>
	
	public ObjetoGrafico getObjetoEnPosicion( Point p ) {
		Component[] lC = layeredPane.getComponentsInLayer( JLayeredPane.DEFAULT_LAYER );
		for (Component c : lC) {
			if (c.contains( p ) && c instanceof ObjetoGrafico)
				return (ObjetoGrafico) c;
		}
		return null;
	}

	// Espera sin hacer nada durante el tiempo indicado en milisegundos
	 
	public void esperaUnRato( int msg ) {
		try {
			Thread.sleep( msg );
		} catch (InterruptedException e) {
		}
	}

	// Espera sin hacer nada durante el tiempo indicado en milisegundos
	
	public void esperaAEvento( int msg ) {
		long esperaHasta = System.currentTimeMillis()+msg;
		boolean sigoEsperando = true;
		while (sigoEsperando && System.currentTimeMillis() < esperaHasta) {
			sigoEsperando = eventosVentana.isEmpty() && isVisible();
			if (!sigoEsperando && generarClicksYDrags) { // Si click/drag mirar si hay algo que hacer
				if (hayClickODragAMedias())
						sigoEsperando = true;  // Hay uno a medias, sigo esperando
			}
			if (sigoEsperando)
				// Espera hasta que el rat�n haga algo
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) { }
		}
	}
	//Espera sin hacer nada a que acaben las animaciones
	 
	public void esperaAFinAnimaciones() {
		do {
			try {
				Thread.sleep( tiempoFrameAnimMsg );
			} catch (InterruptedException e) {
			}
		} while (!animacionesPendientes.isEmpty());
	}

	//m�todo y atributos privados para facilitar los offsets de puntos
			// del programa de prueba
			private static int moviendoOffsetX = 0;
			private static int moviendoOffsetY = 0;
			private static Point aplicaOffset( Point p ) {
				
				return new Point( (int) Math.round(p.getX() - moviendoOffsetX),
						(int) Math.round(p.getY() - moviendoOffsetY) );
			}
			
			class HiloAnimacion extends Thread {
				
				public void run() {
					while (!interrupted()) {
						try {
							Thread.sleep( tiempoFrameAnimMsg );
						} catch (InterruptedException e) {
							break;  // No har�a falta, el while se interrumpe en cualquier caso y se acaba el hilo
						}
						for (int i=animacionesPendientes.size()-1; i>=0; i--) {  // Al rev�s porque puede haber que quitar animaciones si se acaban
							Animacion a = animacionesPendientes.get(i);
							if (a.oj != null) a.oj.setLocation( 
								a.calcNextFrame( tiempoFrameAnimMsg ) );  // Actualizar animaci�n
							if (a.finAnimacion()) animacionesPendientes.remove(i);  // Quitar si se acaba
						}
						if (fondoAnimado && fondoRodando) {
							coorX1 -= pixDespAIzqda;
							coorX2 -= pixDespAIzqda;
							int x1 = (int) Math.round( coorX1 );
							int x2 = (int) Math.round( coorX2 );
							if (x1 < -fondo1.getWidth()) {  // Se sale fondo1 por la izqda
								coorX1 = coorX2 + fondo2.getWidth() - PX_SOLAPE_FONDOS;  // solapa pixels
								x1 = (int) Math.round( coorX1 );
							} else if (x2 < -fondo2.getWidth()) {  // Se sale fondo2 por la izqda
								coorX2 = coorX1 + fondo1.getWidth() - PX_SOLAPE_FONDOS;  // solapa pixels
								x2 = (int) Math.round( coorX2 );
							}
							if (x1<x2) { // muevo primero el de m�s a la derecha
								fondo1.setLocation( x2, 0 );
								fondo2.setLocation( x1, 0 );
							} else {
								fondo1.setLocation( x1, 0 );
								fondo2.setLocation( x2, 0 );
							}
							layeredPane.repaint();
						}
					}
				}
			}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	//Metodo para detener la musica una vez se cierra la ventana
	
	public void windowClosed(WindowEvent e) {
			try {
				apagarSonidos();
				MenuWindowFlappyCar menu = new MenuWindowFlappyCar();
				menu.setVisible(true);
			} catch (LineUnavailableException | IOException
					| UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	}
	
	public void windowClosing(WindowEvent e) {
		
	}
	
	public void windowDeactivated(WindowEvent e) {
		
	}

	public void windowDeiconified(WindowEvent e) {
		
	}
	
	public void windowIconified(WindowEvent e) {
		
	}
	
	//Metodo para activar la musica junto con la apertura de la ventana
	 
	public void windowOpened(WindowEvent e) {
		try {
			sonidos();
		} catch (LineUnavailableException | IOException
				| UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		}
		
	}
	//Carga un archivo de musica para reproducirlo
	 
	public static void sonidos() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		Clip musica = AudioSystem.getClip();
		musica.open(AudioSystem.getAudioInputStream(new File("Sonido.wav")));
		musica.start();
	}
	// Metodo para apagar la musica
	 
	public static void apagarSonidos() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		Clip musica = AudioSystem.getClip();
		musica.open(AudioSystem.getAudioInputStream(new File("Sonido.wav")));
		musica.start();
		musica.stop();
		
	}

}
	class Animacion {
		// Desde qu� x
		double xDesde;    
		// hasta qu� x
		double xHasta;  
		 // Desde qu� y
		double yDesde;   
		 // hasta qu� y
		double yHasta;
		 // en cu�ntos msg
		long msFaltan; 
		// objeto a animar
		ObjetoGrafico oj; 
		public Animacion(double xDesde, double xHasta, double yDesde,
				double yHasta, long msFaltan, ObjetoGrafico oj) {
			this.xDesde = xDesde;
			this.xHasta = xHasta;
			this.yDesde = yDesde;
			this.yHasta = yHasta;
			this.msFaltan = msFaltan;
			this.oj = oj;
		}
		Point calcNextFrame( long msPasados ) {
			if (msFaltan <= msPasados) {  
				msFaltan = 0;
				return new Point( (int)Math.round(xHasta), (int)Math.round(yHasta) );
			} else if (msPasados <= 0) {  
				return new Point( (int)Math.round(xDesde), (int)Math.round(yDesde) );
			} else {  
				xDesde = xDesde + (xHasta-xDesde)/msFaltan*msPasados;
				yDesde = yDesde + (yHasta-yDesde)/msFaltan*msPasados;
				msFaltan -= msPasados;
				return new Point( (int)Math.round(xDesde), (int)Math.round(yDesde) );
			}
		}
	boolean finAnimacion() {
		return (msFaltan <= 0);
	}
	public boolean equals(Object obj) {
		if (!(obj instanceof Animacion)) return false;
		return (oj == ((Animacion)obj).oj);
	}

	public String toString() {
		return "Animacion (" + xDesde + "," + yDesde + ") -> ("
				+ xHasta + "," + yHasta + ") msg: " + msFaltan;
	}
}	