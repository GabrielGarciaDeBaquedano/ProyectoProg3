package ventanas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.FlappyCar;

public class MenuArcade extends JFrame {

	public static void main(String[] args) {
		MenuArcade m = new MenuArcade();
		m.setVisible( true );
	}
	
	private JLabel lMensaje;
	private JLabel lLogotipoArcade;
	private JLabel lLogotipoAsteroids;
	private JLabel lLogotipoPong;
	private JLabel lLogotipoFlappyCar;
	private JButton bAsteroids;
	private JButton bPong;
	private JButton bFlappyCar;
	private JButton bEstadisticas;
	private JButton bSalir; 

	public MenuArcade() {
		setTitle( "Menu Arcade" );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 800, 500 );
		setLocation( 250, 90 );
		
		lLogotipoArcade = new JLabel( new ImageIcon( "src/img/arcade.png" ) );
		lMensaje = new JLabel();
		bAsteroids = new JButton( "Asteroids" );
		bPong = new JButton( "Pong" );
		bFlappyCar = new JButton("Flappy Car"); 
		bEstadisticas = new JButton("Estadisticas");
		bSalir = new JButton("Salir");
		JPanel pInferior = new JPanel();
		JPanel pCentral = new JPanel();
		
		BoxLayout layoutCentral = new BoxLayout( pCentral, BoxLayout.Y_AXIS );
		pCentral.setLayout( layoutCentral );
		
		getContentPane().add( lMensaje, BorderLayout.NORTH );
		
		getContentPane().add( lLogotipoArcade, BorderLayout.CENTER );
		pInferior.add( bAsteroids );
		pInferior.add( bPong );
		pInferior.add( bFlappyCar );
		pInferior.add( bEstadisticas );
		pInferior.add(bSalir); 
		getContentPane().add( pInferior, BorderLayout.SOUTH );
	
		bAsteroids.addActionListener( 
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent e) {
						Thread t = new Thread () {
							public void run() {
									MenuAsteroids
									.main( null );		
							}
						};
						t.start(); 
						dispose();
					}
			});
		
		bPong.addActionListener( 
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent e) {
						Thread t = new Thread () {
							public void run() {
									MainPong
									.main( null );		
							}
						};
						t.start(); 
						dispose();
					}
			});
		
		bFlappyCar.addActionListener( 
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent e) {
						Thread t = new Thread () {
							public void run() {
									FlappyCar
									.main( null );		
							}
						};
						t.start(); 
						dispose();
					}
			});
		
		bSalir.addActionListener( 
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent e) {
					dispose();}
				});
	}
}

