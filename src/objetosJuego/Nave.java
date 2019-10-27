package objetosJuego;

import utils.BoundingCircle;
import utils.Chocable;
import utils.Envolvente;
import utils.Rotable;

/** Clase que permite crear y gestionar naves y dibujarlas en una ventana gráfica
 */
public class Nave extends ObjetoJuego implements Rotable, Chocable{
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================

	double aceleracion, deceleracion, Vx, Vy;
	boolean girandoDerecha, girandoIzquierda, acelerando, activa;
	final int radio = 12;
	int delayDisp, delayRestante;
	private final BoundingCircle BC_NAVE = new BoundingCircle( x, y, radio );

	/** Crea una nueva nave
	 * @param x	Coordenada x del centro de la nave (en píxels)
	 * @param y	Coordenada y del centro de la nave (en píxels)
	 */
	public Nave( double x, double y, double rot, double aceleracion, double deceleracion, int delayDisp) {
		super( x, y, rot );
		this.aceleracion = aceleracion;
		this.deceleracion = deceleracion;
		this.delayDisp = delayDisp;
		Vx=0; 
		Vy=0;
		girandoDerecha=false; 
		girandoIzquierda=false;
		acelerando=false; 
		activa=false;
		delayRestante = 0;
	}
		
	public boolean isAcelerando() {
		return acelerando;
	}

	public void setAcelerando(boolean acelerando) {
		this.acelerando = acelerando;
	}
	
	public double getY(){
		return y;
		}
	
	public double getX(){
		return x;
		}
	
	public boolean isActiva(){
		return activa;
		}

	public void rota( double rot ) {
		setRot(this.rot += rot);
	
		
	}
	
	public void setX(double x){
		this.x=x;
	}
	
	public void setY(float y){
		this.y=y;
	}
	
	@Override
	public void mueve(double segs) {
		super.mueve(segs);
		if(delayRestante>0) 
			 delayRestante--; 
		if(acelerando){ 
			 Vx+=aceleracion*Math.cos(rot);
			 Vy+=aceleracion*Math.sin(rot);
			 }
			BC_NAVE.setRadio(radio);
			x+=Vx; 
			BC_NAVE.setX(x);
			y+=Vy;
			BC_NAVE.setY(y);
			Vx*=deceleracion; 
			Vy*=deceleracion; 
			if(x<0) 
			 x+=1200; 
			else if(x>1200)
			 x-=1200;
			if(y<0)
			 y+=600;
			else if(y>600)
			 y-=600;
			
	}
	
	public double getRadio(){
		return radio; 
		}
	

	/** Comprueba si la nave es igual a otro objeto dado. Se entiende que dos naves son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Nave) {
			Nave p2 = (Nave) obj;  // Cast de obj a nave2 (lo entenderemos mejor al ver herencia)
			return p2.rot==rot && Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de las naves this y p2
		} else {
			return false;
		}
	}
		

	@Override
	public void dibuja(utils.VentanaGrafica v) {
		String imagen = "/img/nave.png";
		String imagenAcelerando = "/img/nave-prop.png";
		if(acelerando){ // dibuja propulsion		
			v.dibujaImagen( imagenAcelerando, x, y, 50.0/500.0, rot, 1.0f );  // el gráfico tiene 500 píxels y la nave quiere dibujarse con 50
	}
		else{
			v.dibujaImagen( imagen, x, y, 50.0/500.0, rot, 1.0f );  // el gráfico tiene 500 píxels y la nave quiere dibujarse con 50
		}
	}
	
	public boolean puedeDisparar(){
		if(delayRestante>0) 
		 return false; 
		else
		 return true;
	}
	
	public Disparo dispara(){
		delayRestante=delayDisp;
		return new Disparo(x,y,rot,Vx,Vy,true);
	}

	@Override
	public Envolvente getEnvolvente() {
		return BC_NAVE;
	}
	
	public void chocaCon( Chocable choc ) {
		if (choc instanceof Asteroide) {
			Asteroide a = ((Asteroide)choc);
		}
	}
	
	@Override
	public String toString() {
		return x+" "+y+" "+radio;
	}
	
}
