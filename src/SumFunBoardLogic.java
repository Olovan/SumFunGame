import java.util.Random;
import java.util.ArrayList;

public class SumFunBoardLogic 
{
	private enum GameState { ACTIVE, ENDED }

	//SETTINGS for easy tweaking
	private final int BOARD_SIZE = 9;
	private final int QUEUE_SIZE = 5;
	private final int INITIAL_MOVE_COUNT = 50;
	private final boolean CHEATS_ENABLED = false;

	private GameState currentState;
	private Random rand;
	private Controller controller;
	private ArrayList<Integer> queue;
	private int movesRemaining;
	private int score;
	private Integer[][] board;

	/** Instatiates variables and passes controller variable into BoardLogic */
	public SumFunBoardLogic(Controller c) {
		controller = c;
		rand = new Random();
		queue = new ArrayList<Integer>(QUEUE_SIZE);
		board = new Integer[BOARD_SIZE][BOARD_SIZE];
	}

	/** Contains all the code required to start/restart the game */
	public void startGame() {
		queue.clear(); //In case the queue has crap in it
		fillQueue();
		score = 0;
		movesRemaining = INITIAL_MOVE_COUNT;
		board = generateRandomBoard();
		currentState = GameState.ACTIVE;
		controller.countdownNameChanged("Moves Remaining");
		notifyControllerOfStateChange();
	}

	/** generates a Game Board filled with random values in the middle and empty borders */
	private Integer[][] generateRandomBoard() {
		Integer[][] newBoard = new Integer[BOARD_SIZE][BOARD_SIZE];
		for(int i = 1; i < BOARD_SIZE - 1; i++) {
			for(int j = 1; j < BOARD_SIZE - 1; j++) {
				newBoard[i][j] = rand.nextInt(10);
			}
		}
		return newBoard;
	}

	/** sends the Controller the current values of all the Model's relevant variables */
	private void notifyControllerOfStateChange() {
		controller.boardChanged(board);
		controller.queueChanged(queue.toArray(new Integer[QUEUE_SIZE]));
		controller.scoreChanged(score);
		controller.countdownChanged("" + movesRemaining);
	}

	/** generates random numbers and inserts them into the queue until the queue has 5 elements */
	private void fillQueue() {
		while(queue.size() < QUEUE_SIZE)
			queue.add(rand.nextInt(10));
	}

	/** Method which is called by the FrontEnd whenever a grid tile is clicked */
	public void gridAction(int row, int col) {


		// Ignore clicks if the game is not running
		if(currentState != GameState.ACTIVE) { return; }

		// Ignores clicks on populated tiles for now
		if (board[row][col] != null) { return; }

		movesRemaining--;
		placeTileOntoBoard(row, col);
		notifyControllerOfStateChange();
		checkGameOver();

		//cheats for debugging purposes
		if(CHEATS_ENABLED) {
			controller.boardEnabled();
			for(int i = 0; i < BOARD_SIZE; i++) {
				for(int j = 0; j < BOARD_SIZE; j++) {
					if(calculateScoreForTilePlacement(queue.get(0), i, j) != 0 && board[i][j] == null)
						controller.highlight(i, j);
				}
			}
		}
	}

	/** Places a tile from the top of the queue onto the board at the selected location */
	private void placeTileOntoBoard(int row, int col) {
		int boundarySum = sumNeighbors(row, col);
		int neighbors = countNeighbors(row, col);

		board[row][col] = queue.remove(0);
		fillQueue();
		// Score is stored as a global variable
		if (boundarySum % 10 == board[row][col]) {
			score += calculateScoreForTilePlacement(board[row][col], row, col);
			clearNeighbors(row, col);
		}
	}

	/** Calculates how many points you would gain if you placed the given value at the given coordinate */
	private int calculateScoreForTilePlacement(int value, int row, int col) {
		if(sumNeighbors(row, col) % 10 != value)
			return 0;

		return sumNeighbors(row, col) + value + bonusPoints(countNeighbors(row, col));
	}

	/** Returns the sum of all the tiles adjacent to the selected tile on the board */
	private int sumNeighbors(int row, int col) {
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
	private int bonusPoints(int tilesRemoved) {
		int bonusPoints = 0;

		if (tilesRemoved >= 3)
			bonusPoints = 10 * tilesRemoved;

		return bonusPoints;
	}

	/** Counts the number of adjacent tiles to the coordinate that are occupied */
	private int countNeighbors(int row, int col) {
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
	private void clearNeighbors(int row, int col) {
		for  (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (isOccupiedTile(row + i, col + j) == true) {
					board[row + i][col + j] = null;
				}
			}
		}
	}

	/** Checks if the game has ended and fires the gameOver function if the game has ended */
	private void checkGameOver() {
		if (isBoardEmpty() || isBoardFull() || movesRemaining <= 0) {
			gameOver();
		} 
	}

	/** Ends the game */
	private void gameOver() {
		currentState = GameState.ENDED;
		controller.gameOver();
	}

	/** returns true if the board is empty */
	private boolean isBoardEmpty() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null)
					return false;
			}
		}
		return true;
	}

	/** returns true if the board is full */
	private boolean isBoardFull() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] == null)
					return false;
			}
		}
		return true;
	}

	/** Returns true if the row/column combination is within bounds */
	private boolean isValidCoordinate(int r, int c) {
		if (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE)
			return true;
		else
			return false;
	}

	/** Returns true if the coordinate on the grid is occupied */
	private boolean isOccupiedTile(int r, int c) {
		if(isValidCoordinate(r, c) && board[r][c] != null)
			return true;
		else
			return false;
	}
}

