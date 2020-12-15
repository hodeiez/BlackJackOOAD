package BlackJack.controller;

import BlackJack.model.*;
import BlackJack.view.Messages;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlackJackLogic implements Runnable {
    private List<Player> players = new ArrayList<>();
    public Dealer dealer1 = new Dealer();
    private Deck deck1 = new Deck(6);
    public Player activePlayer = new Player();
    public BooleanProperty disableButtons = new SimpleBooleanProperty(false);
    public BooleanProperty bettingScreen = new SimpleBooleanProperty(true);
    public BooleanProperty gameOverPanel = new SimpleBooleanProperty(false);
    public BooleanProperty highScorePanel = new SimpleBooleanProperty(false);
    boolean humanBust;
    boolean highScoreSet;
    public static BlockingQueue<Object> actionQueue = new LinkedBlockingQueue();
    public StringProperty messages = new SimpleStringProperty();
    public HighScore highScore = HighScore.getInstance();
    Notifier notifier =new Notifier();
    long startTime;
    long endTime;
    IlogObserver consoleObs=new LogToConsole();
    IlogObserver encryptObs=new LogEncrypted();

    private void setUpGame() throws InterruptedException {
        notifier.attach(consoleObs);
        notifier.attach(encryptObs);

        players.add(activePlayer);
        setStartingBalance(1000);
        playRound();

    }

    private void isItTimeToShuffle() {
        if (deck1.getCardDeck().size() <= 30) {
            deck1 = new Deck(6);
        }
    }

    private void playRound() throws InterruptedException {


        //startTime= System.currentTimeMillis();
        printMessage(Messages.WELCOME.print());
        while (true) {
            isItTimeToShuffle();

            placeBets();


            notifyObs("AT START\n" + LocalTime.now() +"\nPlayer Balance\n" +
                    activePlayer.getBalance()+"\nPlayer bet amount\n" +activePlayer.getCurrentBet()+"\n"+ getPassedSecs());


            dealHands();
            humanPlayerTurn();
            if (!highScoreSet) {
                dealerTurn();
            }
            if (activePlayer.isBroke() || highScoreSet) {
                isGameOver();
            }
        }
    }

    public void updateGraphicBalance() {
        String balance = "Balance: " + activePlayer.getBalance();
        Platform.runLater(() -> activePlayer.balanceValueProperty.set(balance));
    }


    private void isPlayerBroke() {
        if (activePlayer.getBalance() <= 0 && activePlayer.getCurrentBet() <= 0) {
            activePlayer.setBroke(true);
        }
    }

    private void placeBets() throws InterruptedException {

        startTime= System.currentTimeMillis();


        Platform.runLater(() -> bettingScreen.setValue(true));
        updateGraphicBalance();
        Integer bet = (Integer) actionQueue.take();
        activePlayer.setCurrentBet(bet);
        updateGraphicBalance();
        Platform.runLater(() -> bettingScreen.setValue(false));



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
            }
            dealer1.addCard(deck1.drawCard(), false);
            dealer1.addCard(deck1.drawCard());
            for (Player p : players) {
                p.updateHandValue();
            }
            dealer1.updateHandValue();
        }
        notifyObs("Cards at round start\n"+"dealer\n"+ dealer1.hand.toString()+"\ndealer hand value\n"+dealer1.getHandValue()
                + "\nPlayer\n"+activePlayer.hand.toString()+"\nplayer hand value\n"+ activePlayer.getHandValue());

    }

    private void humanPlayerTurn() throws InterruptedException {
        startTime=System.currentTimeMillis();

        printMessage(String.format(Messages.PLAYER_TURN.print(), activePlayer.getName()));
        isPlayerBroke();
        if (activePlayer.isBroke()) {
            printMessage(Messages.YOU_BROKE.print());
            //Game Over
        } else {
            notifyObs("PLAYER DECIDING");
            int choice = 0;
            hitLoop:
            while (true) {
                startTime=System.currentTimeMillis();
                boolean hit = false;
                Platform.runLater(() -> disableButtons.setValue(false));
                choice = (int) BlackJackLogic.actionQueue.take();
                Platform.runLater(() -> disableButtons.setValue(true));
                switch (choice) {
                    case 0 -> { //stay, do nothing
                        notifyObs("STAYS\n"+"Player hand value\n"+ activePlayer.getHandValue()+"\n"+getPassedSecs());
                        break hitLoop;
                    }
                    case 1 -> { //hit
                        Card card= deck1.drawCard();
                        notifyObs("TAKES CARD:\n"+card.toString());


                        activePlayer.addCard(card);
                        activePlayer.updateHandValue();
                        notifyObs("Hand Value:\n"+activePlayer.getHandValue());
                        notifyObs("\n"+getPassedSecs());
                    }
                    case 9 -> { //Pressed submit on highscore
                        String name = (String) BlackJackLogic.actionQueue.take();
                        highScoreSet = highScore.addHighScore(name, activePlayer.balance); //True if added
                        if (highScoreSet) { //Adds highscore if it makes it
                            activePlayer.isBroke();
                            Platform.runLater(() -> highScorePanel.setValue(false));
                            break hitLoop;
                        }
                    }
                    default -> {
                        break hitLoop;
                    }
                }
                if (activePlayer.getHandValue() > 21) {
                    humanBust = true;
                    break;
                }
            }
        }

    }

    private void dealerTurn() throws InterruptedException {
        printMessage(Messages.DEALER_TURN.print());
        dealer1.setObsFaceUp(0, true);
        Thread.sleep(200);
        dealer1.updateHandValue();

        //Deal cards to dealer.
        while (dealer1.getHandValue() < dealer1.getStopValue()) {
            Thread.sleep(1000);
            dealer1.addCard(deck1.drawCard());
            dealer1.updateHandValue();
        }
        boolean dealerBust = dealer1.getHandValue() > 21;
        boolean playerBust = activePlayer.getHandValue() > 21;
        //win/lose-conditions.
        if (!playerBust) {
            if (dealerBust) {
                printMessage(String.format(Messages.BUST.print(), "Dealer"));
                activePlayer.increaseBalance();
                activePlayer.increaseBalance();

                notifyObs("WON");

            } else if (dealer1.getHandValue() > activePlayer.getHandValue() && !dealerBust) { //Dealern hade högre, du förlorar.

                notifyObs("LOST");

                updateGraphicBalance();
                printMessage(String.format(Messages.WON.print(), "Dealer"));
            } else if (dealer1.getHandValue() == activePlayer.getHandValue()) { // oavgjort

                notifyObs("DRAW");

                printMessage(Messages.DRAW.print());
                activePlayer.setBalance(activePlayer.getBalance() + activePlayer.getCurrentBet());
            } else if (dealer1.getHandValue() < activePlayer.getHandValue()) { //Du vinner
                printMessage(String.format(Messages.WON.print(), "You"));

                notifyObs("WON");

                int tempBet = activePlayer.getCurrentBet();
                activePlayer.increaseBalance();
                if(activePlayer.getHandValue() == 21){ //Blackjack!
                    activePlayer.setBalance(activePlayer.getBalance()+tempBet);

                    notifyObs("WON");
                }
            }
        } else {
            printMessage(Messages.YOU_BUST.print());
            notifyObs("LOST");
        }
            notifyObs("RESULTS\nDealer cards\n"+dealer1.hand.toString()+"\nDealer hand value\n"+dealer1.getHandValue()+"\nPlayer Cards\n"
                   + activePlayer.hand.toString()+"\nPlayer hand value\n"+activePlayer.getHandValue());


        humanBust = false;
        activePlayer.setCurrentBet(0);
        Thread.sleep(3000);
        activePlayer.clearHand();
        dealer1.clearHand();
    }
//Hanns inte implementera
    private void computerPlayerTurn(Player player) {
        while (player.getHandValue() <= 21) {
            player.addCard(deck1.drawCard());
            if (player.getHandValue() <= 16) {
                break;
            }
        }
    }

    public void isGameOver() throws InterruptedException {
        if (highScoreSet) {
            printMessage("You made the highscore!");
        } else {
            printMessage("LOOOOOOOOOSEEERRRR!!!!!!!!");
        }
        activePlayer.clearHand();
        dealer1.clearHand();
        Platform.runLater(() -> gameOverPanel.set(true));
        actionQueue.take();
        activePlayer.setBalance(1000);
        activePlayer.setBroke(false);
    }

    public void printMessage(String message) {
        Platform.runLater(() -> messages.set(message));
    }



    private void notifyObs(String text){
        notifier.setMessage(text);
        notifier.notifyObservers();
    }
    private String getPassedSecs(){
        return "Time to decide\n"+TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-startTime);
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
