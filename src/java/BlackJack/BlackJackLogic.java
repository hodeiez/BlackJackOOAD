package BlackJack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BlackJackLogic {
    private List<Player> players = new ArrayList<>();
    private Dealer dealer1 = new Dealer;
    private Deck d1 = new Deck;
    private Player activePlayer = new Player;





    private void setUpGame(){
        Player copyMe = new Player;
        Player.setBalance(1000);
        
    }

    private void isItTimeToShuffle(){
        if (d1.cardList.size()>=30) d1 = new Deck(6);
        Collections.shuffle(d1.cardList);
    }

    private void isPlayerBroke(Player player){
        if(player.getBalace <=0){
            player.setBroke(true);

        }
    }

}
