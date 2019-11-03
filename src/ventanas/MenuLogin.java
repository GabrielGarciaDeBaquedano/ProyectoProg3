package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
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

public class MenuLogin extends JFrame {
	
	static PrintStream usuarios;

		public static void main(String[] args) {
			MenuLogin ml = new MenuLogin();
			ml.setVisible(true);
		}
		
		private JTextField nombreUsuario;

		private JTextField contraseña;


		private JLabel titulo;
		private JButton bRegistro;
		private JButton bIniciosesion;
		private JLabel lGif;
		private String UsuarioValido = "[a-zA-Z_0-9]{1,12}@[a-z]{5,12}.[a-z]{2,4}";
		private String ContraseñaValida = "[a-zA-Z_0-9]{8,}";
		
		private void posicionaLinea( Container p, String etiqueta, Component campo ) {
			JPanel tempPanel = new JPanel();
			tempPanel.setLayout( new FlowLayout(FlowLayout.CENTER) );
			tempPanel.add( new JLabel( etiqueta ) );
			tempPanel.add( campo );
			p.add( tempPanel );
		}
		
		public MenuLogin() {
			setTitle("Menu de Login");
			setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
			setSize( 600, 400 );
			setLocation( 250, 90 );
			
			lGif = new JLabel( new ImageIcon( "src/img/giphy.gif" ) );
			nombreUsuario = new JTextField(20);

			contraseña = new JPasswordField(17);

			contraseña = new JPasswordField(17);

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

			pIzq.add(contraseña);

			pIzq.add(contraseña);

			pIzq.add(titulo);
			
			getContentPane().add(pIzq, BorderLayout.WEST);
			
			pInferior.add(bRegistro);
			pInferior.add(bIniciosesion);
			getContentPane().add( pInferior, BorderLayout.SOUTH );
			
			Container panelContenidos = new JPanel();
			panelContenidos.setLayout(new BoxLayout(panelContenidos,BoxLayout.Y_AXIS));
			pIzq.add(panelContenidos);
			posicionaLinea( panelContenidos, null, titulo );
			posicionaLinea( panelContenidos, "Nick:", nombreUsuario );
			posicionaLinea( panelContenidos, "Password:", contraseña );
			
			bRegistro.addActionListener( 
					new ActionListener() {
						private boolean usuarioValido=true;
						Pattern patUsuario = Pattern.compile(UsuarioValido);
						Pattern patContraseña = Pattern.compile(ContraseñaValida);

						@Override
						public void actionPerformed(ActionEvent e) {
							if(patUsuario.matcher(nombreUsuario.getText()).matches() && patContraseña.matcher(contraseña.getText()).matches()) {
								ArrayList<String> datos = new ArrayList<String>();
								cargarFicheroUsuarios(datos, "usuarios.txt");
								for (String string : datos) {
									String[] nom = string.split(" ");
									if (nom[1].equals(nombreUsuario.getText())) {
										JOptionPane.showMessageDialog(null, "El nombre de usuario ya esta en uso");
										usuarioValido = false;
									}
								}
								if (usuarioValido!=false) {										
									try {
										usuarios = new PrintStream(new FileOutputStream("usuarios.txt", true));
									} catch (Exception e1) {}
									usuarios.println("Usuario: "+nombreUsuario.getText()+" Contraseña: "+contraseña.getText());
									Thread t = new Thread () {
										public void run() {
												MenuArcade.main(null);
										}
									};
									t.start(); 
									dispose();
								}
								
							}else if(patUsuario.matcher(nombreUsuario.getText()).matches()!=true){
								JOptionPane.showMessageDialog(null, "Introduzca un usuario valido");
							
							}else if(patUsuario.matcher(contraseña.getText()).matches()!=true){
								JOptionPane.showMessageDialog(null, "Introduzca una contraseña valida");
							
							}		
						}
					});	
			
			bIniciosesion.addActionListener( 
					new ActionListener() {
						private boolean usuarioValido = false;
						@Override
						public void actionPerformed(ActionEvent e) {
							ArrayList<String> datos = new ArrayList<String>();	
							cargarFicheroUsuarios(datos, "usuarios.txt");
							for (String string : datos) {
								String[] nom = string.split(" ");
								if (nom[1].equals(nombreUsuario.getText()) && nom[3].equals(contraseña.getText())) {
									usuarioValido = true;
									Thread t = new Thread () {
										public void run() {
											MenuArcade.main(null);
										}
									};
									t.start(); 
									dispose();
								}
							if(usuarioValido==false) {
								JOptionPane.showMessageDialog(null, "Introduzca un nombre de usuario y contraseña existentes");
							}
							}
						}
					});	
		}
			
			public static void cargarFicheroUsuarios( ArrayList<String> l, String nombreFic ) {
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
			}
		
}