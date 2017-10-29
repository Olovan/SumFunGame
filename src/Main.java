public class Main {
	public static void main(String[] args) {
		Controller controller = new Controller();
		SumFunBoardLogic model = new SumFunBoardLogic(controller);
		SumFunMainGui view = new SumFunMainGui(controller);
		controller.view = view;
		controller.model = model;
		model.startGame();
		view.setVisible(true);
	}
}
