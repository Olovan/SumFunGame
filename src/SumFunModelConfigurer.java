import java.util.Observable;

class SumFunModelConfigurer extends Observable {
	public static final int UNTIMED = 0;
	public static final int TIMED = 1;
	public SumFunRuleSet rules;
	private static SumFunModelConfigurer instance;

	private SumFunModelConfigurer() {
	}

	public static SumFunModelConfigurer getInstance() {
		if(instance == null) {
			instance = new SumFunModelConfigurer();
		}
		return instance;
	}

	/** Used to perform any backend tasks that need to be done at startup 
	 *  after listeners have been hooked up */
	public void initializeBackend() {
		SumFunHighScoreLogic.getInstance().loadFromFile();
	}

	public void startGame(int type) {
		if(rules != null) {
			rules.deleteObservers();
		}

		switch(type) {
			case TIMED:
				rules = SumFunRuleSet.createGame(type);
				break;
			case UNTIMED:
				rules = SumFunRuleSet.createGame(type);
				break;
			default:
				rules = SumFunRuleSet.createGame(UNTIMED);
				break;
		}
		setChanged();
		notifyObservers(new Object[]{"RULESET_CHANGED", rules});
		rules.startGame();
	}
}
