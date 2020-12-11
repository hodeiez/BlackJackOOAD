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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
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
    public Label bet;
    public DialogPane rulesPanel;
    public HBox dealerBox;
    public HBox dealerbackground;
    public HBox activePlayer;
    public HBox player2;
    public HBox player3;
    public Label handValue;
    public Label dealerValue;
    public Label messages;

    public Pane BettingScreen;
    public Button Plus;
    public Button Minus;
    public Button Bet;
    public Label BettingText;
    public Label BetAmount;

    @FXML
    private AnchorPane gameBoardPane;
    //  private ModelTest modelTest;
    private BlackJackLogic modelTest;
    private Rectangle rect = new Rectangle();

    int tempBet = 100;


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

        setBettingScreenListener();

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

        Plus.setOnAction(e-> plus());
        Minus.setOnAction(e-> minus());
         Bet.setOnAction(e-> betted());

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
        rulesPanel.setHeaderText("Rules"); // TODO: 12/8/2020 : Lukas : figure out how to change font on the header of a dialogPanel
        rulesPanel.setContentText(
                "Players are each dealt two cards, face up or down depending on the casino and the table. In the U.S., the dealer is also dealt two cards, normally one up (exposed) and one down (hidden). In most other countries, the dealer only receives one card face up. The value of cards two through ten is their pip value (2 through 10). Face cards (Jack, Queen, and King) are all worth ten. Aces can be worth one or eleven. A hand's value is the sum of the card values. Players are allowed to draw additional cards to improve their hands. A hand with an ace valued as 11 is called \"soft\", meaning that the hand will not bust by taking an additional card. The value of the ace will become one to prevent the hand from exceeding 21. Otherwise, the hand is called \"hard\". \n\n" +
                "Once all the players have completed their hands, it is the dealer's turn. The dealer hand will not be completed if all players have either busted or received blackjacks. The dealer then reveals the hidden card and must hit until the cards total up to 17 points. At 17 points or higher the dealer must stay. (At most tables the dealer also hits on a \"soft\" 17, i.e. a hand containing an ace and one or more other cards totaling six.) You are betting that you have a better hand than the dealer. The better hand is the hand where the sum of the card values is closer to 21 without exceeding 21. The detailed outcome of the hand follows: \n\n" +
                "* If the player is dealt an Ace and a ten-value card (called a \"blackjack\" or \"natural\"), and the dealer does not, the player wins and usually receives a bonus. \n" +
                "* If the player exceeds a sum of 21 (\"busts\"), the player loses, even if the dealer also exceeds 21. \n" +
                "* If the dealer exceeds 21 (\"busts\") and the player does not, the player wins. \n" +
                "* If the player attains a final sum higher than the dealer and does not bust, the player wins. \n" +
                "* If both dealer and player receive a blackjack or any other hands with the same sum called a \"push\", no one wins. \n\n" +
                "Rules Taken from https://en.wikipedia.org/wiki/Blackjack#Rules ");
    }

    public void setBettingScreenListener() {
        modelTest.bettingScreen.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                BettingScreen.setVisible(newValue);
                bettingScreen();
            }
        });
    }


    public void bettingScreen(){
        tempBet = modelTest.activePlayer.getCurrentBet();
        if(tempBet > Integer.parseInt(balance.getText().substring(balance.getText().indexOf(" ") + 1))){
            BetAmount.setText(String.valueOf(tempBet));
        }else{

        }


    }

    public void plus(){
        if(!(tempBet >= 1000) && !(tempBet >= Integer.parseInt(balance.getText().substring(balance.getText().indexOf(" ") + 1)))){ //1000 is max bet
            tempBet = tempBet + 100; //adds 100 to tempBet
            BetAmount.setText(""+tempBet);
        }else {
            if(tempBet >= 1000){
                BettingText.setText("Set your Bet! \n Max bet is 1000");
            } else {
                BettingText.setText("Set your Bet! \n Bet to large for balance");
            }

        }
    }

    public void minus(){
        if(!(tempBet <= 100)){ //100 is min bet
            tempBet = tempBet - 100;
            BetAmount.setText(""+tempBet);
        } else {
            BettingText.setText("Set your Bet! \n Min bet is 100");
        }
    }
    public void betted(){

        BlackJackLogic.actionQueue.add(tempBet);
        bet.setText("Bet: "+tempBet);
        BettingScreen.setVisible(false);
        tempBet = 100; //resets the bet to 100
        BetAmount.setText(""+tempBet); //sets the bet amount to 100

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
