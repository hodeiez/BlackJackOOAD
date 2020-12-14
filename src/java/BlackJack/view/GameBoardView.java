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
    /**
     * initiates BlackJackLogic, Loads fxml file, asigns and initiates GameboardController for fxml file
     * and sets them into a scene to add to the stage
     * @param stage the window to show
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        BlackJackLogic blackJackLogic = new BlackJackLogic();
        Thread logic = new Thread(blackJackLogic);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameBoard.fxml"));
        loader.setController(new GameBoardController(blackJackLogic));

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

        logic.start();
    }

}


