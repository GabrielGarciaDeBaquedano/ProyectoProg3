package ventanas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuLogin extends JFrame {

		public static void main(String[] args) {
			MenuLogin ml = new MenuLogin();
			ml.setVisible(true);
		}
		
		private JTextField nombreUsuario;
		private JTextField contraseña;
		private JButton bRegistro;
		private JButton bIniciosesion;
		private JLabel lGif;
		
		private void posicionaLinea( Container p, String etiqueta, Component campo ) {
			JPanel tempPanel = new JPanel();
			tempPanel.setLayout( new FlowLayout(FlowLayout.LEFT) );
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
			contraseña = new JTextField(17);
			bRegistro = new JButton("Registrarse");
			bIniciosesion = new JButton("Iniciar sesion");
			
			
			JPanel pIzq = new JPanel();
			JPanel pInferior = new JPanel();
			JPanel pCentral = new JPanel();
			
			BoxLayout layoutCentral = new BoxLayout( pCentral, BoxLayout.Y_AXIS );
			pCentral.setLayout( layoutCentral );
			getContentPane().add( lGif, BorderLayout.CENTER );
			
			pIzq.add(nombreUsuario);
			pIzq.add(contraseña);
			
			getContentPane().add(pIzq, BorderLayout.WEST);
			
			pInferior.add(bRegistro);
			pInferior.add(bIniciosesion);
			getContentPane().add( pInferior, BorderLayout.SOUTH );
			
			Container panelContenidos = new JPanel();
			panelContenidos.setLayout(new BoxLayout(panelContenidos,BoxLayout.Y_AXIS));
			pIzq.add(panelContenidos);
			posicionaLinea( panelContenidos, "Nick:", nombreUsuario );
			posicionaLinea( panelContenidos, "Password:", contraseña );
			
			
			
		}
		
}
