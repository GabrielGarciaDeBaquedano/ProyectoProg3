package ventanas;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import javax.swing.JLabel;



public class MainPong extends JFrame{
	
	/* --------------------------------
	   Declarar las herramientas a usar 
	   -------------------------------- */
	
	
	private static MainPong v;
	private JButton CerrarJuego; 
	public static JTextField Jugador_2;
	public static JTextField Jugador_1;
	private JButton Jugar;
	private JLabel Jugador1;
	private JLabel Jugador2;
	private JLabel Aviso;
	private JLabel Pausa;
	private JLabel Reanudar;
	private JLabel AcabarJuego;
	
	/**
	 * Constructor de la ventana de inicio
	 */
	public MainPong() {
		
		
		
		
		getContentPane().setLayout(null);
		
		CerrarJuego = new JButton("Cerrar Juego");
		CerrarJuego.setBounds(15, 50, 149, 29);
		getContentPane().add(CerrarJuego);
		
		CerrarJuego.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread () {
					public void run() {
						MenuArcade.main( null );		
					}
				};
				t.start(); 
				dispose();
			}
		});
			
		
		Jugador_2 = new JTextField();
		Jugador_2.setBounds(254, 132, 146, 26);
		getContentPane().add(Jugador_2);
		Jugador_2.setColumns(10);
		
		Jugador_1 = new JTextField();
		Jugador_1.setBounds(254, 51, 146, 26);
		getContentPane().add(Jugador_1);
		Jugador_1.setColumns(10);
		
		Jugar = new JButton("Jugar!");
		Jugar.setBounds(15, 16, 149, 29);
		getContentPane().add(Jugar);
		
		
		Jugar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					Thread t = new Thread() {
						public void run() {
							JuegoPong.main(null);
						}
					};
					
					t.start();
					v.dispose();
				}
			});
				
		Jugador1 = new JLabel("Jugador 1:");
		Jugador1.setBounds(239, 30, 69, 20);
		getContentPane().add(Jugador1);
		
		Jugador2 = new JLabel("Jugador 2:");
		Jugador2.setBounds(239, 108, 69, 20);
		getContentPane().add(Jugador2);
		
		Aviso = new JLabel("Primero debes introducir los nombres!");
		Aviso.setBounds(179, 194, 223, 38);
		Aviso.setVisible(false);
		getContentPane().add(Aviso);
		
		Pausa = new JLabel("Q: Pausa");
		Pausa.setBounds(15, 108, 95, 20);
		getContentPane().add(Pausa);
		
		Reanudar = new JLabel("E: Reanudar");
		Reanudar.setBounds(15, 135, 95, 20);
		getContentPane().add(Reanudar);
		
		AcabarJuego = new JLabel("ESC: Acabar juego");
		AcabarJuego.setBounds(15, 167, 120, 20);
		getContentPane().add(AcabarJuego);
		
	}
	
	
	/**
	 * Crea la ventana desde el constructor de la ventana
	 */
	public static void creaVentana() {
		
		v = new MainPong();
		v.setVisible(true);
		v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		v.setSize(420, 290);
		v.setLocation(400, 100);
		
	}
		
	
	/**
	 * @param args
	 * Comienza todo el juego
	 */
	public static void main(String[] args) {
		creaVentana();
			
		}
}

	
	

