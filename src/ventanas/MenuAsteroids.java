
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

public class MenuAsteroids extends JFrame {

	public static void main(String[] args) {
		MenuAsteroids m = new MenuAsteroids();
		m.setVisible( true );
	}
	
	private JLabel lMensaje;
	private JLabel lLogotipo;
	private JButton bJugar;
	private JButton bPuntuaciones;
	private JButton bSalir; 

	public MenuAsteroids() {
//		Audio.lanzaAudioEnHilo("src/img/Alien.wav");
		setTitle( "Menu Asteroids" );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 530, 300 );
		setLocation( 450, 150 );
		
		lLogotipo = new JLabel( new ImageIcon( "src/img/asteroids-deluxe-logo.png" ) );
		lMensaje = new JLabel();
		bJugar = new JButton( "Jugar" );
		bSalir = new JButton( "Salir" );
		bPuntuaciones = new JButton("Puntuaciones"); 
		JPanel pInferior = new JPanel();
		JPanel pCentral = new JPanel();
		
		BoxLayout layoutCentral = new BoxLayout( pCentral, BoxLayout.Y_AXIS );
		pCentral.setLayout( layoutCentral );
		
		getContentPane().add( lMensaje, BorderLayout.NORTH );
		
		getContentPane().add( lLogotipo, BorderLayout.WEST );
		pInferior.add( bJugar );
		pInferior.add( bPuntuaciones );
		pInferior.add(bSalir); 
		getContentPane().add( pInferior, BorderLayout.SOUTH );
	
		bJugar.addActionListener( 
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent e) {
						Thread t = new Thread () {
							public void run() {
									JuegoAsteroids
									.main( null );		
							}
						};
						t.start(); 
						dispose();
					}
			});
		
		bPuntuaciones.addActionListener( 
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent e) {
						Thread t = new Thread () {
							public void run() {
								PuntuacionesAsteroids.main( null );		
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
						Thread t = new Thread () {
							public void run() {
								MenuArcade.main( null );		
							}
						};
						t.start(); 
						dispose();
					}
			});
	}
}
