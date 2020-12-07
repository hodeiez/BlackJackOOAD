package BlackJack;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Lukas Aronsson
 * Date: 01/12/2020
 * Time: 16:22
 * Project: BlackJackOOAD
 * Copyright: MIT
 **/
public class IHasCards {

    ObservableList<Card> hand = FXCollections.observableArrayList();


    /**
     * Calculates and returns the value of the current hand
     *
     * @return the total value of the current hand
     */
    int getHandValue() {
        List<Integer> aces = new ArrayList<>();
        int result = 0;
        for (Card card : hand) {
            int i = card.getRank();
            result += i > 10 ? 10 : i == 1 ? 11 : i; //add all cards to result, ace = 11
            if (i == 1) aces.add(1);
        }
        while (!aces.isEmpty() && result > 21) { //if results is over 21, remove 10 (leave 1) for every ace if needed.
            result -= 10;
            aces.remove(0);
        }
        return result;
    }

    public void clearHand() {
        Platform.runLater(() -> hand.clear());

    }

    public void addCard(Card card) {
        Platform.runLater(() -> hand.add(card));
    }


}
