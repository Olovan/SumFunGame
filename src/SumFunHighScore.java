import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
public class SumFunHighScore extends Observable implements Comparable {
     private String name;
     private String date;
     private int score;
     private SumFunHighScoreLogic logic = new SumFunHighScoreLogic();
     
     public SumFunHighScore() {
       
     }
     
     public SumFunHighScore(String name, int score) {
       this.name = name;
       this.score = score;
       this.date  = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
     }
     
     public int getScore() {
       return score;
     }
     
     public String getDate() {
       return date;
     }
     
     public String getName() {
       return name;
     }
     
     public void addScore(String name, int score) {
       if(name != null && !(name.equals("")))
         logic.add(new SumFunHighScore(name, score));
       else
         return;
     }
     
     @Override
     public int compareTo(Object highScore) {
         int compareScore=((SumFunHighScore)highScore).getScore();

         /* For Descending order do like this */
         return compareScore-this.score;
         
         /* For Ascending order do like this*/
         //return this.score - compareScore;
     }

     @Override
     public String toString() {
         return "[Name=" + name + ", Score=" + score + ", Date=" + date + "]";
     }

}


//private ArrayList<HighScore> scores = new ArrayList<HighScore>();
//private String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
//
//public void add(HighScore newScore) {
//    scores.add(newScore);
//    Collections.sort(scores);
//    
//    if(scores.size() > 10) {
//      scores.remove(10);
//    }
//}