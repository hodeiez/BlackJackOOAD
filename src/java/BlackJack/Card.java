package BlackJack;

public class Card {

    private final Suit suit;
    private final int rank;
    private boolean faceUp;

    public Card(Suit suit, int rank) {
        this.rank = rank;
        this.suit = suit;
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
