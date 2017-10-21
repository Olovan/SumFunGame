class Controller 
{
	static Controller controller = new Controller();
	static SumFunMainGui gui = new SumFunMainGui(controller);
	SumFunBoardLogic backEnd = new SumFunBoardLogic(controller);
	
	public static void main(String[] args) 
	{
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
		return grid;
	}
}
