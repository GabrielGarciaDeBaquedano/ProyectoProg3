package ventanas;

import javax.swing.JButton;
import javax.swing.JFrame;
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
		
		public MenuLogin() {
			setTitle("Menu de Login");
			setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
			setSize( 600, 400 );
			setLocation( 250, 90 );
			
			nombreUsuario = new JTextField();
			contraseña = new JTextField();
			bRegistro = new JButton("Registrarse");
			bIniciosesion = new JButton("Iniciar sesion");
			
			JPanel pIzq = new JPanel();
			JPanel pInferior = new JPanel();
		}
		
}
