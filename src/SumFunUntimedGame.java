class SumFunUntimedGame extends SumFunRuleSet{
	private static final Integer[][] CHEATBOARD = new Integer[][]{
		{ null, null, null, null, null, null, null, null, null }, 
		{ null, null, null, null, null, null, null, null, null }, 
		{ null, null, null, null, null, null, null, null, null }, 
		{ null, null, null, null, null, null, null, null, null }, 
		{ null, null, null, null, 1, null, null, null, null }, 
		{ null, null, null, null, null, null, null, null, null }, 
		{ null, null, null, null, null, null, null, null, null }, 
		{ null, null, null, null, null, null, null, null, null }, 
		{ null, null, null, null, null, null, null, null, null } 
	};

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

		//ENABLE CHEATS
		board = new Integer[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				board[i][j] = CHEATBOARD[i][j];
			}
		}
		queue.set(0, 1);
		setChanged();
		notifyObservers(new Object[]{"ALL", board, queue.toArray(new Integer[5]), score});
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
