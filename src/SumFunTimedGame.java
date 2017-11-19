import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

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

		enableCheats();
	}

	@Override
	protected void checkGameOver() {
		if(isBoardFull())  {
			timer.cancel();
			gameLost();
		} else if(isBoardEmpty()) {
			timer.cancel();
			gameWon();
		}
	}

	private class TickTask extends TimerTask {
		public void run() {
			timeRemaining--;
			sendDataToObservers("TIME_REMAINING");
			if(timeRemaining <= 0) {
				gameLost();
				timer.cancel();
			}
		}
	}

	@Override
	protected void sendDataToObservers(String msg) {
		setChanged();
		switch(msg) {
			case "TIME_REMAINING":
				notifyObservers(new Object[]{"TIME_REMAINING", timeRemaining});
				break;
			//If the message is not specific to TimedGame then let the parent class handle it
			default:
				super.sendDataToObservers(msg);
				break;
		}
	}
}
