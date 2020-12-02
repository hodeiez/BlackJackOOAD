package BlackJack;

/**
 * Created by Lukas Aronsson
 * Date: 01/12/2020
 * Time: 16:23
 * Project: BlackJackOOAD
 * Copyright: MIT
 **/
public class Player implements IHasCards{
    private String name = "Player"; //the players name
    private int balance = 0; //the amount of balance the player has currently
    private int currentBet = 0; //the amount of balance the player have decided to bet this round

    /**
     * Increases the players balance
     */
    public void increaseBalance(){
        setBalance(getBalance()+getCurrentBet()*2);
    }

    // TODO: 12/1/2020 : Lukas : add decrease player balance?

    /**
     * sets the currentBet based on user input
     */
    public void bet(int bet){
        // TODO: 12/2/2020 maybe add a max bet ?
        setCurrentBet(bet);
    }

    /**
     * Getter for name
     * @return the players name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name
     * @param name the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the player balance
     * @return the amount of balance the player has currently
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Setter for the player balance
     * @param balance the amount of balance the player has currently
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Getter for the currentBet
     * @return the amount of balance the player has decided to bet this round
     */
    public int getCurrentBet() {
        return currentBet;
    }

    /**
     * Setter for the currentBet
     * @param currentBet the amount of balance the player has decided to bet this round
     */
    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }
}
