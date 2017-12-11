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

/** Tests the isOccupiedTile function in the RuleSet class
 *  Inputs:
 *    int r       - The row of the tile being tested
 *    int c       - The column of the tile being tested
 *    board       - The current board of the ruleset
 *    BOARD_SIZE  - The dimensions of the board (Should always be 9)
 *
 *  Outputs:
 *    boolean value
 *
 * Test Cases:
 *    A case with an empty space in the middle of the board            ROW:2 COL:4   EXPECTED: false
 *    A case with an occupied space on the left side of the board      ROW:2 COL:0   EXPECTED: true
 *    A case with an unoccupied space on the right side of the board   ROW:6 COL:8   EXPECTED: false
 *    A case with an unoccupied space on the top of the board          ROW:0 COL:5   EXPECTED: false
 *    A case with an occupied space on the bottom of the board         ROW:8 COL:6   EXPECTED: true
 *    A case with an unoccupied space in the bottom right corner       ROW:8 COL:8   EXPECTED: false
 **/
@RunWith(Parameterized.class)
public class TestIsOccupiedTile {
  @Parameters(name = "{index} : isOccupiedTile({0},{1}) = {2}")
  public static Object[][] inputParameters() {
    return new Object[][] {
      {2, 4, false},
      {2, 0, true},
      {6, 8, false},
      {0, 5, false},
      {8, 6, true},
      {8, 8, false},
    };
  }

	//Setup Board
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
	private static Method method = null;
	private int row;
	private int col;
	private boolean expected;
	
	public TestIsOccupiedTile(int row, int col, boolean expected) {
		this.row = row;
		this.col = col;
		this.expected = expected;
	}
	
	@BeforeClass
	//Manually assign the testBoard to the game and get reference to private method
	public static void setUp() {
	  game = new UntimedGame();
	  
	  try {
	    Field boardField = RuleSet.class.getDeclaredField("board");
	    boardField.setAccessible(true);
	    boardField.set(game, testBoard);
      	method = RuleSet.class.getDeclaredMethod("isOccupiedTile", int.class, int.class);
      	method.setAccessible(true);
	  } catch(Exception e) {
	    e.printStackTrace();
	  }
	}

	@Test
	public void runTest() {
		try {
			assertTrue((boolean) method.invoke(game, row, col) == expected);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
