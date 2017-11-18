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
	}

	public void loadFromFile() {
		try {
			List<String> lines = Files.readAllLines(new File(fileName).toPath());
			for(String line : lines) {
				instance.add(new SumFunHighScore(line));
			}
		} catch (Exception e) {
			System.out.print("Missing HighScore File");
		}
	}

	public static SumFunHighScoreLogic getInstance() {
		if(instance == null) {
			instance = new SumFunHighScoreLogic();
		}
		return instance;
	}

	public void add(SumFunHighScore newScore) {
		//Don't submit anything if the user didn't enter a name
		if(newScore.getName() == null || newScore.getName().equals("")) {
			return;
		}

		//Add the new highscore to the list then sort it so the lowest highscores are at the bottom
		//If we have over 10 highscores then cut off the lowest one at the bottom
		scores.add(newScore);
		Collections.sort(scores, Collections.reverseOrder());
		if(scores.size() > 10) {
			scores.remove(10);
		}
		setChanged();
		notifyObservers(new Object[]{"HIGHSCORE_CHANGED", scores});

		//Write new highscore list to file
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
