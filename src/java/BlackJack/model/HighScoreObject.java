package BlackJack.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HighScoreObject implements Comparable<HighScoreObject> {
    private final String name;
    private final int score;
    private final LocalDate date;

    HighScoreObject(String name, int score) {
        this.name = name;
        this.score = score;
        this.date = LocalDate.now();
    }

    HighScoreObject(String name, int score, LocalDate date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public int compareTo(HighScoreObject o) {
        return Integer.compare(this.score, o.score);
    }
}
