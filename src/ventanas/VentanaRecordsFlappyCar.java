package ventanas;

import java.awt.BorderLayout;



import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import utils.Constantes;
import utils.FlappyRecords;
import utils.Record;
import utils.FlappyCar;
import img.Img;
import objetosJuego.ObjetoGrafico;
import utils.FileManager;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class VentanaRecordsFlappyCar extends JFrame implements Runnable, ActionListener {
	private JPanel contentPane, panelNorte, panelSur, panelCentro;
	private JButton btnVolver;
	private JLabel lblRecords;
	private JPanel PanelGanador;
	private JTextArea txtrNombre;
	private JFrame ventanaAnterior;

	public void cargarTextArea(){
		ArrayList<Record> a = FlappyRecords.volcarFicheroAArrayList(Constantes.NF_RECORDS);
		String texto="";
		for (int i=0; i<a.size();i++) {
			Record r = a.get(i);
			texto = texto + r.getNombreUsuario() + "    " + r.getPuntuacion() + "\n";
		}
	    txtrNombre.setText(texto);
	}

	/**
	 * Create the frame.
	 */
	public VentanaRecordsFlappyCar(JFrame va) {
		ventanaAnterior=va;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.setTitle("Records");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		lblRecords = new JLabel("Mejores Records ");
		panelNorte.add(lblRecords);
		
		panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(this);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		panelSur.add(btnVolver);
		
		panelCentro = new JPanel();
		panelCentro.setLayout(null);
		
		txtrNombre = new JTextArea();
		txtrNombre.setBounds(202, 16, 201, 133);
		cargarTextArea();
		JScrollPane scrollArea = new JScrollPane(txtrNombre);
		scrollArea.setBounds(202, 16, 201, 133);
		panelCentro.add(scrollArea);
		
		PanelGanador = new JPanel();
		PanelGanador.setBounds(15, 0, 136, 186);
		ObjetoGrafico fondo = new ObjetoGrafico(Img.getURLRecurso("Winner.jpg"), true);
		PanelGanador.add(fondo);
		PanelGanador.updateUI();
	
		panelCentro.add(PanelGanador);
		contentPane.add(panelCentro, BorderLayout.CENTER);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaGraficaFlappyCar.class.getResource("logo.png")));
		this.setLocationRelativeTo(null);
		
			
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton botonPulsado = (JButton) e.getSource();
		if (botonPulsado == btnVolver){
			//menuWindow menu = new menuWindow();
			//menu.setVisible(true);
			ventanaAnterior.setVisible(true);
			this.dispose();
		}
		
	}
	
   
    	
    }
