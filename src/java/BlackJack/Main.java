package BlackJack;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root=new GameBoardView();
        //primaryStage.setTitle("BlackJack");
        //primaryStage.setScene(new Scene(root));
        //primaryStage.show();
        Parent root = FXMLLoader.load(getClass().getResource("/GameBoard.fxml"));
        primaryStage.setTitle("Black Jack");

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main (String[] args) {
        launch(args);
    }
}
