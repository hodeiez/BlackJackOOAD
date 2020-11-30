package BlackJack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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
public class GameBoardView extends Parent {
    Pane mainPane;
    public GameBoardView(Pane mainPane) {
        this.mainPane = mainPane;
        Parent pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/GameBoard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = (Stage) mainPane.getScene().getWindow();
        primaryStage.getScene().setRoot(pane);
    }


    /*
    GameBoardView(){
        try {

         //   FXMLLoader load= FXMLLoader.load(getClass().getResource("/GameBoard.fxml"));
          FXMLLoader.load(getClass().getResource("/GameBoard.fxml"));
          //loader.setController(new GameBoardController());
        //  load.setController(new GameBoardController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     */
}
