package ventanas;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import ventanas.BD;
public class MenuLogin extends JFrame {
	
	static PrintStream usuarios;
	
	static Logger log;

		public static void main(String[] args) { 
			MenuLogin ml = new MenuLogin();
			ml.setVisible(true);
		}
		
		private static JTextField nombreUsuario;
		
		private static JTextField nick;

		private static JTextField contrasenya;
		

		protected static int idUsuarioEnUso;
		private JLabel titulo;
		private JButton bRegistro;
		private JButton bIniciosesion;
		private JLabel lGif;
		private static String UsuarioValido = "[a-zA-Z_0-9]{1,12}@[a-z]{5,12}.[a-z]{2,4}";
		private static String ContrasenyaValida = "[a-zA-Z_0-9]{8,}";
		Pattern patUsuario = Pattern.compile(UsuarioValido);
		Pattern patContrasenya = Pattern.compile(ContrasenyaValida);
		
		private void posicionaLinea( Container p, String etiqueta, Component campo ) {
			JPanel tempPanel = new JPanel();
			tempPanel.setLayout( new FlowLayout(FlowLayout.CENTER) );
			tempPanel.add( new JLabel( etiqueta ) );
			tempPanel.add( campo );
			p.add( tempPanel );
		}
		public static boolean comprobarCorreo(String Correo) {
			Pattern patUsuario = Pattern.compile(UsuarioValido);
			if(patUsuario.matcher(nombreUsuario.getText()).matches()) {
				System.out.println(Correo + " cumple el patron");
				return true;
			} else {
				System.out.println(Correo + " no cumple el patron");
				return false;
			}
		}
		
		public static boolean comprobarContrasenya(String Contrasenya) {
			Pattern patContrasenya = Pattern.compile(ContrasenyaValida);
			if(patContrasenya.matcher(contrasenya.getText()).matches()) {
				System.out.println(Contrasenya + " cumple el patron");
				return patContrasenya.matcher(Contrasenya).matches();
			} else {
				System.out.println(Contrasenya + " no cumple el patron");
				return false;
			}
		}
		
