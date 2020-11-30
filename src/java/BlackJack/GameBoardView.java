package BlackJack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Hodei Eceiza
 * Date: 11/30/2020
 * Time: 15:50
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class GameBoardView extends Parent{

    GameBoardView(){
        try {

         //   FXMLLoader load= FXMLLoader.load(getClass().getResource("/GameBoard.fxml"));
           FXMLLoader.load(getClass().getResource("/GameBoard.fxml"));
        //  load.setController(new GameBoardController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
