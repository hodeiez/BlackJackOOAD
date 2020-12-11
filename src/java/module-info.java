
module BlackJackOOAD {
     exports BlackJack.view;
     opens BlackJack.controller;
     requires javafx.controls;
     requires javafx.fxml;
     requires javafx.graphics;
     opens BlackJack;
}