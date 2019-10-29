package ventanas;

import java.awt.*;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import utils.FlappyCar;
import utils.FlappyRecords;
import utils.FileManager;
import utils.Score;
public class MenuWindowFlappyCar extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel panelFondo;
	private JButton Realizacion  = new JButton();
	private JButton Record = new JButton();
	private JButton Comienzo = new JButton();
	private JButton Exit = new JButton();
 private void initComponents() {
        panelFondo = new CargaImagenMenuFlappyCar();        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MENU DE FLAPPYCAR");
        this.setLocationRelativeTo(null);
        this.setBounds(100, 100, 450, 300);
        panelFondo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panelFondo.setOpaque(false);
        Realizacion.setText("Creditos");
        Realizacion.addActionListener(this); 
        Record.setText("Records");
        Record.addActionListener(this); 
        Comienzo.setText("Empezar");
        Comienzo.addActionListener(this);         
        Exit.setText("Final");
        Exit.addActionListener(this);
        GroupLayout panelFondoLayout = new GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(panelFondoLayout.createParallelGroup(Alignment.LEADING).addGroup(panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Comienzo, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(Record, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(Realizacion, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(Exit, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelFondoLayout.setVerticalGroup(panelFondoLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, panelFondoLayout.createSequentialGroup()
                .addContainerGap(220, Short.MAX_VALUE).addGroup(panelFondoLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(Realizacion)
                    .addComponent(Record)
                    .addComponent(Comienzo)
                    .addComponent(Exit)).addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap()
                .addComponent(panelFondo, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
        
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap()
                .addComponent(panelFondo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));

        pack();
        ((CargaImagenMenuFlappyCar) panelFondo).setImagen("810.jpg");
       
    }
    public MenuWindowFlappyCar() {
    	
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaGraficaFlappyCar.class.getResource("logo.png")));
    	initComponents();
    	
    }
    public void actionPerformed(ActionEvent arg0) {
    	
    	Object boton = arg0.getSource();
    	
    	if ((JButton)boton == Realizacion){
    		
    			VentanaCreditosFlappyCar.creditosDelJuego();
    	}
    	
    	else if((JButton)boton == Record){
    		
    		    this.dispose();
    		    VentanaRecordsFlappyCar records= new VentanaRecordsFlappyCar(this);
    		    records.setVisible(true);
    			Thread hilo = new Thread (records);
    			hilo.start();
    	}
    	
    	else  if ((JButton)boton == Comienzo){
    			this.dispose();
    			FlappyCar tarea = new FlappyCar();
    			Thread hilo = new Thread (tarea);
    			hilo.start();
    	}
    	
    	else if ((JButton)boton == Exit) {
    		Thread hilo = new Thread (new Exit());
    		hilo.start();
    	}
    }
    
    class Exit implements Runnable{
    	
    
    	public void run() {
    		
    		for(int i =5; i>=1; i--){
    			Exit.setText(i+"");
    			try {
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    		}
    		System.exit(0);
    		MenuArcade m = new MenuArcade();
    		m.setVisible( true );
    	}
    }

    public static void main(String args[]) {
    	
    	MenuWindowFlappyCar v= new MenuWindowFlappyCar();
    	v.setLocationRelativeTo(null);
		v.setVisible(true);
     }
}
