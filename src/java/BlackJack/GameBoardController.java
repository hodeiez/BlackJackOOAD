package BlackJack;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
        Node acard = new CardGraph("hearts", "king");

        gameBoardPane.getChildren().add(acard);
        acard.setOnDragDetected(i -> {
            gameBoardPane.setOnMouseMoved(e -> {
                acard.setTranslateX(e.getX());
                acard.setTranslateY(e.getY());
            });
            i.consume();
        });
        HBox testBox = new HBox();
        HandGraph graph = new HandGraph();
        ArrayList<CardGraph> list = graph.getCards();
        int i = 1;
        for (CardGraph card : list) {
            //card.setTranslateX(i++ * 20);
            // gameBoardPane.getChildren().add(card);
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
            list.add(new CardGraph("spades", "ace"));
            testBox.getChildren().add(observableList.get(observableList.size()-1));
        });
        end.setOnAction(e->{
           list.remove(list.size()-1);
            testBox.getChildren().remove(observableList.get(observableList.size()-1));

        });
        testBox.getChildren().addAll(observableList);
        gameBoardPane.getChildren().add(testBox);
//ORACLE INFO TO LEARN OBSERVABLE LIST
        /*
        // Use Java Collections to create the List.
        List<String> list = new ArrayList<String>();

        // Now add observability by wrapping it with ObservableList.
        ObservableList<String> observableList = FXCollections.observableList(list);
        observableList.addListener(new ListChangeListener() {

            @Override
            public void onChanged(ListChangeListener.Change change) {
                System.out.println("Detected a change! ");
            }
        });

        // Changes to the observableList WILL be reported.
        // This line will print out "Detected a change!"
        observableList.add("item one");

        // Changes to the underlying list will NOT be reported
        // Nothing will be printed as a result of the next line.
        list.add("item two");

        System.out.println("Size: "+observableList.size());

    }

         */
    }
}
