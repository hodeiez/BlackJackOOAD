package BlackJack.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScore {
    private static HighScore instance;
    private List<HighScoreObject> list;
    private final int maxListLength = 10;
    public StringProperty names = new SimpleStringProperty();  //For JavaFX
    public StringProperty scores = new SimpleStringProperty(); //For JavaFX
    public StringProperty dates = new SimpleStringProperty();  //For JavaFX

    private HighScore() {
        getHighScoresFromFile();
        updateStrings();
    }

    private void updateStrings() {
        String names = "";
        String scores = "";
        String dates = "";
        for (var a : list) {
            names += a.getName() + "\n";
            scores += a.getScore() + "\n";
            dates += a.getDate() + "\n";
        }
        String finalNames = names;
        String finalScores = scores;
        String finalDates = dates;
        Platform.runLater(() -> this.names.set(finalNames));
        Platform.runLater(() -> this.scores.set(finalScores));
        Platform.runLater(() -> this.dates.set(finalDates));
    }


    public static HighScore getInstance() {
        if (instance == null) {
            instance = new HighScore();
        }
        return instance;
    }

    public List<HighScoreObject> getHighScores() {
        return this.list;
    }

    public boolean addHighScore(String name, int score) {
        if (this.list.size() < maxListLength) {
            list.add(new HighScoreObject(name, score));
        } else {
            if (score <= list.get(list.size() - 1).getScore()) {
                return false;
            } else {
                list.remove(list.size() - 1);
                list.add(new HighScoreObject(name, score));
            }
        }
        Collections.sort(list);
        Collections.reverse(list);
        updateStrings();
        writeHighScoreToFile();
        return true;
    }

    private void getHighScoresFromFile() {
        List<String> tempList = IOUtil.readFromFileToList("src/resources/highScore.txt");
        list = new ArrayList<>();
        for (String s : tempList) {
            int index = 0;
            index = s.indexOf("|");
            int score = Integer.parseInt(s.substring(0, index));
            index++;
            String name = s.substring(index, s.indexOf("|", index));
            index = s.indexOf("|", index) + 1;
            LocalDate date = LocalDate.parse(s.substring(index));
            list.add(new HighScoreObject(name, score, date));
        }
        Collections.sort(list);
        Collections.reverse(list);
        while(list.size() > maxListLength){
            list.remove(list.size()-1);
        }
    }

    public void writeHighScoreToFile() {
        List<String> tempList = new ArrayList<>();
        for (var a : this.list) {
            tempList.add(a.getScore() + "|" + a.getName() + "|" + a.getDate());
        }
        IOUtil.writeListToFile(tempList, "src/resources/highScore.txt");
    }
}
