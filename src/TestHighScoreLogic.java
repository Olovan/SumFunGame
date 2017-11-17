import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.util.ArrayList;
class TestHighScoreLogic implements Observer{
	public TestHighScoreLogic() {
		SumFunHighScoreLogic.getInstance().addObserver(this);
	}

	@SuppressWarnings("unchecked")
	public void update(Observable src, Object arg) {
		Object[] args = (Object[])arg;
		String msg = (String)args[0];
		switch(msg) {
			case "HIGHSCORE_CHANGED":
				List<SumFunHighScore> scores = new ArrayList<SumFunHighScore>();
				try {
					scores = (List<SumFunHighScore>)args[1];
				} catch (Exception e) {
					System.out.println("High Score data was not received by Observer");
				}
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
