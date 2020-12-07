package BlackJack;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Card {

    private final Suit suit;
    private final int rank;
    private boolean faceUp;
    protected BooleanProperty isFaceUpProperty;
    public Card(Suit suit, int rank) {
        this.rank = rank;
        this.suit = suit;
        faceUp=true;
        isFaceUpProperty=new SimpleBooleanProperty(false);
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUpProperty().set(faceUp);this.faceUp = faceUp;
    }
    public BooleanProperty isFaceUpProperty(){
        return isFaceUpProperty;
    }
    public boolean getFaceUpProp(){return this.isFaceUpProperty().get();}


    public enum Suit {
        HEARTS,
        SPADES,
        CLUBS,
        DIAMONDS
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", rank=" + rank +
                '}';
    }
}
