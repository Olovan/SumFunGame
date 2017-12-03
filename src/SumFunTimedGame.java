import java.util.Timer;
import java.util.TimerTask;

class SumFunTimedGame extends SumFunRuleSet {
	private int timeRemaining;
	private int seconds;
	private int minutes;
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
		sendDataToObservers("TIME_REMAINING");
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

	@Override
	protected void gameWon() {
		currentState = GameState.ENDED;
		sendDataToObservers("TIMED_GAMEWON");
	}

	@Override
	protected void sendDataToObservers(String msg) {
		setChanged();
		switch(msg) {
			case "TIME_REMAINING":
				minutes = timeRemaining / 60;
				seconds = timeRemaining % 60;
				notifyObservers(new Object[]{"TIME_REMAINING", String.format("%d:%02d", minutes, seconds)});
				break;
			case "TIMED_GAMEWON":
				notifyObservers(new Object[]{"TIMED_GAMEWON", score, 180 - timeRemaining});
				break;
			//If the message is not specific to TimedGame then let the parent class handle it
			default:
				super.sendDataToObservers(msg);
				break;
		}
	}

	/* Internal class which contains the task to decrement the timer by 1 second */
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
}
