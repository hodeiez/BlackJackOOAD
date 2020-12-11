package BlackJack;

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
    Dealer dealer1 = new Dealer();
    private Deck deck1 = new Deck(6);
    Player activePlayer = new Player();
    BooleanProperty disableButtons = new SimpleBooleanProperty(false);
    BooleanProperty bettingScreen = new SimpleBooleanProperty(true);
    boolean humanBust;
    boolean highScoreSet;
    public static BlockingQueue<Object> actionQueue = new LinkedBlockingQueue();
    StringProperty messages = new SimpleStringProperty();
    HighScore highScore = HighScore.getInstance();

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

            if(!activePlayer.isBroke()){
                placeBets();

                dealHands();
                //dealer1.hand.get(0).setFaceUp(true);
                humanPlayerTurn();
//              computerPlayerTurn(players.get(1));
//              computerPlayerTurn(players.get(2));
                dealerTurn();
            }
            isGameOver();
        }
    }

    public void updateGraphicBalance() {
        String balance = "Balance: " + activePlayer.getBalance();
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
        if (activePlayer.getBalance() <= 0 && activePlayer.getCurrentBet() <= 0) {
            activePlayer.setBroke(true);
        }
    }

    private void placeBets() throws InterruptedException {

        if(!activePlayer.isBroke()) {

            Platform.runLater(() -> bettingScreen.setValue(true));

            updateGraphicBalance();

            Integer bet = (Integer) actionQueue.take();

            activePlayer.setCurrentBet(bet);

            //sets the bot bet to players bet
            updateGraphicBalance();

            Platform.runLater(() -> bettingScreen.setValue(false));
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

            dealer1.addCard(deck1.drawCard(), false);
            dealer1.addCard(deck1.drawCard());
            for (Player p : players) {
                p.getHandValue();
            }
            dealer1.getHandValue();
        }
    }

    private void humanPlayerTurn() throws InterruptedException {
        printMessage(String.format(Messages.PLAYER_TURN.print(), activePlayer.getName()));
        isPlayerBroke();
        if (activePlayer.isBroke()) {
            System.out.println("Du är pank: " + activePlayer.getBalance());
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
                System.out.println("Val: " + choice);
                switch(choice){
                    case 0 -> { //stay, do nothing
                        System.out.println("Stay");
                        break hitLoop;
                    }
                    case 1 -> { //hit
                        activePlayer.addCard(deck1.drawCard());
                    }
                    case 9 -> { //Pressed submit on highscore
                        System.out.println("HighScore pushed, acting as a Stay right now");
                        String name = (String) BlackJackLogic.actionQueue.take();
                        highScoreSet = highScore.addHighScore(name,activePlayer.balance);
                        if (highScoreSet) { //Adds highscore if it makes it
                            System.out.println("HighScore added.");

                            // TODO: 11/12/2020 maybe change this part not sure
                            while(activePlayer.getBalance()>1){
                                activePlayer.setCurrentBet(0);
                            }
                            isPlayerBroke();
                        } else {
                            System.out.println("Highscore to low");
                        }

                        break hitLoop; //TODO: End game!
                    }
                    default -> {
                        break hitLoop;
                    }
                }

                if (activePlayer.getHandValue() > 21) {
                    System.out.println("in BlackJackLogic / humanPlayerTurn: Player Handvalue: " + activePlayer.getHandValue());
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
        dealer1.getHandValue();
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
                }
                if (dealer1.getHandValue() > 21) {
                  //  System.out.println("Dealern är bust! Du vinner.");
                    printMessage(String.format(Messages.BUST.print(),"Dealer"));
                    activePlayer.increaseBalance();
                    activePlayer.increaseBalance();
                } else if (dealer1.getHandValue() > activePlayer.getHandValue()) {
                    System.out.println("Dealern vinner!");
                    activePlayer.setCurrentBet(0);
                    printMessage(String.format(Messages.WON.print(),"Dealer"));

                } else if (dealer1.getHandValue() == activePlayer.getHandValue()) {
                   System.out.println("Oavgjort!");
                    printMessage(Messages.DRAW.print());
                    activePlayer.setBalance(activePlayer.getBalance() + activePlayer.getCurrentBet());
                } else if (dealer1.getHandValue() < activePlayer.getHandValue()) {
                    printMessage(String.format(Messages.WON.print(),"You"));
                    activePlayer.increaseBalance();
                    activePlayer.increaseBalance();
                }
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

    public void isGameOver() {
        if (activePlayer.isBroke()) {
            if(highScoreSet){
                printMessage("You made the highscore!");
            } else{
                System.out.println("LOOOOOOOOOSEEERRRR!!!!!!!!");
                printMessage("LOOOOOOOOOSEEERRRR!!!!!!!!");
            }
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
