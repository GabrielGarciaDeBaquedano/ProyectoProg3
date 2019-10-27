package objetosJuego;

import utils.BoundingCircle;
import utils.Chocable;
import utils.Envolvente;
import utils.Rotable;
import utils.VentanaGrafica;

/** Clase que permite crear y gestionar asteroides y dibujarlos en una ventana gráfica
 */
public class Asteroide extends ObjetoJuego implements Rotable, Chocable{
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double radio, VMin, VMax, Vx, Vy;  
	private int imp_rest, numDiv;
	private final BoundingCircle BC_AST = new BoundingCircle( x, y, radio );
	
	/** Crea un nuevo asteroide
	 * @param radio	Píxels de radio del asteroide (debe ser mayor que 0)
	 * @param x	Coordenada x del centro del asteroide (en píxels)
	 * @param y	Coordenada y del centro del asteroide (en píxels)
	 * @param rot Angulo de rotacion del asteroide
	 * @param VMin Velocidad minima del asteroide
	 * @param VMax Velocidad maxima del asteroide
	 * @param imp_rest Impactos restantes para destruir el asteroide
	 * @param numDiv Numero de asteroides en los que el asteroide se dividira
	 */
	public Asteroide( double radio, double x, double y, double rot, double VMin,
			 double VMax,int imp_rest ,int numDiv ) {
		super( x, y, rot );
		this.radio = radio;
		this.VMin = VMin;
		this.VMax = VMax;
		this.imp_rest = imp_rest;
		this.numDiv = numDiv;
		 //calcula direccion aleatoria y velocidad aleatoria entre la minima y la maxima
		 double V=VMin + Math.random()*(VMax-VMin),
		 dir=2*Math.PI*Math.random(); 
		 Vx=V*Math.cos(dir);
		 Vy=V*Math.sin(dir);
	}
	
	public void mueve(double segs){
		super.mueve(segs);
		 BC_AST.setRadio(radio);
		 x+=Vx;
		 BC_AST.setX(x);
		 y+=Vy;
		 BC_AST.setY(y);
		 //Control de salida de pantalla
		 if(x<5) 
			 x+=1200; 
		 else if(x>1205)
			 x-=1200;
		 if(y<5)
			 y+=600;
		 else if(y>605)
			 y-=600;
		 }
	
	public void setX(double x){
		this.x=x;
	}
	
	public void setY(double y){
		this.y=y;
	}
	
	public double getRadio() {
		return radio;
	}
	
	public int getImp_rest(){
		return imp_rest;
	}

	public Asteroide creaAsteroideDividido(double vMin,
			 double vMax){
			
			return new Asteroide(radio/Math.sqrt(numDiv),x,y,1.0,
			 vMin,vMax,imp_rest-1,numDiv);
			}
	
	/** Cambia el radio del asteroide. Debe ser mayor que cero
	 * @param radio	Nuevo radio del asteroide (debe ser mayor que cero)
	 */
	public void setRadio(double radio) {
		this.radio = radio;
	}

	/** Dibuja el asteroide en una ventana
	 * @param v	Ventana en la que dibujar el asteroide
	 */
	@Override
	public void dibuja( utils.VentanaGrafica v ) {
		v.dibujaImagen( "/img/asteroid.png", x, y, radio/250, rot, 1.0f );  // el gráfico tiene 500 píxels
	}

	/** Rota el asteroide
	 * @param rot	Ángulo a rotar (en radianes)
	 */
	public void rota( double rot ) {
		this.rot += rot;
	}
	
	
	/** Comprueba si el asteroide se sale completamente por el lado izquierdo de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo completamente por izquierda, false en caso contrario
	 */
	public boolean seSalePorLaIzquierda( VentanaGrafica v ) {
		return x+radio<=0;
	}
	
	/** Comprueba si el asteroide es igual a otro objeto dado. Se entiende que dos asteroides son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Asteroide) {
			Asteroide p2 = (Asteroide) obj;  // Cast de obj a asteroide2 (lo entenderemos mejor al ver herencia)
			return p2.radio==radio && Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de los asteroides this y p2
		} else {
			return false;
		}
	}
	public boolean choqueNave(Nave nave, VentanaGrafica vent ){
			
			if(Math.pow(radio+nave.getRadio(),2) > Math.pow(nave.getX()-x,2)+
			 Math.pow(nave.getY()-y,2))
			 return true;
			else
			return false;
			}

	@Override
	public Envolvente getEnvolvente() {
		return BC_AST;
	}
	
	@Override
	public String toString() {
		return x+" "+y+" "+radio;
	}
}
