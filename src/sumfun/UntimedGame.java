package sumfun;

public class UntimedGame extends RuleSet{
	private static final int INITIAL_MOVE_COUNT = 50;
	private int movesRemaining;

	public UntimedGame() {
		super();
	}

	@Override
	public void startGame() {
		super.startGame();
		movesRemaining = INITIAL_MOVE_COUNT;
		sendDataToObservers("MOVES_REMAINING");
	}

	@Override
	protected void placeTileOntoBoard(int row, int col) {
		super.placeTileOntoBoard(row, col);

		movesRemaining--;
		sendDataToObservers("MOVES_REMAINING");
	}

	@Override
	protected void checkGameOver() {
		super.checkGameOver();
		if(movesRemaining <= 0) {
			gameLost();
		}
	}

	@Override
	protected void sendDataToObservers(String msg) {
		setChanged();
		switch(msg) {
			case "MOVES_REMAINING":
				notifyObservers(new Object[]{"MOVES_REMAINING", movesRemaining});
				break;
			//If the message is not specific to UntimedGame then let the parent class handle it
			default:
				super.sendDataToObservers(msg);
				break;
		}
	}
}
