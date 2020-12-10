package BlackJack.view;

/**
 * Created by Hodei Eceiza
 * Date: 12/9/2020
 * Time: 13:32
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public enum Messages {
    WELCOME("Welcome to Super Black Jack"),
    EXIT("See you next time"),
    DEALER_TURN("Is dealers turn."),
    PLAYER_TURN("Is %s turn"),
    LOOSE("%s LOST"),
    WIN("%s WON");
    private String message;
    Messages(String message) {
        this.message=message;
    }
    public String print(){
        return message;
    }
}
