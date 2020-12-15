package BlackJack.model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player extends Hand {
    public int balance = 1000; //the amount of balance the player has currently
    public StringProperty balanceValueProperty = new SimpleStringProperty();
    private int currentBet = 0; //the amount of balance the player have decided to bet this round
    private boolean broke = false; //if player ran out of money

    /**
     * constructor gives player a hand
     */
    public Player() {
    }

    /**
     * Increases the players balance
     */
    public void increaseBalance() {
        setBalance(getBalance() + (getCurrentBet() * 2));
        setCurrentBet(0);
    }

    /**
     * Getter for name
     *
     * @return the players name
     */
    public String getName() {
        //the players name
        return "Player";
    }

    /**
     * Getter for the player balance
     *
     * @return the amount of balance the player has currently
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Setter for the player balance
     *
     * @param balance the amount of balance the player has currently
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Getter for the currentBet
     *
     * @return the amount of balance the player has decided to bet this round
     */
    public int getCurrentBet() {
        return currentBet;
    }

    /**
     * Setter for the currentBet
     *
     * @param currentBet the amount of balance the player has decided to bet this round
     */
    public void setCurrentBet(int currentBet) {
        if (currentBet <= balance) {
            this.currentBet = currentBet;
            balance -= currentBet;
        } else {
            System.out.println("För lite pengar.");
            broke = true;
        }
    }

    /**
     * Getter For broke
     *
     * @return if the ComputerPlayer is all out of balance (gets removed form the game)
     */
    public boolean isBroke() {
        setBroke(this.getBalance() <= 0 && this.getCurrentBet() <= 0);
        return broke;
    }

    /**
     * Setter for broke
     *
     * @param broke if the ComputerPlayer is all out of balance (gets removed form the game)
     */
    public void setBroke(boolean broke) {
        this.broke = broke;
    }
}

