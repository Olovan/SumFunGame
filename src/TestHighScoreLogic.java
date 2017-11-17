import java.util.Observable;
import java.util.Observer;
import java.util.List;
class TestHighScoreLogic implements Observer{
	public TestHighScoreLogic() {
		SumFunHighScoreLogic.getInstance().addObserver(this);
	}

	public void update(Observable src, Object arg) {
		Object[] args = (Object[])arg;
		String msg = (String)args[0];
		switch(msg) {
			case "HIGHSCORE_CHANGED":
				List<SumFunHighScore> scores = (List<SumFunHighScore>)args[1];
				System.out.println("Received new HighScores!");
				for(SumFunHighScore score : scores) {
					System.out.println(score);
				}
				System.out.println();
				break;
			default:
				break;
		}
	}
}
