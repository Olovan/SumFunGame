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

/** Tests the isValidCoordinate function in the RuleSet class
 *  Inputs:
 *    int r       - The row of the coordinate being tested
 *    int c       - The column of the coordinate being tested
 *    BOARD_SIZE  - The dimensions of the board (Should always be 9 so it doesn't need to be assigned)
 *
 *  Outputs:
 *    boolean value
 *
 *  Test Cases:
 *    A case at the top row of the board                        ROW: 0 COL:3   EXPECTED: true
 *    A case at the bottom row of the board                     ROW: 8 COL:2   EXPECTED: true
 *    A case at the left column of the board                    ROW: 4 COL:0   EXPECTED: true
 *    A case at the right column of the board                   ROW: 7 COL:8   EXPECTED: true
 *    A case at the top left hand corner of the board           ROW: 0 COL:0   EXPECTED: true
 *    A case at the bottom right hand corner of the board       ROW: 8 COL:8   EXPECTED: true
 *    A case in the middle of the board                         ROW: 4 COL:6   EXPECTED: true
 *    A case with an invalid row (negative)                     ROW:-1 COL:4   EXPECTED: false
 *    A case with an invalid col (too big)                      ROW: 3 COL:12  EXPECTED: false
 **/
@RunWith(Parameterized.class)
public class TestIsValidCoordinate {
  
  @Parameters(name = "{index} : isValidCoordinate({0},{1}) = {2}")
  public static Object[][] inputParameters() {
    return new Object[][] {
      {0, 3, true},
      {8, 2, true},
      {4, 0, true},
      {7, 8, true},
      {0, 0, true},
      {8, 8, true},
      {4, 6, true},
      {-1, 4, false},
      {3, 12, false},
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
	
	public TestIsValidCoordinate(int row, int col, boolean expected) {
	  this.row = row;
	  this.col = col;
	  this.expected = expected;
	}
	
	@BeforeClass
	//Manually assign the testBoard to the game and assign private method
	public static void setUp() {
	  game = new UntimedGame();
	  try {
	    Field boardField = RuleSet.class.getDeclaredField("board");
	    boardField.setAccessible(true);
	    boardField.set(game, testBoard);
	    method = RuleSet.class.getDeclaredMethod("isValidCoordinate", int.class, int.class);
      method.setAccessible(true);
	  } catch(Exception e) {
	    e.printStackTrace();
	  }
	}

  @Test
  public void testIsValidCoordinate() {
    try {
      assertTrue((boolean) method.invoke(game, row, col) == expected);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
}
