package sumfun;

import javax.swing.UIManager;

public class Main {
	public static void main(String[] args) {
		try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		MainGui view = new MainGui();
		new LeaderboardGui();
		view.setVisible(true);
	}
}
