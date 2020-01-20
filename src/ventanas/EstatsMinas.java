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


public class EstatsMinas {
	
	private static ArrayList<Puntuacion> l = new ArrayList<Puntuacion>();

	public static void main(String[] args) {
		cargarEstats(MenuLogin.getJugadorEnUso());
		
		DefaultPieDataset data = new DefaultPieDataset();
		int ganadas = 0;
		long tiempog = 0;
		int perdidas = 0;
		long tiempop = 0;
		for (Puntuacion p : l) {
			if(p.getPuntos()==0) {
				perdidas++;
				tiempop += p.getTiempo();
			}else {
				ganadas++;
				tiempog += p.getTiempo();
			}
		}
		data.setValue("Partidas ganadas "+ganadas+" tiempo medio: "+(tiempog/ganadas)/1000+"s", ganadas);
		data.setValue("Partidas ganadas "+perdidas+" tiempo medio: "+(tiempop/perdidas)/1000+"s", perdidas);
		JFreeChart chart = ChartFactory.createPieChart(
		         "", 
		         data, 
		         true, 
		         true, 
		         false);
		 
		        // Mostrar Grafico
		        ChartFrame frame = new ChartFrame("Grafico", chart);
		        frame.pack();
		        frame.setVisible(true);
	
	}

	public static void cargarEstats( Jugador jugador ) {
		Connection conn = BD.initBD("ArcadeMachine");
		String SQL = ""; 
		try {
			Statement stat = conn.createStatement();
			SQL = "select nombreJugador, puntuacion, tiempoPartida from Jugador J, Partida P where J.idusuario = P.idusuario and"
					+ " idjuego = 4 and J.idusuario = "+jugador.getIdusuario();
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
