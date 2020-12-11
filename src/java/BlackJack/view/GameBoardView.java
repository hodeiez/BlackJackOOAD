package BlackJack.view;

import BlackJack.controller.BlackJackLogic;
import BlackJack.controller.GameBoardController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameBoardView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //  ModelTest myTest=new ModelTest(); //here logic model runs
//       BlackJackLogicModel myTest=new BlackJackLogicModel(); //here logic model runs
        BlackJackLogic myTest = new BlackJackLogic();
        Thread th = new Thread(myTest);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameBoard.fxml"));
        loader.setController(new GameBoardController(myTest));

        stage.setTitle("Black Jack");
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add(getClass().getResource("/blackJack.css").toExternalForm());

        stage.show();
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        th.start();
    }

}


