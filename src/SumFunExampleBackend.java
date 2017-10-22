import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class SumFunExampleBackend {
	//Settings
	private final static int NUM_ROWS = 9;
	private final static int NUM_COLS = 9;

	//members
	private Integer[][] grid;
	private List<Integer> queue;
	private int score;
	private int movesRemaining;
	private Controller controller;
	private Random rand;

	public SumFunExampleBackend(Controller c) {
		controller = c;
		grid = new Integer[NUM_ROWS][NUM_COLS]; //By defeault filled with null
		queue = new ArrayList<Integer>(5);
		rand = new Random();
		score = 0;
		movesRemaining = 0;
	}

	//PUBLIC FUNCTIONS
	public void startGame() {
		score = 0;
		movesRemaining = 50;
		resetQueue();
		generateNewGrid();

		controller.setGrid(grid);
		controller.setScore(score);
		controller.setCountdown("" + movesRemaining);
		controller.setQueue(queueToArray());
	}

	public void resetQueue() {
		queue.clear();
		refillQueue();
	}

	public void refillQueue() {
		while(queue.size() < 5)
			queue.add(rand.nextInt(10));
	}

	public void gridAction(int row,int col) {
		if(grid[row][col] != null)
			return;

		placeTile(row, col);
		if(movesRemaining <= 0)
			controller.gameOver();
	}

	//PRIVATE FUNCTIONS
	private Integer[] queueToArray() {
		Integer[] queueArray = new Integer[5];
		queue.toArray(queueArray);
		return queueArray;
	}

	private void generateNewGrid() {
		grid = new Integer[NUM_ROWS][NUM_COLS];
		for(int i = 1; i < NUM_ROWS - 1; i++)
			for(int j = 1; j < NUM_COLS - 1; j++)
			   grid[i][j] = rand.nextInt(10);	
	}

	private void placeTile(int row, int col) {
		movesRemaining--;
		grid[row][col] = queue.remove(0);
		refillQueue();

		//DEBUG
		System.out.println("Sum of Surrounding Tiles: " + sumSurroundingTiles(row, col));

		if(isScoringMove(grid[row][col], row, col)) {

			int points = calculateScore(row, col);
			clearSurroundingTiles(row, col);
			controller.setGrid(grid);
			addScore(points);
		}
		
		controller.setCountdown("" + movesRemaining);
		controller.setGrid(grid);
		controller.setQueue(queueToArray());
	}

	private void addScore(int points) {
		score += points;
		controller.setScore(score);
	}

	private int calculateScore(int row, int col) {
		int score = sumSurroundingTiles(row, col);
		int count = countSurroundingTiles(row, col);

		score += grid[row][col];
		if(count >= 3)
			score += 10 * count;

		return score;
	}

	private boolean isScoringMove(int value, int row, int col) {
		int sum;
		//Add up the sum of the surrounding tiles
		sum = sumSurroundingTiles(row, col);

		if ((sum % 10) == grid[row][col])
			return true;
		return false;
	}

	private int sumSurroundingTiles(int row, int col) {
		int sum = 0;
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if(isValidRowIndex(row + i) && isValidColIndex(col + j) && (i != 0 || j != 0) && grid[row + i][col + j] != null)
					sum += grid[row + i][col + j];
			}
		}
		return sum;
	}

	private int countSurroundingTiles(int row, int col) {
		int count = 0;
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if(isValidRowIndex(row + i) && isValidColIndex(col + j) && (i != 0 || j != 0) && grid[row + i][col + j] != null)
					count++;
			}
		}

		return count;
	}

	private void clearSurroundingTiles(int row, int col) {
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if(isValidRowIndex(row + i) && isValidColIndex(col + j))
					grid[row + i][col + j] = null;
			}
		}
	}


	private boolean isValidRowIndex(int index) {
		if(index >= 0 && index < NUM_ROWS)
			return true;
		return false;
	}

	private boolean isValidColIndex(int index) {
		if(index >= 0 && index < NUM_COLS)
			return true;
		return false;
	}
}
