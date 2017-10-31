import java.util.ArrayList;

class Controller 
{
	public SumFunMainGui view;
	public SumFunBoardLogic model;
	
	/** Reports a mouseClick on a specific grid tile to the backend
	 *  Acts on the queue and updates the queue after placing a value onto board */
	public void gridAction(int row, int col) 
	{
		model.gridAction(row, col);
	}
	
	/** Sets the GUI's grid to match the input array
	 *  A null element in the grid will result in an empty tile */
	public void boardChanged(Integer[][] board) {
		view.setGrid(board);
	}

	/** Sets the GUI's score display to match the input score */
	public void scoreChanged(int score) {
		view.setScore(score);
	}
	
	/** Sets the GUI's queue display to match the input queue
	 *  queue[0] is considered the head of the queue */
	public void queueChanged(Integer[] queue) {
		view.setQueue(queue);
	}

	/** Sets the GUI's countdown value to the string provided
	 *  The countdown is passed in as a string so that the number
	 *  may be formatted by the backend incase the number should
	 *  be formatted like a time or like a number
	 *  checks if counter = 0, if so, calls the game over method */
	public void countdownChanged(String countdown) {
		view.setCountdown(countdown);
	}

	/** Sets the GUI's countdown name to the input string
	 *  This is done so that the countdown value can represent
	 *  both Time remaining and Moves remaining or any other 
	 *  metric that is required by simply changing its label */
	public void countdownNameChanged(String name) {
		view.setCountdownName(name);
	}

	/** Disables the GUI's board and displays the GameOver Message */
	public void gameOver() {
		view.disableBoard();
		view.gameOver();
	}

	/** Enables the GUI's board */
	public void boardEnabled() {
		view.enableBoard();
	}

	/** Disables the GUI's board */
	public void boardDisabled() {
		view.disableBoard();
	}
}
