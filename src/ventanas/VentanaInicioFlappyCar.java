package ventanas;

import java.awt.Toolkit;


import javax.swing.*;

import img.Img;
import objetosJuego.ObjetoGrafico;
import utils.FileManager;
import utils.Score;
public class VentanaInicioFlappyCar extends JFrame{
	private static final long serialVersionUID = 1L;
	public static Score score;
	JPanel imagen;
	public VentanaInicioFlappyCar(){
		score = FileManager.readScoreFromFile();
		this.setTitle("FlappyCar");
		this.setSize(1600, 1030);
		ObjetoGrafico fondo = new ObjetoGrafico(Img.getURLRecurso("lamborghini_inicio.jpg"), true);
		imagen = new JPanel();
		imagen.add(fondo);
		this.add(imagen);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaGraficaFlappyCar.class.getResource("src/img/logo.png")));
	}
	public static void main(String [] s){
		VentanaInicioFlappyCar v=new VentanaInicioFlappyCar();
		v.setVisible(true);
	}
}
