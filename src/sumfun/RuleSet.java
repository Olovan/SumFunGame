package sumfun;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

// This class contains the base ruleset for the game which includes things like tile placement logic and 
// basic gameover logic
public abstract class RuleSet extends Observable {
	public enum GameState { ACTIVE, ENDED }

	//SETTINGS for easy tweaking
	protected static final int BOARD_SIZE = 9;
	protected static final int QUEUE_SIZE = 5;

	protected GameState currentState;
	protected Random rand;
	protected ArrayList<Integer> queue;
	protected int score;
	protected int scoreFromLastAction;
	protected Integer[][] board;
	protected boolean[][] hintBoard;

	/** Instatiates variables */
	public RuleSet() {
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

		// enableCheats();

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
		queue.set(1, 2);
		queue.set(2, 4);
		queue.set(3, 8);
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
	
	public void displayHints() {
		int val = queue.get(0);
		boolean[][] gridHints = new boolean[9][9];
		ArrayList<int[]> coords = new ArrayList<int[]>();
		int boundarySum;
		
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				boundarySum = sumNeighbors(i, j);
				if(val == (boundarySum % 10) && board[i][j] == null) {
					coords.add(new int[]{i, j});
					coords.sort((a, b)-> {
						return countNeighbors(b[0], b[1]) - countNeighbors(a[0], a[1]);
					});
				} 
			}
		}
		for(int i = 0; i < coords.size() && i < 5; i++) {
			gridHints[coords.get(i)[0]][coords.get(i)[1]] = true;
		}
		
		hintBoard = gridHints;
		sendDataToObservers("HINTS");
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
		scoreFromLastAction = 0;

		board[row][col] = queue.remove(0);
		fillQueue();

		if (boundarySum % 10 == board[row][col]) {
			sendDataToObservers("TILE_REMOVED");
			scoreFromLastAction = calculateScoreForTilePlacement(board[row][col], row, col);
			score += scoreFromLastAction;
			clearNeighbors(row, col);
		} else {
			sendDataToObservers("TILE_ADDED");
		}
	}

	/** Calculates how many points you would gain if you placed the top of the queue at the given coordinate */
	public int calculateScoreForTilePlacement(int row, int col) {
		return calculateScoreForTilePlacement(queue.get(0), row, col);
	}
	
	/** Calculates how many points you would gain if you placed the given value at the given coordinate */
	public int calculateScoreForTilePlacement(int value, int row, int col) {
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

	/** Deletes every tile that has the same value as the input value */
	protected void removeAllTilesOfValue(Integer value) {
		if(value == null) {
			return;
		}

		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if(board[i][j] != null && board[i][j].equals(value)) {
					board[i][j] = null;
				}
			}
		}
		sendDataToObservers("ALL");
		checkGameOver();
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
	
	
	/** isOccupiedTile(int r, int c)
	 * 
	 *  INPUTS
	 *  int r	- The row of the tile being tested
	 *  int c	- The column of the tile being tested
	 * 
	 *  OUTPUTS
	 *  boolean true	- the coordinate on the grid is a number
	 *  				and the coordinate is within bounds on the 9x9 board 
	 *  boolean false	- the coordinate on the grid is null
	 *  				or the coordinate is out of bounds on the 9x9 board
	 **/
	protected boolean isOccupiedTile(int r, int c) {
		if(isValidCoordinate(r, c) && board[r][c] != null) {
			return true;
		} else {
			return false;
		}
	}

	protected void sendDataToObservers(String msg) {
		setChanged();
		switch(msg) {
			case "ALL":
				notifyObservers(new Object[]{msg, board, queue.toArray(new Integer[5]), score, scoreFromLastAction});
				break;
			case "QUEUE_CHANGED":
				notifyObservers(new Object[]{msg, queue.toArray(new Integer[5])});
				break;
			case "GAMEWON":
				notifyObservers(new Object[]{msg, score});
				break;
			case "GAMELOST":
				notifyObservers(new Object[]{msg});
				break;
			case "HINTS":
				notifyObservers(new Object[]{msg, hintBoard});
				break;
			default:
				notifyObservers(new Object[]{msg});
				break;
		}
	}

	public static RuleSet createGame(int type) {
		RuleSet newGame = null;
		switch(type) {
			case ModelConfigurer.TIMED:
				newGame = new TimedGame();
				break;
			case ModelConfigurer.UNTIMED:
				newGame = new UntimedGame();
				break;
			default:
				break;
		}

		return newGame;
	}
}

