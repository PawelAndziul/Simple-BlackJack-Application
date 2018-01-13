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

        int currentPoints = countPoints(dealerCards);
        System.out.println("Dealer picked a" + " " + newCard.getValue() + " of " + newCard.getType() + "s worth " + newCard.getNumericalValue() + ", with total of " + currentPoints);
    }

    private void addCardToScene(String text, HBox container) {
        Label newLabel = createNewCard();
        newLabel.setText(text);
        container.getChildren().add(newLabel);
    }

    private int countPoints(List<Card> cardList) {
        int value = 0;
        for (Card card : cardList) {
            value += card.getNumericalValue();
        }

        return value;
    }

    @FXML
    private void pickCardButtonClicked() {

        int currentPoints = countPoints(playerCards);
        if (currentPoints > 21) {
            System.out.println("Player lost!");
            return;
        }
        System.out.println("Current value:" + " " + currentPoints);

        Card newCard = cardPack.pickCard();
        if (newCard == null) {
            System.out.println("Pack is out of cards!");
            return;
        }
        playerCards.add(newCard);
        addCardToScene(newCard.getValue() + "\nof\n" + newCard.getType() + "s", playerCardContainer);
        currentPoints = countPoints(playerCards);
        System.out.println("Player picked a" + " " + newCard.getValue() + " of " + newCard.getType() + "s worth " + newCard.getNumericalValue() + ", with total of " + currentPoints);

        if (currentPoints > 21) {
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
        int dealerPoints;
        while ((dealerPoints = countPoints(dealerCards)) < 17) {
            Card newCard = cardPack.pickCard();
            dealerCards.add(newCard);
            addCardToScene(newCard.getValue() + "\nof\n" + newCard.getType(), dealerCardContainer);
        }

        int playerPoints = countPoints(playerCards);

        if (dealerPoints > playerPoints)
            System.out.println("Dealer won! " + dealerPoints + " > " + playerPoints);
        else if (dealerPoints == playerPoints)
            System.out.println("DRAW!");
        else
            System.out.println("Player won! "  + playerPoints + " > " + dealerPoints);

    }

}
