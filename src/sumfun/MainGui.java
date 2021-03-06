package sumfun;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class MainGui extends JFrame implements Observer{

	private JPanel contentPane;
	private BoardTile[][] board;
	private JLabel[] queue;
	private JLabel lblScore;
	private JLabel lblLastMoveScore;
	private JLabel countdown;
	private JLabel countdownName;
	private JButton btnRefresh;
	private JButton btnNewUntimedGame;
	private JButton btnNewTimedGame;
	private JButton btnRemoveSquares;
	private HintsButton btnHints;
	private Clip tileRemoved;
	private Clip tileAdded;
	private Clip victory;
	private Clip defeat;


	/**
	 * Create the frame.
	 */
	public MainGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
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
		rightPanel.setAlignmentX(CENTER_ALIGNMENT);
		centerPanel.add(rightPanel);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		JPanel scorePanel = new JPanel();
		rightPanel.add(scorePanel);
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));

		//Current Score
		JLabel scoreTitleLabel = new JLabel("Score");
		scorePanel.add(scoreTitleLabel);
		scoreTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblScore = new JLabel("0");
		scorePanel.add(lblScore);
		lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblScore.setFont(new Font("Arial", Font.BOLD, 20));

		Component verticalStrut = Box.createVerticalStrut(10);
		rightPanel.add(verticalStrut);

		//PreviousScore
		JPanel scoreFromLastMovePanel = new JPanel();
		rightPanel.add(scoreFromLastMovePanel);
		scoreFromLastMovePanel.setLayout(new BoxLayout(scoreFromLastMovePanel, BoxLayout.Y_AXIS));
		JLabel lastMoveTitle = new JLabel("Score From Previous Move:");
		lastMoveTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreFromLastMovePanel.add(lastMoveTitle);
		lblLastMoveScore = new JLabel("0");
		lblLastMoveScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreFromLastMovePanel.add(lblLastMoveScore);

		rightPanel.add(Box.createVerticalStrut(10));

		//Countdown
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

		rightPanel.add(Box.createVerticalStrut(20));

		//Queue
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

		btnRefresh = new JButton("Refresh Queue");
		btnRefresh.setAlignmentX(CENTER_ALIGNMENT);
		btnRefresh.setFont(new Font("Arial", Font.BOLD, 12));
		btnRefresh.addActionListener(new RefreshQueueButtonController(ModelConfigurer.getInstance()));
		rightPanel.add(btnRefresh);

		rightPanel.add(Box.createVerticalStrut(20));

		//PowerUps
		JPanel powerUpPanel = new JPanel();
		rightPanel.add(powerUpPanel);
		powerUpPanel.setLayout(new BoxLayout(powerUpPanel, BoxLayout.Y_AXIS));

		JLabel powerupText = new JLabel("Powerups");
		powerupText.setAlignmentX(CENTER_ALIGNMENT);
		powerUpPanel.add(powerupText);


		btnRemoveSquares = new JButton("Remove Squares");
		btnRemoveSquares.addActionListener(new RemoveSquaresController(ModelConfigurer.getInstance()));
		btnRemoveSquares.setAlignmentX(CENTER_ALIGNMENT);
		btnRemoveSquares.setFont(new Font("Arial", Font.BOLD, 12));
		btnRemoveSquares.setPreferredSize(new Dimension(180, 40));
		btnRemoveSquares.setMaximumSize(new Dimension(180, 40));
		btnRemoveSquares.setEnabled(false);
		powerUpPanel.add(btnRemoveSquares);

		btnHints = new HintsButton(ModelConfigurer.getInstance());
		powerUpPanel.add(btnHints);

		rightPanel.add(Box.createVerticalGlue());

		JPanel newGamesPanel = new JPanel();
		rightPanel.add(newGamesPanel);
		newGamesPanel.setLayout(new BoxLayout(newGamesPanel, BoxLayout.Y_AXIS));

		btnNewTimedGame = new JButton("Start Timed Game");
		btnNewTimedGame.setPreferredSize(new Dimension(200, 40));
		btnNewTimedGame.setMaximumSize(new Dimension(400, 50));
		btnNewTimedGame.setAlignmentX(CENTER_ALIGNMENT);
		newGamesPanel.add(btnNewTimedGame);
		btnNewTimedGame.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewTimedGame.addActionListener(new TimedGameButtonController());

		btnNewUntimedGame = new JButton("Start Untimed Game");
		btnNewUntimedGame.setPreferredSize(new Dimension(200, 40));
		btnNewUntimedGame.setMaximumSize(new Dimension(400, 50));
		btnNewUntimedGame.setAlignmentX(CENTER_ALIGNMENT);
		newGamesPanel.add(btnNewUntimedGame);
		btnNewUntimedGame.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewUntimedGame.addActionListener(new UntimedGameButtonController());

		//Instantiate Grid
		board = new BoardTile[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				board[i][j] = new BoardTile(this, i, j, ModelConfigurer.getInstance());
				gridPanel.add(board[i][j]);
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

		//Set up sound clips
		try {
			tileRemoved = AudioSystem.getClip(null);
			tileAdded = AudioSystem.getClip(null);
			victory = AudioSystem.getClip(null);
			defeat = AudioSystem.getClip(null);
			tileRemoved.open(AudioSystem.getAudioInputStream(new File("assets/sounds/tile_removed.wav")));
			tileAdded.open(AudioSystem.getAudioInputStream(new File("assets/sounds/tile_placed.wav")));
			victory.open(AudioSystem.getAudioInputStream(new File("assets/sounds/victory.wav")));
			defeat.open(AudioSystem.getAudioInputStream(new File("assets/sounds/defeat.wav")));
		}catch(Exception e) {
            e.printStackTrace();
		}
		
		
		pack();

		disableBoard();
		ModelConfigurer.getInstance().addObserver(this);
	}

	/** Tells grid to display the input grid */
	public void setGrid(Integer[][] grid) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				this.board[i][j].setValue(grid[i][j]);
				this.board[i][j].resetBackgroundColor();
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

	/** Tells the GUI to display the score from last move */
	public void setLastMoveScore(int lastMoveScore) {
		lblLastMoveScore.setText("" + lastMoveScore);
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
		this.board[row][col].setValue(value);
	}

	public void setActionType(String actionType) {
		for(BoardTile[] row : board) {
			for(BoardTile tile : row) {
				tile.setActionType(actionType);
			}
		}
	}

	/**Allows the board to accept input again */
	public void enableBoard() {
		for(BoardTile[] row : board) {
			for(BoardTile tile : row) {
				tile.enable();
			}
		}
	}

	/**Disables input from the board */
	public void disableBoard() {
		btnRefresh.setEnabled(false);
		btnRemoveSquares.setEnabled(false);
		btnHints.setEnabled(false);
		for(BoardTile[] row : board) {
			for(BoardTile tile : row) {
				tile.disable();
			}
		}
	}

	/**Allows the refresh queue button to be clicked */
	public void enableRefresh() {
		btnRefresh.setEnabled(true);
	}

	/**Disables the refresh queue button */
	public void disableRefresh() {
		btnRefresh.setEnabled(false);
	}

	public void highlightAllTilesOfValue(Integer value) {
		for(BoardTile[] row : board) {
			for(BoardTile tile : row) {
				if(tile.getValue() != null && tile.getValue() == value) {
					tile.setBackground(new Color(0xFF8888));
				} else {
					tile.resetBackgroundColor();
				}
			}
		}
	}
	
	public void highlightAllHintedTiles(boolean[][] tiles) {
		boolean empty = true;
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(tiles[i][j] == true) {
					empty = false;
					board[i][j].highlight(new Color(0xBBFFBB));
				} else {
					board[i][j].resetBackgroundColor();
				}
			}
		}

		if(empty) {
			JOptionPane.showMessageDialog(this, "There are no scoring moves");
		}
	}

	/** Implementation of update message from Observer interface
	 *  Can be sent messages in the form of Strings instructing it what 
	 *  specifically to update or if no message is supplied then it will update
	 *  everything
	 */
	public void update(Observable src, Object arg) {
		Object[] args = (Object[])arg;
		String message = (String)args[0];

		switch(message) {
			case "GAMELOST":
				defeat.setFramePosition(0);
				defeat.start();
				disableBoard();
				JOptionPane.showMessageDialog(this, "Game Over. You Lose.");
				break;
			case "GAMEWON":
				victory.setFramePosition(0);
				victory.start();
				disableBoard();
				break;
			case "TIMED_GAMEWON":
				victory.setFramePosition(0);
				victory.start();
				disableBoard();
				break;
			case "MOVES_REMAINING":
				setCountdownName("Moves Remaining: ");
				setCountdown("" + (int)args[1]);
				break;
			case "TIME_REMAINING":
				setCountdownName("Time Remaining: ");
				setCountdown("" + (String)args[1]);
				break;
			case "QUEUE_CHANGED":
				setQueue((Integer[])args[1]);
				break;
			case "ALL":
				setGrid((Integer[][])args[1]);
				setQueue((Integer[])args[2]);
				setScore((int)args[3]);
				setLastMoveScore((int)args[4]);
				btnHints.refresh();
				break;
			case "RULESET_CHANGED":
				((Observable)args[1]).addObserver(this);
				btnRefresh.setEnabled(true);
				btnRemoveSquares.setEnabled(true);
				btnHints.resetUses();
				enableBoard();
				break;
			case "TILE_REMOVED":
				tileRemoved.setFramePosition(0);
				tileRemoved.start();
				break;
			case "TILE_ADDED":
				tileAdded.setFramePosition(0);
				tileAdded.start();
				break;
			case "HINTS":
				highlightAllHintedTiles((boolean[][])args[1]);
				break;
			default:
				break;
		}
	}

	/** Controller class for Time Game Button */
	private class TimedGameButtonController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ModelConfigurer.getInstance().startGame(ModelConfigurer.TIMED);
		}
	}
	
	/** Controller class for Untimed Game Button */
	private class UntimedGameButtonController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ModelConfigurer.getInstance().startGame(ModelConfigurer.UNTIMED);
		}
	}
	
	/** Controller class for Refresh Queue Button */
	private class RefreshQueueButtonController implements ActionListener, Observer {
		private RuleSet rules;

		public RefreshQueueButtonController(Observable configurer) {
			configurer.addObserver(this);
		}

		public void actionPerformed(ActionEvent e) {
			if(rules != null) {
				btnRefresh.setEnabled(false);
				rules.refreshQueue();
			}
		}

		// Keeps and up-to-date reference to the current ruleset so it sends the refresh command 
		// to the current ruleset
		public void update(Observable src, Object arg) {
			Object[] args = (Object[])arg;
			String msg = (String)args[0];

			switch(msg) {
				case "RULESET_CHANGED":
					rules = (RuleSet)args[1];
					break;
				default:
					break;
			}
		}
	}

	private class RemoveSquaresController implements ActionListener, Observer {
		private int uses;
		private RuleSet rules;
		
		public RemoveSquaresController(Observable configurer) {
			configurer.addObserver(this);
		}

		public void actionPerformed(ActionEvent e) {
			btnRemoveSquares.setEnabled(false);
			setActionType("REMOVE");
		}

		//Use updates from the configurer to keep track of current ruleset
		public void update(Observable src, Object arg) {
			Object[] args = (Object[])arg;
			String msg = (String)args[0];
			switch(msg) {
				case "RULESET_CHANGED":
					rules = (RuleSet)args[1];
					break;
				default:
					break;
			}
		}
	}

	private class HintsButton extends JButton {
		public int uses = 0;

		public void refresh() {
			if(uses > 0) {
				setEnabled(true);
			} else {
				setEnabled(false);
			}
		}

		public void resetUses() {
			uses = 3;
			setText("Show Hints (" + uses + " uses left)");
			refresh();
		}

		public HintsButton(Observable configurer) {
			setText("Show Hints (" + uses + " uses left)");
			setFont(new Font("Arial", Font.BOLD, 12));
			setAlignmentX(CENTER_ALIGNMENT);
			setPreferredSize(new Dimension(180, 40));
			setMaximumSize(new Dimension(180, 40));
			addActionListener(new HintsController(configurer));
		}

		private class HintsController implements ActionListener, Observer {
			private RuleSet rules;
			private boolean[][] hintTiles;

			public HintsController(Observable configurer) {
				configurer.addObserver(this);
			}

			public void actionPerformed(ActionEvent e) {
				uses--;
				setText("Show Hints (" + uses + " uses left)");
				setEnabled(false);
				rules.displayHints();
			}

			//Use updates from the configurer to keep track of current ruleset
			public void update(Observable src, Object arg) {
				Object[] args = (Object[])arg;
				String msg = (String)args[0];
				switch(msg) {
					case "RULESET_CHANGED":
						rules = (RuleSet)args[1];
						break;
					default:
						break;
				}
			}
		}
	}
}


