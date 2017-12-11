package sumfun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class LeaderboardModel extends Observable{
	private static final String scoreFileName = "scores.txt";
	private static final String timeFileName = "times.txt";
	private static LeaderboardModel instance;
	private List<HighScore> scores = new ArrayList<HighScore>();
	private List<HighScore> times = new ArrayList<HighScore>();

	private LeaderboardModel() {
		scores = new ArrayList<HighScore>();
		times = new ArrayList<HighScore>();
	}
	
	public void loadFromFile() {
		try {
			// Load HighScore data
			List<String> scoreLines = Files.readAllLines(new File(scoreFileName).toPath());
			List<String> timeLines = Files.readAllLines(new File(timeFileName).toPath());

			for(String line : scoreLines) {
				instance.addScore(new HighScore(line));
			}

			for(String line : timeLines) {
				instance.addTime(new HighScore(line));
			}
		} catch (Exception e) {
			System.out.print("Missing Score File");
		}
	}
	
	private void writeToFile() {
		PrintWriter scoreWriter;
		PrintWriter timeWriter;
		try {
			scoreWriter = new PrintWriter(scoreFileName);
			//Record best scores in scores file
			for(HighScore high: scores) {
				scoreWriter.println(high.getName() + " " + high.getScore() + " " + high.getDate());
			}
			scoreWriter.close();

			timeWriter = new PrintWriter(timeFileName);

			//Record best times in times file
			for(HighScore time: times) {
				timeWriter.println(time.getName() + " " + time.getScore() + " " + time.getDate());
			}
			timeWriter.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file was not found and could not be created.");
		}
	}

	public static LeaderboardModel getInstance() {
		if(instance == null) {
			instance = new LeaderboardModel();
		}
		return instance;
	}

	public void addScore(HighScore newScore) {
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
	
	public void addScore(String name, int score) {
		HighScore newScore = new HighScore(name, score);
		addScore(newScore);
	}
	
	/** Adds a new best time to the time list and sorts the best time list and removes any overflow */
	public void addTime(HighScore newTime) {
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
	
	public void addTime(String name, int time) {
		HighScore newTime = new HighScore(name, time);
		addTime(newTime);
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
			timeStrings[i][1] = String.format("%01d:%02d", times.get(i).getScore() / 60, times.get(i).getScore() % 60);
			timeStrings[i][2] = times.get(i).getDate();
		}
		return timeStrings;
	}
}
