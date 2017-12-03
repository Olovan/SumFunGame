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
			// Load HighScore data
			List<String> scoreLines = Files.readAllLines(new File(scoreFileName).toPath());
			List<String> timeLines = Files.readAllLines(new File(timeFileName).toPath());

			for(String line : scoreLines) {
				instance.addScore(new SumFunHighScore(line));
			}

			for(String line : timeLines) {
				instance.addTime(new SumFunHighScore(line));
			}
		} catch (Exception e) {
			System.out.print("Missing Score File");
		}
		
		try {
		} catch (Exception e) {
			System.out.print("Missing Time File");
		}
	}
	
	private void writeToFile() {
		PrintWriter scoreWriter;
		PrintWriter timeWriter;
		try {
			scoreWriter = new PrintWriter(scoreFileName);
			//Record best scores in scores file
			for(SumFunHighScore high: scores) {
				scoreWriter.println(high.getName() + " " + high.getScore() + " " + high.getDate());
			}
			scoreWriter.close();

			timeWriter = new PrintWriter(timeFileName);

			//Record best times in times file
			for(SumFunHighScore time: times) {
				timeWriter.println(time.getName() + " " + time.getScore() + " " + time.getDate());
			}
			timeWriter.close();
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

	public void addScore(String name, int score) {
		SumFunHighScore newScore = new SumFunHighScore(name, score);
		addScore(newScore);
	}

	public void addTime(String name, int time) {
		SumFunHighScore newTime = new SumFunHighScore(name, time);
		addTime(newTime);
	}

	public void addScore(SumFunHighScore newScore) {
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
		notifyObservers(new Object[]{"HIGHSCORE_CHANGED", encodeScoresToStringArrays()});

		writeToFile();
	}
	
	/** Adds a new best time to the time list and sorts the best time list and removes any overflow */
	public void addTime(SumFunHighScore newTime) {
		//Don't submit anything if the user didn't enter a name
		if(newTime.getName() == null || newTime.getName().equals("")) {
			return;
		}

		//Add the new highscore to the list then sort it so the lowest highscores are at the bottom
		//If we have over 10 highscores then cut off the lowest one at the bottom
		times.add(newTime);
		Collections.sort(times);
		if(times.size() > 10) {
			times.remove(10);
		}
		setChanged();
		notifyObservers(new Object[]{"BEST_TIME_CHANGED", encodeTimesToStringArrays()});

		writeToFile();
	}

	/** Encodes scores to string arrays to reduce Coupling with Highscore class */
	private String[][] encodeScoresToStringArrays() {
		String[][] scoreStrings = new String[scores.size()][3];
		for(int i = 0; i < scores.size(); i++) {
			scoreStrings[i][0] = scores.get(i).getName();
			scoreStrings[i][1] = "" + scores.get(i).getScore();
			scoreStrings[i][2] = scores.get(i).getDate();
		}
		return scoreStrings;
	}

	/** Encodes time to string arrays to reduce Coupling with Highscore class */
	private String[][] encodeTimesToStringArrays() {
		String[][] timeStrings = new String[times.size()][3];
		for(int i = 0; i < times.size(); i++) {
			timeStrings[i][0] = times.get(i).getName();
			timeStrings[i][1] = String.format("%02d:%02d", times.get(i).getScore() / 60, times.get(i).getScore() % 60);
			timeStrings[i][2] = times.get(i).getDate();
		}
		return timeStrings;
	}
}
