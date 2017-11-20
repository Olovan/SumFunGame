import javax.swing.UIManager;

public class Main {
	public static void main(String[] args) {
		try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SumFunMainGui view = new SumFunMainGui();
		new SumFunLeaderboardGui();
		view.setVisible(true);
	}
}
