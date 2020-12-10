package BlackJack.model;

import java.time.LocalDateTime;

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
    private LocalDateTime date; //maybe for later use?

    HighScoreObject(String name, int score){
       this.name = name;
       this.score = score;
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    @Override
    public int compareTo(HighScoreObject o) {
        return Integer.compare(this.score, o.score);
    }
}
