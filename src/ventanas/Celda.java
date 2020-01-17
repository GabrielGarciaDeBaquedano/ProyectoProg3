package ventanas;
public class Celda
{
	private boolean isMina, isMarcada, isTapada;
	private int number;
 
	public Celda()
	{
		isMina = false;
		isMarcada = false;
		isTapada = true;
		number = 0;
	}
 
	public void setMina()
	{
		isMina = true;
	}
 
	public void marca()
	{
		isMarcada = true;
	}
 
	public void desmarca()
	{
		isMarcada = false;
	}
 
	public void revela()
	{
		isTapada = false;
	}
 
	public void setNumber(int i)
	{
		number = i;
	}
 
	public boolean isMina()
	{
		return isMina;
	}
 
	public boolean isMarcada()
	{
		return isMarcada;
	}
 
	public boolean isTapada()
	{
		return isTapada;
	}
 
	public int getNumber()
	{
		return number;
	}
}