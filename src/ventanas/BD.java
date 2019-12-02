package ventanas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class BD {
	
	protected static Connection con;	
	private static Logger logger = null;
	
	public static boolean initBD( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			log( Level.INFO, "Conectada base de datos " + nombreBD, null );
			Statement st = con.createStatement();
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Jugador(idJugador INTEGER PRIMARY KEY AUTOINCREMENT,  nombreJugador VARCHAR(100);");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida(codPartida INTEGER PRIMARY KEY AUTOINCREMENT,  puntuacion INTEGER, tiempoPartida DECIMAL(5, 2), fechaPartida DATE ;");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Juego(idJuego INTEGER PRIMARY KEY AUTOINCREMENT,  nombreJuego VARCHAR(100);");
			
		    return true;
		} catch (ClassNotFoundException | SQLException e) {
			log( Level.SEVERE, "Error en conexi�n de base de datos " + nombreBD, e );
			return false;
		}
	}
	

	private static void insertarJugador( String nombreJugador) {
		try {
			Statement st = con.createStatement();
			try {
				String sent = "insert into Jugador(idJugador, nombreJugador) values(1," + nombreJugador +" );";
				
				
			}catch(Exception e) {
				}
		
		}catch( Exception e ) {
						e.printStackTrace();
						JOptionPane.showInputDialog("No se ha podido recorrer la lista de jugadores!",  JOptionPane.WARNING_MESSAGE);
		}
	}
	

	public static void cerrarBD( Statement st ) {
		try {
			if (st!=null) st.close();
			if (con != null) con.close();
			log( Level.INFO, "Cierre de base de datos", null );
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en cierre de base de datos", e );
		}
	}
	
	// Metodo local para loggear
		private static void log( Level level, String msg, Throwable excepcion ) {
			if (logger==null) {  // Logger por defecto local:
				logger = Logger.getLogger( BD.class.getName() );  // Nombre del logger - el de la clase
				logger.setLevel( Level.ALL );  // Loguea todos los niveles
			}
			if (excepcion==null)
				logger.log( level, msg );
			else
				logger.log( level, msg, excepcion );
		}
}
