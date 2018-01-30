package main.java.sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.java.sample.Dao.DBStatsInserter;
import main.java.sample.GameLogic.Game;

import java.util.List;

public class MainWindowController {

    private Game game = new Game();

    // result overlay
    @FXML
    private BorderPane resultMessagePane;

    @FXML
    private Label resultLabel;

    // main window
    @FXML
    private HBox playerCardContainer;

    @FXML
    private HBox dealerCardContainer;

    @FXML
    private Button buttonHit;

    @FXML
    private Button buttonStand;

    @FXML
    private void hitButtonClicked() {
        if (game.getGameResult().equals("Player lost")) {
            gameEnd("Player lost");
            return;
        }

        String cardText = game.hit();
        addCardToScene(cardText, playerCardContainer);

        if (game.getGameResult().equals("Player lost")) {
            gameEnd("Player lost");
        }
    }

    @FXML
    private void standButtonClicked() {
        disableHitStandButtons(true);

        List<String> cardList = game.dealerDrawCards();
        for (String cardText : cardList) {
            addCardToScene(cardText, dealerCardContainer);
        }

        checkWinner();
    }

    @FXML
    private void startNewGameButtonClicked() {
        game.startNewGame();

        disableHitStandButtons(false);
        removeCardsFromScreen();

        dealerPickCardFromDeck();
    }

    @FXML
    private void resultMessagePaneClicked() {
        resultMessagePane.setVisible(false);
        resultMessagePane.setDisable(true);
    }

    private void showResultMessage() {
        disableHitStandButtons(true);
        resultMessagePane.setVisible(true);
        resultMessagePane.setDisable(false);
    }

    private void removeCardsFromScreen() {
        dealerCardContainer.getChildren().removeAll();
        dealerCardContainer.getChildren().clear();

        playerCardContainer.getChildren().removeAll();
        playerCardContainer.getChildren().clear();
    }

    private void checkWinner() {

        game.calculateWinner();
        String gameResult = game.getGameResult();
        gameEnd(gameResult);
    }

    private void gameEnd(String winner) {
        if (winner.equals("draw"))
            resultLabel.setText("DRAW!");
        else
            resultLabel.setText(winner + "!");

        showResultMessage();
        saveResultsIntoDatabase(game.getPlayerPoints(),
                game.getDealerPoints(),
                winner);
    }

    private Label createCardLabel() {
        Label newLabel = new Label();
        newLabel.getStyleClass().add("card");
        newLabel.getStyleClass().add("card-player");

        return newLabel;
    }

    private void dealerPickCardFromDeck() {
        String cardText = game.dealerPickCard();
        addCardToScene(cardText, dealerCardContainer);

        if (game.getGameResult().equals("Dealer lost")) {
            gameEnd("Player");
        }
    }

    private void addCardToScene(String text, HBox container) {
        Label newLabel = createCardLabel();
        newLabel.setText(text);
        container.getChildren().add(newLabel);
    }

    private void disableHitStandButtons(Boolean lock) {
        buttonHit.setDisable(lock);
        buttonStand.setDisable(lock);
    }

    private void saveResultsIntoDatabase(int playerPoints, int dealerPoints, String winner) {
        DBStatsInserter dbStatsInserter = new DBStatsInserter(0, playerPoints, dealerPoints, winner);
        dbStatsInserter.addToDatabase();
    }
}
