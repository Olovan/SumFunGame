import java.util.ArrayList;

class Controller 
{
	static Controller controller;
	static SumFunMainGui gui;
	static SumFunBoardLogic backEnd;
	
	//Entry point for the program
	//Test Commit
	public static void main(String[] args) 
	{
		controller = new Controller();
		backEnd = new SumFunBoardLogic(controller);
		gui = new SumFunMainGui(controller);
		backEnd.startGame();
		gui.setVisible(true);
	}
	
	//Reports a mouseClick on a specific grid tile to the backend
	//Acts on the queue and updates the queue after placing a value onto board
	public void gridAction(int row, int col) 
	{
		backEnd.gridAction(row, col);
	}
	
	//Sets the GUI's grid to match the input array
	//A null element in the grid will result in an empty tile
	public void boardChanged(Integer[][] board) {
		gui.setGrid(board);
	}

	//Sets the GUI's score display to match the input score
	public void scoreChanged(int score) {
		gui.setScore(score);
	}
	
	//Sets the GUI's queue display to match the input queue
	//queue[0] is considered the head of the queue
	public void queueChanged(Integer[] queue) {
		gui.setQueue(queue);
	}

	//Sets the GUI's countdown value to the string provided
	//The countdown is passed in as a string so that the number
	//may be formatted by the backend incase the number should
	//be formatted like a time or like a number
	//checks if counter = 0, if so, calls the game over method
	public void countdownChanged(String countdown) {
		gui.setCountdown(countdown);
	}

	//Sets the GUI's countdown name to the input string
	//This is done so that the countdown value can represent
	//both Time remaining and Moves remaining or any other 
	//metric that is required by simply changing its label
	public void countdownNameChanged(String name) {
		gui.setCountdownName(name);
	}

	//Disables the GUI's board and displays the GameOver Message
	public void gameOver() {
		gui.disableBoard();
		gui.gameOver();
	}

	//Enables the GUI's board
	public void boardEnabled() {
		gui.enableBoard();
	}

	//Disables the GUI's board
	public void boardDisabled() {
		gui.disableBoard();
	}
}
