class Controller 
{
	static Controller controller;
	static SumFunMainGui gui;
	static SumFunBoardLogic backEnd;
	
	//Entry point for the program
	public static void main(String[] args) 
	{
		controller = new Controller();
		backEnd = new SumFunBoardLogic(controller);
		gui = new SumFunMainGui(controller);
		controller.fillGridAtStart();
		controller.fillQueueAtStart();
		gui.setVisible(true);
	}
	
	//Reports a mouseClick on a specific grid tile to the backend
	//Acts on the queue and updates the queue after placing a value onto board
	public void gridAction(int row, int col) 
	{
		//DEBUG -  remove this in final product
		System.out.println(row + " " + col);
		
		backEnd.passValue(row, col);
		gui.setQueue(backEnd.passQueue());
	}
	
	//sets a specific tile in the GUI's grid to the input value
	public void returnCoordinateValue(Integer value, int row, int col)
	{
		gui.setGridValue(value, row, col);
	}
	
	//Requests a new board from the backend and assigns it to the GUI
	public Integer[][] fillGridAtStart() { 
		Integer[][] grid;
		grid = backEnd.populateBoard();
		gui.setGrid(grid);
		return grid;
	}

	//Sets the GUI's grid to match the input array
	//A null element in the grid will result in an empty tile
	public void setGrid(Integer[][] grid) {
		gui.setGrid(grid);
	}

	//Sets the GUI's score display to match the input score
	public void setScore(int score) {
		gui.setScore(score);
	}
	
	//Sets the GUI's queue display to match the input queue
	public Integer[] fillQueueAtStart() {
		Integer[] queue;
		queue = backEnd.populateQueue();
		gui.setQueue(queue);
		return queue;
	}
	
	//Sets the GUI's queue display to match the input queue
	//queue[0] is considered the head of the queue
	public void setQueue(Integer[] queue) {
		gui.setQueue(queue);
	}

	//Sets the GUI's countdown value to the string provided
	//The countdown is passed in as a string so that the number
	//may be formatted by the backend incase the number should
	//be formatted like a time or like a number
	public void setCountdown(String countdown) {
		gui.setCountdown(countdown);
	}

	//Sets the GUI's countdown name to the input string
	//This is done so that the countdown value can represent
	//both Time remaining and Moves remaining or any other 
	//metric that is required by simply changing its label
	public void setCountdownName(String name) {
		gui.setCountdownName(name);
	}

	//Disables the GUI's board and displays the GameOver Message
	public void gameOver() {
		gui.disableBoard();
		gui.gameOver();
	}

	//Enables the GUI's board
	public void enableBoard() {
		gui.enableBoard();
	}

	//Disables the GUI's board
	public void disableBoard() {
		gui.disableBoard();
	}
}
