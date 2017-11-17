import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Component;

public class SumFunLeaderboardGui extends JFrame implements Observer {

  private JPanel contentPane;
  private JLabel scoreType;
  private JLabel leaderboardList;
  
  /** Each time the leaderboard is called, it will be updated 
   * based on the "leaderboardEntries" Object[] it receives
   * in the form of a preordered array. This would be in the 
   * form of {"Name - Score", "Name - Score", "Name - Score"} */
  public SumFunLeaderboardGui(String leaderboardEntries) {
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);
    
    /** Leaderboard is filled with entries concatenated
     * into a string and separated by "\n" */
    leaderboardList = new JLabel(leaderboardEntries);
    contentPane.add(leaderboardList, BorderLayout.CENTER);
    
    /** This will be changed based on which version of the 
     * game the user is currently playing. */
    scoreType = new JLabel("Moves Scores");
    contentPane.add(scoreType, BorderLayout.NORTH);
    scoreType.setAlignmentY(Component.TOP_ALIGNMENT);
    scoreType.setFont(new Font("Arial", Font.BOLD, 20));
  }
  
  /** Enables visibility of the leaderboard */
  public void showLeaderboard()
  {
    contentPane.setVisible(true);
  }
  
  /** Disables visibility of the leaderboard */
  public void hideLeaderboard()
  {
    contentPane.setVisible(false);
  }

  @Override
  public void update(Observable src, Object arg) {
    /** @TODO: fill this in */
    
  }
}
