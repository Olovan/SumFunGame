import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class SumFunGridButton extends JPanel {
	//Settings
	private static final Color MOUSEOVER_COLOR = new Color(0xFFFFDD);
	private static final Color BACKGROUND_COLOR = new Color(0xEEEEEE);
	private static final Color DISABLED_COLOR = new Color(0xD0D0D0);
	private static final int FONT_SIZE = 34;
	private static final String PLACEMENT_ACTION_TYPE = "ADD";
	private static final String REMOVAL_ACTION_TYPE = "REMOVE";

	private Color currentBackgroundColor;
	private String actionType;
	private boolean enabled = true;
	private Integer value = null;
	private int row;
	private int col;

	public JLabel text;

	/** Instantiates Grid button and all of its members/listeners */
	public SumFunGridButton(SumFunMainGui gui, int row, int col, Observable configurer) {
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
		value = val;
		if(val != null) {
			text.setText(val.toString());
		}  else {
			text.setText("");
		}
	}

	/** gets grid tile value */
	public Integer getValue() {
		return value;
	}

	/** Enables the tile allowing it to respond to mouse input */
	@SuppressWarnings("deprecation")
	public void enable() {
		currentBackgroundColor = BACKGROUND_COLOR;
		setBackground(BACKGROUND_COLOR);
		enabled = true;
	}

	/** Disables the tile preventing it from responding to mouse input
	 *  Changes the tile's background to indicate that it is disabled */
	@SuppressWarnings("deprecation")
	public void disable() {
		currentBackgroundColor = DISABLED_COLOR;
		setBackground(DISABLED_COLOR);
		enabled = false;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Color getCurrentBackgroundColor() {
		return currentBackgroundColor;
	}


	/** Controller for Board Tiles */
	private class TileController implements MouseListener, Observer{
		private SumFunRuleSet ruleSet;
		private SumFunMainGui gui;

		public TileController(SumFunMainGui gui, Observable configurer) {
			this.gui = gui;
			configurer.addObserver(this);
		}

		public void mouseClicked(MouseEvent e) {
			if(!enabled || ruleSet == null) {
				return;
			}

			switch(actionType) {
				case PLACEMENT_ACTION_TYPE:
					ruleSet.gridAction(row, col);
					break;
				case REMOVAL_ACTION_TYPE:
					// Something to the effect of: ruleSet.removeAll( value );
					break;
			}
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
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void update(Observable src, Object arg) {
			Object[] args = (Object[])arg;
			String msg = (String)args[0];
			switch(msg) {
				case "RULESET_CHANGED":
					ruleSet = (SumFunRuleSet)args[1];
					break;
				default:
					break;
			}
		}
	}
}
