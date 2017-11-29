import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class SumFunHighScoreLogic extends Observable{
	private static final String scoreFileName = "scores.txt";
	private static final String timeFileName = "times.txt";
	private static SumFunHighScoreLogic instance;
	private List<SumFunHighScore> scores = new ArrayList<SumFunHighScore>();
	private List<SumFunHighScore> times = new ArrayList<SumFunHighScore>();

	private SumFunHighScoreLogic() {
		scores = new ArrayList<SumFunHighScore>();
		times = new ArrayList<SumFunHighScore>();
	}
	
	public void loadFromFile() {
		try {
			List<String> scoreLines = Files.readAllLines(new File(scoreFileName).toPath());
			for(String line : scoreLines) {
				instance.add(new SumFunHighScore(line), false);
			}
		} catch (Exception e) {
			System.out.print("Missing Score File");
		}
		
		try {
			// Split lists for score and time values 
			// Will be stored in separate files
			List<String> timeLines = Files.readAllLines(new File(timeFileName).toPath());
			for(String line : timeLines) {
				instance.add(new SumFunHighScore(line), false);
			}
		} catch (Exception e) {
			System.out.print("Missing Time File");
		}
	}
	
	private void writeToFile() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(scoreFileName);
			for(SumFunHighScore high: scores) {
				writer.println(high.getName() + " " + high.getScore() + " " + high.getDate());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file was not found and could not be created.");
		}
	}

	public static SumFunHighScoreLogic getInstance() {
		if(instance == null) {
			instance = new SumFunHighScoreLogic();
		}
		return instance;
	}

	public void add(String name, int score, boolean isTimedGame) {
		SumFunHighScore newScore = new SumFunHighScore(name, score);
		add(newScore, isTimedGame);
	}

	public void add(SumFunHighScore newScore, boolean isTimedGame) {
		//Don't submit anything if the user didn't enter a name
		if(newScore.getName() == null || newScore.getName().equals("")) {
			return;
		}

		//Scores are always checked, but timed are only checked if that is the game mode
		//Add the new highscore to the list then sort it so the lowest highscores are at the bottom
		//If we have over 10 highscores then cut off the lowest one at the bottom
		scores.add(newScore);
		Collections.sort(scores, Collections.reverseOrder());
		if(scores.size() > 10) {
			scores.remove(10);
		}
		if(isTimedGame == true) {
			times.add(newScore);
			Collections.sort(times, Collections.reverseOrder());
			if(times.size() > 10) {
				times.remove(10);
			}
		}
		setChanged();
		notifyObservers(new Object[]{"HIGHSCORE_CHANGED", encodeScoresToStringArrays()});

		writeToFile();
	}
	
	//Encodes scores to string arrays to reduce Coupling with HighScore class
	private String[][] encodeScoresToStringArrays() {
		String[][] scoreStrings = new String[scores.size()][3];
		for(int i = 0; i < scores.size(); i++) {
			scoreStrings[i][0] = scores.get(i).getName();
			scoreStrings[i][1] = "" + scores.get(i).getScore();
			scoreStrings[i][2] = scores.get(i).getDate();
		}
		return scoreStrings;
	}
}
