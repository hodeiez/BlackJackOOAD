package BlackJack;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
    public Label handValue;
    @FXML
    private AnchorPane gameBoardPane;
  //  private ModelTest modelTest;
   private BlackJackLogicModel modelTest;
    private Rectangle rect = new Rectangle();
/*
    public GameBoardController(ModelTest modelTest) {
        this.modelTest = modelTest;
    }

 */
     public GameBoardController(BlackJackLogicModel modelTest) {

        this.modelTest = modelTest;
    }

    public void initialize() {
setListener(modelTest.activePlayerHand,activePlayer);
setListener(modelTest.dealerHand,dealerBox);
//Listens changes of the observableList


//balance its binded, should do a double bind? or call method from logic to change the balance?
       // balance.textProperty().bind(modelTest.balanceProperty());
        handValue.textProperty().bind(modelTest.handValueProperty());


        //using buttons for test
        //test for changeFaceUp
       // stay.setOnAction(e-> modelTest.activePlayerHandArr.get(0).changeFace());

       //testing hit
        //hit.setOnAction(e->modelTest.hitListener());
        hit.setOnAction(e->modelTest.hitListener());

        end.setOnAction(e -> player2.getChildren().add(new CardGraph("clubs", "ace", true)));



    }
    public void setListener(ObservableList<CardGraph> observable, HBox playerBox){
        observable.addListener((ListChangeListener<CardGraph>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    CardGraph c=change.getAddedSubList().get(0);
                    if(observable.size()>1)
                        c.setTranslateX(-(50*(observable.size()-1)));//this has to be simplified
                    playerBox.getChildren().add(c);

                } else if (change.wasRemoved()) {
                    playerBox.getChildren().clear();
                }
                else if(change.wasUpdated()) {
                    ((CardGraph) playerBox.getChildren().get(change.getFrom())).changeFace();
                }
            }
        });
    }
}
