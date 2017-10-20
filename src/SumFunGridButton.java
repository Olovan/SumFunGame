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
	private final Color HIGHLIGHT_COLOR = new Color(0xFFFFFF);
	private final Color BACKGROUND_COLOR = new Color(0xF0F0F0);
	private final int FONT_SIZE = 34;
	
	public JLabel text;
	public int row, col;
	
	public SumFunGridButton(int row, int col) {
		this.row = row;
		this.col = col;
		
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
				// Report Mouse Click to Backend
				setBackground(new Color(0x00FF00));
			}
			
			public void mouseEntered(MouseEvent e) {
				setBackground(HIGHLIGHT_COLOR);
			}
			
			public void mouseExited(MouseEvent e) {
				setBackground(BACKGROUND_COLOR);
			}
		});	
	}
	
	public void setValue(Integer val) {
		if(val != null)
			text.setText(val.toString());
		else
			text.setText("");
	}
}
