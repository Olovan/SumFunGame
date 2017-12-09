package test;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import sumfun.RuleSet;
import sumfun.UntimedGame;

import static org.junit.Assert.assertTrue;

/** Tests the countNeighbors method in the RuleSet class.
 *  Inputs:
 *    int r       - The row of the coordinate being tested
 *    int c       - The column of the coordinate being tested
 * 
 *  Outputs:
 *    The amount of Neighbors as an integer
 **/

@RunWith(Parameterized.class)
public class testCountNeighbors {

	private static UntimedGame game = new UntimedGame();
	private static Method method = null;
	private int row;
	private int col;
	private int neighbors;
	
	//Setup Board
	static Integer[][] testBoard = new Integer[][]{
				{ null, null, null, null,    9, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{    4, null, null, null, null, null, null, null, null },
				{ null, null,    7,    1, null, null,    8,    5, null },
				{ null, null, null,    2, null, null, null, null,    1 },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null,    0, null, null },
				{ null, null, null, null, null,    3,    6,    9, null },
	};
	
	public testCountNeighbors(int row, int col, int neighbors) {
		this.row = row;
		this.col = col;
		this.neighbors = neighbors;
	}
	
	@Parameters(name = "{index} : countNeighbors")
	  public static Object[][] inputParameters() {
	    return new Object[][] {
	      {0, 4, 0},
	      {2, 0, 0},
	      {3, 3, 2},
	      {3, 7, 2},
	      {4, 8, 1},
	      {8, 6, 3}
	    };
	  }
	
	@BeforeClass
	//Manually assign the testBoard to the game and assign private method
	public static void setUp() {
	  game = new UntimedGame();
	  try {
	    Field boardField = RuleSet.class.getDeclaredField("board");
	    boardField.setAccessible(true);
	    boardField.set(game, testBoard);
	    method = RuleSet.class.getDeclaredMethod("countNeighbors", int.class, int.class);
	    method.setAccessible(true);
	  } catch(Exception e) {
	    e.printStackTrace();
	  }
	}
	
	 @Test
	  public void testIsValidCoordinate() {
	    try {
	      assertTrue((int) method.invoke(game, row, col) == neighbors);
	    } catch (Exception e) {
	      e.printStackTrace();
	      assertTrue(false);
	    }
	  }
}
