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
	
	// Score is returned based on how many tiles are removed and the boundary sum
	public int CalcScore(int row, int col, int placement, Integer[][] grid) {
		int boundarySum = 0;
		int tilesRemoved = 0;
		
		// Sums the 3x3 square with "placement" in the middle
		if (grid[row][col] == null) {
			for  (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (BoundaryCheck(row + i, col + j) == true) {
						boundarySum += grid[row + i][col + j];
						if (grid[row + i][col + j] != null)
							tilesRemoved++;
					}
				}
			}
		}
		
		// The algorithm above will include the middle number
		// We want to remove this for the next calculation
		boundarySum -= placement;
		
		
		if (boundarySum % 10 == placement) {
			if (tilesRemoved >= 3) {
				boundarySum += 10 * tilesRemoved;   
			}
			
			return boundarySum;
		}
		else
			return 0;
	}
	
	// This is ONLY called if the placement will remove the surrounding blocks
	// Returns the updated grid with removed spaces 
	public Integer[][] UpdateGrid(int row, int col, Integer[][] grid) {
		for  (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (BoundaryCheck(row + i, col + j) == true) {
					grid[row + i][col + j] = null;
				}
			}
		}
		
		return grid;
	}
	
	// Returns true if the row/column combination is within bounds
	// This can be changed later if the size of the board changes
	public boolean BoundaryCheck(int r, int c) {
		if (r >= 0 && r <= 8 && c >= 0 && c <= 8)
			return true;
		else
			return false;
	}
}
