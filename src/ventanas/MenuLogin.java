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
						private boolean usuarioValido;

						@Override
						public void actionPerformed(ActionEvent e) {
							if(nombreUsuario.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "Introduzca un nombre de usuario");
							}
							
							else if(contraseña.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "Introduzca una contraseña");
							}
							
							else {
							ArrayList<String> l = new ArrayList<>();
							ArrayList<String> nomUsuario = new ArrayList<>();
							cargarFicheroUsuarios(l, "usuarios.txt");
							
							for (String linea : l) {
								nomUsuario.add(linea.split(": ")[1].split(" ")[0]);
							}
							
							for (String string : nomUsuario) {
								if(string == nombreUsuario.getText()) {
									usuarioValido = false;
								}
							}
							
							if(usuarioValido = true) {
							
							try {
								usuarios = new PrintStream(new FileOutputStream("usuarios.txt", true));
							} catch (Exception e1) {
							}
							usuarios.println("Usuario: "+nombreUsuario.getText()+" Contraseña: "+contraseña.getText());
							
							Thread t = new Thread () {
								public void run() {
										MenuArcade.main(null);
								}
							};
							t.start(); 
							dispose();
						}
							else {
								System.out.println("El nombre de usuario ya esta en uso");
							}
						}
						}
					});	
			
			bIniciosesion.addActionListener( 
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(nombreUsuario.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "Introduzca un nombre de usuario");
							}
							
							else if(contraseña.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "Introduzca una contraseña");
							}
							
							else {
							Thread t = new Thread () {
								public void run() {
									MenuArcade.main(null);
								}
							};
							t.start(); 
							dispose();
						}
						}
					});	
		}
			
			public static void cargarFicheroUsuarios( ArrayList<String> l, String nombreFic ) {
				try {
					Scanner fE = new Scanner( new FileInputStream( nombreFic ) );
					while (fE.hasNext()) {
						String linea = fE.nextLine();
						// Trabajo con cada lÃ­nea
						try {
							l.add( linea );
						} catch (Exception e) {
							System.out.println( "Problema en la lÃ­nea " + linea );
						}
					}
					fE.close();
				} catch (IOException e) {
					System.out.println( "No ha sido posible leer el fichero." );
				}
			}
		
}