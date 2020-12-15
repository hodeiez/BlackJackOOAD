package BlackJack.model;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public abstract class Hand {
    public ArrayList<Card> hand = new ArrayList<>();
    public ObservableList<Card> handObs = FXCollections.observableArrayList(card -> new Observable[]{
            card.isFaceUpProp()
    });

    StringProperty handValueSP = new SimpleStringProperty("0");

    /**
     * Calculates and returns the value of the current hand
     *
     * @return the total value of the current hand
     */
    public synchronized int getHandValue() {
        List<Integer> aces = new ArrayList<>();
        int result = 0;
        for (Card card : hand) {
            int i = card.getRank();
            if (card.isFaceUp()) {
                result += i > 10 ? 10 : i == 1 ? 11 : i; //add all cards to result, ace = 11
            }
            if (i == 1) aces.add(1);
        }
        while (!aces.isEmpty() && result > 21) { //if results is over 21, remove 10 (leave 1) for every ace if needed.
            result -= 10;
            aces.remove(0);
        }
        return result;
    }

    /**
     * Clears the hand
     */
    public void clearHand() {
        hand.clear();
        Platform.runLater(() -> handObs.clear());
    }

    /**
     * adds a card to the hand
     *
     * @param card a playing card
     */
    public void addCard(Card card) {
        hand.add(card);
        Platform.runLater(() -> handObs.add(card));
    }

    /**
     * adds card selecting the faceUp state
     *
     * @param card   the card sent to the list
     * @param faceUp sets the face state, true is faceUp
     */
    public void addCard(Card card, boolean faceUp) {
        card.setFaceUp(faceUp);
        hand.add(card);
        Platform.runLater(() -> handObs.add(card));
    }

    /**
     * the value of the hand but stored in a StringProperty
     *
     * @return StringProperty that stores handValue
     */
    public StringProperty handValueSPProperty() {
        return handValueSP;
    }

    /**
     * set the value of the hand but stored in a StringProperty
     *
     * @param handValue StringProperty that stores handValue
     */
    public void setHandValueSP(String handValue) {
        handValueSPProperty().set(handValue);
    }

    /**
     * gets the card from observableList and changes face state
     *
     * @param index to select index of the card
     * @param state to select state, true is faceUp
     */
    public void setObsFaceUp(int index, boolean state) {
        if (hand.size() > 0) {
            Platform.runLater(() -> (handObs.get(index)).setIsFaceUp(state));
        }
    }

    /**
     * calls on setHandValueSp and gives it a string version of getHandValue
     */
    public void updateHandValue() {
        Platform.runLater(() -> setHandValueSP(getHandValue() + ""));
    }


}
