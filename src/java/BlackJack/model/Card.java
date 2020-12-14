package BlackJack.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Card {

    private final Suit suit;
    private final int rank;
    private boolean faceUp;
    private BooleanProperty isFaceUp = new SimpleBooleanProperty();

    public Card(Suit suit, int rank) {
        this.rank = rank;
        this.suit = suit;
        this.faceUp = true;
    }

    public BooleanProperty isFaceUpProp() {
        return isFaceUp;
    }

    /**
     * sets the state for isFaceUp Property
     *
     * @param state if state true set card face up
     */
    public final void setIsFaceUp(boolean state) {
        isFaceUp.set(state);
        faceUp = state;
    }

    public final boolean getIsFaceUp() {
        return isFaceUp.get();
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
        this.faceUp = faceUp;
    }

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
