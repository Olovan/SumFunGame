import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionListener;

class SumFunTimedGame extends SumFunRuleSet {
	private int timeRemaining;
	private Timer timer;

	public SumFunTimedGame() {
		super();
		timer = new Timer();
		timeRemaining = 180;
	}

	@Override
	public void startGame() {
		super.startGame();
		timer.schedule(new TickTask(), 1000, 1000);
		setChanged();
		notifyObservers(new Object[]{"TIME_REMAINING", timeRemaining});
	}

	@Override
	protected void checkGameOver() {
		if(isBoardFull())  {
			timer.cancel();
			gameLost();
		} else if(isBoardEmpty()) {
			gameWon();
		}
	}

	private class TickTask extends TimerTask {
		public void run() {
			timeRemaining--;
			setChanged();
			notifyObservers(new Object[]{"TIME_REMAINING", timeRemaining});
			if(timeRemaining <= 0) {
				gameLost();
				timer.cancel();
			}
		}
	}
}
