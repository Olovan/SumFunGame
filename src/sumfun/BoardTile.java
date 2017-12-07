package sumfun;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class BoardTile extends JPanel {
	//Settings
	private static final Color MOUSEOVER_COLOR = new Color(0xFFFFDD);
	private static final Color BACKGROUND_COLOR = new Color(0xEEEEEE);
	private static final Color DISABLED_COLOR = new Color(0xD0D0D0);
	private static final Color REMOVAL_FLASH_COLOR = new Color(0x88EE88);
	private static final Color ADDITION_FLASH_COLOR = new Color(0xEE8888);
	private static final int FONT_SIZE = 50;
	private static final String PLACEMENT_ACTION_TYPE = "ADD";
	private static final String REMOVAL_ACTION_TYPE = "REMOVE";
	private static final Color[] numberColors = new Color[] {
			new Color(0x009900), //0
			new Color(0x330099), //1
			new Color(0xcc33cc), //2
			new Color(0x663333), //3
			new Color(0x666699), //4
			new Color(0x990099), //5
			new Color(0x996600), //6
			new Color(0xcc6600), //7
			new Color(0x9900ff), //8
			new Color(0xcc3333)  //9
	};


	private Color currentBackgroundColor;
	private String actionType;
	private boolean enabled = true;
	private Integer value = null;
	private int row;
	private int col;

	public JLabel text;

	/** Instantiates Grid button and all of its members/listeners */
	public BoardTile(MainGui gui, int row, int col, Observable configurer) {
		currentBackgroundColor = BACKGROUND_COLOR;
		this.row = row;
		this.col = col;
		actionType = PLACEMENT_ACTION_TYPE;

		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOR);
		setBorder(new LineBorder(new Color(0, 0, 0)));

		text = new JLabel();
		text.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		text.setVerticalAlignment(JLabel.CENTER);
		text.setHorizontalAlignment(JLabel.CENTER);
		add(text, BorderLayout.CENTER);

		addMouseListener(new TileController(gui, configurer));	
	}

	/**Sets the Grid Tile's string to match the input value
	 * A null value results in an empty tile */
	public void setValue(Integer val) {
		if(value != val && val == null) {
			new HighlightAction(REMOVAL_FLASH_COLOR, 0.5f);
		} else if(value != val) {
			new HighlightAction(ADDITION_FLASH_COLOR, 0.5f);
		}
		
		if(val != null) {
			text.setText(val.toString());
			text.setForeground(numberColors[val]);
		}  else {
			text.setText("");
		}
		value = val;
	}
	
	/** gets grid tile value */
	public Integer getValue() {
		return value;
	}

	public void highlight(Color highlightColor) {
		currentBackgroundColor = highlightColor;
		setBackground(highlightColor);
	}

	/** resets the tile to the default background color */
	public void resetBackgroundColor() {
		currentBackgroundColor = BACKGROUND_COLOR;
		setBackground(BACKGROUND_COLOR);
	}

	/** Enables the tile allowing it to respond to mouse input */
	@SuppressWarnings("deprecation")
	public void enable() {
		resetBackgroundColor();
		enabled = true;
	}

	/** Disables the tile preventing it from responding to mouse input
	 *  Changes the tile's background to indicate that it is disabled */
	@SuppressWarnings("deprecation")
	public void disable() {
		currentBackgroundColor = DISABLED_COLOR;
		setBackground(DISABLED_COLOR);
		text.setForeground(Color.BLACK);
		enabled = false;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Color getCurrentBackgroundColor() {
		return currentBackgroundColor;
	}

	@Override
	public void setBackground(Color color) {
		if(!enabled) {
			super.setBackground(DISABLED_COLOR);
		} else {
			super.setBackground(color);
		}
	}

	/** Controller for Board Tiles */
	private class TileController implements MouseListener, Observer{
		private RuleSet ruleSet;
		private MainGui gui;

		public TileController(MainGui gui, Observable configurer) {
			this.gui = gui;
			configurer.addObserver(this);
		}

		public void mouseClicked(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
			if(!enabled) {
				return;
			}

			switch(actionType) {
				case PLACEMENT_ACTION_TYPE: //Normal mouseover
					setBackground(MOUSEOVER_COLOR);
					break;
				case REMOVAL_ACTION_TYPE:  //MouseOver when we are looking to delete something
					gui.highlightAllTilesOfValue(value);
					break;
			}
		}
		public void mouseExited(MouseEvent e) {
			if(!enabled) {
				return;
			}
			setBackground(currentBackgroundColor);
		}
		public void mousePressed(MouseEvent e) {
			if(!enabled || ruleSet == null) {
				return;
			}

			switch(actionType) {
				case PLACEMENT_ACTION_TYPE:
					ruleSet.gridAction(row, col);
					break;
				case REMOVAL_ACTION_TYPE:
					//Can't remove an empty tile
					if(value == null) {
						return;
					}
					ruleSet.removeAllTilesOfValue(value);
					gui.setActionType(PLACEMENT_ACTION_TYPE);
					break;
			}
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void update(Observable src, Object arg) {
			Object[] args = (Object[])arg;
			String msg = (String)args[0];
			switch(msg) {
				case "RULESET_CHANGED":
					ruleSet = (RuleSet)args[1];
					break;
				default:
					break;
			}
		}
	}

	private class HighlightAction implements ActionListener {
		private static final int CALL_INTERVAL = 50; //Time between ticks in milliseconds

		Color color;
		float totalTime;
		float currentTime;
		Timer timer;


		public HighlightAction(Color color, float time) {
			this.color = color;
			this.totalTime = time;
			this.currentTime = 0;
			setBackground(color);

			timer = new Timer(CALL_INTERVAL, this);
			timer.start();
		}

		public void actionPerformed(ActionEvent e) {
			currentTime += 0.05;
			currentTime = (currentTime > totalTime) ? totalTime : currentTime;
			float lerpFactor = (currentTime) / totalTime;
			Color background = lerpColor(color, BACKGROUND_COLOR, lerpFactor);
			setBackground(background);

			if(currentTime == totalTime) {
				timer.stop();
			}
		}

		private Color lerpColor(Color start, Color end, float factor) {
			factor = (factor >= 0) ? ((factor <= 1) ? factor : 1) : 0;
			float inverseFactor = 1 - factor;
			float red = start.getRed() * inverseFactor + end.getRed() * factor;
			float green = start.getGreen() * inverseFactor + end.getGreen() * factor;
			float blue = start.getBlue() * inverseFactor + end.getBlue() * factor;

			return new Color(red / 255, green / 255, blue / 255);
		}
	}
}
