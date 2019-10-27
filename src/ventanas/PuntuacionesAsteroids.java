package ventanas;

import javax.swing.*;

import java.awt.*; 
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;


public class PuntuacionesAsteroids extends JFrame {

		
		

		public static void main(String[] args) {
			PuntuacionesAsteroids p = new PuntuacionesAsteroids();
			p.setVisible( true );
		}
		
		// Atributos de ventana
		private JLabel lMensaje;
		private JLabel lPuntuaciones;
		private JLabel lLogotipo;
		private JButton bVolver;
		private JList puntuaciones;
		private JScrollPane barra;
		
		public PuntuacionesAsteroids() {
			// Configurar ventana
			setTitle( "Puntuaciones" );
			setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
			setSize( 400, 300 );
			setLocation( 450, 150 );  // Coord. absoluta
			
			ArrayList<String> l = new ArrayList<>();
			cargarFicheroTexto(l, "Puntuaciones.txt");
			TreeSet<String> t = new TreeSet<>();
			for(String s : l){
				t.add(s);
			}
			DefaultListModel lm = new DefaultListModel();
			puntuaciones = new JList<>();
			puntuaciones.setVisible(true);
			for(String s : t){
				lm.addElement( s );
				puntuaciones.setModel(lm);
			}
			

			
			bVolver = new JButton( "Volver" );
			lPuntuaciones = new JLabel("Ranking puntuaciones:");
			
			JPanel pInferior = new JPanel();
			JPanel pCentral = new JPanel();
			JPanel pFila1 = new JPanel( /* new FlowLayout() */ );
			JPanel pFila2 = new JPanel();
			Container barra = new JScrollPane(puntuaciones);
			
			BoxLayout layoutCentral = new BoxLayout( pCentral, BoxLayout.Y_AXIS );
			pCentral.setLayout( layoutCentral );
			
			// Asociar componentes y contenedores
			pCentral.add(barra);
			getContentPane().add(lPuntuaciones, BorderLayout.NORTH);
			getContentPane().add( pCentral, BorderLayout.EAST );
			pInferior.add( bVolver );
			getContentPane().add( pInferior, BorderLayout.SOUTH );

			// Eventos
			bVolver.addActionListener( 
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						Thread t = new Thread () {
							public void run() {
									MenuAsteroids.main( null );
									
							}
						};
						t.start(); 
						dispose();
					}
				});
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

