package BlackJack;

import javafx.application.Application;


public class Main  {

    public static void main(String[] args) {
//        Application.launch(GameBoardView.class,args);

BlackJackLogic bnj= new BlackJackLogic();
bnj.playRoundConsoleVersion();

    }

}
