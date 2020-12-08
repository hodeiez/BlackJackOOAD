package BlackJack;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlackJackLogic implements Runnable {
    private List<Player> players = new ArrayList<>();
    Dealer dealer1 = new Dealer();
    private Deck deck1 = new Deck(1);
    Player activePlayer = new Player();
    BooleanProperty disableButtons = new SimpleBooleanProperty(false);
    boolean humanBust;
    public static BlockingQueue<Integer> actionQueue = new LinkedBlockingQueue();

    private void setUpGame() throws InterruptedException {
        players.add(activePlayer);
//        players.add(new ComputerPlayer());
//        players.add(new ComputerPlayer());
        setStartingBalance(1000);
        playRound();
    }

    private void isItTimeToShuffle() {
        if (deck1.getCardDeck().size() <= 30) {
            deck1 = new Deck(6);
        }
    }

    private void playRound() throws InterruptedException {
        while (true) {
            isItTimeToShuffle();
            dealHands();
            //dealer1.hand.get(0).setFaceUp(true);
            humanPlayerTurn();
//            computerPlayerTurn(players.get(1));
//            computerPlayerTurn(players.get(2));
            dealerTurn();
//            isGameOver();
        }
    }

    public void playRoundConsoleVersion() throws InterruptedException {

        Deck deck1 = new Deck(1);
        players.add(activePlayer);
        while (true) {
            isItTimeToShuffle();
            dealHands();
            dealer1.hand.get(0).setFaceUp(true);
            System.out.println("Dealerns hand är värd: " + dealer1.getHandValue());
            humanPlayerTurn();
//            computerPlayerTurn(players.get(1));
//            computerPlayerTurn(players.get(2));
            dealerTurn();
//            isGameOver();
        }
    }

    private void isPlayerBroke(Player player) {
        if (player.getBalance() <= 0) {
            player.setBroke(true);
        }
    }

    private void setStartingBalance(int startCash) {
        for (Player p : players) {
            p.setBalance(startCash);
        }
    }

    private void dealHands() throws InterruptedException {
        Deck deck1 = new Deck(1);
        for (Player p : players) {
            p.addCard(deck1.drawCard());
            p.addCard(deck1.drawCard());
            Thread.sleep(70);
            p.getHandValue();
            System.out.println("in BlackJackLogic / dealHands: Player: " + p.getName() + " HandSize: " + p.hand.size());
        }
        dealer1.addCard(deck1.drawCard());
        deck1.cardDeck.remove(0);
    }

    private void humanPlayerTurn() throws InterruptedException {
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
    }

    private void dealerTurn() throws InterruptedException {
        Thread.sleep(1000);
        dealer1.addCard((deck1.drawCard()));
        Thread.sleep(3);
        if (humanBust) {
            humanBust = false;
        } else {
            boolean dealerWin;
//        dealer1.hand.get(1).setFaceUp(true);
            while (dealer1.getHandValue() < 17 && dealer1.getHandValue() < activePlayer.getHandValue()) {
                Thread.sleep(1000);
                dealer1.addCard(deck1.drawCard());
                Thread.sleep(3);
            }
            if (dealer1.getHandValue() > 21) {
                System.out.println("Dealern är bust! Du vinner.");
            } else if (dealer1.getHandValue() > activePlayer.getHandValue()) {
                System.out.println("Dealern vinner!");
                dealerWin = true;
            } else if (dealer1.getHandValue() == activePlayer.getHandValue()) {
                System.out.println("Oavgjort!");
            } else if (dealer1.getHandValue() < activePlayer.getHandValue()) {
                System.out.println("Du vinner!");
            }
        }
        Thread.sleep(1000);
        System.out.println("RENSA");
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

    @Override
    public void run() {
        try {
            setUpGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
