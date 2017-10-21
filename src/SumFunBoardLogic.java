
public class SumFunBoardLogic 
{
	private Controller c;
	
	private Integer[][] testGrid = {
			new Integer[]{null, null, null, null, null, null, null, null, null},
			new Integer[]{null, 1, 2, 3, 4, 5, 6, 7, null},
			new Integer[]{null, 2, 3, 4, 5, 6, 7, 1, null},
			new Integer[]{null, 3, 4, 5, 6, 7, 1, 2, null},
			new Integer[]{null, 4, 5, 6, 7, 1, 2, 3, null},
			new Integer[]{null, 5, 6, 7, 1, 2, 3, 4, null},
			new Integer[]{null, 6, 7, 1, 2, 3, 4, 5, null},
			new Integer[]{null, 7, 1, 2, 3, 4, 5, 6, null},
			new Integer[]{null, null, null, null, null, null, null, null, null}
	};
	
	public SumFunBoardLogic(Controller c) {
		this.c = c;
	}
	
	public Integer[][] populateBoard() {
		return testGrid;
	}
	
	public void passValue(int row, int col) {
		//Quentin will need to implement a function that grabs the next value in the queue
		//for now will just return a 7
		
		testGrid[row][col] = 7;
		
		c.returnCoordinateValue(testGrid[row][col], row, col);
	}
}
