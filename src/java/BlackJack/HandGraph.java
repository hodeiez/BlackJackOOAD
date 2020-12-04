package BlackJack;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;

/**
 * Created by Hodei Eceiza
 * Date: 12/1/2020
 * Time: 19:44
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class HandGraph {
    public final ListProperty<CardGraph>cardProperty=new SimpleListProperty<CardGraph>();
    ObservableList<CardGraph> observableList;

    ArrayList<CardGraph> cards=new ArrayList<CardGraph>();
    HandGraph(){
        cards.add(new CardGraph("spades","queen",true));
        cards.add(new CardGraph("diamonds","king",false));
        cards.add(new CardGraph("clubs", "ace",true));
        cards.get(0).setTranslateX(10);
        cards.get(1).setTranslateX(30);
        cards.get(2).setTranslateX(50);
       // setCardProperty();
        observableList = FXCollections.observableList(cards);

    }
    public void setCardProperty(){
        this.cardProperty.setAll(cards);
    }
    public void removeFromList(){
        if(cards.size()>0)
            cards.remove(cards.size()-1);
        else
            System.out.println("cant remove");
    }
    public ArrayList<CardGraph> getCards() {
        return cards;
    }

    public void removeFromObser(){
        observableList.remove(observableList.size()-1);
    }
}
