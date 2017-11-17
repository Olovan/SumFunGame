import java.util.Observer;
import java.util.Observable;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class SumFunMainGui extends JFrame implements Observer{

	private JPanel contentPane;
	private SumFunGridButton[][] grid;
	private JLabel[] queue;
	private JLabel lblScore;
	private JLabel countdown;
	private JLabel countdownName;
	private JButton btnRefresh;
	private JButton btnNewUntimedGame;
	private JButton btnNewTimedGame;

	/**
	 * Create the frame.
	 */
	public SumFunMainGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));

		JPanel gridPanel = new JPanel();
		gridPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		gridPanel.setPreferredSize(new Dimension(600, 600));
		centerPanel.add(gridPanel);
		gridPanel.setLayout(new GridLayout(9, 9, 0, 0));

		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(200, 600));
		centerPanel.add(rightPanel);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		JPanel scorePanel = new JPanel();
		rightPanel.add(scorePanel);
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));

		JLabel scoreTitleLabel = new JLabel("Score");
		scorePanel.add(scoreTitleLabel);
		scoreTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblScore = new JLabel("0");
		scorePanel.add(lblScore);
		lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblScore.setFont(new Font("Arial", Font.BOLD, 20));

		Component verticalStrut = Box.createVerticalStrut(20);
		rightPanel.add(verticalStrut);

		JPanel countdownPanel = new JPanel();
		rightPanel.add(countdownPanel);
		countdownPanel.setLayout(new BoxLayout(countdownPanel, BoxLayout.Y_AXIS));

		countdownName = new JLabel("Moves Remaining: ");
		countdownName.setAlignmentX(Component.CENTER_ALIGNMENT);
		countdownPanel.add(countdownName);

		countdown = new JLabel("0");
		countdown.setAlignmentX(Component.CENTER_ALIGNMENT);
		countdownPanel.add(countdown);
		countdown.setFont(new Font("Arial", Font.BOLD, 18));

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		rightPanel.add(verticalStrut_1);

		JPanel queuePanel = new JPanel();
		rightPanel.add(queuePanel);
		queuePanel.setLayout(new BoxLayout(queuePanel, BoxLayout.Y_AXIS));

		JLabel lblQueue = new JLabel("Queue");
		queuePanel.add(lblQueue);
		lblQueue.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel queueTopPanel = new JPanel();
		queueTopPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		queueTopPanel.setMaximumSize(new Dimension(50, 100));
		queueTopPanel.setBackground(Color.WHITE);
		queuePanel.add(queueTopPanel);

		JPanel powerUpPanel = new JPanel();
		rightPanel.add(powerUpPanel);
		powerUpPanel.setLayout(new BoxLayout(powerUpPanel, BoxLayout.Y_AXIS));

		btnRefresh = new JButton("Refresh");
		btnRefresh.setAlignmentX(CENTER_ALIGNMENT);
		powerUpPanel.add(btnRefresh);
		btnRefresh.setFont(new Font("Arial", Font.BOLD, 12));
		btnRefresh.addActionListener(new RefreshQueueButtonController());

		JPanel newGamesPanel = new JPanel();
		rightPanel.add(newGamesPanel);
		newGamesPanel.setLayout(new BoxLayout(newGamesPanel, BoxLayout.Y_AXIS));

		Component verticalStrut_2 = Box.createVerticalStrut(50);
		newGamesPanel.add(verticalStrut_2);

		btnNewTimedGame = new JButton("New Timed");
		btnNewTimedGame.setAlignmentX(CENTER_ALIGNMENT);
		newGamesPanel.add(btnNewTimedGame);
		btnNewTimedGame.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewTimedGame.addActionListener(new TimedGameButtonController());

		btnNewUntimedGame = new JButton("New Untimed");
		btnNewUntimedGame.setAlignmentX(CENTER_ALIGNMENT);
		newGamesPanel.add(btnNewUntimedGame);
		btnNewUntimedGame.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewUntimedGame.addActionListener(new UntimedGameButtonController());

		Component verticalGlue = Box.createVerticalGlue();
		rightPanel.add(verticalGlue);

		//Instantiate Grid
		grid = new SumFunGridButton[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				grid[i][j] = new SumFunGridButton(i, j);
				gridPanel.add(grid[i][j]);
			}
		}

		//Instantiate Queue
		queue = new JLabel[5];
		queue[0] = new JLabel("0");
		queue[0].setFont(new Font("Arial", Font.BOLD, 25));
		queueTopPanel.add(queue[0]);
		for(int i = 1; i < 5; i++) {
			queue[i] = new JLabel("0");
			queuePanel.add(queue[i]);
		}

		pack();

		SumFunModelConfigurer.getInstance().addObserver(this);
	}

	/** Tells grid to display the input grid */
	public void setGrid(Integer[][] grid) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				this.grid[i][j].setValue(grid[i][j]);
			}
		}
	}

	/** Tells the Gui to display the input queue */
	public void setQueue(Integer[] queue) {
		for(int i = 0; i < 5; i++) {
			this.queue[i].setText(queue[i].toString());
		}
	}

	/** Tells GUI to display the input score */
	public void setScore(int score) {
		lblScore.setText("" + score);
	}

	/** Tells GUI to display the input countdown string */
	public void setCountdown(String text) {
		countdown.setText(text);
	}

	/** Tells GUI to display the input countdown name 
	 *  Used so that the countdown field can be used to display
	 *  both moves remaining and time remaining */
	public void setCountdownName(String text) {
		countdownName.setText(text);
	}

	/**Tells a specific grid tile to display the input value */
	public void setGridValue(Integer value, int row, int col) {
		this.grid[row][col].setValue(value);
	}

	/**Locks the board and displays the game over Message */
	public void gameLost() {
		disableBoard();
		JOptionPane.showMessageDialog(this, "Game Over. You Lose.");
	}

	/**Handles the event of winning by locking the board,
	 * asking the player for their name and returning it */
	public String gameWon() {
		disableBoard();
		return getName();
	}

	/**Gets the name of the user. If nothing is entered, returns empty string. */
	public String getName() {
		String name = JOptionPane.showInputDialog(this, "You win! Enter your name if you want your score saved.");
		return name;
	}

	/**Allows the board to accept input again */
	public void enableBoard() {
		for(SumFunGridButton[] row : grid)
			for(SumFunGridButton tile : row)
				tile.enable();
	}

	/**Disables input from the board */
	public void disableBoard() {
		for(SumFunGridButton[] row : grid)
			for(SumFunGridButton tile : row)
				tile.disable();
	}

	/**Allows the refresh queue button to be clicked */
	public void enableRefresh() {
		btnRefresh.setEnabled(true);
	}

	/**Disables the refresh queue button */
	public void disableRefresh() {
		btnRefresh.setEnabled(false);
	}

	/** Implementation of update message from Observer interface
	 *  Can be sent messages in the form of Strings instructing it what 
	 *  specifically to update or if no message is supplied then it will update
	 *  everything
	 */
	public void update(Observable src, Object arg) {
		Object[] args = (Object[])arg;
		String message = (String)args[0];
		SumFunHighScoreLogic highScore = null;
		
		switch(message) {
			case "GAMELOST":
				//gameLost();
				highScore.getInstance().add(new SumFunHighScore(gameWon(), Integer.parseInt(lblScore.getText())));
				break;
			case "GAMEWON":
				gameWon();
				break;
			case "MOVES_REMAINING":
				setCountdownName("Moves Remaining: ");
				setCountdown("" + (int)args[1]);
				break;
			case "TIME_REMAINING":
				setCountdownName("Time Remaining: ");
				setCountdown("" + (int)args[1]);
				break;
			case "QUEUE_CHANGED":
				setQueue((Integer[])args[1]);
				break;
			case "ALL":
				setGrid((Integer[][])args[1]);
				setQueue((Integer[])args[2]);
				setScore((int)args[3]);
				break;
			case "RULESET_CHANGED":
				((Observable)args[1]).addObserver(this);
				btnRefresh.setEnabled(true);
				enableBoard();
				break;
			default:
				break;
		}
	}

	private class TimedGameButtonController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
				SumFunModelConfigurer.getInstance().startGame(SumFunModelConfigurer.TIMED);
		}
	}
	private class UntimedGameButtonController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
				SumFunModelConfigurer.getInstance().startGame(SumFunModelConfigurer.UNTIMED);
		}
	}
	private class RefreshQueueButtonController implements ActionListener, Observer {
		private SumFunRuleSet rules;

		public RefreshQueueButtonController() {
			SumFunModelConfigurer.getInstance().addObserver(this);
		}

		public void actionPerformed(ActionEvent e) {
			if(rules != null) {
				btnRefresh.setEnabled(false);
				rules.refreshQueue();
			}
		}

		public void update(Observable src, Object arg) {
			Object[] args = (Object[])arg;
			String msg = (String)args[0];

			switch(msg) {
				case "RULESET_CHANGED":
					rules = (SumFunRuleSet)args[1];
					break;
				default:
					break;
			}
		}
	}
}


