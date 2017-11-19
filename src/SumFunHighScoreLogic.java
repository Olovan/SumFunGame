import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

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
	
	private void writeToFile() {
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

	public static SumFunHighScoreLogic getInstance() {
		if(instance == null) {
			instance = new SumFunHighScoreLogic();
		}
		return instance;
	}

	public void add(String name, int score) {
		SumFunHighScore newScore = new SumFunHighScore(name, score);
		add(newScore);
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
