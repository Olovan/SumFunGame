public class Main {
	public static void main(String[] args) {
		SumFunMainGui view = new SumFunMainGui();
		SumFunBoardLogic.getInstance().startGame();
		view.setVisible(true);
	}
}
