public class Main {
	public static void main(String[] args) {
		SumFunMainGui view = new SumFunMainGui();
		SumFunLeaderboardGui leaderboard = new SumFunLeaderboardGui();
		TestHighScoreLogic test = new TestHighScoreLogic();
		SumFunHighScoreLogic.getInstance().loadFromFile();
		view.setVisible(true);
	}
}
