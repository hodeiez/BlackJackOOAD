package BlackJack;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJackLogic {
    private List<Player> players = new ArrayList<>();
    private Dealer dealer1 = new Dealer();
    private Deck deck1 = new Deck(1);
    private Player activePlayer = new Player();
    boolean humanBust;

    private void setUpGame() {
        players.add(activePlayer);
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());
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
    public void playRoundConsoleVersion() {

        Deck deck1 = new Deck(1);
        players.add(activePlayer);
        while (true) {
            isItTimeToShuffle();
            dealHands();

            dealer1.hand.get(0).setFaceUp(true);
            System.out.println("Dealerns hand är värd: "+ dealer1.getHandValue());
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

    private void dealHands() {
        Deck deck1 = new Deck(1);
        for (Player p : players) {
            System.out.println(p.hand.size());
            p.hand.add(deck1.drawCard());
            p.hand.add(deck1.drawCard());

        }

        dealer1.hand.add(deck1.drawCard());
        System.out.println("Dealern drog: "+ dealer1.hand.get(dealer1.hand.size()-1));
        deck1.cardDeck.remove(0);
        System.out.println((activePlayer.hand==dealer1.hand));
        System.out.println(activePlayer.hand.equals(dealer1.hand));

    }

    private void humanPlayerTurn() {

        int choice = 0;
        Scanner scan = new Scanner(System.in);

        while (true) {
            boolean hit = false;
            if(+activePlayer.getHandValue()>21){
                humanBust = true;
                break;
            }
            System.out.println("Du drog: " +activePlayer.hand.get(activePlayer.hand.size()-2));
            System.out.println("Du drog: "+ activePlayer.hand.get(activePlayer.hand.size()-1));
            System.out.println("Din hand är värd: "+activePlayer.getHandValue());
            choice=Integer.parseInt(scan.nextLine());//Input från användaren Hit/Stay
            System.out.println("Val: "+choice);
            if (choice==1){
                hit = true;
            }
            if (hit) {
                activePlayer.hand.add(deck1.drawCard());
                System.out.println("Du drog: "+ activePlayer.hand.get(activePlayer.hand.size()-1));
            } else {
                System.out.println("Sluta dra");
                break;
                }

        }
    }

    private void dealerTurn() {
        if (humanBust){
            System.out.println("Din hand är värd: "+activePlayer.getHandValue()+"Du är bust! Dealern vinner!");
        }else{
        boolean dealerWin;
//        dealer1.hand.get(1).setFaceUp(true);
        System.out.println("Dealern drog: "+ dealer1.hand.get(dealer1.hand.size()-1));
        while (dealer1.getHandValue() < 21&&dealer1.getHandValue() < activePlayer.getHandValue()) {
            dealer1.hand.add(deck1.drawCard());
            System.out.println("Dealern drog: "+ dealer1.hand.get(dealer1.hand.size()-1));}

            if (dealer1.getHandValue() > 21) {
                System.out.println("Din hand är värd: "+activePlayer.getHandValue());
                System.out.println("Dealerns hand är värd: "+dealer1.getHandValue());
                System.out.println("Dealern är bust! Du vinner.");

            } else if (dealer1.getHandValue() > activePlayer.getHandValue()) {
                System.out.println("Din hand är värd: "+activePlayer.getHandValue());
                System.out.println("Dealerns hand är värd: "+dealer1.getHandValue());
                System.out.println("Dealern vinner!");
                dealerWin = true;

            } else if (dealer1.getHandValue() == activePlayer.getHandValue()) {
                System.out.println("Din hand är värd: "+activePlayer.getHandValue());
                System.out.println("Dealerns hand är värd: "+dealer1.getHandValue());
                System.out.println("Oavgjort!");

            } else if (dealer1.getHandValue() < activePlayer.getHandValue()) {
                System.out.println("Din hand är värd: "+activePlayer.getHandValue());
                System.out.println("Dealerns hand är värd: "+dealer1.getHandValue());
                System.out.println("Du vinner!");

            }}
            activePlayer.clearHand();
            dealer1.clearHand();


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
