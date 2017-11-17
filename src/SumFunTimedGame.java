import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionListener;

class SumFunTimedGame extends SumFunRuleSet {
	private int timeRemaining;
	private Timer t;

	public SumFunTimedGame() {
		super();
		t = new Timer();
		timeRemaining = 180;
	}

	@Override
	public void startGame() {
		super.startGame();
		t.schedule(new TickTask(), 1000, 1000);
		setChanged();
		notifyObservers(new Object[]{"TIME_REMAINING", timeRemaining});
	}

	@Override
	protected void checkGameOver() {
		super.checkGameOver();
		if(currentState == GameState.ENDED) {
			t.cancel();
		} 	
	}

	private class TickTask extends TimerTask {
		public void run() {
			timeRemaining--;
			setChanged();
			notifyObservers(new Object[]{"TIME_REMAINING", timeRemaining});
			if(timeRemaining <= 0) {
				gameLost();
				t.cancel();
			}
		}
	}
}
