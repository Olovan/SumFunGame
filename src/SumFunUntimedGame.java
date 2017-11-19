class SumFunUntimedGame extends SumFunRuleSet{
	private static final int INITIAL_MOVE_COUNT = 50;
	private int movesRemaining;

	public SumFunUntimedGame() {
		super();
	}

	@Override
	public void startGame() {
		super.startGame();
		movesRemaining = INITIAL_MOVE_COUNT;
		sendDataToObservers("MOVES_REMAINING");

		enableCheats();
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
		if(currentState == GameState.ACTIVE && movesRemaining <= 0) {
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
