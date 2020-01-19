package ventanas;


import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class TablaFlappy extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;





	public static void main(String[] args) {
		TablaFlappy ver = new TablaFlappy(); 
		ver.mostrar();
		ver.setVisible(true);
	}

	JButton bVolver, bBorrar; 
	JTable tablaEstats; 
	JPanel pBotonera, pPrincipal; 
	JTableHeader header; 
	






	public TablaFlappy() {
		setSize(600,400);
		setLocation(300, 200);

		setTitle("Tabla Estadisticas");
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

		pBotonera = new JPanel(); 

		bVolver = new JButton("volver"); 
		bBorrar = new JButton("Borrar fila"); 

		pBotonera.add(bVolver); 
		pBotonera.add(bBorrar); 

		pPrincipal = new JPanel(); 
		tablaEstats = new JTable();
		header = new JTableHeader(); 
	

		pPrincipal.add(tablaEstats);
		

		




		getContentPane().add(pPrincipal, BorderLayout.CENTER);
		getContentPane().add(pBotonera, BorderLayout.SOUTH); 






	}



	private void mostrar() {

		DefaultTableModel modelo = new DefaultTableModel();
		

	


		modelo.setColumnIdentifiers(new Object[] {"codPartida", "Nombre", "Juego","Puntuacion","TiempoJuego", "Fecha"});

		Connection conn = BD.initBD("Arcade.db");
		String SQL = ""; 
		try {
			Statement stat = conn.createStatement();
			SQL = "select codPartida,nombreJugador,nombreJuego,puntuacion,tiempoPartida,fechaPartida from partida"
					+ "where nombreJuego='Flappy Car' "; 



			ResultSet rs = stat.executeQuery( SQL );
			while(rs.next()) {
				

				modelo.addRow(new Object[] {rs.getString("codPartida"), rs.getString("nombreJugador"), rs.getString("nombreJuego"), rs.getString("puntuacion"), rs.getString("tiempoPartida"), rs.getString("fechaPartida")});

			}
			

			tablaEstats.setModel(modelo);
			

		}catch(Exception e){
			System.out.println(e);

		}

	}
}
