package sumfun;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class LeaderboardGui extends JFrame implements Observer {

	private JPanel contentPane;
	private LeaderboardList topScores;
	private LeaderboardList bestTimes;

	/** Each time the leaderboard is called, it will be updated 
	 * based on the "leaderboardEntries" Object[] it receives
	 * in the form of a preordered array. This would be in the 
	 * form of {"Name - Score", "Name - Score", "Name - Score"} */
	public LeaderboardGui() {
		setPreferredSize(new Dimension(500, 450));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/* Leaderboard is filled with entries concatenated
		 * into a string and separated by "\n" */
		topScores = new LeaderboardList("Top Scores");
		contentPane.add(topScores, BorderLayout.EAST);

		bestTimes = new LeaderboardList("Best Times");
		contentPane.add(bestTimes, BorderLayout.WEST);

		ModelConfigurer.getInstance().addObserver(this);
		LeaderboardModel.getInstance().addObserver(this);
		LeaderboardModel.getInstance().loadFromFile();
		pack();
	}

	/** Enables visibility of the leaderboard */
	public void showLeaderboard() {
		setVisible(true);
	}

	/** Disables visibility of the leaderboard */
	public void hideLeaderboard() {
		setVisible(false);
	}

	public void setList(String[][] scores, JLabel list) {
		list.setText("<html>");
		for(String[] score : scores) {
			list.setText(list.getText() + printHighScore(score) + "<br>");
		}
		list.setText(list.getText() + "</html>");
	}

	private String printHighScore(String[] score) {
		return String.format("<tr><td align='left'>%s</td><td color=blue>&emsp;&emsp;<font size=\"5\">%s</font>&emsp;&emsp;<td>%s</td></tr>", score[0], score[1], score[2]);
	}

	/** */
	public void update(Observable src, Object arg) {
		Object[] args = (Object[])arg;
		String message = (String)args[0];

		switch(message) {
			case "GAMEWON":
				String name = JOptionPane.showInputDialog(this, "You win! Enter your name if you want your score saved.");
				LeaderboardModel.getInstance().addScore(name, (int)args[1]);
				showLeaderboard();
				break;
			case "TIMED_GAMEWON":
				String nameTimed = JOptionPane.showInputDialog(this, "You win! Enter your name if you want your score saved.");
				LeaderboardModel.getInstance().addScore(nameTimed, (int)args[1]);
				LeaderboardModel.getInstance().addTime(nameTimed, (int)args[2]);
				showLeaderboard();
				break;
			case "RULESET_CHANGED":
				((Observable)args[1]).addObserver(this);
				break;
			case "HIGHSCORE_CHANGED":
				String[][] scores = (String[][])args[1];
				setList(scores, topScores.list);
				break;
			case "BEST_TIME_CHANGED":
				String[][] times = (String[][])args[1];
				setList(times, bestTimes.list);
				break;
			default:
				break;
		}
	}

	private class LeaderboardList extends JPanel {
		public JLabel list;
		public JLabel title;
		public LeaderboardList(String titleString) {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setAlignmentX(CENTER_ALIGNMENT);

			title = new JLabel(titleString);
			list = new JLabel();

			title.setHorizontalAlignment(JLabel.CENTER);
			title.setAlignmentX(CENTER_ALIGNMENT);
			title.setFont(new Font("Arial", Font.BOLD, 20));
			list.setHorizontalAlignment(JLabel.CENTER);
			list.setAlignmentX(CENTER_ALIGNMENT);

			add(title);
			add(list);
		}
	}
}
