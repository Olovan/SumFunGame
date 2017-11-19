import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

// This class contains the base ruleset for the game which includes things like tile placement logic and 
// basic gameover logic
public abstract class SumFunRuleSet extends Observable {
	protected enum GameState { ACTIVE, ENDED }

	//SETTINGS for easy tweaking
	protected static final int BOARD_SIZE = 9;
	protected static final int QUEUE_SIZE = 5;

	protected GameState currentState;
	protected Random rand;
	protected ArrayList<Integer> queue;
	protected int score;
	protected int scoreFromLastAction;
	protected Integer[][] board;

	/** Instatiates variables */
	public SumFunRuleSet() {
		rand = new Random();
		queue = new ArrayList<Integer>(QUEUE_SIZE);
		board = new Integer[BOARD_SIZE][BOARD_SIZE];
	}

	public void startGame() {
		queue.clear(); //In case the queue has crap in it
		fillQueue();
		score = 0;
		board = generateRandomBoard();
		currentState = GameState.ACTIVE;
		sendDataToObservers("ALL");
	}

	//Set board and queue so that the game can be won in 1 move
	public void enableCheats() {
		board = new Integer[][]{
			{ null, null, null, null, null, null, null, null, null }, 
				{ null, null, null, null, null, null, null, null, null }, 
				{ null, null, null, null, null, null, null, null, null }, 
				{ null, null, null, null, null, null, null, null, null }, 
				{ null, null, null, null, 1, null, null, null, null }, 
				{ null, null, null, null, null, null, null, null, null }, 
				{ null, null, null, null, null, null, null, null, null }, 
				{ null, null, null, null, null, null, null, null, null }, 
				{ null, null, null, null, null, null, null, null, null } 
		};
		queue.set(0, 1);
		sendDataToObservers("ALL");
	}

	/** generates a Game Board filled with random values in the middle and empty borders */
	protected Integer[][] generateRandomBoard() {
		Integer[][] newBoard = new Integer[BOARD_SIZE][BOARD_SIZE];
		for(int i = 1; i < BOARD_SIZE - 1; i++) {
			for(int j = 1; j < BOARD_SIZE - 1; j++) {
				newBoard[i][j] = rand.nextInt(10);
			}
		}
		return newBoard;
	}

	/** generates random numbers and inserts them into the queue until the queue has 5 elements */
	protected void fillQueue() {
		while(queue.size() < QUEUE_SIZE) {
			queue.add(rand.nextInt(10));
		}
	}

	public void refreshQueue() {
		if(currentState != GameState.ACTIVE) {
			return;
		}

		queue.clear();
		fillQueue();
		sendDataToObservers("QUEUE_CHANGED");
	}

	/** Method which is called by the FrontEnd whenever a grid tile is clicked */
	public void gridAction(int row, int col) {
		// Ignore clicks if the game is not running
		if(currentState != GameState.ACTIVE) { 
			return; 
		}

		// Ignores clicks on populated tiles for now
		if (board[row][col] != null) { 
			return; 
		}

		placeTileOntoBoard(row, col);
		sendDataToObservers("ALL");
		checkGameOver();
	}

	/** Places a tile from the top of the queue onto the board at the selected location */
	protected void placeTileOntoBoard(int row, int col) {
		int boundarySum = sumNeighbors(row, col);
		int neighbors = countNeighbors(row, col);
		scoreFromLastAction = 0;

		board[row][col] = queue.remove(0);
		fillQueue();

		if (boundarySum % 10 == board[row][col]) {
			scoreFromLastAction = calculateScoreForTilePlacement(board[row][col], row, col);
			score += scoreFromLastAction;
			clearNeighbors(row, col);
		}
	}

	/** Calculates how many points you would gain if you placed the given value at the given coordinate */
	protected int calculateScoreForTilePlacement(int value, int row, int col) {
		if(sumNeighbors(row, col) % 10 != value) {
			return 0;
		}

		return sumNeighbors(row, col) + value + bonusPoints(countNeighbors(row, col));
	}

	/** Returns the sum of all the tiles adjacent to the selected tile on the board */
	protected int sumNeighbors(int row, int col) {
		int boundarySum = 0;

		// Sums the 3x3 square with placement in the middle 
		// does not include center tile in sum
		for  (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (isOccupiedTile(row + i, col + j) == true && (i != 0 || j != 0)) {
					boundarySum += board[row + i][col + j];
				}
			}
		}

		return boundarySum;
	}

	/** Calculates bonus points for removing the supplied number of tiles */
	protected int bonusPoints(int tilesRemoved) {
		int bonusPoints = 0;

		if (tilesRemoved >= 3) {
			bonusPoints = 10 * tilesRemoved;
		}

		return bonusPoints;
	}

	/** Counts the number of adjacent tiles to the coordinate that are occupied */
	protected int countNeighbors(int row, int col) {
		int neighbors = 0;

		for  (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (isOccupiedTile(row + i, col + j) && (i != 0 || j != 0)) {
					neighbors++;
				}
			}
		}

		return neighbors;
	}

	/** clears out every tile adjacent to the input coordinate */
	protected void clearNeighbors(int row, int col) {
		for  (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (isOccupiedTile(row + i, col + j) == true) {
					board[row + i][col + j] = null;
				}
			}
		}
	}

	/** Checks if the game has ended and fires the gameLoss or gameWon function
	 * depending on the state of the board */
	protected void checkGameOver() {
		if (isBoardFull()) {
			gameLost();
		} else if (isBoardEmpty()) {
			gameWon();
		}
	}

	/** Player loses the game */
	protected void gameLost() {
		currentState = GameState.ENDED;
		sendDataToObservers("GAMELOST");
	}

	protected void gameWon() {
		currentState = GameState.ENDED;
		sendDataToObservers("GAMEWON");
	}

	/** returns true if the board is empty */
	protected boolean isBoardEmpty() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null) {
					return false;
				}
			}
		}
		return true;
	}

	/** returns true if the board is full */
	protected boolean isBoardFull() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] == null) {
					return false;
				}
			}
		}
		return true;
	}

	/** Returns true if the row/column combination is within bounds */
	protected boolean isValidCoordinate(int r, int c) {
		if (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE) {
			return true;
		} else {
			return false;
		}
	}

	/** Returns true if the coordinate on the grid is occupied */
	protected boolean isOccupiedTile(int r, int c) {
		if(isValidCoordinate(r, c) && board[r][c] != null) {
			return true;
		} else {
			return false;
		}
	}

	public Integer[][] getBoard() {
		return board;
	}

	public Integer[] getQueue() {
		return queue.toArray(new Integer[5]);
	}

	public int getScore() {
		return score;
	}

	protected void sendDataToObservers(String msg) {
		setChanged();
		switch(msg) {
			case "ALL":
				notifyObservers(new Object[]{"ALL", board, queue.toArray(new Integer[5]), score, scoreFromLastAction});
				break;
			case "QUEUE_CHANGED":
				notifyObservers(new Object[]{"QUEUE_CHANGED", queue.toArray(new Integer[5])});
				break;
			case "GAMEWON":
				notifyObservers(new Object[]{"GAMELOST"});
				break;
			case "GAMELOST":
				notifyObservers(new Object[]{"GAMELOST"});
				break;
			default:
				notifyObservers(new Object[]{msg});
				break;
		}
	}
}