		public MenuLogin() {
			setTitle("Menu de Login");
			setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			setSize( 600, 400 );
			setLocation( 250, 90 );
			
			lGif = new JLabel( new ImageIcon( "src/img/giphy.gif" ) );
			
			nombreUsuario = new JTextField(19);
			
			nick = new JTextField(20);

			contrasenya = new JPasswordField(17);

			titulo = new JLabel("ARCADE MACHINE");
			
			bRegistro = new JButton("Registrarse");
			bIniciosesion = new JButton("Iniciar sesion");
			Font font1 = new Font("Tahoma", Font.BOLD, 24);
			titulo.setFont(font1);
			Font fontBotones = new Font( "Arial", Font.BOLD, 16 );
			for (JButton b : new JButton[] { bRegistro, bIniciosesion } )
				b.setFont( fontBotones );
			
			JPanel pIzq = new JPanel();
			pIzq.setBackground(Color.lightGray);
			JPanel pInferior = new JPanel();
			pInferior.setBackground(Color.DARK_GRAY);
			JPanel pCentral = new JPanel();
			
			BoxLayout layoutCentral = new BoxLayout( pCentral, BoxLayout.Y_AXIS );
			pCentral.setLayout( layoutCentral );
			getContentPane().add( lGif, BorderLayout.CENTER );
			
			pIzq.add(nombreUsuario);
			
			pIzq.add(nick);

			pIzq.add(contrasenya);

			pIzq.add(titulo);
			
			getContentPane().add(pIzq, BorderLayout.WEST);
			
			
			Container panelContenidos = new JPanel();
			panelContenidos.setLayout(new BoxLayout(panelContenidos,BoxLayout.Y_AXIS));
			pIzq.add(panelContenidos);
			posicionaLinea( panelContenidos, null, titulo );
			posicionaLinea( panelContenidos, "Correo:", nombreUsuario );
			posicionaLinea( panelContenidos, "Nick:", nick);
			posicionaLinea( panelContenidos, "Password:", contrasenya );
			
			bRegistro.addActionListener( 
					
					new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if(comprobarCorreo(nombreUsuario.getText()) && comprobarContrasenya(contrasenya.getText())) {
								
								//BD bd = new BD();
								Jugador jugador = new Jugador();
								
								jugador.setNombreJugador(nombreUsuario.getText());
								jugador.setContrasenya(contrasenya.getText());
								
								if(BD.insertarJugador(jugador)==true) {
									JOptionPane.showMessageDialog(null, "Jugador registrado con exito");
								}else {
									JOptionPane.showMessageDialog(null, "No se ha podido registrar");
								}
								//MenuArcade.main(null);
								
								
								/*ArrayList<String> datos = new ArrayList<String>();
								cargarFicheroUsuarios(datos, "usuarios.txt");
								for (String string : datos) {
									String[] nom = string.split(" ");
									if (nom[1].equals(nombreUsuario.getText())) {
										JOptionPane.showMessageDialog(null, "El nombre de usuario ya esta en uso");
										usuarioValido = false;
									}
								}*/
								
							/*	if (usuarioValido=true) {
									//A�adir un jugador a la bd
								//	Jugador jugador = new Jugador(nombreUsuario.getText());
									System.out.println(nombreUsuario.getText());
									
									BD.insertarJugador(jugador);
									System.out.println("Jugador a�adido");
									try {
										usuarios = new PrintStream(new FileOutputStream("usuarios.txt", true));
									} catch (Exception e1) {}
									usuarios.println("Usuario: "+nombreUsuario.getText()+" Contrasenya: "+contrasenya.getText());
									/*Thread t = new Thread () {
										public void run() {
												MenuArcade.main(null);
										}
									};
									t.start(); 
									dispose();*/
								
								
								}
							
								
								
							
				
						else if(patUsuario.matcher(nombreUsuario.getText()).matches()!=true){
								JOptionPane.showMessageDialog(null, "Introduzca un usuario valido");
							
							}else if(patContrasenya.matcher(contrasenya.getText()).matches()!=true){
								JOptionPane.showMessageDialog(null, "Introduzca una contrasenya valida");
							
							}		
						}
					});	
			
			bIniciosesion.addActionListener( 
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							//BD bd = new BD();
							Jugador jugador = new Jugador();
							if(!nombreUsuario.getText().equals("") && !contrasenya.equals("")) {
								jugador.setNombreJugador(nombreUsuario.getText());
								jugador.setContrasenya(contrasenya.getText());
								
								if(BD.obtenerJugador(jugador)) {
									//jugador = new Jugador(jugador.getIdusuario(), jugador.getNombreJugador(), jugador.getContrasenya());
									idUsuarioEnUso = jugador.getIdusuario();
									MenuArcade.main(null);
								}else {
									JOptionPane.showMessageDialog(null, "Usuario no encontrado");
								}
							}
							/*ArrayList<String> datos = new ArrayList<String>();	
							cargarFicheroUsuarios(datos, "usuarios.txt");
							for (String string : datos) {
								String[] nom = string.split(" ");
								if (nom[1].equals(nombreUsuario.getText()) && nom[3].equals(contrasenya.getText())) {
									usuarioValido = true;
									Thread t = new Thread () {
										public void run() {
											MenuArcade.main(null);
										}
									};
									t.start(); 
									dispose();
								}
							}*/
						
							try {
								log = Logger.getLogger("login-logger");
								log.addHandler(new FileHandler("Log.xml"));
							} catch (SecurityException ex) {
								// TODO Auto-generated catch block
								ex.printStackTrace();
							} catch (IOException ex) {
								// TODO Auto-generated catch block
								ex.printStackTrace();
							}
							log.log(Level.INFO, "Fecha de login " + (new Date()));
							System.out.println("Abierto");
						}
					});
			
			pInferior.add(bRegistro);
			pInferior.add(bIniciosesion);
			getContentPane().add( pInferior, BorderLayout.SOUTH );
			
			
			this.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent ev) {
					log.log(Level.INFO, "Fecha logout " + (new Date()));
					System.out.println("cerrado");
				}
				 public void windowOpened(WindowEvent ev) {
					 BD.initBD("ArcadeMachine");
					 System.out.println("BD abierta");
				 }
				
			});
		}
			
		/*	public static void cargarFicheroUsuarios( ArrayList<String> l, String nombreFic ) {
				try {
					Scanner fE = new Scanner( new FileInputStream( nombreFic ) );
					while (fE.hasNext()) {
						String linea = fE.nextLine();
						// Trabajo con cada linea
						try {
							l.add( linea );
						} catch (Exception e) {
							System.out.println( "Problema en la linea " + linea );
						}
					}
					fE.close();
				} catch (IOException e) {
					System.out.println( "No ha sido posible leer el fichero." );
				}
			}*/
			
			public static String getNick() {
				return nombreUsuario.getText();
			}
			
			public static int getIdUsuarioEnUso() {
				return idUsuarioEnUso;
			}
		
}