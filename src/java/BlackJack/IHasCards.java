package BlackJack;

import java.util.List;

/**
 * Created by Lukas Aronsson
 * Date: 01/12/2020
 * Time: 16:22
 * Project: BlackJackOOAD
 * Copyright: MIT
 **/
public interface IHasCards {
    private List<Card> hand = new List<Card>();


    /**
     * Calculates and returns the value of the current hand
     * @return the total value of the current hand
     */
    default int getHandValue(){

        return 0;
    }


}
