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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

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
    private Rectangle rect=new Rectangle();
    private GridPane gp;
public GameBoardController(ModelTest modelTest){
    this.modelTest=modelTest;
}
    public void initialize() {
       balance.textProperty().bind(modelTest.balanceProperty());
        stay.setOnAction(e->modelTest.setBalance("NEW BALANCE"));
        rect.setWidth(80);
        rect.setHeight(130);
        rect.setFill(Color.BLACK);
        rect.fillProperty().bind(modelTest.cardProperty());
        //rect.setWidth(80);
       // rect.setHeight(130);
        dealerBox.getChildren().add(rect);

        Rectangle bat=new Rectangle();
        bat.setFill(new CardGraph("spades","ace",true).getImgPattern());
        bat.setWidth(80);
        bat.setHeight(130);
        gameBoardPane.getChildren().add(bat);
      //  rect.fillProperty().bind(modelTest.cardProperty());
       // gameBoardPane.getChildren().add(rect);
//NOIZE DOWN HERE!!!
      //  ImageView view=new ImageView();
       // Group gr=new Group();

/*        IntegerProperty intValue=new SimpleIntegerProperty();
        HandGraph hand=new HandGraph();
       // HBox sp=new HBox();
     // ListView sp=new ListView();
       // GridPane sp=new GridPane();
        dealerBox.setStyle("-fx-background-color: green");
        hand.observableList.addListener(new ListChangeListener(){

            @Override
            public void onChanged(Change change) {
                System.out.println("changed");
            }
        });*/

   //     dealerBox.getChildren().addAll(hand.observableList);
       // sp.getChildren().addAll(hand.observableList);
     //   rules.setOnAction(e->hand.removeFromObser());
       // end.setOnAction(e->hand.observableList.add(new CardGraph("clubs","7",true)));


        //gameBoardPane.getChildren().add(dealerBox);

    }
}
