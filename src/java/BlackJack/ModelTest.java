package BlackJack;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Created by Hodei Eceiza
 * Date: 12/3/2020
 * Time: 18:02
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class ModelTest implements Runnable {
    private StringProperty balance = new SimpleStringProperty("blank");
  //  private ListProperty activePlayerHand=new SimpleListProperty();

    private ArrayList<CardGraph> handTest=new ArrayList<CardGraph>();
    public ObservableList activePlayerHand= FXCollections.observableArrayList(handTest);
    private Rectangle rect;

    private ObjectProperty card = new SimpleObjectProperty(rect);
    String bet;// still have to set

    public ModelTest() {
        rect = new Rectangle(80, 130);
        rect.setFill(new CardGraph("spades", "ace", true).getImgPattern());
    }
    public void setHandTest(CardGraph card){
        handTest.add(card);
    }
    public ArrayList<CardGraph> getHandTest(){
        return handTest;
    }
   // public ListProperty activePlayerProperty(){return activePlayerHand;}
    //public void setActivePlayerHand(CardGraph card){activePlayerProperty().add(card);}
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
      // final int[] i = {0};
        {
            while (true) {

                Platform.runLater(() ->{
                        setHandTest(new CardGraph("spades","ace",true));
                        setHandTest(new CardGraph("spades","king",true));});
                      //  setBalance("new Balance"));
                       //setBalance(String.valueOf(i[0]++)));
                      //  System.out.println(i);
                        // setCard(new CardGraph("spades", String.valueOf(i[0] +2), true).getImgPattern());




                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
