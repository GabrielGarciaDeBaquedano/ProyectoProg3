package objetosJuego;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

import javax.imageio.ImageIO;

import img.*;
public class ObjetoGrafico extends JLabel {
	protected String nombreImagenObjeto; 
	protected JPanel panelJuego; 
	protected boolean esVisible; 
	protected int anchuraObjeto;
	protected int alturaObjeto;  	
	protected int xInicioChoque; 
	protected int xFinChoque;    
	protected int yInicioChoque;
	protected int yFinChoque;
	protected ImageIcon icono; 
	protected boolean escalado; 
	protected BufferedImage imagenObjeto;  
	private static final long serialVersionUID = 1L; 
	public ObjetoGrafico( String nombreImagenObjeto, boolean visible, int anchura, int altura ) {
		setName( nombreImagenObjeto );
		panelJuego = null;
		anchuraObjeto = anchura;
		alturaObjeto = altura;
		this.nombreImagenObjeto = nombreImagenObjeto;
        URL imgURL = Img.getURLRecurso(nombreImagenObjeto);
        if (imgURL == null) {
        	icono = null;
    		setOpaque( true );
    		setBackground( Color.red );
    		setForeground( Color.blue );
        	setBorder( BorderFactory.createLineBorder( Color.blue ));
        	setText( nombreImagenObjeto );
        } else {
        	icono = new ImageIcon(imgURL);
    		setIcon( icono );
        	if (anchura==icono.getIconWidth() && altura==icono.getIconHeight()) {
        		escalado = false;
        	} else {  
        		escalado = true;
            	try {  
        			imagenObjeto = ImageIO.read(imgURL);
        		} catch (IOException e) {
        			escalado = false;
        		}
        	}
        }
    	setSize( anchura, altura );
		this.xInicioChoque = 0;
		this.xFinChoque = anchura; 
		this.yInicioChoque = 0;
		this.yFinChoque = altura;
		esVisible = visible;
		setVisible( esVisible );
	}
	public ObjetoGrafico( String nombreImagenObjeto, boolean visible ) {
		this( nombreImagenObjeto, visible, 10, 10 );

		if (icono != null) { 
			alturaObjeto = icono.getIconHeight();
			setSize( anchuraObjeto, alturaObjeto );
			this.xFinChoque = anchuraObjeto; 
			this.yFinChoque = alturaObjeto;
		}
	}
	public ObjetoGrafico( java.net.URL urlImagenObjeto, boolean visible, int anchura, int altura ) {
		if (urlImagenObjeto!=null) setName( urlImagenObjeto.getQuery());
		panelJuego = null;
		anchuraObjeto = anchura;
		alturaObjeto = altura;
		nombreImagenObjeto = "";
        if (urlImagenObjeto == null) {
        	icono = null;
    		setOpaque( true );
    		setBackground( Color.red );
    		setForeground( Color.blue );
        	setBorder( BorderFactory.createLineBorder( Color.blue ));
        	setText( nombreImagenObjeto );
        } else {
        	icono = new ImageIcon(urlImagenObjeto);
    		setIcon( icono );
        	if (anchura==icono.getIconWidth() && altura==icono.getIconHeight()) {
        		escalado = false;
        	} else {  
        		escalado = true;
            	try {  
        			imagenObjeto = ImageIO.read(urlImagenObjeto);
        		} catch (IOException e) {
        			escalado = false;
        		}
        	}
        }
    	setSize( anchura, altura );
		this.xInicioChoque = 0;
		this.xFinChoque = anchura; 
		this.yInicioChoque = 0;
		this.yFinChoque = altura;
		esVisible = visible;
		setVisible( esVisible );
	}
	public ObjetoGrafico( java.net.URL urlImagenObjeto, boolean visible ) {
		this( urlImagenObjeto, visible, 10, 10 );
		if (icono != null) {  
			anchuraObjeto = icono.getIconWidth();
			alturaObjeto = icono.getIconHeight();
			setSize( anchuraObjeto, alturaObjeto );
			this.xFinChoque = anchuraObjeto; 
			this.yFinChoque = alturaObjeto;
		}
	}
	public void setVisible( boolean visible ) {
		super.setVisible( visible );
		esVisible = visible;
	} 
	public int getAnchuraObjeto() {
		return anchuraObjeto;
	}
	public int getAlturaObjeto() {
		return alturaObjeto;
	}
	public Rectangle getRectanguloInternoChoque() {
		return new Rectangle( xInicioChoque, yInicioChoque, xFinChoque, yFinChoque );
	}
	public void setRectanguloDeChoque( int xInicioChoque, int yInicioChoque, int xFinChoque, int yFinChoque ) {
		this.xInicioChoque = xInicioChoque;
		this.xFinChoque = xFinChoque; 
		this.yInicioChoque = yInicioChoque;
		this.yFinChoque = yFinChoque;
	}
	public boolean contains(Point p) {
		if (p==null) return false;
		return (p.getX()>=getX() && p.getX()<getX()+getWidth() &&
				p.getY()>=getY() && p.getY()<getY()+getHeight());
	}
	public boolean chocaCon( ObjetoGrafico o2, int margenPixels ) {
		boolean choca = !(getX()+xInicioChoque+margenPixels > o2.getX()+o2.xFinChoque ||
				getX()+xFinChoque-margenPixels < o2.getX()+o2.xInicioChoque ||
				getY()+yInicioChoque+margenPixels> o2.getY()+o2.yFinChoque ||
				getY()+yFinChoque -margenPixels < o2.getY()+o2.yInicioChoque);
		return choca;
	}	
	protected void paintComponent(Graphics g) {
		if (escalado) {
			Graphics2D g2 = (Graphics2D) g;  
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
	        g2.drawImage(imagenObjeto, 0, 0, anchuraObjeto, alturaObjeto, null);

        } else {  
			super.paintComponent(g);
		}
	}

	
}