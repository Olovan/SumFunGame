public class Main {
	public static void main(String[] args) {
		SumFunMainGui view = new SumFunMainGui();
		view.setVisible(true);
		//TestHighScoreLogic test = new TestHighScoreLogic();

		SumFunHighScoreLogic.getInstance().add(new SumFunHighScore("Bill", 2323));
		SumFunHighScoreLogic.getInstance().add(new SumFunHighScore("George", 98323));
		SumFunHighScoreLogic.getInstance().add(new SumFunHighScore("Will", 30234));
		SumFunHighScoreLogic.getInstance().add(new SumFunHighScore("Anonymous", 1013));
	}
}
