package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.Models.Card;
import sample.Models.CardPack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MainWindowController {

    private CardPack cardPack = new CardPack();
    List<Card> playerCards = new ArrayList<>();
    List<Card> dealerCards = new ArrayList<>();

    @FXML
    private HBox playerCardContainer;

    @FXML
    private HBox dealerCardContainer;

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

    @FXML
    private void pickCardButtonClicked() {

        List<Integer> playerPoints = countPoints(playerCards);
        int lowestPlayerPoints = playerPoints.get(0);
        if (lowestPlayerPoints > 19) {
            System.out.println("Player lost!");
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
            System.out.println("BUSTED!");
        }
    }

    @FXML
    private void standButtonClicked() {
        dealerDrawCards();
    }

    @FXML
    private void startNewGameButtonClicked() {
        cardPack = new CardPack();

        dealerCardContainer.getChildren().removeAll();
        dealerCardContainer.getChildren().clear();
        playerCardContainer.getChildren().removeAll();
        playerCardContainer.getChildren().clear();

        dealerCards = new ArrayList<>();
        playerCards = new ArrayList<>();

        dealerDrawCard();
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

        List<Integer> playerPointsList = countPoints(playerCards);
        int playerPoints = 0;
        for (int i = playerPointsList.size() - 1; i >= 0; i--) {
            playerPoints = playerPointsList.get(i);

            if (playerPoints <= 21)
                break;
        }

        System.out.println("Dealer points: " + dealerPoints);
        System.out.println("Player points: " + playerPointsList);

        if (maxDealerPoints == playerPoints)
            System.out.println("DRAW! " + maxDealerPoints + " = " + playerPoints);
        else if (21 - maxDealerPoints < 21 - playerPoints && maxDealerPoints <= 21)
            System.out.println("Dealer won! " + maxDealerPoints + " > " + playerPoints);
        else
            System.out.println("Player won! " + maxDealerPoints + " < " + playerPoints);


    }

}
