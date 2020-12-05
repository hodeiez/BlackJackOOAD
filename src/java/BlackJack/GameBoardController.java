package BlackJack;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
                   // System.out.println(modelTest.activePlayerHandArr.size());
                    System.out.println(modelTest.activePlayerHand.size());
                } else if (change.wasRemoved()) {
                    activePlayer.getChildren().clear();
                }
                else if(change.wasUpdated()) {
                    for(int i=change.getFrom();i<change.getTo();i++){
                        ((CardGraph) modelTest.activePlayerHand.get(i)).changeFace(false);
                        System.out.println(i+"changed!");}
                    //  ((CardGraph) activePlayer.getChildren().get(0)).setFaceUp(false);
                }
            }
        });
        CardGraph cardTest=new CardGraph("diamonds","ace",true);
        cardTest.setTranslateX(-50);
        hit.setOnAction(e-> modelTest.addCardActPlayer(cardTest));
        stay.setOnAction(e->Platform.runLater (()->((CardGraph) modelTest.activePlayerHandArr.get(1)).setFaceUp(false)));



        modelTest.player2Hand.addListener((ListChangeListener<CardGraph>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    CardGraph card = change.getAddedSubList().get(0);
                    card.setTranslateX(110);
                    player2.getChildren().add(card);
                } else if (change.wasRemoved()) {

                }
            }
        });
        end.setOnAction(e -> player2.getChildren().add(new CardGraph("clubs", "ace", true)));


        balance.textProperty().bind(modelTest.balanceProperty());


    }
}
