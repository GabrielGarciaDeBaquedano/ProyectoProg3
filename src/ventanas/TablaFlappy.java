package ventanas;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TreeSet;
import java.util.logging.Level;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;



public class TablaFlappy extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static JButton bGraficos;




	public static void main(String[] args) {
		TablaFlappy vLA = new TablaFlappy(); 
		vLA.mostrar();
		vLA.setVisible(true);
	}

	JButton bVolver; 
	JTable tablaFlappy; 
	JPanel pBotonera, pPrincipal; 
	JTableHeader header; 
	DefaultTableModel modelo;
	Object valor; 






	public TablaFlappy() {
		setSize(600,400);
		setLocation(300, 200);

		setTitle("Tabla Flappy");
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

		pBotonera = new JPanel(); 

		bGraficos = new JButton("Graficos");
		bVolver = new JButton("volver"); 

		pBotonera.add(bGraficos);
		pBotonera.add(bVolver);  

		pPrincipal = new JPanel(); 


		tablaFlappy = new JTable(modelo);

		pPrincipal.add(new JScrollPane(tablaFlappy));






		getContentPane().add(pPrincipal, BorderLayout.CENTER);
		getContentPane().add(pBotonera, BorderLayout.SOUTH); 


		bVolver.addActionListener((ActionEvent e ) -> {volver(); });
		bGraficos.addActionListener((ActionEvent e ) -> {graficos(); });

	}

	private void graficos() {
		EstatsFlappy.main(null); 
	}
	
	public void volver() {

		setVisible(false);
		VentanaEstadisticas.main(null); 
		dispose();
	}				
	
	private void mostrar() {

		TreeSet<Puntuacion> puntuaciones = new TreeSet<Puntuacion>();
		
		String column_names[]= {"Nombre", "Puntuacion", "Tiempo"};

		modelo = new DefaultTableModel(column_names,0);


		Connection conn = BD.initBD("ArcadeMachine");
		String SQL = ""; 
		try {
			Statement stat = conn.createStatement();
			SQL = "select nombreJugador, puntuacion, tiempoPartida from Jugador J, Partida P where J.idusuario = P.idusuario and"
					+ " idjuego = 3 ";  



			ResultSet rs = stat.executeQuery( SQL );
			while(rs.next()) {

				puntuaciones.add(new Puntuacion(rs.getString("nombreJugador"), rs.getInt("puntuacion"), rs.getLong("tiempoPartida")));

			}
			
			for (Puntuacion puntuacion : puntuaciones) {
				modelo.addRow(new Object[] {puntuacion.getNombreUsuario(), puntuacion.getPuntos(), puntuacion.getTiempo()});
			}
			tablaFlappy.setModel(modelo);

		}catch(Exception e){
			System.out.println(e);

		}

	}
}
