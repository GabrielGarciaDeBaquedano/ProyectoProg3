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
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Jugador(idusuario INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " nombreJugador VARCHAR(100),"
					+ " contrasenya VARCHAR(20))");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Juego(idjuego int(10) PRIMARY KEY NOT NULL, "
					+ " nombreJuego VARCHAR(100));");
			st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida"
					+ " (codPartida INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " idusuario int(10),"
					+ " puntuacion VARCHAR(7), tiempoPartida DECIMAL(5, 2), fechaPartida DATE,"
					+ " idjuego int(10),"
					+ " FOREIGN KEY(idusuario) references Jugador(idusuario) ON DELETE CASCADE, "
					+ " FOREIGN KEY(idjuego) references Juego(idjuego) ON DELETE CASCADE);");
			
			try {
				st.executeUpdate( "insert into Juego (idjuego, nombreJuego) values (1,'Asteroids');" );
				st.executeUpdate( "insert into Juego (idjuego, nombreJuego) values (2,'Pong');" );
				st.executeUpdate( "insert into Juego (idjuego, nombreJuego) values (3,'Flappy Car');" );
				st.executeUpdate( "insert into Juego (idjuego, nombreJuego) values (4,'Busca Minas');" );
				
			} catch(Exception e) {e.printStackTrace();}
			
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			log( Level.SEVERE, "Error en conexi�n de base de datos " + nombreBD, e );
			return null;
		}
	}
	

	public static boolean insertarJugador(Jugador jugador) {
		//con = initBD("Arcade.db");
		String sql = "INSERT INTO jugador(nombreJugador, contrasenya) VALUES(?,?)";
			System.out.println(sql);
		try {
				
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, jugador.getNombreJugador());
				pst.setString(2, jugador.getContrasenya());
				pst.executeUpdate();
				return true;
			}catch( SQLException e ) {
				e.printStackTrace();
				return false;
			}		
		
	}
	
	public static boolean obtenerJugador(Jugador jugador) {
		//con = initBD("Arcade.db");
		String sql = "SELECT idusuario, nombreJugador, contrasenya FROM Jugador WHERE nombreJugador = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, jugador.getNombreJugador());
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				System.out.println(sql);
				System.out.println(rs.getString(3));
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
			+ partida.getIdJugador()
			+ partida.getIdJuego()
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
