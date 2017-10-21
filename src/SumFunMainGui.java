import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class SumFunMainGui extends JFrame {
	
	//PURELY FOR TESTING PURPOSES
	private Integer[][] testGrid = {
			new Integer[]{null, null, null, null, null, null, null, null, null},
			new Integer[]{null, 1, 2, 3, 4, 5, 6, 7, null},
			new Integer[]{null, 8, 9, 0, 1, 2, 3, 4, null},
			new Integer[]{null, 8, 9, 0, 1, 2, 3, 4, null},
			new Integer[]{null, 8, 9, 0, 1, 2, 3, 4, null},
			new Integer[]{null, 8, 9, 0, 1, 2, 3, 4, null},
			new Integer[]{null, 8, 9, 0, 1, 2, 3, 4, null},
			new Integer[]{null, 8, 9, 0, 1, 2, 3, 4, null},
			new Integer[]{null, null, null, null, null, null, null, null, null}
	};
	private Integer[] testQueue = {1, 2, 3, 4, 5};

	private JPanel contentPane;
	private SumFunGridButton[][] grid;
	private JLabel[] queue;
	private JLabel lblScore;
	private JLabel countdown;
	private JLabel countdownName;
	private Controller controller;


	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SumFunMainGui frame = new SumFunMainGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

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
		
		Component verticalGlue = Box.createVerticalGlue();
		rightPanel.add(verticalGlue);
		
		grid = new SumFunGridButton[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				grid[i][j] = new SumFunGridButton(i, j);
				gridPanel.add(grid[i][j]);
			}
		}
		
		queue = new JLabel[5];
		for(int i = 0; i < 5; i++) {
			queue[i] = new JLabel("0");
			queuePanel.add(queue[i]);
		}
		
		//TEST CODE
		setGrid(testGrid);
		setQueue(testQueue);
		setScore(9000);
		setCountdown("50");
		setCountdownName("Moves Remaining");
		
		
		pack();
	}
	
	public void setGrid(Integer[][] grid) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				this.grid[i][j].setValue(grid[i][j]);
			}
		}
	}
	
	public void setQueue(Integer[] queue) {
		for(int i = 0; i < 5; i++) {
			this.queue[i].setText(queue[i].toString());
		}
	}
	
	public void setScore(int score) {
		lblScore.setText("" + score);
	}
	
	public void setCountdown(String text) {
		countdown.setText(text);
	}
	
	public void setCountdownName(String text) {
		countdownName.setText(text);
	}

}
