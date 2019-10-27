package objetosJuego;

public class GrupoObjetos {
	private Object[] aColumnas;
	private int numObjetos;
	private final int NUM_MAX_OPS = 9999;
	public GrupoObjetos() {
		aColumnas = new Object[NUM_MAX_OPS];
		numObjetos = 0;
	}
	public void anyadir( Object o ) {
		aColumnas[numObjetos] = o;
		numObjetos++;
	}
	public Object coger( int numOp ) {
		return aColumnas[numOp];
	}
	public Object quitar( int numOp ) {
		Object of = aColumnas[numOp];
		for (int i=numOp; i<numObjetos-1; i++)
			aColumnas[i] = aColumnas[i+1];
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
			ret += ( (i+1) + ": " + aColumnas[i].toString() + "\n" );
		}
		return ret;
	}

}
