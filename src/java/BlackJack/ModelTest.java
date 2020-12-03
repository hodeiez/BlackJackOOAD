package BlackJack;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Created by Hodei Eceiza
 * Date: 12/3/2020
 * Time: 18:02
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class ModelTest implements Runnable {
    private StringProperty balance = new SimpleStringProperty("blank");

    private Rectangle rect;

    private ObjectProperty card = new SimpleObjectProperty(rect);
    String bet;// still have to set

    public ModelTest() {
        rect = new Rectangle(80, 130);
        rect.setFill(new CardGraph("spades", "ace", true).getImgPattern());
    }


    public ObjectProperty cardProperty() {
        return card;
    }

    public void setCard(ImagePattern card) {
        cardProperty().set(card);
    }


    public StringProperty balanceProperty() {
        return balance;
    }

    public String getBalance() {
        return balanceProperty().get();
    }

    public void setBalance(String balance) {
        balanceProperty().set(balance);
    }

    @Override
    public void run() {
       final int[] i = {0};
        {
            while (true) {

                Platform.runLater(() ->

                        setBalance(String.valueOf(i[0]++)));
                        System.out.println(i);
                         setCard(new CardGraph("spades", String.valueOf(i[0] +2), true).getImgPattern());




                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
