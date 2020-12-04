package BlackJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> cardDeck = new ArrayList<>();
    private int numberOfCompleteDecks;

    public Deck(int numberOfCompleteDecks) {
        this.numberOfCompleteDecks = numberOfCompleteDecks;
        for (int i = 0; i < numberOfCompleteDecks; i++) {
            generateDeck();
        }
    }

    private void generateDeck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (int rank = 1; rank < 14; rank++) {
                Card card = new Card(suit, rank);
                cardDeck.add(card);
            }
        }
        shuffle();
    }

    public Card drawCard() {
        return cardDeck.remove(0);
    }

    public void shuffle() {
        Collections.shuffle(cardDeck);
    }

    public List<Card> getCardDeck() {
        return cardDeck;
    }

    public boolean isDeckEmpty() {
        return cardDeck.isEmpty();
    }
}
