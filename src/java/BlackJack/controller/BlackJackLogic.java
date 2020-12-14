package BlackJack.controller;

import BlackJack.model.Dealer;
import BlackJack.model.Deck;
import BlackJack.model.HighScore;
import BlackJack.model.Player;
import BlackJack.view.Messages;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
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

    private void setUpGame() throws InterruptedException {
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
        printMessage(Messages.WELCOME.print());
        while (true) {
            isItTimeToShuffle();
            placeBets();
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
// Används inte just nu
    public void playRoundConsoleVersion() throws InterruptedException {
        Deck deck1 = new Deck(1);
        players.add(activePlayer);
        while (true) {
            isItTimeToShuffle();
            dealHands();
            humanPlayerTurn();
            dealerTurn();
        }
    }

    private void isPlayerBroke() {
        if (activePlayer.getBalance() <= 0 && activePlayer.getCurrentBet() <= 0) {
            activePlayer.setBroke(true);
        }
    }

    private void placeBets() throws InterruptedException {
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
    }

    private void humanPlayerTurn() throws InterruptedException {
        printMessage(String.format(Messages.PLAYER_TURN.print(), activePlayer.getName()));
        isPlayerBroke();
        if (activePlayer.isBroke()) {
            printMessage(Messages.YOU_BROKE.print());
            //Game Over
        } else {
            System.out.println(activePlayer.getBalance());
            int choice = 0;
            hitLoop:
            while (true) {
                boolean hit = false;
                Platform.runLater(() -> disableButtons.setValue(false));
                choice = (int) BlackJackLogic.actionQueue.take();
                Platform.runLater(() -> disableButtons.setValue(true));
                switch (choice) {
                    case 0 -> { //stay, do nothing
                        break hitLoop;
                    }
                    case 1 -> { //hit
                        activePlayer.addCard(deck1.drawCard());
                        activePlayer.updateHandValue();
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
            } else if (dealer1.getHandValue() > activePlayer.getHandValue() && !dealerBust) { //Dealern hade högre, du förlorar.
                updateGraphicBalance();
                printMessage(String.format(Messages.WON.print(), "Dealer"));
            } else if (dealer1.getHandValue() == activePlayer.getHandValue()) { // oavgjort
                printMessage(Messages.DRAW.print());
                activePlayer.setBalance(activePlayer.getBalance() + activePlayer.getCurrentBet());
            } else if (dealer1.getHandValue() < activePlayer.getHandValue()) { //Du vinner
                printMessage(String.format(Messages.WON.print(), "You"));
                int tempBet = activePlayer.getCurrentBet();
                activePlayer.increaseBalance();
                if(activePlayer.getHandValue() == 21){ //Blackjack!
                    activePlayer.setBalance(activePlayer.getBalance()+tempBet);
                }
            }
        } else {
            printMessage(Messages.YOU_BUST.print());
        }

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

    @Override
    public void run() {
        try {
            setUpGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
