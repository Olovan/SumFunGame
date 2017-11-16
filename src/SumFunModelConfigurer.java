import java.util.Observable;

class SumFunModelConfigurer extends Observable {
	public final static int UNTIMED = 0;
	public final static int TIMED = 1;
	public SumFunRuleSet rules;
	private static SumFunModelConfigurer instance;

	private SumFunModelConfigurer() {
	}

	public static SumFunModelConfigurer getInstance() {
		if(instance == null)
			instance = new SumFunModelConfigurer();
		return instance;
	}

	public void startGame(int type) {
		if(rules != null)
			rules.deleteObservers();

		switch(type) {
			case TIMED:
				rules = new SumFunTimedGame();
				break;
			case UNTIMED:
				rules = new SumFunUntimedGame();
				break;
			default:
				rules = new SumFunUntimedGame();
				break;
		}
		setChanged();
		notifyObservers(new Object[]{"RULESET_CHANGED", rules});
		rules.startGame();
	}
}
