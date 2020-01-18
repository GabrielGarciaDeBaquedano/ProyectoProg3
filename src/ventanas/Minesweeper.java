package ventanas;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
 
public class Minesweeper extends JFrame
{
	private static final long serialVersionUID = 1L;
	private int anchura, altura;
	private Celda[][] celdas;
	private int dificultad;
	private Tablero tablero;
	private JButton reset;
	private boolean acabado;
 
	public Minesweeper(int x, int y, int d)
	{
		anchura = x;
		altura = y;
		dificultad = d;
		celdas = new Celda[anchura][altura];
 
		reset();
 
		tablero = new Tablero(this);
		reset = new JButton("Reset");
 
		add(tablero, BorderLayout.CENTER);
		add(reset, BorderLayout.SOUTH);
 
		reset.addActionListener(new Acciones(this));
 
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
	}
 
	public int getx()
	{
		return anchura;
	}
 
	public int gety()
	{
		return altura;
	}
 
	public Celda[][] getCeldas()
	{
		return celdas;
	}
 
	public void reset()
	{
		Random random = new Random();
		acabado = false;
 
		for (int i = 0; i < anchura; i++)
		{
			for (int j = 0; j < altura; j++)
			{
				Celda c = new Celda();
				celdas[i][j] = c;
				int r = random.nextInt(100);
 
				if (r < dificultad)
				{
					celdas[i][j].setMina();
				}
			}
		}
		setNumbers();
	}
 
	private void setNumbers()
	{
		for (int i = 0; i < anchura; i++)
		{
			for (int j = 0; j < altura; j++)
			{
				int count = 0;
 
				if (i > 0 &&	j > 0 && celdas[i - 1]	[j - 1]	.isMina()) count++;
				if (j > 0 && celdas[i][j - 1].isMina()) count++;
				if (i < anchura - 1 && j > 0 && celdas[i + 1][j - 1].isMina()) count++;
 
				if (i > 0 && celdas[i - 1][j].isMina()) count++;
				if (i < anchura - 1 && celdas[i + 1][j].isMina()) count++;
 
				if (i > 0 && j < altura - 1 && celdas[i - 1][j + 1].isMina()) count++;
				if (j < altura - 1	&& celdas[i] [j + 1].isMina()) count++;
				if (i < anchura - 1 && j < altura - 1 && celdas[i + 1][j + 1].isMina()) count++;
 
				celdas[i][j].setNumber(count);
 
				if (celdas[i][j].isMina())
				{
					celdas[i][j].setNumber(-1);
				}
 
				if (celdas[i][j].getNumber() == 0)
				{
					//celdas[i][j].revela();
				}
			}
		}
 
		for (int i = 0; i < anchura; i++)
		{
			for (int j = 0; j < altura; j++)
			{
//				if (i > 0 &&	j > 0 && celdas[i - 1][j - 1].getNumber() == 0) celdas[i][j].revela();
//				if (j > 0 && celdas[i][j - 1].getNumber() == 0) celdas[i][j].revela();
//				if (i < anchura - 1 && j > 0 && celdas[i + 1][j - 1].getNumber() == 0) celdas[i][j].revela();
// 
//				if (i > 0 && celdas[i - 1][j].getNumber() == 0) celdas[i][j].revela();
//				if (i < anchura - 1 && celdas[i + 1]	[j]		.getNumber() == 0) celdas[i][j].revela();
// 
//				if (i > 0 && j < altura - 1 && celdas[i - 1][j + 1].getNumber() == 0) celdas[i][j].revela();
//				if (j < altura - 1 && celdas[i][j + 1].getNumber() == 0) celdas[i][j].revela();
//				if (i < anchura - 1 && j < altura - 1 && celdas[i + 1][j + 1]	.getNumber() == 0) celdas[i][j].revela();
			}
		}
	}
 
	public void refresh()
	{
		tablero.repaint();
	}
 
	public void select(int x, int y)
	{
		if (celdas[x][y].isMarcada()) return;
		//celdas[x][y].revela();
		resetMarks();
		refresh();
		if (celdas[x][y].getNumber()==0) busquedaRecursivaCeros(x, y);
		if (celdas[x][y].getNumber()!=0) celdas[x][y].revela();
		if (celdas[x][y].isMina())
		{
			loose();
		}
		else if (won())
		{
			win();
		}
	}
 
	private void busquedaRecursivaCeros(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9) return; // check for bounds

           if ( celdas[x][y].getNumber() == 0 && celdas[x][y].isTapada()) {
               celdas[x][y].revela();
               busquedaRecursivaCeros( x+1, y );
               busquedaRecursivaCeros( x+1, y+1);
               busquedaRecursivaCeros( x-1, y );
               busquedaRecursivaCeros( x-1, y-1);
               busquedaRecursivaCeros( x, y-1 );
               busquedaRecursivaCeros( x+1, y-1 );
               busquedaRecursivaCeros( x, y+1 );
               busquedaRecursivaCeros( x-1, y+1 );
           } else if(celdas[x][y].getNumber() != 0 && celdas[x][y].isTapada()){
               celdas[x][y].revela();
           }
        }
	
	private void loose()
	{
		acabado = true;
		for (int i = 0; i < anchura; i++)
		{
			for (int j = 0; j < altura; j++)
			{
				if (!celdas[i][j].isTapada()) celdas[i][j].desmarca();
				celdas[i][j].revela();
			}
		}
		refresh();
		JOptionPane.showMessageDialog(null, "BOOOOM!");
		reset();
	}
 
	private void win()
	{
		acabado = true;
		for (int i = 0; i < anchura; i++)
		{
			for (int j = 0; j < altura; j++)
			{
				celdas[i][j].revela();
				if (!celdas[i][j].isMina()) celdas[i][j].desmarca();
			}
		}
 
		refresh();
		JOptionPane.showMessageDialog(null, "Congratulations! You won!");
		reset();
	}
 
	private boolean won()
	{
		for (int i = 0; i < anchura; i++)
		{
			for (int j = 0; j < altura; j++)
			{
				if (celdas[i][j].isTapada() && !celdas[i][j].isMina())
				{
					return false;
				}
			}
		}
 
		return true;
	}
 
	public void mark(int x, int y)
	{
		if (celdas[x][y].isMarcada()) celdas[x][y].desmarca();
		else if (celdas[x][y].isTapada()) celdas[x][y].marca();
 
		resetMarks();
	}
 
	private void resetMarks()
	{
		for (int i = 0; i < anchura; i++)
		{
			for (int j = 0; j < altura; j++)
			{
				if (!celdas[i][j].isTapada()) celdas[i][j].desmarca();
			}
		}
	}
 
	public boolean isAcabado()
	{
		return acabado;
	}
}