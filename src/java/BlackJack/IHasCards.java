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

    //Den här metoden räknar ut värdet av en spelares hand. Den tar till vara på om ifall en spelare har Ess
    //Vanliga kort har värde 1-13 ess har värde 0, eller -1 beroende på om en vill att det ska vara värt
    //11 eller 1 poäng.
    default int getHandValue() {
        int lenght = hand.size();
        int total = 0;
        for (int i = 0; i < lenght; i++) {
            Card temp = (Card) hand.get(i);
            if (temp.getRank() >= 10) total = total + 10;//Alla kort från tio och upp är värda 10
            if (temp.getRank() < 10 && temp.getRank() > 1)
                total = total + temp.getRank();//kortets värde läggs till totalen
            if (temp.getRank() == 0) total = total + 11;//Ess kan vara värda 11
            if (temp.getRank() == -1) total = total + 1;//Ess kan vara värda 1
            if (total > 21) {
                //Om vi har mer än 21 kollar denna loop igenom ifall vi har Ess, om vi har det sätter vi ässet till att
                //vara värt ett istället för elva och börjar om räkningen.
                for (int j = 0; j < lenght; j++) {
                    Card temp2 = (Card) hand.get(j);
                    if (temp2.getRank() == 0) {
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