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
import sumfun.UntimedGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** Tests the checkGameOver method in the UntimedGame class.
 *  Inputs:
 *    int movesRemaining       - The amount of moves remaining in an untimed game
 * 
 *  Outputs:
 *    enum sent to the observers of the RuleSet class that says that the current game is now over
 **/
@RunWith(Parameterized.class)
public class TestIsGameOver {

	private static UntimedGame game = new UntimedGame();
	private static Method method = null;
	private static int moves;
	private static Field currentGameStateField;
    private static Field movesRemainingField;
    private RuleSet.GameState expected;
	
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
	
	public TestIsGameOver(int moves, RuleSet.GameState expected) {
	  this.moves = moves;
	  this.expected = expected;
	  
	  try {
          movesRemainingField.set(game, moves);
          currentGameStateField.set(game, RuleSet.GameState.ACTIVE);
      } catch (Exception e) {
          e.printStackTrace();
      }
	}
	
	@Parameters(name = "{index} : checkGameOver")
	  public static Object[][] inputParameters() {
	    return new Object[][] {
	      {0, RuleSet.GameState.ENDED},
	      {5, RuleSet.GameState.ACTIVE}
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
		    movesRemainingField = UntimedGame.class.getDeclaredField("movesRemaining");
		    movesRemainingField.setAccessible(true);
		    movesRemainingField.set(game, moves);
		    currentGameStateField = RuleSet.class.getDeclaredField("currentState");
            currentGameStateField.setAccessible(true);
		    method = UntimedGame.class.getDeclaredMethod("checkGameOver");
	        method.setAccessible(true);
		  } catch(Exception e) {
		    e.printStackTrace();
		  }
		}
	
	@Test
	  public void testIsGameOver() {
	    try {
	      method.invoke(game);
	    	  assertTrue((RuleSet.GameState)currentGameStateField.get(game) ==  expected);
	    } catch (Exception e) {
	      e.printStackTrace();
	      assertTrue(false);
	    }
	  }
}
