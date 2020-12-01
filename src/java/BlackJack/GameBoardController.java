package BlackJack;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Hodei Eceiza
 * Date: 11/30/2020
 * Time: 15:50
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class GameBoardController {
    public Button hit;
    public Button stay;
    public Button rules;
    public Button end;
    public Label balance;
    @FXML
    private AnchorPane gameBoardPane;
    public void initialize(){
        Node acard=new CardGraph("heart","3");
        acard.setLayoutX(200);
        acard.setLayoutY(200);
        gameBoardPane.getChildren().add(acard);
        gameBoardPane.setOnMouseMoved(e-> {acard.setTranslateX(e.getX());acard.setTranslateY(e.getY());});

    }
}
