/**
 * Created by Hodei Eceiza
 * Date: 11/26/2020
 * Time: 20:25
 * Project: JavaFxMaven
 * Copyright: MIT
 */
module BlackJackOOAD {
     exports BlackJack.view;
     opens BlackJack.controller;
     requires javafx.controls;
     requires javafx.fxml;
     requires javafx.graphics;
     opens BlackJack;
}