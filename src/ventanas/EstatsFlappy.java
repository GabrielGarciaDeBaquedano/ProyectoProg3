package ventanas;
import java.io.FileInputStream;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.*;
import org.jfree.data.general.*;;


public class EstatsFlappy {
	
	private static ArrayList<Puntuacion> l = new ArrayList<Puntuacion>();

	public static void main(String[] args) {
		cargarEstats(MenuLogin.getJugadorEnUso());
		
		DefaultPieDataset data = new DefaultPieDataset();
		int c = 0;
		for (Puntuacion p : l) {
			c++;
			data.setValue("Partida "+c+" puntos: "+p.getPuntos()+" tiempo: "+p.getTiempo(), p.getPuntos());
		}
		
		JFreeChart chart = ChartFactory.createPieChart(
		         "Grafico Flappy", 
		         data, 
		         true, 
		         true, 
		         false);
		 
		        // Mostrar Grafico
		        ChartFrame frame = new ChartFrame("Grafico Flappy", chart);
		        frame.pack();
		        frame.setVisible(true);
	
	}

	public static void cargarEstats( Jugador jugador ) {
		Connection conn = BD.initBD("ArcadeMachine");
		String SQL = ""; 
		try {
			Statement stat = conn.createStatement();
			SQL = "select nombreJugador, puntuacion, tiempoPartida from Jugador J, Partida P where J.idusuario = P.idusuario and"
					+ " idjuego = 3 and J.idusuario = "+jugador.getIdusuario();
			ResultSet rs = stat.executeQuery( SQL );
			
			while (rs.next()) {
				l.add(new Puntuacion(rs.getString("nombreJugador"), rs.getInt("puntuacion"), rs.getLong("tiempoPartida")));
			}
			System.out.println(l);
		}catch(Exception e){
			System.out.println(e);

		}
	}
	
}
