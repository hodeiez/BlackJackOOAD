package BlackJack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by Hodei Eceiza
 * Date: 11/30/2020
 * Time: 15:50
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class GameBoardView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       ModelTest myTest=new ModelTest(); //here logic model runs
        Thread th=new Thread(myTest);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameBoard.fxml"));
        loader.setController(new GameBoardController(myTest));

        stage.setTitle("Black Jack");
        Parent root=loader.load();
        stage.setScene(new Scene(root));
       stage.getScene().getStylesheets().getClass().getResource("/blackJack.css");
     //  getClass().getClassLoader().getResource("/blackJack.css");
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

       th.start();
    }

    }


