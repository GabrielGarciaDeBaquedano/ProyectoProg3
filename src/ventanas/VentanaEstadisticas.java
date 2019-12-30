package ventanas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import javafx.scene.layout.Border;
import sun.font.CreatedFontTracker;

public class VentanaEstadisticas extends JFrame {

	private static  JLabel estadisticasJuegos;
	private static JPanel escogerJuego;
	private  static JButton bPong;
	private static JButton bAsteroids;
	private static JButton bFlappyCar;
	private static JButton bVolver;
	
	public  VentanaEstadisticas() {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(800, 400);
		setLocation(200, 100);
		
		estadisticasJuegos = new JLabel("Estadisticas");
		escogerJuego = new JPanel();
		bPong = new JButton("Pong" );
		bAsteroids = new JButton("Asteroids");
		bFlappyCar = new JButton("Flappy Car");
		bVolver = new JButton("Volver ");
		
		escogerJuego.add(estadisticasJuegos, BorderLayout.NORTH);
		escogerJuego.add(bAsteroids);
		escogerJuego.add(bPong );
		escogerJuego.add(bFlappyCar);
		escogerJuego.add(bVolver);
		getContentPane().add(escogerJuego, BorderLayout.WEST);
		
		bVolver.addActionListener( 
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent e) {
						Thread t = new Thread () {
							public void run() {
									MenuArcade
									.main( null );		
							}
						};
						t.start(); 
						dispose();
					}
			});
		
		bAsteroids.addActionListener( 
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent e) {
						Thread t = new Thread () {
							public void run() {
									EstatsAsteroids.main( null );		
							}
						};
						t.start(); 
						dispose();
					}
			});
		
	}
	public static void main(String arg0) {
		VentanaEstadisticas v = new VentanaEstadisticas();
		v.setVisible(true);
		
	}
	
	
	
		
		
	
	

}
