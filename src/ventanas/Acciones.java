package ventanas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
 
public class Acciones implements ActionListener, MouseListener
{
	private Minesweeper mina;
 
	public Acciones(Minesweeper m)
	{
		mina = m;
	}
 
	public void actionPerformed(ActionEvent e)
	{	
		mina.reset();
 
		mina.refresh();
	}
 
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == 1)
		{
			int x = e.getX() / 20;
			int y = e.getY() / 20;
 
			mina.select(x, y);
		}
 
		if (e.getButton() == 3)
		{
			int x = e.getX() / 20;
			int y = e.getY() / 20;
 
			mina.mark(x, y);
		}
 
		mina.refresh();
	}
 
	public void mouseEntered(MouseEvent e)
	{
 
	}
 
	public void mouseExited(MouseEvent e)
	{
 
	}
 
	public void mousePressed(MouseEvent e)
	{
 
	}
 
	public void mouseReleased(MouseEvent e)
	{
 
	}
 
}