import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class SumFunGridButton extends JPanel {
	//Settings
	private final Color MOUSEOVER_COLOR = new Color(0xFFFFDD);
	private final Color HIGHLIGHT_COLOR = new Color(0xFFFF00);
	private final Color BACKGROUND_COLOR = new Color(0xEEEEEE);
	private final Color DISABLED_COLOR = new Color(0xD0D0D0);
	private final int FONT_SIZE = 34;
	
	private Controller c;
	private Color currentBackgroundColor;
	
	public JLabel text;
	public int row, col;
	private boolean enabled = true;
	
	/** Instantiates Grid button and all of its members/listeners */
	public SumFunGridButton(int row, int col) {
		this.row = row;
		this.col = col;

		currentBackgroundColor = BACKGROUND_COLOR;
		
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOR);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		
		text = new JLabel();
		text.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		text.setVerticalAlignment(JLabel.CENTER);
		text.setHorizontalAlignment(JLabel.CENTER);
		add(text, BorderLayout.CENTER);
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(!enabled)
					return;
				// Report Mouse Click to Backend
				c.gridAction(row, col);
			}
			
			public void mouseEntered(MouseEvent e) {
				if(!enabled)
					return;
				setBackground(MOUSEOVER_COLOR);
			}
			
			public void mouseExited(MouseEvent e) {
				if(!enabled)
					return;
				setBackground(currentBackgroundColor);
			}
		});	
	}
	
	/**Sets the Grid Tile's string to match the input value
	 * A null value results in an empty tile */
	public void setValue(Integer val) {
		if(val != null)
			text.setText(val.toString());
		else
			text.setText("");
	}
	
	/** Assigns the controller that the tile reports mouse clicks to */
	public void setController(Controller c) {
		this.c = c;
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
		setBackground(DISABLED_COLOR);
		enabled = false;
	}

	/** Highlights a tile for cheating purposes */
	public void highlight() {
		currentBackgroundColor = HIGHLIGHT_COLOR;
		setBackground(new Color(0xFFFF00));
	}
}
