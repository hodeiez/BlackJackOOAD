package BlackJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {

    List<Card> cardDeck = new LinkedList<>();
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
        Card temp = cardDeck.get(0);
        cardDeck.remove(0);
        return temp;
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
