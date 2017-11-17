import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
public class SumFunHighScoreLogic {
  private ArrayList<SumFunHighScore> scores = new ArrayList<SumFunHighScore>();
  
  //TODO:method has to read from the file to stop it from deleting previous scores
  //when reading from file, make sure that the amount of objects in the array is <= 10
  public void add(SumFunHighScore newScore) {
   //wouldn't the issue be that a new arraylist is being created every time we call the program?
   //since we are instantiating a new SumFunHighScoreLogic everytime we boot up the system? 
    scores.add(newScore);
    Collections.sort(scores);
    
    if(scores.size() > 10) {
      scores.remove(10);
    }
    

    PrintWriter writer;
    try {
      writer = new PrintWriter("scores.txt");
//      writer.printf("%-18s%-18s%-18s\n", "NAME", "SCORE", "DATE");
//      writer.println("------------------------------------------------");
      
      for(SumFunHighScore high: scores) {
        writer.println(high.getName() + " " + high.getScore() + " " + high.getDate());
      }
      writer.close();
    } catch (FileNotFoundException e) {
      System.out.println("The file was not found.");
    }
    
    for(SumFunHighScore high: scores) {
      System.out.println(high);
    }
    
  }
}