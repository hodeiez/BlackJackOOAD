package BlackJack;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Hodei Eceiza
 * Date: 11/30/2020
 * Time: 23:46
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class CardGraph extends AnchorPane {
    CardGraph(String suite,String number){
        this.setStyle("-fx-background-color: white");

        this.setWidth(80);
        this.setHeight(130);
        this.setPrefSize(80,130);
        this.getChildren().add(new Label(number + "\n"+suite));
    }
}
