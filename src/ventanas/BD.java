package ventanas;

import java.sql.Connection;

import ventanas.Jugador;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Jugador(idusuario int(10) AUTOINCREMENT PRIMARY KEY NOT NULL,"
					+ " nombreJugador VARCHAR(100),"
					+ "contrasenya VARCHAR(20);");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Juego(idjuego int(10) AUTOINCREMENT PRIMARY KEY NOT NULL, "
					+ "nombreJuego VARCHAR(100);");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida"
					+ "(codPartida int(10) PRIMARY KEY AUTOINCREMENT NOT NULL, "
					+ "idusuario int(10),"
					+ " nombreJuego VARCHAR(100),  puntuacion VARCHAR(7), tiempoPartida DECIMAL(5, 2), fechaPartida DATE,"
					+ "idjuego int(10),"
					+ "FOREIGN KEY(idusuario) references Jugador(idusuario) ON DELETE CASCADE"
					+ "FOREIGN KEY(idjuego) references Juego(idjuego) ON DELETE CASCADE;");
			
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			log( Level.SEVERE, "Error en conexi�n de base de datos " + nombreBD, e );
			return null;
		}
	}
	

	public static boolean insertarJugador(Jugador jugador) {
		con = initBD("Arcade.db");
		String sql = "INSERT INTO Jugador values(idusuario, nombreJugador, contrasenya) VALUES(?,?,?)";
			try {
				
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setInt(1, jugador.getIdusuario());
				pst.setString(2, jugador.getNombreJugador());
				pst.setString(3, jugador.getContrasenya());
				return true;
			}catch( SQLException e ) {
				e.printStackTrace();
				return false;
			}		
		
	}
	
	public static boolean obtenerJugador(Jugador jugador) {
		con = initBD("Arcade.db");
		String sql = "SELECT idusuario, nombreJugador, contrasenya FROM Jugador WHERE nombreJugador = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, jugador.getNombreJugador());
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				if(jugador.getContrasenya().equals(rs.getString(3))) {
					jugador.setIdusuario(rs.getInt(1));
					jugador.setNombreJugador(rs.getString(2));
					jugador.setContrasenya(rs.getString(3));
					return true;
				}
			}else {
				return false;
			}
		return false;
		}catch(SQLException e) {
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
