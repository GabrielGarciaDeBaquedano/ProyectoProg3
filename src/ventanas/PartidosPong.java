package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JButton;

public class PartidosPong extends JFrame {
	
	/* --------------------------------
	   Declarar las herramientas a usar 
	   -------------------------------- */
	
	public JPanel panel;
	public static JList list;
	public JLabel Partidos;
	public static ArrayList<String> registro;
	private JButton Cerrar;
	
	
	/**
	 * Constructor de la ventana 
	 */
	public PartidosPong() {
		
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 191, 244);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		list = new JList();
		list.setBounds(31, 44, 126, 143);
		panel.add(list);
		
		Partidos = new JLabel("Partidos");
		Partidos.setBounds(48, 16, 69, 20);
		panel.add(Partidos);
		
		Cerrar = new JButton("Cerrar ");
		Cerrar.setBounds(41, 199, 115, 29);
		panel.add(Cerrar);
		
		Cerrar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	/**
	 * Carga el fichero de puntuaciones en el mapa y a�ade el ultimo partido al mapa.
	 */
	public static void cargarPuntuaciones() {
		
		registro = new ArrayList<String>();
		cargarFichero(registro, "jugadores.txt");
		String Jugador = MainPong.Jugador_1.getText() + "|=>"+ JuegoPong.pala1.getMarcador() + ":" + JuegoPong.pala2.getMarcador() + "<=|" + MainPong.Jugador_2.getText();
		registro.add(Jugador);
		guardarFichero(registro, "jugadores.txt");
		
		TreeSet<String> s = new TreeSet<>();
		for(String m : registro) {
			s.add(m);
		}
		
		DefaultListModel listModel = new DefaultListModel();
		list.setVisible(true);
		for(String f : s) {
			listModel.addElement(f);
			list.setModel(listModel);
		}
		
	
	}
	
	public static void main(String[] args){
		PartidosPong p = new PartidosPong();
		p.cargarPuntuaciones();
		p.setVisible(true);
		p.setSize(200, 300);
		p.setLocation(500, 170);
		p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}


	
	/**
	 * @param p
	 * @param nombreFichero
	 * Carga el fichero con los partidos
	 */
	public static void cargarFichero(ArrayList<String> p, String nombreFichero) {
			
			try {
				
				Scanner fich = new Scanner(new FileInputStream(nombreFichero));
				while(fich.hasNext()) {
					String linea = fich.next();
					try {
						p.add(linea);
						
					}catch (Exception e){
						System.out.println("Imposible continuar!");
					}
					
					
				}
				fich.close();
				
			} catch (IOException e) {
				System.out.println("No ha sido posible cargar el fichero!");
			}
			
		}
	
	
	/**
	 * @param l
	 * @param nombreFichero
	 * Guarda la puntuacion de los partidos en el fichero + el ultimo jugado
	 */
	public static void guardarFichero(ArrayList<String> l, String nombreFichero) {
			
			try {
				PrintStream fich = new PrintStream(nombreFichero);
				for(String s : l) {
				
					fich.println(s);
				
				}
				fich.close();
				
			} catch (IOException e) {
				System.out.println("Imposible crear fichero!");
			}
			
			
		}
	
}

