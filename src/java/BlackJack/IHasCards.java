package BlackJack;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas Aronsson
 * Date: 01/12/2020
 * Time: 16:22
 * Project: BlackJackOOAD
 * Copyright: MIT
 **/
public abstract class IHasCards {

    ArrayList<Card> hand = new ArrayList();
    ObservableList<Card> handObs = FXCollections.observableArrayList(hand);
    StringProperty handValueSP = new SimpleStringProperty("0");


    /**
     * Calculates and returns the value of the current hand
     *
     * @return the total value of the current hand
     */
    synchronized int getHandValue() {
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

        int finalResult = result;
//        setHandValueSP(result + ""); //Får Thread Exception, utanför Java FX-tråd
//        Platform.runLater(() -> handValueSPProperty().set(finalResult + "")); //Funkar också.
        Platform.runLater(() -> setHandValueSP(finalResult + ""));
        return result;
    }

    public void clearHand() {
       hand.clear();
        Platform.runLater(() -> handObs.clear());

    }

    public void addCard(Card card) {
        hand.add(card);
        Platform.runLater(() -> handObs.add(card));
        System.out.println(card);
      //  System.out.println((hand.size()>0)?"What is the first element?"+ hand.get(0).toString():"Observable is empty");
    }

    public StringProperty handValueSPProperty() {
        return handValueSP;
    }

    public String getHandValueSP() {
        return handValueSPProperty().get();
    }

    public void setHandValueSP(String handValue) {
        handValueSPProperty().set(handValue);
    }

}
