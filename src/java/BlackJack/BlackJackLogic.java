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
        players.add(new Player);
        players.add(new Player);
        players.add(activePlayer);
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
        dealHands():

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
    }

}
