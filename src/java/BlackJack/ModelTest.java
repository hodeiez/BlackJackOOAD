package BlackJack;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by Hodei Eceiza
 * Date: 12/3/2020
 * Time: 18:02
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class ModelTest implements Runnable {
    private StringProperty balance = new SimpleStringProperty("blank");
    private String bet;
    public ObservableList activePlayerHand = FXCollections.observableArrayList(new ArrayList<CardGraph>());
    public ObservableList player2Hand = FXCollections.observableArrayList(new ArrayList<CardGraph>());
    public ObservableList dealerHand=FXCollections.observableArrayList(new ArrayList<CardGraph>());



    public ModelTest() {

    }

    public void addCardActPlayer(CardGraph card) {
        activePlayerHand.add(card);
    }

    public void addCardPlayer2(CardGraph card){
        player2Hand.add(card);
    }
    public void addCardDealer(CardGraph card){
        dealerHand.add(card);
    }
    public void clearDealerHand(){
        dealerHand.clear();
    }
    public void clearActPlayerHand(){
        activePlayerHand.clear();
    }
    public void clearPlayer2Hand(){
        player2Hand.clear();
    }

    public StringProperty balanceProperty() {
        return balance;
    }

    public String getBalance() {
        return balanceProperty().get();
    }

    public void setBalance(String balance) {
        balanceProperty().set(balance);
    }

    @Override
    public void run() {
        // final int[] i = {0};
        {
            while (true) {

                Platform.runLater(() -> {
                    addCardActPlayer(new CardGraph("spades", "ace", true));
                    addCardActPlayer(new CardGraph("spades", "king", true));
                    addCardPlayer2(new CardGraph("diamonds","queen",true));

                    //activePlayerHand.remove(activePlayerHand.size()-1);
                });
                //  setBalance("new Balance"));
                //setBalance(String.valueOf(i[0]++)));
                //  System.out.println(i);
                // setCard(new CardGraph("spades", String.valueOf(i[0] +2), true).getImgPattern());


                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               Platform.runLater(()-> clearActPlayerHand());
            }
        }

    }
}
