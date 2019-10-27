package img;

public class Img {
	public static java.net.URL getURLRecurso( String nomRecImg ) {
		java.net.URL recurso = null;
		try {
			recurso = Img.class.getResource( nomRecImg ).toURI().toURL();
		} catch (Exception e) {
			System.err.println( "Recurso no encontrado: " + nomRecImg );
			e.printStackTrace();
		}
		return recurso;
	}
}
