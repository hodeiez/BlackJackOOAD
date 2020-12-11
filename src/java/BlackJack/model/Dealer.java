package BlackJack.model;

public class Dealer extends Hand {

    private int stopValue = 17; //the value of the hand were the dealer will stop drawing cards

    /**
     * Should the dealer hit or not? (should the dealer draw a card?
     *
     * @return returns true if the dealers hand has a value smaller then the stopValue and false if the opposite
     */
    public boolean shouldDealerHit() {
        return getHandValue() < getStopValue();
    }

    /**
     * shows the dealers hidden card.
     */
    public void showCards() {
        // TODO: 12/1/2020 : Lukas : figure out what im supposed to do here (i guess i have 2 wait)
    }

    /**
     * Getter for stopValue
     *
     * @return the value of the hand were the dealer will stop drawing cards
     */
    public int getStopValue() {
        return stopValue;
    }

    /**
     * Setter for stopValue
     *
     * @param stopValue the value of the hand were the dealer will stop drawing cards
     */
    public void setStopValue(int stopValue) {
        this.stopValue = stopValue;
    }

}
