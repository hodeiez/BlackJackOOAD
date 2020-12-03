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
    @FXML
    private AnchorPane gameBoardPane;

    public void initialize() {
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
        //ObservableList<CardGraph>observableArray=FXCollections.observableArrayList(hand.cards);
        //ObservableList<CardGraph> observableList = FXCollections.observableList(hand.cards);
       // .bind(Bindings.createObjectBinding(()->(hand.cards.get(intValue.getValue())));




      //  sp.getChildren().addAll(observableList);
        //gameBoardPane.getChildren().add(sp);


        //add a card and drag it
        /*
        Node aCard = new CardGraph("hearts", "king",true);

        gameBoardPane.getChildren().add(aCard);
        aCard.setOnDragDetected(i -> {
            gameBoardPane.setOnMouseMoved(e -> {
                aCard.setTranslateX(e.getX());
                aCard.setTranslateY(e.getY());
            });
            i.consume();
        });

         */

        //testing to add a list of cards and add listeners
        /*
        StackPane testBox = new StackPane();
        testBox.setLayoutX(720);
        HandGraph graph = new HandGraph();
        ArrayList<CardGraph> list = graph.getCards();
        int i = 1;
        for (CardGraph card : list) {
            card.setTranslateX(i++ * 20);
             gameBoardPane.getChildren().add(card);
        }

        ObservableList<CardGraph> observableList = FXCollections.observableList(list);
        //  gameBoardPane.getChildren().add((Node) observableList);
        observableList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                System.out.println("it changed");
            }
        });
        //  gameBoardPane.getChildren().add(new VBox(observableList));


        rules.setOnAction(e -> {
            list.add(new CardGraph("spades", "ace",true));
            testBox.getChildren().add(observableList.get(observableList.size()-1));
        });
        end.setOnAction(e->{
           list.remove(list.size()-1);
            testBox.getChildren().remove(observableList.get(observableList.size()-1));

        });
        testBox.getChildren().addAll(observableList);
        gameBoardPane.getChildren().add(testBox);

         */
    }
}
