package test;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import sumfun.RuleSet;
import sumfun.UntimedGame;

/** Tests the countNeighbors method in the RuleSet class.
 *  Inputs:
 *    int r       - The row of the coordinate being tested
 *    int c       - The column of the coordinate being tested
 * 
 *  Outputs:
 *    The amount of Neighbors as an integer
 *    
 *  Test Cases:
 *    Test case #1:
 *      We wanted to test the logic branch in which everything that can be false is forced for to false.
 *      We used a square filled with empty tiles.
 *    Test case #2:
 *      We wanted to test the logic branch in which the first for condition was true.
 *      Because this condition is automatically satisfied we picked a random test.
 *    Test case #3:
 *      We wanted to test the logic branch in which the second for condition was true. 
 *      Because this condition is automatically true we picked a random test.
 *    Test case #4:
 *      We wanted to test the logic branch in which the isOccupiedTile() condition was true so
 *       we picked a case in which every single surrounding tile was occupied.
 *    Test case #5:
 *     We wanted to test the logic in which i != 0  is true.
 *     This test case is automatically true regardless of input so we picked a random test
 *      in which we made sure there were a few occupied tiles surrounding the middle
 *      so that this condition would be evaluated and not short circuited and ignored.
 *    Test case #6:
 *     We wanted to test the logic in which j != 0 is true.
 *     This test is automatically true regardless of input so we picked a random test
 *      in which we made sure there were a few occupied tiles surrounding the middle 
 *      so that this condition would be evaluated and not short circuited and ignored.
 **/

@RunWith(Parameterized.class)
public class TestCountNeighbors {

	private static UntimedGame game = new UntimedGame();
	private static Method method = null;
	private int row;
	private int col;
	private int neighbors;
	
	//Setup Board
	static Integer[][] testBoard = new Integer[][]{
				{ null, null, null, null,    9, null, null, null, null },
				{ null,    7, null, null, null, null, null, null, null },
				{ null,    8, null, null, null, null, null, null, null },
				{ null,    4, null,    8,    1,    2, null,    5,   7 },
				{ null, null, null,    7,    0,    3, null,    3,   1 },
				{ null, null, null,    6,    5,    4, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null,    3, null, null },
				{ null, null, null, null, null, null,    6,    9, null },
	};
	
	public TestCountNeighbors(int row, int col, int neighbors) {
		this.row = row;
		this.col = col;
		this.neighbors = neighbors;
	}
	
	@Parameters(name = "{index} : countNeighbors")
	  public static Object[][] inputParameters() {
	    return new Object[][] {
	      {6, 1, 0},
	      {2, 1, 2},
	      {0, 4, 0},
	      {4, 4, 8},
	      {8, 6, 2},
	      {4, 7, 3}
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
