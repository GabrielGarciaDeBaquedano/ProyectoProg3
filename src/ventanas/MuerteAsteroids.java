package ventanas;

import javax.swing.*;

import java.awt.*; 
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


public class MuerteAsteroids extends JFrame {

		
		

		public static void main(String[] args) {
			MuerteAsteroids m = new MuerteAsteroids();
			m.setVisible( true );
		}

		// Atributos de ventana
		private JLabel lNombre;
		private JTextField tNombre;
		private JLabel lPuntuacion;
		private JLabel lLogotipo;
		private JButton bGuardar;
		private JLabel lGameOver;
		
		public MuerteAsteroids() {
			// Configurar ventana
			setTitle( "Guardar puntuacion" );
			setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
			setSize( 400, 300 );
			setLocation( 450, 150 );  // Coord. absoluta
			
			lGameOver = new JLabel("Game Over");
			lGameOver.setForeground(Color.RED);
			
			int puntos = JuegoAsteroids.getPuntuacion();
			lPuntuacion = new JLabel("Puntos "+puntos);
			//lPuntuacion.setForeground(Color.black);
			
			lNombre = new JLabel("Nombre: ");
			
			tNombre = new JTextField(10);
			
			bGuardar = new JButton( "Guardar" );
			
			lLogotipo = new JLabel( new ImageIcon( "Asteroids/img/game_over.png" ) );
			
			JPanel pSuperior = new JPanel();
			JPanel pInferior = new JPanel();
			JPanel pCentral = new JPanel();
			JPanel foto = new JPanel();
			JPanel pFila2 = new JPanel();
			
			GridLayout layoutCentral = new GridLayout(2, 2);
			pCentral.setLayout( layoutCentral );
			
			// Asociar componentes y contenedores
			pSuperior.add(lGameOver);
			getContentPane().add( pSuperior, BorderLayout.NORTH );
			foto.add(lLogotipo);
			pFila2.add(lPuntuacion);
			pFila2.add( lNombre );
			pFila2.add( tNombre );
			getContentPane().add( pFila2, BorderLayout.CENTER );
			pInferior.add( bGuardar );
			getContentPane().add( pInferior, BorderLayout.SOUTH );

			// Eventos
			
			bGuardar.addActionListener( 
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						String puntosUsuario = tNombre.getText() +  ": " + JuegoAsteroids.getPuntuacion();
						ArrayList<String> l = new ArrayList<>();
						cargarFicheroTexto(l, "Puntuaciones.txt");
						l.add(puntosUsuario);
						guardarFicheroTexto(l, "Puntuaciones.txt");
						
						Thread t = new Thread () {
							public void run() {
									PuntuacionesAsteroids.main( null );
									
							}
						};
						t.start(); 
						dispose();
					}
				});
		}	
		
		public static void guardarFicheroTexto( ArrayList<String> l , String nombreFic ) {
			// Stream - flujos de datos
			// En este caso de salida
			try {
				PrintStream fS = new PrintStream( nombreFic );
				for(String s : l){
					fS.println( s ); 
				}
				fS.close();
			} catch (IOException e) {
				System.out.println( "No ha sido posible crear el fichero." );
			}
		}
		
		public static void cargarFicheroTexto( ArrayList<String> l, String nombreFic ) {
			try {
				Scanner fE = new Scanner( new FileInputStream( nombreFic ) );
				while (fE.hasNext()) {
					String linea = fE.nextLine();
					// Trabajo con cada línea
					try {
						l.add( linea );
					} catch (Exception e) {
						System.out.println( "Problema en la línea " + linea );
					}
				}
				fE.close();
			} catch (IOException e) {
				System.out.println( "No ha sido posible leer el fichero." );
			}
		}
		
	}

