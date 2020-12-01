package BlackJack;

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
    ArrayList<CardGraph> cards=new ArrayList<CardGraph>();
    HandGraph(){
        cards.add(new CardGraph("spades","queen"));
        cards.add(new CardGraph("diamonds","king"));
        cards.add(new CardGraph("clubs", "ace"));

    }

    public ArrayList<CardGraph> getCards() {
        return cards;
    }
}
