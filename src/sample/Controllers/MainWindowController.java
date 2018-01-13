package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.Models.Card;
import sample.Models.CardPack;

import java.util.ArrayList;
import java.util.List;


public class MainWindowController {

    private CardPack cardPack = new CardPack();
    List<Card> playerCards = new ArrayList<Card>();

    @FXML
    private HBox playerCardContainer;

    private Label createNewCard() {
        Label newLabel = new Label();
        newLabel.getStyleClass().add("card");
        newLabel.getStyleClass().add("card-player");

        return newLabel;
    }

    private void addCardToScene(String text) {
        Label newLabel = createNewCard();
        newLabel.setText(text);
        playerCardContainer.getChildren().add(newLabel);
    }

    public void pickCardButtonClicked() {

        Card newCard = cardPack.pickCard();

        if (newCard == null) {
            System.out.println("Pack is out of cards!");
            return;
        }

        playerCards.add(newCard);

        System.out.println("Player picked a" + " " + newCard.getValue() + " of " + newCard.getType() + "s" + "!");
        int value = 0;
        for (Card card : playerCards) {
            value += card.getNumericalValue();
        }

        System.out.println("Current value:" + " " + value);

        addCardToScene(newCard.getValue() + "\nof\n" + newCard.getType() + "s");
        System.out.println(playerCards.size() + "/" + cardPack.getNumberOfCards());
    }
}
