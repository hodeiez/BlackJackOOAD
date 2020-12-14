
module BlackJackIndividuellUppgift {
     exports BlackJack.view;
     requires javafx.controls;
     requires javafx.fxml;
     requires javafx.graphics;
     opens BlackJack.controller;
     opens BlackJack;
     opens BlackJack.model;
     opens BlackJack.view;
}