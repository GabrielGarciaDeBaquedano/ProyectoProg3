package ventanas;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.*;
import org.jfree.data.general.*;;


public class EstatsAsteroids {
	
	private static ArrayList<String> l = new ArrayList<String>();

	public static void main(String[] args) {
		cargarEstats(l, "estadisticasAsteroids.txt");
		
		DefaultPieDataset data = new DefaultPieDataset();
		for (String string : l) {
			data.setValue(string.split(" ")[12], Integer.parseInt(string.split(" ")[13]));
			data.setValue(string.split(" ")[15], Integer.parseInt(string.split(" ")[16]));
		}
		
		JFreeChart chart = ChartFactory.createPieChart(
		         "Ejemplo Rapido de Grafico en un ChartFrame", 
		         data, 
		         true, 
		         true, 
		         false);
		 
		        // Mostrar Grafico
		        ChartFrame frame = new ChartFrame("JFreeChart", chart);
		        frame.pack();
		        frame.setVisible(true);
	
	}

	public static void cargarEstats( ArrayList<String> l, String nombreFic ) {
		try {
			Scanner fE = new Scanner( new FileInputStream( nombreFic ) );
			while (fE.hasNext()) {
				String linea = fE.nextLine();
				// Trabajo con cada linea
				try {
					l.add( linea );
				} catch (Exception e) {
					System.out.println( "Problema en la linea " + linea );
				}
			}
			fE.close();
		} catch (IOException e) {
			System.out.println( "No ha sido posible leer el fichero." );
		}
	}
	
}
