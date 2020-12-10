package BlackJack;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

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
    StringProperty messages = new SimpleStringProperty();
    int choice;
    boolean hit = true;

    public void setUpGame() throws InterruptedException {
        activePlayer.setBalance(300);
        players.add(activePlayer);
        // playRound();
    }

    private void isItTimeToShuffle() {
        if (deck1.getCardDeck().size() <= 30) {
            deck1 = new Deck(6);
        }
    }

    public void playRound() throws InterruptedException {
        printMessage(Messages.WELCOME.print());

        isItTimeToShuffle();

        placeBets();

        dealHands();

        humanPlayerTurn();
/*
            dealerTurn();
            isGameOver();

 */

    }

    public void updateGraphicBalance() {

        String balance = "Balance: " + activePlayer.getBalance();
        Platform.runLater(() -> activePlayer.balanceValueProperty.set(balance));
    }


    private void isPlayerBroke() {
        if (activePlayer.getBalance() < 0) {
            activePlayer.setBroke(true);
        }
    }

    private void placeBets() {
        for (Player p : players) {
            p.setCurrentBet(100);
        }
        updateGraphicBalance();
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
                //System.out.println("in BlackJackLogic / dealHands: Player: " + p.getName() + " HandSize: " + p.hand.size());
                //  printMessage("dealHands: Player: " + p.getName() + " HandSize: " + p.hand.size());
            }

            dealer1.addCard(deck1.drawCard(), false);
            dealer1.addCard(deck1.drawCard());
            for (Player p : players) {
                p.getHandValue();
                p.updateHandValue();
            }
            dealer1.getHandValue();
            dealer1.updateHandValue();
        }
    }

    public void userChoice(int i) {
        if (hit) {
            if (i == 1 && activePlayer.getHandValue() < 21) {
                //System.out.println("Före drawCard" + activePlayer.getHandValue());
                activePlayer.addCard(deck1.drawCard());
                activePlayer.updateHandValue();
                if( activePlayer.getHandValue() > 21) {
                    new Thread(rundealerTurn).start();
                }

            }
        } else {
            new Thread(rundealerTurn).start();
            System.out.println("HERE");

        }
    }

        Task<Void> rundealerTurn= new Task() {
            @Override
            protected Void call() throws Exception {
                printMessage("dealer deals");
                dealer1.setObsFaceUp(0, true);
                //  Thread.sleep(200);
                dealer1.getHandValue();
                dealer1.updateHandValue();
                if (!activePlayer.isBroke()) {
                    if (humanBust) {
                        humanBust = false;
                    } else {
                        boolean dealerWin;
//        dealer1.hand.get(1).setFaceUp(true);

                        while (dealer1.getHandValue() < 17 && dealer1.getHandValue() < activePlayer.getHandValue()) {
                            Thread.sleep(1000);
                            dealer1.addCard(deck1.drawCard());
                            dealer1.getHandValue();
                            dealer1.updateHandValue();
                        }
                        if (dealer1.getHandValue() > 21) {
                            //System.out.println("Dealern är bust! Du vinner.");
                            printMessage("Dealern är bust! Du vinner.");
                            activePlayer.increaseBalance();
                            activePlayer.increaseBalance();
                        } else if (dealer1.getHandValue() > activePlayer.getHandValue()) {
                            //System.out.println("Dealern vinner!");
                            printMessage("Dealern vinner!");

                        } else if (dealer1.getHandValue() == activePlayer.getHandValue()) {
                            //System.out.println("Oavgjort!");
                            printMessage("Oavgjort!");
                            activePlayer.increaseBalance();
                        } else if (dealer1.getHandValue() < activePlayer.getHandValue()) {
                            printMessage("Du vinner!");
                            activePlayer.increaseBalance();
                            activePlayer.increaseBalance();
                        }
                    }
                }
                // Thread.sleep(1000);

                printMessage("RENSA");
                activePlayer.clearHand();
                dealer1.clearHand();
                return null;
            }
        };

    private void humanPlayerTurn() throws InterruptedException {
        printMessage(String.format(Messages.PLAYER_TURN.print(), activePlayer.getName()));
        isPlayerBroke();
        if (activePlayer.isBroke()) {
            //System.out.println("Du är pank: " + activePlayer.getBalance());
            printMessage("Du är pank: " + activePlayer.getBalance());
            //Game Over
        }
        /*
        else {
            //System.out.println(activePlayer.getBalance());
            //int choice = 0;
            //  while (true) {
            boolean hit = false;
            //   Platform.runLater(() -> disableButtons.setValue(false));
            //  choice = (int) BlackJackLogic.actionQueue.take();
            //  Platform.runLater(() -> disableButtons.setValue(true));
            //System.out.println("Val: " + choice);


            if (activePlayer.getHandValue() > 21) { //TODO borde vara i Player eller HasCards?
                //System.out.println("in BlackJackLogic / humanPlayerTurn: Player Handvalue: " + activePlayer.getHandValue());
                humanBust = true;
                //  break;
            }
            // }


        }

         */
    }

    private void dealerTurn() throws InterruptedException {
        printMessage("dealer deals");
        dealer1.setObsFaceUp(0, true);
      //  Thread.sleep(200);
        dealer1.getHandValue();
        dealer1.updateHandValue();
        if (!activePlayer.isBroke()) {
            if (humanBust) {
                humanBust = false;
            } else {
                boolean dealerWin;
//        dealer1.hand.get(1).setFaceUp(true);

                while (dealer1.getHandValue() < 17 && dealer1.getHandValue() < activePlayer.getHandValue()) {
                    Thread.sleep(1000);
                    dealer1.addCard(deck1.drawCard());
                    dealer1.getHandValue();
                    dealer1.updateHandValue();
                }
                if (dealer1.getHandValue() > 21) {
                    //System.out.println("Dealern är bust! Du vinner.");
                    printMessage("Dealern är bust! Du vinner.");
                    activePlayer.increaseBalance();
                    activePlayer.increaseBalance();
                } else if (dealer1.getHandValue() > activePlayer.getHandValue()) {
                    //System.out.println("Dealern vinner!");
                    printMessage("Dealern vinner!");

                } else if (dealer1.getHandValue() == activePlayer.getHandValue()) {
                    //System.out.println("Oavgjort!");
                    printMessage("Oavgjort!");
                    activePlayer.increaseBalance();
                } else if (dealer1.getHandValue() < activePlayer.getHandValue()) {
                    printMessage("Du vinner!");
                    activePlayer.increaseBalance();
                    activePlayer.increaseBalance();
                }
            }
        }
       // Thread.sleep(1000);

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

    public void isGameOver() {
        if (activePlayer.isBroke()) {
            printMessage("LOOOOOOOOOSEEERRRR!!!!!!!!");
            activePlayer.clearHand();
            dealer1.clearHand();
            activePlayer.setBalance(1000);
            activePlayer.setBroke(false);
        }
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
