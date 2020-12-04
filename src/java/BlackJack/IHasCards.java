package BlackJack;

import java.util.ArrayList;

/**
 * Created by Lukas Aronsson
 * Date: 01/12/2020
 * Time: 16:22
 * Project: BlackJackOOAD
 * Copyright: MIT
 **/
public interface IHasCards {

    ArrayList<Card> hand = new ArrayList<>();


    /**
     * Calculates and returns the value of the current hand
     *
     * @return the total value of the current hand
     */
    default int getHandValue() {
        int HandTotal = 0;
        for (Card card : hand) {
            HandTotal = HandTotal + card.getNumber();
        }
        return HandTotal;
    }


}
