package ventanas;
public class MainBuscaminas
{
	public static void main (String[] args)
	{
		int x = 10;		//Width of the board
		int y = 10;		//Height of the board
		int d = 25;		//The difficulty of the game, the percentage of mines in the board. The number of mines per board is random, but this number is the probability that a cell will become
					//a mine.
 
		new Minesweeper(x, y, d);
	}
}