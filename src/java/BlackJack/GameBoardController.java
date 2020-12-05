package BlackJack;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    public HBox dealerBox;
    public HBox activePlayer;
    public HBox player2;
    public HBox player3;
    @FXML
    private AnchorPane gameBoardPane;
    private ModelTest modelTest;
    private Rectangle rect = new Rectangle();

    public GameBoardController(ModelTest modelTest) {
        this.modelTest = modelTest;
    }

    public void initialize() {

//Listens changes of the observableList
        modelTest.activePlayerHand.addListener((ListChangeListener<CardGraph>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    activePlayer.getChildren().add(change.getAddedSubList().get(0));

                } else if (change.wasRemoved()) {
                    activePlayer.getChildren().clear();
                }
                else if(change.wasUpdated()) {
                      ((CardGraph) activePlayer.getChildren().get(change.getFrom())).setFill(Color.PURPLE);
                }
            }
        });

        //using buttons for test
        stay.setOnAction(e-> modelTest.activePlayerHandArr.get(0).setFaceUp(false));


        end.setOnAction(e -> player2.getChildren().add(new CardGraph("clubs", "ace", true)));

//balance its binded, should do a double bind? or call method from logic to change the balance?
        balance.textProperty().bind(modelTest.balanceProperty());


    }
}
