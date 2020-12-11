package BlackJack;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Created by Hodei Eceiza
 * Date: 11/30/2020
 * Time: 15:50
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class GameBoardController {

    public Button hit;
    public Button stay;
    public Button rules;
    public Button end;
    public Label balance;
    public DialogPane rulesPanel;
    public HBox dealerBox;
    public HBox dealerbackground;
    public HBox activePlayer;
    public HBox player2;
    public HBox player3;
    public Label handValue;
    public Label dealerValue;
    public Label messages;
    @FXML
    private AnchorPane gameBoardPane;
    //  private ModelTest modelTest;
    private BlackJackLogic modelTest;
    private Rectangle rect = new Rectangle();


    /*
        public GameBoardController(ModelTest modelTest) {
            this.modelTest = modelTest;
        }

     */
    public GameBoardController(BlackJackLogic modelTest) {

        this.modelTest = modelTest;
    }


    public void initialize() {
       setListener(modelTest.activePlayer.handObs, activePlayer);

        setListener(modelTest.dealer1.handObs, dealerBox);

        rulesPanelSettings();

        setButtonListener();

        setHandValueListeners();

        setBalanceValueListener();

messages.textProperty().bind(modelTest.messages);
//Listens changes of the observableList


//balance its binded, should do a double bind? or call method from logic to change the balance?
        // balance.textProperty().bind(modelTest.balanceProperty());
//        handValue.textProperty().bind(modelTest.handValueSPProperty());



        //using buttons for test
        //test for changeFaceUp
        // stay.setOnAction(e-> modelTest.activePlayerHandArr.get(0).changeFace());

        //testing hit
        //hit.setOnAction(e->modelTest.hitListener());
        hit.setOnAction(e -> BlackJackLogic.actionQueue.add(1));
//        end.setOnAction(e -> player2.getChildren().add(new CardGraph("clubs", "ace", true)));
//        end.setOnAction(e -> balance.setText(BlackJackLogic.test()));
        stay.setOnAction(e -> BlackJackLogic.actionQueue.add(0));

        rules.setOnMouseClicked(e ->rulesPanel.setVisible(!rulesPanel.isVisible()));

    }

    public void changeBalance(String string){
        balance.setText(string);
    }
    public void setListener(ObservableList<Card> observable, HBox playerBox) {
        observable.addListener((ListChangeListener<Card>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    CardGraph c = cardToGraph(change.getAddedSubList().get(0));
                    System.out.println(change.getAddedSubList().get(0).isFaceUp());
                    fadeTransition(c);

                    if (observable.size() > 1) {
                        c.setTranslateX(-(50 * (observable.size() - 1)));//this has to be simplified
                        fadeTransition(c);
                    }
                    playerBox.getChildren().add(c);

                } else if (change.wasRemoved()) {
                    playerBox.getChildren().clear();
                }


                else if(change.wasUpdated()) {
                    System.out.println("UPDATED");
                    //Sets the faceUp state to true
                    ((CardGraph) playerBox.getChildren().get(change.getFrom())).setFaceUp(true);
               //here applied changeFace method. gets the card from the box which was updated and swaps face
                   ((CardGraph) playerBox.getChildren().get(change.getFrom())).changeFace();
                }


            }
        });
    }

    public void setButtonListener() {
        modelTest.disableButtons.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                hit.setDisable(newValue);
                stay.setDisable(newValue);
            }
        });
    }

    public void setHandValueListeners() {
        handValue.textProperty().bind(modelTest.activePlayer.handValueSPProperty());
        dealerValue.textProperty().bind(modelTest.dealer1.handValueSPProperty());
    }
    public void setBalanceValueListener(){
        modelTest.activePlayer.balanceValueProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                balance.setText(newValue);
            }
        });
    }

    public CardGraph cardToGraph(Card card) {
        String rank = String.valueOf(card.getRank());
        String suit = String.valueOf(card.getSuit()).toLowerCase();
        switch (rank) {
            case "1" -> rank = "ace";
            case "11" -> rank = "jack";
            case "12" -> rank = "queen";
            case "13" -> rank = "king";
            default -> rank = rank;
        }
        //System.out.println(card.isFaceUp());
        return new CardGraph(suit, rank, card.isFaceUp());
    }


    /**
     * sets up the rulesPanel
     */
    public void rulesPanelSettings(){
        rulesPanel.setVisible(false);
        Label rules=new Label("Rules");
        rules.setStyle("-fx-font-size: 50;-fx-background-color: #a29f9f");
        rulesPanel.setHeader(rules);
        rulesPanel.setContentText(
                Messages.RULES.print());
    }
    public void fadeTransition(CardGraph c){
        FadeTransition ft=new FadeTransition();
        ft.setDuration(Duration.seconds(0.5));
        ft.setNode(c);
        ft.setFromValue(0);
        ft.setToValue(100);
        ft.play();
    }
}
