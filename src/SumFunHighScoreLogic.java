import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Observable;
import java.nio.file.Files;
import java.io.File;

public class SumFunHighScoreLogic extends Observable{
	private static final String fileName = "scores.txt";
	private static SumFunHighScoreLogic instance;
	private List<SumFunHighScore> scores = new ArrayList<SumFunHighScore>();

	private SumFunHighScoreLogic() {
		scores = new ArrayList<SumFunHighScore>();
		try {
			List<String> lines = Files.readAllLines(new File(fileName).toPath());
			for(String line : lines) {
				scores.add(new SumFunHighScore(line));
			}
		} catch (Exception ignoredForNow) {
			System.out.print("Missing HighScore File");
		}

	}

	public static SumFunHighScoreLogic getInstance() {
		if(instance == null)
			instance = new SumFunHighScoreLogic();
		return instance;
	}

	//TODO:method has to read from the file to stop it from deleting previous scores
	//when reading from file, make sure that the amount of objects in the array is <= 10
	public void add(SumFunHighScore newScore) {
		if(newScore.getName() == null || newScore.getName().equals(""))
			return;
		
		scores.add(newScore);
		Collections.sort(scores, Collections.reverseOrder());

		if(scores.size() > 10) {
			scores.remove(10);
		}

		setChanged();
		notifyObservers(new Object[]{"HIGHSCORE_CHANGED", scores});


		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName);
			for(SumFunHighScore high: scores) {
				writer.println(high.getName() + " " + high.getScore() + " " + high.getDate());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file was not found and could not be created.");
		}
	}
}
