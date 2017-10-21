class Controller 
{
	static Controller controller;
	static SumFunMainGui gui;
	static SumFunBoardLogic backEnd;
	
	public static void main(String[] args) 
	{
		controller = new Controller();
		backEnd = new SumFunBoardLogic(controller);
		gui = new SumFunMainGui(controller);
		controller.fillGridAtStart();
		gui.setVisible(true);
	}
	
	public void gridAction(int row, int col) 
	{
		System.out.println(row + " " + col);
		
		backEnd.passValue(row, col);
	}
	
	public void returnCoordinateValue(Integer value, int row, int col)
	{
		gui.setGridValue(value, row, col);
	}
	
	public Integer[][] fillGridAtStart() { 
		Integer[][] grid;
		grid = backEnd.populateBoard();
		gui.setGrid(grid);
		return grid;
	}
}
