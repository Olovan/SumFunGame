import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		setPreferredSize(new Dimension(450, 350));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/* Leaderboard is filled with entries concatenated
		 * into a string and separated by "\n" */
		leaderboardList = new JLabel();
		leaderboardList.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(leaderboardList, BorderLayout.CENTER);

		/* This will be changed based on which version of the 
		 * game the user is currently playing. */
		scoreType = new JLabel("Top 10 High Scores");
		contentPane.add(scoreType, BorderLayout.NORTH);
		scoreType.setAlignmentY(Component.TOP_ALIGNMENT);
		scoreType.setHorizontalAlignment(JLabel.CENTER);
		scoreType.setFont(new Font("Arial", Font.BOLD, 20));

		SumFunModelConfigurer.getInstance().addObserver(this);
		SumFunHighScoreLogic.getInstance().addObserver(this);

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

	public void setLeaderboardScores(List<SumFunHighScore> scores) {
		leaderboardList.setText("<html>");
		for(SumFunHighScore score : scores) {
			leaderboardList.setText(leaderboardList.getText() + printHighScore(score) + "<br>");
		}
		leaderboardList.setText(leaderboardList.getText() + "</html>");
	}

	private String printHighScore(SumFunHighScore score) {
		return String.format("<tr><td align='left'>%s</td><td></td><td>%d</td></td><td>&emsp;%s</td></tr>", score.getName(), score.getScore(), score.getDate());
	}

	/** This won't show the leaderboard 
	  Can LeaderboardGUI listen for the same message as MainGUI? */
	@SuppressWarnings("unchecked")
	public void update(Observable src, Object arg) {
		Object[] args = (Object[])arg;
		String message = (String)args[0];

		switch(message) {
			case "GAMEWON":
				showLeaderboard();
				break;
			case "RULESET_CHANGED":
				((Observable)args[1]).addObserver(this);
				break;
			case "HIGHSCORE_CHANGED":
				List<SumFunHighScore> scores = (List<SumFunHighScore>)args[1];
				setLeaderboardScores(scores);
				break;
			default:
				break;
		}
	}
}
