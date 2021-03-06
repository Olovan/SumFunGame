package sumfun;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

public class HighScore extends Observable implements Comparable<HighScore> {
     private String name;
     private String date;
     private int score;
     
     public HighScore() {
       
     }
     
     public HighScore(String name, int score) {
       this.name = name.replaceAll(" ", "");
       this.score = score;
       this.date  = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
     }

	 public HighScore(String inputString) {
		 String[] fields = inputString.split(" ");
		 name = fields[0];
		 score = Integer.parseInt(fields[1]);
		 date = fields[2];
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

	 public String encodeToString() {
		 return name + " " + score + " " + date;
	 }
     
     @Override
     public int compareTo(HighScore highScore) {
         int compareScore = highScore.getScore();

         return this.score - compareScore;
     }

     @Override
     public String toString() {
         return "[Name=" + name + ", Score=" + score + ", Date=" + date + "]";
     }

}