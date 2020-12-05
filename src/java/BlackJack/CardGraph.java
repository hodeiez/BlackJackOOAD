package BlackJack;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableArray;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


/**
 * Created by Hodei Eceiza
 * Date: 11/30/2020
 * Time: 23:46
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class CardGraph extends Rectangle {
    private ImagePattern cardImage;
    private String suit;
    private String rank;
    private boolean faceUp;
    private final BooleanProperty faceUpProp;
    CardGraph(String suit, String rank, boolean faceUp) {
        this.suit = suit;
        this.rank = rank;
        this.faceUpProp=new SimpleBooleanProperty(faceUp);
       // this.faceUp=faceUp;
        cardImage = new ImagePattern(new Image(String.valueOf(getClass().getResource("/cardsPng/" + rank + "_of_" + suit + ".png"))));


        this.setWidth(80);
        this.setHeight(130);
        this.setFill((faceUp) ? cardImage : Color.PURPLE);
        this.prefWidth(cardImage.getWidth());
        this.prefHeight(cardImage.getHeight());

    }
    public BooleanProperty faceUpProperty(){
        return faceUpProp;
    }
    public void setFaceUp(boolean faceUp){
        this.faceUpProperty().set(faceUp);
    }
    public void changeFace(boolean c){
        this.faceUp=c;
    }
    @Override
    public String toString() {
        return "Suit: " + suit + " number " + rank;
    }
}
