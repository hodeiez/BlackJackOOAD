package BlackJack;

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
public class CardGraph extends Group {


    CardGraph(String suit,String number, boolean faceUp){
        //BUILDING CARDS FROM FOLDER
//Insert image in CARD class??
        ImagePattern cardImage=new ImagePattern(new Image(String.valueOf(getClass().getResource("/cardsPng/"+number+"_of_"+suit+".png"))));
        Rectangle rect=new Rectangle();
        rect.setWidth(80);
        rect.setHeight(130);

        if ((faceUp)) {
            rect.setFill(cardImage);
        } else {
            rect.setFill(Color.PURPLE);
        }
        this.prefWidth(cardImage.getWidth());
        this.prefHeight(cardImage.getHeight());
        this.getChildren().add(rect);


        //BUILDING CARDS PROGRAMMATICALLY
        /*
       Rectangle baserRect=new Rectangle(); //<-the card base
        baserRect.setStyle("-fx-fill:white;-fx-arc-width: 10;-fx-arc-height: 10");
       // this.setFill(Color.WHITE);

        baserRect.setWidth(80);
        baserRect.setHeight(130);


        Label numbL=new Label(number);//label up left
        Label suitL=new Label("\u2663");//label up left
        //this.setPrefSize(80,130);
        numbL.setTranslateY(0);
        numbL.setTranslateX(0);
        suitL.setTranslateY(10);
        suitL.setTranslateX(0);

        Label numb2L=new Label(number);//label down right
        Label suit2L=new Label("\u2663");//label down right
        //this.setPrefSize(80,130);
        numb2L.setRotate(180);
        suit2L.setRotate(180);
        numb2L.setTranslateY(115);
        numb2L.setTranslateX(70);
        suit2L.setTranslateY(105);
        suit2L.setTranslateX(70);

        Group suitsInMiddel=new Group();
for(int i=0;i<Integer.parseInt(number);i++) {

    Label suitMiddle = new Label("\u2663");
    suitMiddle.setTextAlignment(TextAlignment.CENTER);
    suitMiddle.setPrefSize(0, 0);
    suitMiddle.setWrapText(true);
    suitMiddle.setScaleX(1.2);
    suitMiddle.setScaleY(1.2);
    suitMiddle.setTranslateX((80/2));
    suitMiddle.setTranslateY((20)+i*20);
    suitsInMiddel.getChildren().add(suitMiddle);
}
        this.prefWidth(80);
        this.prefHeight(130);
        this.getChildren().addAll(baserRect,numbL,suitL, numb2L,suit2L,suitsInMiddel);

         */
    }
}
