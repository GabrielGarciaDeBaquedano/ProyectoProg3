package objetosJuego;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ObjetoGraficoRotable extends ObjetoGrafico {
	protected double radsRotacion = 0;  
	private static final long serialVersionUID = 1L; 
	public ObjetoGraficoRotable( String nombreImagenObjeto, boolean visible, int anchura, int altura, double rotacion ) {
		super( nombreImagenObjeto, visible, anchura, altura );
		radsRotacion = rotacion;
	} 
	public ObjetoGraficoRotable( String nombreImagenObjeto, boolean visible, double rotacion ) {
		super( nombreImagenObjeto, visible );
		radsRotacion = rotacion;
	} 
	public ObjetoGraficoRotable( java.net.URL urlImagenObjeto, boolean visible, int anchura, int altura, double rotacion ) {
		super( urlImagenObjeto, visible, anchura, altura );
		radsRotacion = rotacion;
	} 
	public ObjetoGraficoRotable( java.net.URL urlImagenObjeto, boolean visible, double rotacion ) {
		super( urlImagenObjeto, visible );
		radsRotacion = rotacion;
	}
	public void setRotacion( double rotacion ) {
		radsRotacion = rotacion;
		repaint();
	} 
	public double getRotacion() {
		return radsRotacion;
	} 
	public void setRotacionGrados( double rotacion ) {
		radsRotacion = rotacion / 180 * Math.PI;
		repaint();
	}
 
	public double getRotacionGrados() {
		return radsRotacion / Math.PI * 180;
	}
	@Override
	protected void paintComponent(Graphics g) {
		if (escalado) {
			Graphics2D g2 = (Graphics2D) g;  
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
            g2.rotate( radsRotacion, anchuraObjeto/2, alturaObjeto/2 );
	        g2.drawImage(imagenObjeto, 0, 0, anchuraObjeto, alturaObjeto, null);
	       
        } else { 
			Graphics2D g2 = (Graphics2D) g; 
            g2.rotate( radsRotacion, anchuraObjeto/2, alturaObjeto/2 );
	        g2.drawImage(imagenObjeto, 0, 0, anchuraObjeto, alturaObjeto, null);
	        
		}
	}
	
}
