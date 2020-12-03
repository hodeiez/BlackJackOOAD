package BlackJack;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main  {

    public static void main(String[] args) {
        Application.launch(GameBoardView.class,args);

    }
/*
    @Override
    public void start(Stage primaryStage) throws Exception{
       // Parent root=new GameBoardView();
       // primaryStage.setTitle("BlackJack");
        //primaryStage.setScene(new Scene(root));
        //primaryStage.show();

    }


    public static void main (String[] args) {
        launch(args);
    }

 */
}
