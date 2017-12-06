package sumfun;

import java.util.Observable;

public class ModelConfigurer extends Observable {
	public static final int UNTIMED = 0;
	public static final int TIMED = 1;
	
	public RuleSet rules;
	private static ModelConfigurer instance;

	private ModelConfigurer() {
	}

	public static ModelConfigurer getInstance() {
		if(instance == null) {
			instance = new ModelConfigurer();
		}
		return instance;
	}

	public void startGame(int type) {
		if(rules != null) {
			rules.deleteObservers();
		}

		switch(type) {
			case TIMED:
				rules = RuleSet.createGame(type);
				break;
			case UNTIMED:
				rules = RuleSet.createGame(type);
				break;
			default:
				rules = RuleSet.createGame(UNTIMED);
				break;
		}
		setChanged();
		notifyObservers(new Object[]{"RULESET_CHANGED", rules});
		rules.startGame();
	}
}
