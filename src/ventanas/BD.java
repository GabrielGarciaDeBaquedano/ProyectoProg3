package ventanas;

import java.sql.Connection;

import ventanas.Jugador;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BD {
	
	protected static Connection con;	
	private static Logger logger = null;
	
	public static Connection initBD( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			log( Level.INFO, "Conectada base de datos " + nombreBD, null );
			Statement st = con.createStatement();
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Jugador(nombreJugador VARCHAR(100) PRIMARY KEY;");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida(codPartida INTEGER PRIMARY KEY AUTOINCREMENT, nombreJugador VARCHAR(100), nombreJuego VARCHAR(100),  puntuacion INTEGER, tiempoPartida DECIMAL(5, 2), fechaPartida DATE ;");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Juego(nombreJuego VARCHAR(100) PRIMARY KEY;");
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			log( Level.SEVERE, "Error en conexi�n de base de datos " + nombreBD, e );
			return null;
		}
	}
	

	public static boolean insertarJugador(Jugador jugador) {
			try {
				Statement stmt = con.createStatement();
				String sentSQL = "insert into Jugador(nombreJugador) values(" + "'"
				+ jugador.getNombreJugador();
				stmt.executeUpdate(sentSQL);
				return true;
			}
		catch( Exception e ) {
						e.printStackTrace();
						return false;
		}
	}
	
	public static boolean insertarPartida( Partida partida) {
		try {
			Statement stmt = con.createStatement();
			String sentSQL = "insert into Partida(codigo, nombreJugador, nombreJuego, puntuacion, tiempoJuego, fecha) values(,"
			+ partida.getCodPartida()
			+ partida.getNombreJugador()
			+ partida.getNombreJuego()
			+ partida.getPuntuacion()
			+ partida.getTiempoPartida() 
			+ partida.getFechaPartida() + " );";
			stmt.executeUpdate(sentSQL);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
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
