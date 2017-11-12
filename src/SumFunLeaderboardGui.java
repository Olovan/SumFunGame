import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import java.awt.Component;

public class SumFunLeaderboardGui extends JFrame{

  private JPanel contentPane;
  private JLabel scoreType;
  private JList leaderboardList;
  
  /** Each time the leaderboard is called, it will be updated 
   * based on the "leaderboardEntries" Object[] it receives
   * in the form of a preordered array. This would be in the 
   * form of {"Name - Score", "Name - Score", "Name - Score"} */
  public SumFunLeaderboardGui(Object[] leaderboardEntries) {
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);
    
    leaderboardList = new JList(leaderboardEntries);
    contentPane.add(leaderboardList, BorderLayout.CENTER);
    
    /** This will be changed based on which version of the 
     * game the user is currently playing. */
    scoreType = new JLabel("Moves Scores");
    contentPane.add(scoreType, BorderLayout.NORTH);
    scoreType.setAlignmentY(Component.TOP_ALIGNMENT);
    scoreType.setFont(new Font("Arial", Font.BOLD, 20));
  }
  
  /** Enables visibility of the leaderboard */
  public void showJFrame()
  {
    contentPane.setVisible(true);
  }
}
