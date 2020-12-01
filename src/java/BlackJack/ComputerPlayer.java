package BlackJack;

/**
 * Created by Lukas Aronsson
 * Date: 01/12/2020
 * Time: 16:23
 * Project: BlackJackOOAD
 * Copyright: MIT
 **/
public class ComputerPlayer extends Player {

    private boolean broke = false; //if the ComputerPlayer is all out of balance (gets removed form the game)

    // TODO: 12/1/2020 : Lukas :  add bot stopValue for easier properties later?

    /**
     * Returns a random generated name for the ComputerPlayer
     * @return a random generated name from a list
     */
    private String generateRandomName(){
        return "stupidBot";
    }

    /**
     * a check if the ComputerPlayer Should pickup cards or not
     * @return the ComputerPlayer should pickup a card or not
     */
    private boolean shouldHit(){
        return true;
    }

    /**
     * Getter For broke
     * @return if the ComputerPlayer is all out of balance (gets removed form the game)
     */
    public boolean isBroke() {
        return broke;
    }

    /**
     * Setter for broke
     * @param broke if the ComputerPlayer is all out of balance (gets removed form the game)
     */
    public void setBroke(boolean broke) {
        this.broke = broke;
    }
}
