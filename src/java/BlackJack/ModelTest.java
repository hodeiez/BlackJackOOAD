package BlackJack;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Hodei Eceiza
 * Date: 12/3/2020
 * Time: 18:02
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class ModelTest implements Runnable{
    private  StringProperty balance=new SimpleStringProperty("blank");
    private ObjectProperty card=new SimpleObjectProperty(new CardGraph("spades","ace",true));

    String bet;// still have to set

    public ObjectProperty cardProperty(){
        return card;
    }
    public void setCard(CardGraph card){
        cardProperty().set(card);
    }
    public StringProperty balanceProperty(){
        return balance;
    }
    public  String getBalance(){
        return balanceProperty().get();
    }
    public void setBalance(String balance){
        balanceProperty().set(balance);
    }

    @Override
    public void run() {
        final int []i = {0};
        {
            while(true){

                Platform.runLater(() -> setBalance(String.valueOf(i[0]++))); //here logic runnable should run
                System.out.println(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}

    }
}
