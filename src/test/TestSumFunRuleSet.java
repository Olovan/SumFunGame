package test;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

import org.junit.BeforeClass;
import org.junit.Test;
import sumfun.RuleSet;
import sumfun.UntimedGame;

import static org.junit.Assert.assertTrue;

/** Tests the functions in the SumFunRuleSet class */
public class TestSumFunRuleSet {

	//Parameters
	static Integer[][] testBoard = new Integer[][]{
		{ null, null, null, null,    9, null, null, null, null },
		{ null, null, null, null, null, null, null, null, null },
		{    4, null, null, null, null, null, null, null, null },
		{ null, null, null, null, null, null,    8, null, null },
		{ null, null, null,    2, null, null, null, null,    1 },
		{ null, null, null, null, null, null, null, null, null },
		{ null, null, null, null, null, null, null, null, null },
		{ null, null, null, null, null, null, null, null, null },
		{ null, null, null, null, null, null,    6, null, null },
	};

	private static UntimedGame game = new UntimedGame();
	
	@BeforeClass
	public static void setUp() {
	  game = new UntimedGame();
	  
	  //Manually assign the testBoard to the game
	  try {
	    Field boardField = RuleSet.class.getDeclaredField("board");
	    boardField.setAccessible(true);
	    boardField.set(game, testBoard);
	  } catch(Exception e) {
	    e.printStackTrace();
	  }
	}

	/** Tests the isOccupiedTile function 
	 *  Inputs:
	 *  	int r       - The row of the tile being tested
	 *  	int c       - The column of the tile being tested
	 *  	board       - The current board of the ruleset
	 *  	BOARD_SIZE  - The dimensions of the board (Should always be 9)
	 *
	 *  Outputs:
	 *  	boolean value
	 *
	 * Test Cases:
	 * 		A case with an empty space in the middle of the board            ROW:2 COL:4   EXPECTED: false
	 * 		A case with an occupied space on the left side of the board      ROW:2 COL:0   EXPECTED: true
	 * 		A case with an unoccupied space on the right side of the board   ROW:6 COL:8   EXPECTED: false
	 * 		A case with an unoccupied space on the top of the board          ROW:0 COL:5   EXPECTED: false
	 * 		A case with an occupied space on the bottom of the board         ROW:8 COL:6   EXPECTED: true
	 * 		A case with an unoccupied space in the bottom right corner       ROW:8 COL:8   EXPECTED: false
	 **/
	@Test
	public void testIsOccupiedTile() {
	  Method method = null;
		//Allow us to access the protected method
		try {
      method = RuleSet.class.getDeclaredMethod("isOccupiedTile", int.class, int.class);
      method.setAccessible(true);

      assertTrue((boolean) method.invoke(game, 2, 4) == false);
      assertTrue((boolean) method.invoke(game, 2, 0) == true);
      assertTrue((boolean) method.invoke(game, 6, 8) == false);
      assertTrue((boolean) method.invoke(game, 0, 5) == false);
      assertTrue((boolean) method.invoke(game, 8, 6) == true);
      assertTrue((boolean) method.invoke(game, 8, 8) == false);
		} catch(Exception e) {
		  e.printStackTrace();
		  assertTrue(false);
		}
	}

	/** Tests the isValidCoordinate function
	 * 	Inputs:
	 * 		int r       - The row of the coordinate being tested
	 * 		int c       - The column of the coordinate being tested
	 * 		BOARD_SIZE  - The dimensions of the board (Should always be 9)
	 *
	 * 	Outputs:
	 * 		boolean value
	 *
	 * 	Test Cases:
	 * 		A case at the top row of the board                        ROW: 0 COL:3   EXPECTED: true
	 * 		A case at the bottom row of the board                     ROW: 8 COL:2   EXPECTED: true
	 * 		A case at the left column of the board                    ROW: 4 COL:0   EXPECTED: true
	 * 		A case at the right column of the board                   ROW: 7 COL:8   EXPECTED: true
	 * 		A case at the top left hand corner of the board           ROW: 0 COL:0   EXPECTED: true
	 * 		A case at the bottom right hand corner of the board       ROW: 8 COL:8   EXPECTED: true
	 * 		A case in the middle of the board                         ROW: 4 COL:6   EXPECTED: true
	 * 		A case with an invalid row (negative)                     ROW:-1 COL:4   EXPECTED: false
	 * 		A case with an invalid col (too big)                      ROW: 3 COL:12  EXPECTED: false
	 **/
  @Test
  public void testIsValidCoordinate() {
    try {
      // Allow us to access the protected method
      Method method = RuleSet.class.getDeclaredMethod("isValidCoordinate", int.class, int.class);
      method.setAccessible(true);

      assertTrue((boolean) method.invoke(game, 0, 3) == true);
      assertTrue((boolean) method.invoke(game, 8, 2) == true);
      assertTrue((boolean) method.invoke(game, 4, 0) == true);
      assertTrue((boolean) method.invoke(game, 7, 8) == true);
      assertTrue((boolean) method.invoke(game, 0, 0) == true);
      assertTrue((boolean) method.invoke(game, 8, 8) == true);
      assertTrue((boolean) method.invoke(game, 4, 6) == true);
      assertTrue((boolean) method.invoke(game, -1, 4) == false);
      assertTrue((boolean) method.invoke(game, 3, 12) == false);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  /** Tests the countNeighbors function
   *  Inputs:
   *    int row
   *    int col
   *    Integer[][] board
   *    BOARD_SIZE           
   *    
   *  Outputs:
   *    int  -  the number of occupied tiles neighboring the selected tile
   *    
   *  Logical Paths
   *    4
   */
  @Test
  public void testCountNeighbors() {
    assertTrue(false);
  }
}
