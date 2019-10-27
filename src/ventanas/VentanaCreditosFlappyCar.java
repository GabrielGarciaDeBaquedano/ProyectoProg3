package ventanas;

import java.awt.Toolkit;


import javax.swing.JFrame;
import javax.swing.JPanel;

import img.Img;
import objetosJuego.ObjetoGrafico;
import utils.FileManager;
import utils.Score;

public class VentanaCreditosFlappyCar extends JFrame {

	private static final long serialVersionUID = 1L;
	public static Score score;
	JPanel imagen;
	
	public VentanaCreditosFlappyCar(){
		score = FileManager.readScoreFromFile();
		this.setTitle("Creditos");
		this.setSize(1000, 380);
		ObjetoGrafico fondo = new ObjetoGrafico(Img.getURLRecurso("Captura.PNG"), true);
		imagen = new JPanel();
		imagen.add(fondo);
		this.add(imagen);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaGraficaFlappyCar.class.getResource("Captura.PNG")));
		this.setLocationRelativeTo(null);
	}
	
	public static void creditosDelJuego(){				
			
			class creditosRunnable implements Runnable{
	
			
				public void run() {
					
					VentanaCreditosFlappyCar credits = new VentanaCreditosFlappyCar();
					credits.setVisible(true);					
				}
			}
			creditosRunnable tarea = new creditosRunnable();
			Thread hilo = new Thread(tarea);
			hilo.start();
	}

	
	public static void main(String [] s){
		VentanaCreditosFlappyCar v = new VentanaCreditosFlappyCar();
		v.setVisible(true);
	}
}