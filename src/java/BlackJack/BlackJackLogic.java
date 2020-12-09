package BlackJack;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlackJackLogic implements Runnable {
    private List<Player> players = new ArrayList<>();
    Dealer dealer1 = new Dealer();
    private Deck deck1 = new Deck(6);
    Player activePlayer = new Player();
    BooleanProperty disableButtons = new SimpleBooleanProperty(false);
    boolean humanBust;
    public static BlockingQueue<Integer> actionQueue = new LinkedBlockingQueue();
    StringProperty messages=new SimpleStringProperty();

    private void setUpGame() throws InterruptedException {
        activePlayer.setBalance(300);
        players.add(activePlayer);
        playRound();
    }

    private void isItTimeToShuffle() {
        if (deck1.getCardDeck().size() <= 30) {
            deck1 = new Deck(6);
        }
    }

    private void playRound() throws InterruptedException {
        printMessage(Messages.WELCOME.print());
        while (true) {
            isItTimeToShuffle();

            placeBets();
            updateGraphicBalance();
            dealHands();
            //dealer1.hand.get(0).setFaceUp(true);
            humanPlayerTurn();
//            computerPlayerTurn(players.get(1));
//            computerPlayerTurn(players.get(2));
            dealerTurn();
            isGameOver();
        }
    }
public void updateGraphicBalance(){

    String balance = "Balance: "+activePlayer.getBalance();
    Platform.runLater(() -> activePlayer.balanceValueProperty.set(balance));
}
    public void playRoundConsoleVersion() throws InterruptedException {

        Deck deck1 = new Deck(1);
        players.add(activePlayer);
        while (true) {
            isItTimeToShuffle();
            dealHands();
         //   ((Card)dealer1.hand.get(0)).setIsFaceUp(true);
            System.out.println("Dealerns hand är värd: " + dealer1.getHandValue());
            System.out.println("Dealerns hand är värd: " + dealer1.getHandValue());
            humanPlayerTurn();
//            computerPlayerTurn(players.get(1));
//            computerPlayerTurn(players.get(2));
            dealerTurn();
//            isGameOver();
        }
    }

    private void isPlayerBroke() {
        if (activePlayer.getBalance() < 0) {
            activePlayer.setBroke(true);
        }
    }
   private void placeBets(){
       for (Player p:players
            ) {
           p.setCurrentBet(100);
       }
   }
    private void setStartingBalance(int startCash) {
        for (Player p : players) {
            p.setBalance(startCash);
        }
    }

    private void dealHands() throws InterruptedException {
        printMessage("Dealer deals cards");
        if (!activePlayer.isBroke()) {
        for (Player p : players) {
            p.addCard(deck1.drawCard());
            p.addCard(deck1.drawCard());
            System.out.println("in BlackJackLogic / dealHands: Player: " + p.getName() + " HandSize: " + p.hand.size());
          //  printMessage("dealHands: Player: " + p.getName() + " HandSize: " + p.hand.size());

        }

        dealer1.addCard(deck1.drawCard(),false);
       // dealer1.setObsFaceUp(0,false);
        Thread.sleep(200);
        for(Player p : players){
            p.getHandValue();
        }
        dealer1.getHandValue();
        //deck1.cardDeck.remove(0); //why do we remove this??
    }}

    private void humanPlayerTurn() throws InterruptedException {
        printMessage(String.format(Messages.PLAYER_TURN.print(),activePlayer.getName()));
        isPlayerBroke();
        if(activePlayer.isBroke()){
            System.out.println("Du är pank: "+activePlayer.getBalance());
            printMessage("Du är pank: "+activePlayer.getBalance());
            //Game Over
        }else {
            System.out.println(activePlayer.getBalance());
        int choice = 0;
        while (true) {
            boolean hit = false;
            Platform.runLater(() -> disableButtons.setValue(false));
            choice = (int) BlackJackLogic.actionQueue.take();
            Platform.runLater(() -> disableButtons.setValue(true));
            System.out.println("Val: " + choice);
            if (choice == 1) {
                System.out.println("Före drawCard" + activePlayer.getHandValue());
                activePlayer.addCard(deck1.drawCard());
                Thread.sleep(3);
                System.out.println("Efter drawCard" + activePlayer.getHandValue());
            } else {
                break;
            }

            if (activePlayer.getHandValue() > 21) { //TODO borde vara i Player eller HasCards?
                System.out.println("in BlackJackLogic / humanPlayerTurn: Player Handvalue: " + activePlayer.getHandValue());
                humanBust = true;
                break;
            }
        }
    }}

    private void dealerTurn() throws InterruptedException {
        printMessage("dealer deals");
        dealer1.setObsFaceUp(0,true);
        if(!activePlayer.isBroke()){
        Thread.sleep(1000);
        dealer1.addCard((deck1.drawCard()));
        Thread.sleep(100);
        dealer1.getHandValue();
        if (humanBust) {
            humanBust = false;
        } else {
            boolean dealerWin;
//        dealer1.hand.get(1).setFaceUp(true);
            while (dealer1.getHandValue() < 17 && dealer1.getHandValue() < activePlayer.getHandValue()) {
                Thread.sleep(1000);
                dealer1.addCard(deck1.drawCard());
                Thread.sleep(100);
                dealer1.getHandValue();
//                Thread.sleep(100);
            }
            if (dealer1.getHandValue() > 21) {
                System.out.println("Dealern är bust! Du vinner.");
                printMessage("Dealern är bust! Du vinner.");
                activePlayer.increaseBalance();
                activePlayer.increaseBalance();
            } else if (dealer1.getHandValue() > activePlayer.getHandValue()) {
                System.out.println("Dealern vinner!");
                printMessage("Dealern vinner!");

            } else if (dealer1.getHandValue() == activePlayer.getHandValue()) {
                System.out.println("Oavgjort!");
                printMessage("Oavgjort!");
                activePlayer.increaseBalance();
            } else if (dealer1.getHandValue() < activePlayer.getHandValue()) {
                printMessage("Du vinner!");
                activePlayer.increaseBalance();
                activePlayer.increaseBalance();
            }
        }}
        Thread.sleep(1000);
        System.out.println("RENSA");
        printMessage("RENSA");
        activePlayer.clearHand();
        dealer1.clearHand();
    }

    private void computerPlayerTurn(Player player) {
        while (player.getHandValue() <= 21) {
            player.addCard(deck1.drawCard());
            if (player.getHandValue() <= 16) {
                break;
            }
        }
    }
    public void isGameOver(){
        if(activePlayer.isBroke()){
            System.out.println("LOOOOOOOOOSEEERRRR!!!!!!!!");
            printMessage("LOOOOOOOOOSEEERRRR!!!!!!!!");
            activePlayer.clearHand();
            dealer1.clearHand();
            activePlayer.setBalance(1000);
            activePlayer.setBroke(false);
        }
    }
    public void printMessage(String message){
        Platform.runLater(()->messages.set(message));
    }
    @Override
    public void run() {
        try {
            setUpGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
