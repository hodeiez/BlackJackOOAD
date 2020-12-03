package BlackJack;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

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
    @FXML
    private AnchorPane gameBoardPane;
    private ModelTest modelTest;


public GameBoardController(ModelTest modelTest){
    this.modelTest=modelTest;
}
    public void initialize() {
       balance.textProperty().bind(modelTest.balanceProperty());
        stay.setOnAction(e->modelTest.setBalance("NEW BALANCE"));


//NOIZE DOWN HERE!!!
        ImageView view=new ImageView();
        Group gr=new Group();

        IntegerProperty intValue=new SimpleIntegerProperty();
        HandGraph hand=new HandGraph();
        HBox sp=new HBox();
     // ListView sp=new ListView();
       // GridPane sp=new GridPane();
        sp.setStyle("-fx-fill: white");
        hand.observableList.addListener(new ListChangeListener(){

            @Override
            public void onChanged(Change change) {
                System.out.println("changed");
            }
        });

        sp.getChildren().addAll(hand.observableList);
       // sp.getChildren().addAll(hand.observableList);
        rules.setOnAction(e->hand.removeFromObser());
        end.setOnAction(e->hand.observableList.add(new CardGraph("clubs","7",true)));


        gameBoardPane.getChildren().add(sp);

    }
}
