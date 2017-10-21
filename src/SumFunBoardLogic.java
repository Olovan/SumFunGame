import java.util.Random;

public class SumFunBoardLogic 
{
	private Controller c;
	private Random rand = new Random();
	private String counter;
	
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
	
	private Integer[] queue = new Integer[5];
	
	public SumFunBoardLogic(Controller c) {
		this.c = c;
	}
	
	//generates a populated board with fixed values for now
	public Integer[][] populateBoard() {
		return testGrid;
	}
	
	//sets countdown for moves and later on, timer as well
	public String startCountdown() {
		counter = "50";
		
		return counter;
	}
	
	public String setCountdown(Integer count) {
		counter = String.valueOf(count);
		
		return counter;
	}
	
	//generates a populated queue with random values
	public Integer[] populateQueue() {
		for(int i = 0; i < queue.length; i++) {
			queue[i] = rand.nextInt(10);
		}
		return queue;
	}
	
	//this will push the top number from the queue and pass it along,
	//it will then generate a new random number for the queue
	//proceeds to return the top value from the queue
	public Integer newQueueNumber() {
		Integer answer;
		
		answer = queue[0];
		
		for(int i = 0; i < queue.length; i++) {
			if(i == 4) {
				queue[4] = rand.nextInt(10);
			}
			else {
				queue[i] = queue[i+1];
			}
		}
		
		return answer;
	}
	
	//returns current state of the queue
	public Integer[] passQueue() {
		return queue;
	}
	
	//calls the newQueueNumber() method to pass the top number of the current queue
	//decreases counter and if it equals 0, stop the game
	public void passValue(int row, int col) {
		Integer count;
		count = Integer.parseInt(counter);
		count--;
		c.setCountdown(setCountdown(count));
		
		if(count == 0) {
			return;
		}
		testGrid[row][col] = newQueueNumber();
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
