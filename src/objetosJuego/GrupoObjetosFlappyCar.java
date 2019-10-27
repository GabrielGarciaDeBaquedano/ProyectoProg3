package objetosJuego;

public class GrupoObjetosFlappyCar {
	private ObjetoFlappyCar[] grupo;
	private int numObjetos;
	private final int NUM_MAX_OPS = 9999;
	public GrupoObjetosFlappyCar() {
		grupo = new ObjetoFlappyCar[NUM_MAX_OPS];
		numObjetos = 0;
	}
	public void anyadir( ObjetoFlappyCar o ) {
		grupo[numObjetos] = o;
		numObjetos++;
	}
	public ObjetoFlappyCar coger( int numOp ) {
		return grupo[numOp];
	}
	public ObjetoFlappyCar quitar( int numOp ) {
		ObjetoFlappyCar of = grupo[numOp];
		for (int i=numOp; i<numObjetos-1; i++)
			grupo[i] = grupo[i+1];
		numObjetos--;
		return of;
	}
	public int tamanyo() {
		return numObjetos;
	}

	public boolean esVacio() {
		return (numObjetos==0);
	}
	public String toString() {
		String ret = "";
		for (int i=0; i<numObjetos; i++) {
			ret += ( (i+1) + ": " + grupo[i].toString() + "\n" );
		}
		return ret;
	}

}
