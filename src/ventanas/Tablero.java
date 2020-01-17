package ventanas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
 
public class Tablero extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Minesweeper mina;
	private Celda[][] celdas;
 
	public Tablero(Minesweeper m)
	{
		mina = m;
		celdas = mina.getCeldas();
 
		addMouseListener(new Acciones(mina));
 
		setPreferredSize(new Dimension(mina.getx() * 20, mina.gety() * 20));
	}
 
	public void paintComponent(Graphics g)
	{
		celdas = mina.getCeldas();
 
		for (int i = 0; i < mina.getx(); i++)
		{
			for (int j = 0; j < mina.gety(); j++)
			{
				Celda current = celdas[i][j];
 
				if (current.isMarcada())
				{
					if (current.isMina() && mina.isAcabado())
					{
						g.setColor(Color.ORANGE);
						g.fillRect(i * 20, j * 20, i * 20 + 20, j * 20 + 20);
						g.setColor(Color.BLACK);
 
						g.drawLine(i * 20, j * 20, i * 20 + 20, j * 20 + 20);
						g.drawLine(i * 20, j * 20 + 20, i * 20 + 20, j * 20);
					}
					else if (mina.isAcabado())
					{
						g.setColor(Color.GREEN);
						g.fillRect(i * 20, j * 20, i * 20 + 20, j * 20 + 20);
						g.setColor(Color.BLACK);
					}
					else
					{
						g.setColor(Color.YELLOW);
						g.fillRect(i * 20, j * 20, i * 20 + 20, j * 20 + 20);
						g.setColor(Color.BLACK);
					}
				}
				else if (current.isTapada())
				{
					g.setColor(Color.GRAY);
					g.fillRect(i * 20, j * 20, i * 20 + 20, j * 20 + 20);
					g.setColor(Color.BLACK);
				}
				else if (current.isMina())
				{
					g.setColor(Color.RED);
					g.fillRect(i * 20, j * 20, i * 20 + 20, j * 20 + 20);
					g.setColor(Color.BLACK);
					g.drawLine(i * 20, j * 20, i * 20 + 20, j * 20 + 20);
					g.drawLine(i * 20, j * 20 + 20, i * 20 + 20, j * 20);
				}
				else
				{
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(i * 20, j * 20, i * 20 + 20, j * 20 + 20);
					g.setColor(Color.BLACK);
				}
				if (!current.isTapada())
				{
					if (current.getNumber() == 1)
					{
						g.drawLine(i * 20 + 13, j * 20 + 5, i * 20 + 13, j * 20 + 9);	//3
						g.drawLine(i * 20 + 13, j * 20 + 11, i * 20 + 13, j * 20 + 15);	//6
					}
					else if (current.getNumber() == 2)
					{
						g.drawLine(i * 20 + 8, j * 20 + 4, i * 20 + 12, j * 20 + 4);	//2
						g.drawLine(i * 20 + 13, j * 20 + 5, i * 20 + 13, j * 20 + 9);	//3
						g.drawLine(i * 20 + 8, j * 20 + 10, i * 20 + 12, j * 20 + 10);	//4
						g.drawLine(i * 20 + 7, j * 20 + 11, i * 20 + 7, j * 20 + 15);	//5
						g.drawLine(i * 20 + 8, j * 20 + 16, i * 20 + 12, j * 20 + 16);	//7
					}
					else if (current.getNumber() == 3)
					{
						g.drawLine(i * 20 + 8, j * 20 + 4, i * 20 + 12, j * 20 + 4);	//2
						g.drawLine(i * 20 + 13, j * 20 + 5, i * 20 + 13, j * 20 + 9);	//3
						g.drawLine(i * 20 + 8, j * 20 + 10, i * 20 + 12, j * 20 + 10);	//4
						g.drawLine(i * 20 + 13, j * 20 + 11, i * 20 + 13, j * 20 + 15);	//6
						g.drawLine(i * 20 + 8, j * 20 + 16, i * 20 + 12, j * 20 + 16);	//7
					}
					else if (current.getNumber() == 4)
					{
						g.drawLine(i * 20 + 7, j * 20 + 5, i * 20 + 7, j * 20 + 9);		//1
						g.drawLine(i * 20 + 13, j * 20 + 5, i * 20 + 13, j * 20 + 9);	//3
						g.drawLine(i * 20 + 8, j * 20 + 10, i * 20 + 12, j * 20 + 10);	//4
						g.drawLine(i * 20 + 13, j * 20 + 11, i * 20 + 13, j * 20 + 15);	//6
					}
					else if (current.getNumber() == 5)
					{
						g.drawLine(i * 20 + 7, j * 20 + 5, i * 20 + 7, j * 20 + 9);		//1
						g.drawLine(i * 20 + 8, j * 20 + 4, i * 20 + 12, j * 20 + 4);	//2
						g.drawLine(i * 20 + 8, j * 20 + 10, i * 20 + 12, j * 20 + 10);	//4
						g.drawLine(i * 20 + 13, j * 20 + 11, i * 20 + 13, j * 20 + 15);	//6
						g.drawLine(i * 20 + 8, j * 20 + 16, i * 20 + 12, j * 20 + 16);	//7
					}
					else if (current.getNumber() == 6)
					{
						g.drawLine(i * 20 + 7, j * 20 + 5, i * 20 + 7, j * 20 + 9);		//1
						g.drawLine(i * 20 + 8, j * 20 + 4, i * 20 + 12, j * 20 + 4);	//2
						g.drawLine(i * 20 + 8, j * 20 + 10, i * 20 + 12, j * 20 + 10);	//4
						g.drawLine(i * 20 + 7, j * 20 + 11, i * 20 + 7, j * 20 + 15);	//5
						g.drawLine(i * 20 + 13, j * 20 + 11, i * 20 + 13, j * 20 + 15);	//6
						g.drawLine(i * 20 + 8, j * 20 + 16, i * 20 + 12, j * 20 + 16);	//7
					}
					else if (current.getNumber() == 7)
					{
						g.drawLine(i * 20 + 8, j * 20 + 4, i * 20 + 12, j * 20 + 4);	//2
						g.drawLine(i * 20 + 13, j * 20 + 5, i * 20 + 13, j * 20 + 9);	//3
						g.drawLine(i * 20 + 13, j * 20 + 11, i * 20 + 13, j * 20 + 15);	//6
					}
					else if (current.getNumber() == 8)
					{
						g.drawLine(i * 20 + 7, j * 20 + 5, i * 20 + 7, j * 20 + 9);		//1
						g.drawLine(i * 20 + 8, j * 20 + 4, i * 20 + 12, j * 20 + 4);	//2
						g.drawLine(i * 20 + 13, j * 20 + 5, i * 20 + 13, j * 20 + 9);	//3
						g.drawLine(i * 20 + 8, j * 20 + 10, i * 20 + 12, j * 20 + 10);	//4
						g.drawLine(i * 20 + 7, j * 20 + 11, i * 20 + 7, j * 20 + 15);	//5
						g.drawLine(i * 20 + 13, j * 20 + 11, i * 20 + 13, j * 20 + 15);	//6
						g.drawLine(i * 20 + 8, j * 20 + 16, i * 20 + 12, j * 20 + 16);	//7
					}
				}
				g.setColor(Color.BLACK);
				g.drawRect(i * 20, j * 20, i * 20 + 20, j * 20 + 20);
			}
		}
	}
}