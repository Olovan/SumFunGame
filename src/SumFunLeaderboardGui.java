import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SumFunLeaderboardGui extends JFrame implements Observer {

	private JPanel contentPane;
	private JLabel scoreType;
	private JLabel leaderboardList;

	/** Each time the leaderboard is called, it will be updated 
	 * based on the "leaderboardEntries" Object[] it receives
	 * in the form of a preordered array. This would be in the 
	 * form of {"Name - Score", "Name - Score", "Name - Score"} */
	public SumFunLeaderboardGui() {
		setPreferredSize(new Dimension(900, 450));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/* Leaderboard is filled with entries concatenated
		 * into a string and separated by "\n" */
		leaderboardList = new JLabel();
		leaderboardList.setHorizontalAlignment(JLabel.RIGHT);
		contentPane.add(leaderboardList, BorderLayout.EAST);

		/* This will be changed based on which version of the 
		 * game the user is currently playing. */
		scoreType = new JLabel("Top 10 High Scores");
		contentPane.add(scoreType, BorderLayout.NORTH);
		scoreType.setAlignmentY(Component.TOP_ALIGNMENT);
		scoreType.setHorizontalAlignment(JLabel.CENTER);
		scoreType.setFont(new Font("Arial", Font.BOLD, 20));

		SumFunModelConfigurer.getInstance().addObserver(this);
		SumFunHighScoreLogic.getInstance().addObserver(this);
		SumFunHighScoreLogic.getInstance().loadFromFile();
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

	public void setLeaderboardScores(String[][] scores) {
		leaderboardList.setText("<html>");
		for(String[] score : scores) {
			leaderboardList.setText(leaderboardList.getText() + printHighScore(score) + "<br>");
		}
		leaderboardList.setText(leaderboardList.getText() + "</html>");
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
				SumFunHighScoreLogic.getInstance().add(name, (int)args[1], true);
				showLeaderboard();
				break;
			case "RULESET_CHANGED":
				((Observable)args[1]).addObserver(this);
				break;
			case "HIGHSCORE_CHANGED":
				String[][] scores = (String[][])args[1];
				setLeaderboardScores(scores);
				break;
			default:
				break;
		}
	}
}
