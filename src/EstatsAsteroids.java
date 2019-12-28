import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


public class EstatsAsteroids {
	
	private static ArrayList<String> l = new ArrayList<String>();

	public static void main(String[] args) {
		cargarEstats(l, "estadisticasAsteroids.txt");
		
		DefaultPieDataset dataset = new DefaultPieDataset();
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
