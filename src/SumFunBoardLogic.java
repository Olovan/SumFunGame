import java.util.Random;

public class SumFunBoardLogic 
{
	private Controller c;
	private Random rand = new Random();
	private Integer counter;
	private int score = 0;
	
	/*
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
	*/
	
	private Integer[][] testGrid = createNewGrid(9, 9);
	
	private Integer[] queue = new Integer[5];
	
	public SumFunBoardLogic(Controller c) {
		this.c = c;
	}
	
	//generates a populated board with fixed values for now
	public Integer[][] populateBoard() {
		return testGrid;
	}
	
	//sets countdown for moves at the start of the game and later on, timer as well
	public String startCountdown() {
		counter = 50;
		
		return String.valueOf(counter);
	}
	
	//sets the countdown for moves and later on, timer as well
	public String setCountdown(Integer count) {	
		return String.valueOf(counter);
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
		// Ignores clicks on populated tiles
		if (testGrid[row][col] != null) {
			return;
		}
		
		counter--;
		c.setCountdown(setCountdown(counter));
		
		if(counter == 0) {
			return;
		}
		
		testGrid[row][col] = newQueueNumber();
		
		c.returnCoordinateValue(testGrid[row][col], row, col);

		calcScore(row, col);
	}
	
	public Integer[][] createNewGrid(int rows, int cols) {
		Integer[][] newGrid = new Integer[rows][cols];
		
		for (int i = 1; i < rows - 1; i++) {
			for (int j = 1; j < cols - 1; j++) {
				newGrid[i][j] = rand.nextInt(10);
			}
		}
		
		return newGrid;
	}
	
	// Score is returned based on how many tiles are removed and the boundary sum
	public void calcScore(int row, int col) {
		Integer boundarySum = boundarySum(row, col);
		int neighbors = countNeighbors(row, col);

		// Score is stored as a global variable
		if (boundarySum % 10 == testGrid[row][col]) {
			score += boundarySum + testGrid[row][col] + bonusPoints(neighbors, boundarySum);
		
			c.setScore(score);
		
			updateGrid(row, col);
		
			c.setGrid(testGrid);
		}
		
		checkGameOver();
	}
	
	public Integer boundarySum(int row, int col) {
		Integer boundarySum = 0;
		
		// Sums the 3x3 square with placement in the middle
		for  (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (isNumber(row + i, col + j) == true) {
					boundarySum += testGrid[row + i][col + j];
				}
			}
		}
		
		boundarySum -= testGrid[row][col];
		
		return boundarySum;
	}
	
	public int bonusPoints(int neighbors, int boundarySum) {
		int bonusPoints = 0;
		
		if (neighbors >= 3)
			bonusPoints = 10 * neighbors;
		
		return bonusPoints;
	}
	
	// Counts the number of neighbors of the placement that are not null
	public int countNeighbors(int row, int col) {
		int neighbors = 0;
		
		for  (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (isNumber(row + i, col + j) == true) {
					neighbors++;
				}
			}
		}
		
		neighbors--;
		
		return neighbors;
	}
	
	// This is ONLY called if the placement will remove the surrounding blocks
	// Returns the updated grid with removed spaces 
	public void updateGrid(int row, int col) {
		for  (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (isNumber(row + i, col + j) == true) {
					testGrid[row + i][col + j] = null;
				}
			}
		}
	}
	
	public void checkGameOver() {
		if (isBoardEmpty() == true || isBoardFull() == true) {
			c.gameOver();
			c.disableBoard();
		} 
	}
	
	public boolean isBoardEmpty() {
		int emptyCounter = 0;
		
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				if (testGrid[i][j] == null)
					emptyCounter++;
			}
		}
		
		if (emptyCounter == 8 * 8)
			return true;
		else
			return false;
	}
	
	public boolean isBoardFull() {
		int fullCounter = 0;
		
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				if (testGrid[i][j] != null)
					fullCounter++;
			}
		}
		
		if (fullCounter == 8 * 8)
			return true;
		else
			return false;
	}
	
	// Returns true if the row/column combination is within bounds
	// This can be changed later if the size of the board changes
	public boolean isNumber(int r, int c) {
		if (r >= 0 && r <= 8 && c >= 0 && c <= 8 && testGrid[r][c] != null)
			return true;
		else
			return false;
	}
}
