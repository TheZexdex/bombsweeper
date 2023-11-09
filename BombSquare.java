import java.util.*;

public class BombSquare extends GameSquare
{
	public static final int MINE_PROBABILITY = 10;

	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png", board);

		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
	}

	public boolean returnBomb()
	{
		return(thisSquareHasBomb);
	}

	public void clicked()
	{
		System.out.println(xLocation);
		System.out.println(yLocation);
		if (thisSquareHasBomb)
		{
			System.out.println("has bomb");
			setImage("images/bomb.png");
		}
		else
		{
			System.out.println("no bomb");
			int counter = 0;
			for (int x = -1; x <= 1; x++)
			{
				for (int y = -1; y <= 1; y++)
				{
					if ((x != 0 || y != 0) && board.getSquareAt(xLocation + x, yLocation + y).thisSquareHasBomb == true)
					{
						counter = counter + 1;
					}
				}
			}
			setImage("images/" + counter + ".png");
		}
	}
}
