package BlackJack.controller;

import BlackJack.model.Card;
import BlackJack.view.CardGraph;
import BlackJack.view.Messages;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class GameBoardController {

    @FXML
    public Button hit;
    public Pane welcome;
    public Label clickToPlay;

    public Button stay;
    public Button rules;
    public Button end;
    public Button highScore;
    public Label balance;
    public Label bet;
    public HBox dealerBox;
    public HBox activePlayer;
    public HBox player2; //Cards-space for Future AI#1
    public HBox player3; //Cards-space for future AI#2
    public Label handValue;
    public Label dealerValue;
    public Label messages;

    public DialogPane rulesPanel;

    public AnchorPane highScorePane;
    public Label highScoreDate;
    public Label highScoreName;
    public Label highScoreScore;
    public TextField textFieldHS;
    public Button highScoreSubmit;
    public Label highScoreNotice;

    public Pane BettingScreen;
    public Label BettingText;
    public Label BetAmount;
    public Button Plus;
    public Button Minus;
    public Button Bet;

    public FadeTransition infiniteFade;
    public AnchorPane gameOver;
    public Button buttonQuit;
    public Button buttonResume;
    public AnchorPane highScoreList;
    @FXML

    private final BlackJackLogic blackJackLogic;
    int tempBet = 0;

    public GameBoardController(BlackJackLogic blackJackLogic) {
        this.blackJackLogic = blackJackLogic;
    }

    public void initialize() {
        welcomeAnimation();

        setListener(blackJackLogic.activePlayer.handObs, activePlayer);
        setListener(blackJackLogic.dealer1.handObs, dealerBox);
        setBettingScreenListener();
        rulesPanelSettings();
        setButtonListener();
        setHandValueListeners();
        setBalanceValueListener();
        setGameOverPanelListener();
        setHighScoreListener();
        messages.textProperty().bind(blackJackLogic.messages);
        highScoreDate.textProperty().bind(blackJackLogic.highScore.dates);
        highScoreName.textProperty().bind(blackJackLogic.highScore.names);
        highScoreScore.textProperty().bind(blackJackLogic.highScore.scores);

        hit.setOnAction(e -> BlackJackLogic.actionQueue.add(1));
        end.setOnAction(e -> System.exit(0));
        highScore.setOnAction(e -> buttonHighScoreAction());
        stay.setOnAction(e -> BlackJackLogic.actionQueue.add(0));
        highScoreSubmit.setOnAction(e -> buttonHighScoreSubmitAction());
        textFieldHS.setOnKeyPressed(e -> {
            highScoreNotice.setText("Adding highscore ends current game.");
            highScoreNotice.setStyle("-fx-text-fill: white");
        });
        rules.setOnMouseClicked(e -> {
            rulesPanel.toFront();
            rulesPanel.setVisible(!rulesPanel.isVisible());
        });
        Plus.setOnAction(e -> plus());
        Minus.setOnAction(e -> minus());
        Bet.setOnAction(e -> betted());
        buttonQuit.setOnAction(e -> buttonQuitAction());
        buttonResume.setOnAction(e -> buttonResumeAction());

        welcome.setOnMouseClicked(e -> welcomeClose());
    }

    public void setListener(ObservableList<Card> observable, HBox playerBox) {
        observable.addListener((ListChangeListener<Card>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    CardGraph c = cardToGraph(change.getAddedSubList().get(0));
                    fadeTransition(c);

                    if (observable.size() > 1) {
                        c.setTranslateX(-(50 * (observable.size() - 1)));//this has to be simplified
                        fadeTransition(c);
                    }
                    playerBox.getChildren().add(c);

                } else if (change.wasRemoved()) {
                    playerBox.getChildren().clear();
                } else if (change.wasUpdated()) {
                    //Sets the faceUp state to true
                    ((CardGraph) playerBox.getChildren().get(change.getFrom())).setFaceUp(true);
                    //here applied changeFace method. gets the card from the box which was updated and swaps face
                    ((CardGraph) playerBox.getChildren().get(change.getFrom())).changeFace();
                }
            }
        });
    }

    private void setButtonListener() {
        blackJackLogic.disableButtons.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                hit.setDisable(newValue);
                stay.setDisable(newValue);
                highScoreSubmit.setDisable(newValue);
            }
        });
    }

    private void setHighScoreListener() {
        blackJackLogic.highScorePanel.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                highScoreList.setVisible(newValue);
                highScorePane.setVisible(newValue);
                highScorePane.toFront();
                highScoreList.toFront();
                highScore.toFront();
            }
        });
    }

    private void setHandValueListeners() {
        handValue.textProperty().bind(blackJackLogic.activePlayer.handValueSPProperty());
        dealerValue.textProperty().bind(blackJackLogic.dealer1.handValueSPProperty());
    }

    private void setBalanceValueListener() {
        blackJackLogic.activePlayer.balanceValueProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                balance.setText(newValue);
            }
        });
    }

    private CardGraph cardToGraph(Card card) {
        String rank = String.valueOf(card.getRank());
        String suit = String.valueOf(card.getSuit()).toLowerCase();
        switch (rank) {
            case "1" -> rank = "ace";
            case "11" -> rank = "jack";
            case "12" -> rank = "queen";
            case "13" -> rank = "king";
            default -> rank = rank;
        }
        return new CardGraph(suit, rank, card.isFaceUp());
    }

    private void rulesPanelSettings() {
        Label rules = new Label("Rules");
        rules.setStyle("-fx-font-size: 40px; -fx-background-radius: 10px; -fx-text-fill: white");
        rules.prefWidth(620);
        rules.setAlignment(Pos.CENTER);
        rulesPanel.setHeader(rules);
        rulesPanel.setContentText(Messages.RULES.print());
    }

    private void setBettingScreenListener() {
        blackJackLogic.bettingScreen.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                BettingScreen.setVisible(newValue);
                if (BettingScreen.isVisible()) {
                    betUpdater();
                }
            }
        });
    }

    private void betUpdater() {
        BettingScreen.toFront();
        tempBet = 0;
        BetAmount.setText(String.valueOf(tempBet));
        bet.setText("Bet: " + tempBet);
    }

    private void plus() {
        if (!(tempBet >= 1000) && !(tempBet >= Integer.parseInt(balance.getText().substring(balance.getText().indexOf(" ") + 1)))) { //1000 is max bet
            tempBet = tempBet + 100;
            BetAmount.setText("" + tempBet);
            BettingText.setText("Set your Bet!");
        } else {
            if (tempBet >= 1000) {
                BettingText.setText("Set your Bet!\nMax bet is 1000");
            } else {
                BettingText.setText("Set your Bet!\nBet to large for balance");
            }
        }
    }

    private void minus() {
        if (!(tempBet <= 100)) {
            tempBet = tempBet - 100;
            BetAmount.setText("" + tempBet);
            BettingText.setText("Set your Bet!");
        } else {
            BettingText.setText("Set your Bet! \n Min bet is 100");
        }
    }

    private void betted() {
        if (!(tempBet <= 0)) {
            BlackJackLogic.actionQueue.add(tempBet);
            bet.setText("Bet: " + tempBet);
            BetAmount.setText("" + tempBet);
            BettingScreen.setVisible(false);
        } else {
            BettingText.setText("Set your Bet!\nMin bet is 100 !!!!!!!!!!!!!!!!!!!");
        }
    }

    private void fadeTransition(CardGraph c) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.seconds(0.5));
        ft.setNode(c);
        ft.setFromValue(0);
        ft.setToValue(100);
        ft.play();
    }

    private void buttonHighScoreAction() {
        blackJackLogic.highScorePanel.setValue(!blackJackLogic.highScorePanel.getValue());
        highScore.setText(highScorePane.isVisible() ? "RESUME" : "HIGHSCORE");
    }

    private void buttonHighScoreSubmitAction() {
        if (textFieldHS.getText().isEmpty()) {
            highScoreNotice.setText("Must enter name to submit!");
            highScoreNotice.setStyle("-fx-text-fill: pink");
        } else if (textFieldHS.getText().length() > 30) {
            highScoreNotice.setText("A bit too long, ey? Keep it under 30 characters.");
            highScoreNotice.setStyle("-fx-text-fill: pink");
            textFieldHS.clear();
        } else {
            BlackJackLogic.actionQueue.add(9);
            BlackJackLogic.actionQueue.add(textFieldHS.getText());
            textFieldHS.clear();
        }
    }

    /**
     * animation for welcome panel adds cards, animates them and starts "click here" animation.
     */
    private void welcomeAnimation() {
        Group gr = new Group();
        gr.getChildren().add(new CardGraph("diamonds", "king", true));
        gr.getChildren().add(new CardGraph("spades", "ace", true));
        gr.getChildren().add(new CardGraph("hearts", "queen", true));
        gr.setTranslateX(welcome.getPrefWidth() / 2 - 50);
        gr.setTranslateY(welcome.getPrefHeight() / 2);
        welcome.getChildren().add(gr);

        infiniteFade = new FadeTransition();
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.seconds(0.5));
        ft.setNode(clickToPlay);
        ft.setFromValue(0);
        ft.setToValue(100);
        ft.setAutoReverse(true);
        ft.setCycleCount(Animation.INDEFINITE);
        ft.play();

        TranslateTransition ts = new TranslateTransition(Duration.seconds(2), gr);
        ts.setFromY(400);
        ts.setByY(gr.getTranslateY() - 500);
        ts.setAutoReverse(false);
        ts.play();
        rotateTransition(gr.getChildren().get(0), -30);
        rotateTransition(gr.getChildren().get(2), 30);
    }

    /**
     * animation to use with cards in welcomeAnimation
     *
     * @param node  the card to animate
     * @param angle the angle to rotate
     */
    private void rotateTransition(Node node, double angle) {
        node.getTransforms().add(new Rotate(0, 30, 0));
        RotateTransition rt = new RotateTransition();
        rt.setNode(node);
        rt.setAxis(new Point3D(0, 0, 10));
        rt.setToAngle(angle);
        rt.setDuration(Duration.seconds(2));
        rt.play();

    }

    /**
     * scale animation when welcome pane is clicked
     */
    private void welcomeClose() {
        ScaleTransition sc = new ScaleTransition();
        sc.setNode(welcome);
        sc.setToX(0);
        sc.setToY(0);
        sc.setDuration(Duration.seconds(1));
        sc.play();
        sc.setOnFinished(e -> {
            welcome.setVisible(false);
            infiniteFade.stop();
        });
    }

    private void setGameOverPanelListener() {
        blackJackLogic.gameOverPanel.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                gameOver.setVisible(newValue);
                highScoreList.setVisible(gameOver.isVisible());
                highScoreList.toFront();
            }
        });
    }

    private void buttonQuitAction() {
        System.exit(0);
    }

    private void buttonResumeAction() {
        BlackJackLogic.actionQueue.add(0);
        blackJackLogic.gameOverPanel.setValue(false);
        highScoreList.setVisible(gameOver.isVisible());
    }
}
