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
		setChanged();
		notifyObservers(new Object[]{"MOVES_REMAINING", movesRemaining});
	}

	@Override
	protected void placeTileOntoBoard(int row, int col) {
		super.placeTileOntoBoard(row, col);

		movesRemaining--;
		setChanged();
		notifyObservers(new Object[]{"MOVES_REMAINING", movesRemaining});
	}

	@Override
	protected void checkGameOver() {
		super.checkGameOver();
		if(currentState == GameState.ACTIVE && movesRemaining <= 0)
			gameLost();
	}
}
