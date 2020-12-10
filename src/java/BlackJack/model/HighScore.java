package BlackJack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by David Hedman <br>
 * Date: 2020-12-03 <br>
 * Time: 13:23 <br>
 * Project: BlackJackOOAD <br>
 * Copyright: Nackademin <br>
 */
public class HighScore {
    private static HighScore instance;
    private List<HighScoreObject> list;
    private final int maxListLength = 10;

    private HighScore() {
        getHighScoresFromFile();
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
            if(score <= list.get(list.size()-1).getScore()){
                return false;
            } else {
                list.remove(list.size()-1);
                list.add(new HighScoreObject(name, score));
            }
        }
        Collections.sort(list);
        Collections.reverse(list);
        return true;
    }

    private void getHighScoresFromFile() {
        List<String> tempList = IOUtil.readFromFileToList("src/resources/highScore.txt");
        list = new ArrayList<>();
        for (String s : tempList) {
            int score = Integer.parseInt(s.substring(0, s.indexOf("|")));
            String name = s.substring(s.indexOf("|") + 1);
            list.add(new HighScoreObject(name, score));
        }
        Collections.sort(list);
        Collections.reverse(list);
    }

    public void writeHighScoreToFile(){
        List<String> tempList = new ArrayList<>();
        for(var a : this.list){
            tempList.add(a.getScore() + "|" + a.getName());
        }
        IOUtil.writeListToFile(tempList, "src/resources/highScore.txt");
    }
}
