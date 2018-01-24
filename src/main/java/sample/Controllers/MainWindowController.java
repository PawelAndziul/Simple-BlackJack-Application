package main.java.sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.java.sample.Dao.DBStatsInserter;
import main.java.sample.Models.Card;
import main.java.sample.Models.CardPack;

import java.util.ArrayList;
import java.util.List;

public class MainWindowController {

    private CardPack cardPack = new CardPack();
    List<Card> playerCards = new ArrayList<>();
    List<Card> dealerCards = new ArrayList<>();

    @FXML
    private BorderPane resultMessagePane;

    @FXML
    private Label resultLabel;

    @FXML
    private HBox playerCardContainer;

    @FXML
    private HBox dealerCardContainer;

    @FXML
    private Button buttonHit;

    @FXML
    private Button buttonStand;

    @FXML
    private void pickCardButtonClicked() {

        List<Integer> playerPoints = countPoints(playerCards);
        int lowestPlayerPoints = playerPoints.get(0);
        if (lowestPlayerPoints > 19) {
            gameEnd("Dealer");
            return;
        }

        Card newCard = cardPack.pickCard();
        if (newCard == null) {
            System.out.println("Pack is out of cards!");
            return;
        }
        playerCards.add(newCard);
        addCardToScene(newCard.getValue() + "\nof\n" + newCard.getType() + "s", playerCardContainer);

        lowestPlayerPoints = countPoints(playerCards).get(0);
        if (lowestPlayerPoints > 21) {
            gameEnd("Dealer");
        }
    }

    @FXML
    private void standButtonClicked() {
        enableHitStandButtons(true);
        dealerDrawCards();
    }

    @FXML
    private void startNewGameButtonClicked() {
        enableHitStandButtons(false);

        cardPack = new CardPack();

        dealerCardContainer.getChildren().removeAll();
        dealerCardContainer.getChildren().clear();
        playerCardContainer.getChildren().removeAll();
        playerCardContainer.getChildren().clear();

        dealerCards = new ArrayList<>();
        playerCards = new ArrayList<>();

        dealerDrawCard();
    }

    @FXML
    private void resultMessagePaneClicked() {
        resultMessagePane.setVisible(false);
        resultMessagePane.setDisable(true);
    }

    private void checkWinner(int maxDealerPoints) {
        List<Integer> playerPointsList = countPoints(playerCards);
        int playerPoints = 0;
        for (int i = playerPointsList.size() - 1; i >= 0; i--) {
            playerPoints = playerPointsList.get(i);

            if (playerPoints <= 21)
                break;
        }

        String winner = "";
        if (maxDealerPoints == playerPoints) {
            System.out.println("DRAW! " + maxDealerPoints + " = " + playerPoints);
            winner = "draw";
        } else if (21 - maxDealerPoints < 21 - playerPoints && maxDealerPoints <= 21) {
            System.out.println("Dealer won! " + maxDealerPoints + " > " + playerPoints);
            winner = "dealer";
        } else {
            System.out.println("Player won! " + maxDealerPoints + " < " + playerPoints);
            winner = "player";
        }

        saveResultsIntoDatabase(playerPoints, maxDealerPoints , winner);
        gameEnd(winner);
    }

    private void gameEnd(String winner) {
        resultMessagePane.setVisible(true);
        resultMessagePane.setDisable(false);

        if (winner == "DRAW")
            resultLabel.setText("DRAW!");
        else
            resultLabel.setText(winner + " won!");
    }
    
    private void dealerDrawCards() {
        List<Integer> dealerPoints = countPoints(dealerCards);
        int maxDealerPoints = dealerPoints.get(dealerPoints.size() - 1);

        while (maxDealerPoints < 17) {
            Card newCard = cardPack.pickCard();
            dealerCards.add(newCard);
            addCardToScene(newCard.getValue() + "\nof\n" + newCard.getType(), dealerCardContainer);

            maxDealerPoints = countPoints(dealerCards).get(dealerPoints.size() - 1);
        }

        checkWinner(maxDealerPoints);
    }

    private Label createNewCard() {
        Label newLabel = new Label();
        newLabel.getStyleClass().add("card");
        newLabel.getStyleClass().add("card-player");

        return newLabel;
    }

    private void dealerDrawCard() {
        Card newCard = cardPack.pickCard();
        if (newCard == null) {
            System.out.println("Pack is out of cards!");
            return;
        }
        dealerCards.add(newCard);
        addCardToScene(newCard.getValue() + "\nof\n" + newCard.getType() + "s", dealerCardContainer);
    }

    private void addCardToScene(String text, HBox container) {
        Label newLabel = createNewCard();
        newLabel.setText(text);
        container.getChildren().add(newLabel);
    }

    private List<Integer> countPoints(List<Card> cardList) {

        int value = 0;
        int aces = 0;
        for (Card card : cardList) {
            value += card.getNumericalValue();
            if (card.getValue() == "Ace") {
                aces++;
            }
        }

        List<Integer> possiblePoints = new ArrayList<>();
        possiblePoints.add(value);

        for (int i = 1; i <= aces; i++) {
            possiblePoints.add(value - i + i * 11);
        }

        return possiblePoints;
    }

    private void enableHitStandButtons(Boolean lock) {
        buttonHit.setDisable(lock);
        buttonStand.setDisable(lock);
    }

    private void saveResultsIntoDatabase(int playerPoints, int dealerPoints, String winner)
    {
        DBStatsInserter dbStatsInserter = new DBStatsInserter(0, playerPoints, dealerPoints, winner);
        dbStatsInserter.addToDatabase();
    }
}
