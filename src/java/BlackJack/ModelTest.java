package BlackJack;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

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

    public ArrayList<CardGraph> activePlayerHandArr= new ArrayList<>();
    public ObservableList activePlayerHand = FXCollections.observableArrayList(activePlayerHandArr); //instead of new ArrayList put activeHand

    public ObservableList player2Hand = FXCollections.observableArrayList(new ArrayList<CardGraph>());
    public ObservableList dealerHand=FXCollections.observableArrayList(new ArrayList<CardGraph>());



    public ModelTest() {

    }

    public void addCardActPlayer(CardGraph card) {
       activePlayerHandArr.add(card);
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
        {
            //while (true) {

                Platform.runLater(() -> {
                    Random rnd =new Random();
                    addCardActPlayer(new CardGraph("spades", "ace", true));
                    addCardActPlayer(new CardGraph("spades", String.valueOf(rnd.nextInt(8)+2), true));
                });


                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
              // Platform.runLater(()-> clearActPlayerHand());
           // }
        }

    }
}
