import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class SumFunMainGui extends JFrame {
	
	private JPanel contentPane;
	private SumFunGridButton[][] grid;
	private JLabel[] queue;
	private JLabel lblScore;
	private JLabel countdown;
	private JLabel countdownName;
	private Controller controller;

	/**
	 * Create the frame.
	 */
	public SumFunMainGui(Controller c) {
		controller = c;
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
		gridPanel.setMinimumSize(new Dimension(800, 800));
		gridPanel.setPreferredSize(new Dimension(800, 800));
		centerPanel.add(gridPanel);
		gridPanel.setLayout(new GridLayout(9, 9, 0, 0));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(100, 800));
		centerPanel.add(rightPanel);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		JPanel scorePanel = new JPanel();
		rightPanel.add(scorePanel);
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Score");
		scorePanel.add(lblNewLabel);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
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
		
		Component verticalGlue = Box.createVerticalGlue();
		rightPanel.add(verticalGlue);
		
		//Instantiate Grid
		grid = new SumFunGridButton[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				grid[i][j] = new SumFunGridButton(i, j);
				grid[i][j].setController(c);
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
	}

	//Tells grid to display the input grid
	public void setGrid(Integer[][] grid) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				this.grid[i][j].setValue(grid[i][j]);
			}
		}
	}
	
	//Tells the Gui to display the input queue
	public void setQueue(Integer[] queue) {
		for(int i = 0; i < 5; i++) {
			this.queue[i].setText(queue[i].toString());
		}
	}
	
	//Tells GUI to display the input score
	public void setScore(int score) {
		lblScore.setText("" + score);
	}
	
	//Tells GUI to display the input countdown string
	public void setCountdown(String text) {
		countdown.setText(text);
	}
	
	//Tells GUI to display the input countdown name
	//Used so that the countdown field can be used to display
	//both moves remaining and time remaining
	public void setCountdownName(String text) {
		countdownName.setText(text);
	}
	
	//Tells a specific grid tile to display the input value
	public void setGridValue(Integer value, int row, int col) {
		this.grid[row][col].setValue(value);
	}

	//Locks the board and displays the game over Message
	public void gameOver() {
		disableBoard();
		JOptionPane.showMessageDialog(this, "Game Over");
	}

	//Allows the board to accept input again
	public void enableBoard() {
		for(SumFunGridButton[] row : grid)
			for(SumFunGridButton tile : row)
				tile.enable();
	}

	//Disables input from the board
	public void disableBoard() {
		for(SumFunGridButton[] row : grid)
			for(SumFunGridButton tile : row)
				tile.disable();
	}

}
