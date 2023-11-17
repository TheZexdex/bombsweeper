import java.util.*;

public class BombSquare extends GameSquare
{
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;

	GameSquare g = board.getSquareAt(xLocation, yLocation); //This instantiates a gamesquare for the purposes of gathering info at neighbouring squares. For now its just placed at the current position...
	BombSquare b = (BombSquare) g; //... before casting it to the bombsquare. Both are to be used

	private boolean revealed = false; //This boolean is set to true whenever a bombsquare is revealed. This is crucial to be used as a terminating case in the recursion algorithm

	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png", board);

		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
	}

	public void clicked()
	{
		if (thisSquareHasBomb == true) //If the square clicked on has a bomb, then it reveals it, sets the image to a bomb and does nothing else
		{
			setImage("images/bomb.png");
			revealed = true;
		}
		else //If the square does not have a bomb...
		{
			int bombs = countSurroundingBombs(xLocation, yLocation); //It calls a method that counts the surrounding bombs and sets the image to reflect the number. The method also returns the number of bombs as an int...
			if (bombs == 0)
			{
				checkSurroundingSquares(xLocation, yLocation); //... if the square had no neighbouring bombs, it calls a method to reveal the surrounding squares
			}
		}
	}
	public int countSurroundingBombs(int xP, int yP) //This method counts the surrounding bombs and sets the image to reflect the number. this method also returns the number of bombs as an int
	{
		int counter = 0; //This int variable is to keep track of the number of bombs, and is returned at the end of the method

		for (int y = -1; y <= 1; y++) //This double for loop through the eight neighbouring cells...
		{
			for (int x = -1; x <= 1; x++)
			{
				if (y != 0 || x != 0) //... with this if statement stopping the actual central calling cell being counted and affected. A try-catch statement also prevents any cells 'off the board' causing an exception
				{
					try
					{
						g = board.getSquareAt(xP + x, yP + y); //The following statements set the g and b gamesquare and bombsquare variables to the specific cell being checked
						b = (BombSquare) g;
						if (b.thisSquareHasBomb == true) //If the cell has a bomb, it increasing the counter by 1
						{
							counter = counter + 1;
						}
					}
					catch (Exception e) {}
				}
			}
		}

		g = board.getSquareAt(xP, yP); //The following statements resets the g and b gamesquare and bombsquare variables back to the central calling cell
		b = (BombSquare) g;

		b.revealed = true; //The calling cell is set to being revealed...
		g.setImage("images/" + counter + ".png"); //... and given the apropriate image relevant to the number of bombs it contains

		return counter; //Finally, The bomb counter is returned
	}
	public void checkSurroundingSquares(int xP, int yP) //This method reveals the surrounding squares if a calling cell has no neighbouring bombs
	{
		for (int y = -1; y <= 1; y++) //This double for loop through the eight neighbouring cells...
		{
			for (int x = -1; x <= 1; x++)
			{
				if (y != 0 || x != 0) //... with this if statement stopping the actual central calling cell being counted and affected. A try-catch statement also prevents any cells 'off the board' causing an exception
				{
					try
					{
						g = board.getSquareAt(xP + x, yP + y); //The following statements set the g and b gamesquare and bombsquare variables to the specific cell being checked
						b = (BombSquare) g;
						if (b.revealed == false) //If the square hasnt already been revealed, it will be checked. This is cruicial for preventing an overflow exception when the method is being called recursively
						{
							int bombs = countSurroundingBombs(xP + x, yP + y); //On the square being checked, it will count the number of neighbouring bombs and set its image to relevant number...
							if (bombs == 0) //...and if there are none, it will call the current method recusively to the current cell being checked
							{
								checkSurroundingSquares(xP + x, yP + y);
							}
						}
					}
					catch (Exception e) {}
				}
			}
		}
	}
}
