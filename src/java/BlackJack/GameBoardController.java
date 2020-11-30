package BlackJack;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Hodei Eceiza
 * Date: 11/30/2020
 * Time: 15:50
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class GameBoardController {
    @FXML
    private AnchorPane gameBoardPane;
    public void initialize(){
        Node acard=new CardGraph("heart","1");
        acard.setLayoutX(200);
        acard.setLayoutY(200);
        gameBoardPane.getChildren().add(acard);
    }
}
