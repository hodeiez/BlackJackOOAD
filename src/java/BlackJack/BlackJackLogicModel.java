package BlackJack;


import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class BlackJackLogicModel implements Runnable{
    private List<Player> players = new ArrayList<>();
    private Dealer dealer1 = new Dealer();
    private Deck deck1 = new Deck(6); //lugnt om deck1 är tom här, isåfall så fixas det ihop ny lek i början av första rundan
    private Player activePlayer = new Player();



    //Graphics side
    private StringProperty handValue = new SimpleStringProperty("");
    public boolean hit=false;

    public ObservableList activePlayerHand;
    public ObservableList dealerHand;
    public ArrayList<CardGraph> activePlayerHandArr;
    public ArrayList<CardGraph> dealerHandArr;

    public BlackJackLogicModel(){
        setUpModel();
    }
    public void setUpModel(){

        activePlayerHand = FXCollections.observableArrayList(new Callback<CardGraph, Observable[]>() {
            @Override
            public Observable[] call(CardGraph cardGraph) {
                return new Observable[]{
                        cardGraph.faceUpProperty()
                };
            }
        },activePlayerHandArr);
        dealerHand = FXCollections.observableArrayList(new Callback<CardGraph, Observable[]>() {
            @Override
            public Observable[] call(CardGraph cardGraph) {
                return new Observable[]{
                        cardGraph.faceUpProperty()
                };
            }
        },dealerHandArr);
    }


    //the original
    private void setUpGame() {
        players.add(activePlayer);
      //  players.add(new Player());
       // players.add(new Player());
        setStartingBalance(1000);

        playRound();

    }

    private void isItTimeToShuffle() {
        if (deck1.getCardDeck().size() <= 30) {
            deck1 = new Deck(6);

        }
    }

    private void playRound() {
     //  while (true) {
            isItTimeToShuffle();
            dealHands();
          //  dealer1.hand.get(0).setFaceUp(true);
            humanPlayerTurn();
           // computerPlayerTurn(players.get(1));
            //computerPlayerTurn(players.get(2));
            dealerTurn();
//            isGameOver();

     //  }
    }

    private void isPlayerBroke(Player player) {
        if (player.getBalance() <= 0) {
//            player.setBroke(true);

        }
    }

    private void setStartingBalance(int startCash) {
        for (Player p : players) {
            p.setBalance(startCash);
        }
    }

    private void dealHands() {

        Card card=deck1.drawCard();
        activePlayer.hand.add(card);
        Card pCard=card;
        //show card
        Platform.runLater(()->activePlayerHand.add(cardToGraph(pCard)));
       // activePlayerHand.add()
        //simplified

        card=deck1.drawCard();
        dealer1.hand.add(card);
        Card dCard=card;
        CardGraph cardG=cardToGraph(dCard);
        cardG.changeFace();
        Platform.runLater(()->dealerHand.add(cardG));


    }

    private void humanPlayerTurn() {
int checkSize=activePlayer.hand.size();
while(!hit){
    System.out.println("human");
    if(checkSize < activePlayer.hand.size()){
        hit=true;
        System.out.println("human");
        break;

    }
}
    /*
       // boolean hit;
        System.out.println("humantTurn");
        while (true) {
            System.out.println(activePlayer.getHandValue());
            //Input från användaren Hit/Stay
            if (hit) {
                System.out.println("hit true");
                Card card=deck1.drawCard();
                activePlayer.hand.add(card);
               Platform.runLater(()-> activePlayerHand.add(cardToGraph(card)));
                hit=false;
                break;
            } else break;

        }

     */


    }

    private void dealerTurn() {
       // dealer1.hand.get(1).setFaceUp(true);
        Platform.runLater(()->((CardGraph)dealerHand.get(0)).changeFace());
        while (dealer1.getHandValue() < 21) {
         //   if (dealer1.getHandValue() <= activePlayer.getHandValue()) {
                Card card=deck1.drawCard();
                dealer1.hand.add(card);
                Platform.runLater(()->dealerHand.add(cardToGraph(card)));


           // }
            /*
            if (dealer1.getHandValue() > 21) {
                System.out.println("Dealern är bust! Du vinner.");

            } else if (dealer1.getHandValue() > activePlayer.getHandValue()) {
                System.out.println("Dealern vinner!");

            } else if (dealer1.getHandValue() == activePlayer.getHandValue()) {
                System.out.println("Oavgjort!");

            } else if (dealer1.getHandValue() < activePlayer.getHandValue()) {
                System.out.println("Du vinner!");

            }

             */


        }
    }

    private void computerPlayerTurn(Player player) {
        while (player.getHandValue() <= 21) {
            player.hand.add(deck1.drawCard());
            if (player.getHandValue() <= 16) {
                break;
            }

        }
    }
//GRAPHIX THINGS
public StringProperty handValueProperty() {
    return handValue;
}

    public String getHandValue() {
        return handValueProperty().get();
    }

    public void setHandValue(String balance) {
        handValueProperty().set(balance);
    }
    public void hitListener(){
        System.out.println(hit);
        hit=true;
        System.out.println(hit);
        Card card=deck1.drawCard();
       activePlayer.hand.add(card);
       Platform.runLater(()-> activePlayerHand.add(cardToGraph(card)));
      //  System.out.println(activePlayer.getHandValue());
        setHandValue(String.valueOf(activePlayer.getHandValue()));
    }


    //Simple converter to graphical card
public CardGraph cardToGraph(Card card){
    String rank=String.valueOf(card.getRank());
    String suit=String.valueOf(card.getSuit()).toLowerCase();
    switch (rank){
    case "1"->rank="ace";
    case "11"-> rank="jack";
    case "12"->rank="queen";
    case "13"->rank="king";
        default -> rank=rank;
    }
    return new CardGraph(suit,rank,true);
}

    @Override
    public void run() {
       setUpGame();
       //Platform.runLater(()->setUpGame());

    }
}
