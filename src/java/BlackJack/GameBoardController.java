package BlackJack;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

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
    private Rectangle rect=new Rectangle();
    private GridPane gp;
    private TilePane tp=new TilePane();

public GameBoardController(ModelTest modelTest){
    this.modelTest=modelTest;
}
    public void initialize() {
        List<CardGraph> cardsTest=new ArrayList<CardGraph>();
        cardsTest.add(new CardGraph("spades","ace",true));
        cardsTest.add(new CardGraph("spades","2",true));
        cardsTest.add(new CardGraph("diamonds","3",true));
        List<CardGraph> cardsTest2=new ArrayList<CardGraph>();
        cardsTest2.add(new CardGraph("spades","4",true));
        cardsTest2.add(new CardGraph("spades","5",true));
        cardsTest2.add(new CardGraph("diamonds","6",true));
        ArrayList<CardGraph> cardsTes3=new ArrayList<CardGraph>();



       dealerBox.getChildren().addAll(cardsTest2);

        player3.getChildren().addAll(cardsTest);
      activePlayer.getChildren().addAll(cardsTes3);

        ObservableList<CardGraph>activePlayerCards=FXCollections.observableArrayList(modelTest.getHandTest());
        player2.getChildren().addAll(modelTest.activePlayerHand);





       balance.textProperty().bind(modelTest.balanceProperty());
        stay.setOnAction(e->modelTest.setBalance("NEW BALANCE"));
        rect.setWidth(80);
        rect.setHeight(130);
        rect.setFill(Color.BLACK);
        rect.fillProperty().bind(modelTest.cardProperty());
        //rect.setWidth(80);
       // rect.setHeight(130);
        dealerBox.getChildren().add(rect);



//NOIZE DOWN HERE!!!


        HandGraph hand=new HandGraph();
       // HBox sp=new HBox();
     ListView sp=new ListView();

     // StackPane sp=new StackPane();
       // GridPane sp=new GridPane();
        dealerBox.setStyle("-fx-background-color: green");
        hand.observableList.addListener(new ListChangeListener(){

            @Override
            public void onChanged(Change change) {
                System.out.println("changed");
            }
        });

       dealerBox.getChildren().addAll(hand.observableList);
       // dealerBox.getChildren().add(sp); //<-
       //sp.getChildren().add(hand.observableList);
        sp.setItems(hand.observableList);
        //sp.setFixedCellSize(4);
        sp.setPadding(new Insets(-1000,-1000,-1000,-1000));
       rules.setOnAction(e->hand.removeFromObser());
        end.setOnAction(e->hand.observableList.add(new CardGraph("clubs","7",true)));


        //gameBoardPane.getChildren().add(dealerBox);

    }
}
