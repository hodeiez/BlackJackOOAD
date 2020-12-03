package BlackJack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BlackJackLogic {
    private List<Player> players = new ArrayList<>();
    private Dealer dealer1 = new Dealer;
    private Deck deck1 = new Deck(); //lugnt om deck1 är tom här, isåfall så fixxas deet ihop ny lek i början av första rundan
    private Player activePlayer = new Player;





    private void setUpGame(){
        players.add(activePlayer);
        players.add(new Player);
        players.add(new Player);
        setStartingBalance(1000);
        playRound();

    }

    private void isItTimeToShuffle(){
        if (deck1.cardList.size()<=30) deck1 = new Deck(6);
        Collections.shuffle(deck1.cardList);
    }

    private void playRound(){
       while (true){
        isItTimeToShuffle();
        dealHands();
        dealer1.hand.get(0).isFaceUp(true);
        humanPlayerTurn();
        computerPlayerTurn(players.get(1));
        computerPlayerTurn(players.get(2));
        dealerTurn();
        isGameOver();

    }}

    private void isPlayerBroke(Player player){
        if(player.getBalace <=0){
            player.setBroke(true);

        }
    }
    private void setStartingBalance(int startCash){
        for (Player p: players) {
            p.setBalance(startCash);
        }
    }
    private void dealHands(){
        for (Player p: players) {
            p.hand.add(deck1.drawCard());
            p.hand.add(deck1.drawCard());

        }
        dealer1.hand.add(deck1.drawCard());
        dealer1.hand.add(deck1.drawCard());
    }
private void humanPlayerTurn(){
        boolean hit;
        while (true){
            System.out.println(activePlayer.getHandValue());
            //Input från användaren Hit/Stay
            if(hit = true){
                activePlayer.hand.add(deck1.drawCard());
            }else break;

        }
}
    private void dealerTurn(){
        dealer1.hand.get(1).isFaceUp(true);
        while (dealer1.getHandValue < 21){
            //Todo- Skriv vad dealern ska hitta på här :)
        }


    }
    private void computerPlayerTurn(Player player){
        while (player.getHandValue() <=21){
            player.hand.add(deck1.drawCard());
            if (player.getHandValue() <=16){
                break;
            }

        }
    }

    //Den här metoden räknar ut värdet av en spelares hand. Den tar till vara på om ifall en spelare har Ess
    //Vanliga kort har värde 1-13 ess har värde 0, eller -1 beroende på om en vill att det ska vara värt
    //11 eller 1 poäng.
    private int getHandValue() {
        int lenght = hand.size();
        int total = 0;
        for (int i = 0; i < lenght; i++) {
            Card temp = (Card) hand.get(i);
            if (temp.getNumber() >= 10) total = total + 10;//Alla kort från tio och upp är värda 10
            if (temp.getNumber() < 10 && temp.getNumber() > 0) total = total + temp.getNumber();//kortets värde läggs till totalen
            if (temp.getNumber() == 0) total = total + 11;//Ess kan vara värda 11
            if (temp.getNumber() == -1) total = total + 1;//Ess kan vara värda 1
            if (total > 21) {
                //Om vi har mer än 21 kollar denna loop igenom ifall vi har Ess, om vi har det sätter vi ässet till att
                //vara värt ett istället för elva och börjar om räkningen.
                for (int j = 0; j < lenght; j++) {
                    Card temp2 = (Card) hand.get(j);
                    if (temp2.getNumber() == 0) {
                        hand.set(j, new Card(temp2.getSuit(), -1));
                        i = -1;
                        total = 0;
                        j = lenght;
                    }
                }
            }
        }
        for (int i = 0; i < hand.size(); i++) { //Skriver ut korten till konsolen.
            System.out.println(hand.get(i));
        }
        System.out.println("Totalt värde: " + total); // Skriver ut handens totala värde.
        return total;
    }

}
