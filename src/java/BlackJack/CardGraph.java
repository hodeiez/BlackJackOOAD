package BlackJack;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
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
        this.faceUp=true;
        this.faceUpProp=new SimpleBooleanProperty(faceUp);
        cardImage = new ImagePattern(new Image(String.valueOf(getClass().getResource("/cardsPng/" + rank + "_of_" + suit + ".png"))));
        setStyling();


    }
    public BooleanProperty faceUpProperty(){
        return faceUpProp;
    }
    public void setFaceUp(boolean faceUp){
        this.faceUpProperty().set(faceUp);
    }
    public void changeFace(){
        this.setFill((faceUp) ? cardImage : Color.PURPLE);
    }




    public void setStyling(){
        this.setFill(cardImage);
        this.setWidth(80);
        this.setHeight(130);
        this.prefWidth(cardImage.getWidth());
        this.prefHeight(cardImage.getHeight());
        this.setArcHeight(10);
        this.setArcWidth(10);
    }

    //For tests and debug
    @Override
    public String toString() {
        return "Suit: " + suit + " number " + rank;
    }
}
