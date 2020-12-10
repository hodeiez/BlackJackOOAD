package BlackJack;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by David Hedman <br>
 * Date: 2020-12-03 <br>
 * Time: 14:03 <br>
 * Project: BlackJackOOAD <br>
 * Copyright: Nackademin <br>
 */
public class HighScoreObject implements Comparable<HighScoreObject>{
    private String name;
    private int score;
    private LocalDate date; //maybe for later use?

    HighScoreObject(String name, int score){
       this.name = name;
       this.score = score;
       this.date = LocalDate.now();
    }
    HighScoreObject(String name, int score, LocalDate date){
       this.name = name;
       this.score = score;
       this.date = date;
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    public String getDate(){
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public int compareTo(HighScoreObject o) {
        return Integer.compare(this.score, o.score);
    }
}
