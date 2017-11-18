public class Main {
	public static void main(String[] args) {
		SumFunMainGui view = new SumFunMainGui();
		SumFunLeaderboardGui leaderboard = new SumFunLeaderboardGui();
		SumFunHighScoreLogic.getInstance().loadFromFile();
		view.setVisible(true);
	}
}
