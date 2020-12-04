package BlackJack;


import java.util.ArrayList;
import java.util.List;

public class BlackJackLogic {
    private List<Player> players = new ArrayList<>();
    private Dealer dealer1 = new Dealer();
    private Deck deck1 = new Deck(6); //lugnt om deck1 är tom här, isåfall så fixas det ihop ny lek i början av första rundan
    private Player activePlayer = new Player();


    private void setUpGame() {
        players.add(activePlayer);
        players.add(new Player());
        players.add(new Player());
        setStartingBalance(1000);
        playRound();

    }

    private void isItTimeToShuffle() {
        if (deck1.getCardDeck().size() <= 30) {
            deck1 = new Deck(6);

        }
    }

    private void playRound() {
        while (true) {
            isItTimeToShuffle();
            dealHands();
            dealer1.hand.get(0).setFaceUp(true);
            humanPlayerTurn();
            computerPlayerTurn(players.get(1));
            computerPlayerTurn(players.get(2));
            dealerTurn();
//            isGameOver();

        }
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
        for (Player p : players) {
            p.hand.add(deck1.drawCard());
            p.hand.add(deck1.drawCard());

        }
        dealer1.hand.add(deck1.drawCard());
        dealer1.hand.add(deck1.drawCard());
    }

    private void humanPlayerTurn() {
        boolean hit;
        while (true) {
            System.out.println(activePlayer.getHandValue());
            //Input från användaren Hit/Stay
            if (hit = true) {
                activePlayer.hand.add(deck1.drawCard());
            } else break;

        }
    }

    private void dealerTurn() {
        dealer1.hand.get(1).setFaceUp(true);
        while (dealer1.getHandValue() < 21) {
            if (dealer1.getHandValue() <= activePlayer.getHandValue()) {
                dealer1.hand.add(deck1.drawCard());
            }
            if (dealer1.getHandValue() > 21) {
                System.out.println("Dealern är bust! Du vinner.");

            } else if (dealer1.getHandValue() > activePlayer.getHandValue()) {
                System.out.println("Dealern vinner!");

            } else if (dealer1.getHandValue() == activePlayer.getHandValue()) {
                System.out.println("Oavgjort!");

            } else if (dealer1.getHandValue() < activePlayer.getHandValue()) {
                System.out.println("Du vinner!");

            }


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



}
