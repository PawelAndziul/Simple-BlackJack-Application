package main.java.sample.GameLogic;

import main.java.sample.Models.Card;
import main.java.sample.Models.CardPack;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private CardPack cardPack = new CardPack();
    List<Card> playerCards = new ArrayList<>();
    List<Card> dealerCards = new ArrayList<>();

    private String gameResult = "";
    private int playerPoints = -1;
    private int dealerPoints = -1;

    public String getGameResult() {
        return gameResult;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public int getDealerPoints() {
        return dealerPoints;
    }

    public String hit() {
        Card newCard = cardPack.pickCard();
        playerCards.add(newCard);

        if (checkIfPlayerLost()) {
            gameResult = "Player lost";
        }

        return newCard.getValue() + "\nof\n" + newCard.getType() + "s";
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

        // count points for each ace combination
        for (int i = 1; i <= aces; i++) {
            possiblePoints.add(value - i + i * 11);
        }

        return possiblePoints;
    }

    private boolean checkIfPlayerLost() {
        int playerPoints = countPoints(playerCards).get(0);
        if (playerPoints > 21) {
            return true;
        }

        return false;
    }

    private boolean checkIfDealerLost() {
        int dealerPoints = countPoints(dealerCards).get(0);
        if (dealerPoints > 21)
            return true;

        return false;
    }

    public void startNewGame() {
        gameResult = "";
        playerPoints = -1;
        dealerPoints = -1;
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();
    }

    public String dealerPickCard() {
        Card newCard = cardPack.pickCard();
        dealerCards.add(newCard);

        if (checkIfDealerLost() == true) {
            gameResult = "Dealer lost";
        }

        return newCard.getValue() + "\nof\n" + newCard.getType() + "s";
    }

    public List<String> dealerDrawCards() {
        List<String> cardList = new ArrayList<>();
        List<Integer> dealerPoints = countPoints(dealerCards);
        int maxDealerPoints = dealerPoints.get(dealerPoints.size() - 1);

        while (maxDealerPoints < 17) {
            Card newCard = cardPack.pickCard();
            dealerCards.add(newCard);
            //addCardToScene(newCard.getValue() + "\nof\n" + newCard.getType(), dealerCardContainer);
            cardList.add(newCard.getValue() + "\nof\n" + newCard.getType());

            maxDealerPoints = countPoints(dealerCards).get(dealerPoints.size() - 1);
        }

        return cardList;
    }

    public void calculateWinner() {
        int maxDealerPoints = -1;
        for (int pointValue : countPoints(dealerCards)) {
            if (pointValue > maxDealerPoints && pointValue <= 21 || maxDealerPoints == -1)
                maxDealerPoints = pointValue;
        }
        dealerPoints = maxDealerPoints;

        int maxPlayerPoints = -1;
        for (int pointValue : countPoints(playerCards)) {
            if (pointValue > maxPlayerPoints && pointValue <= 21  || maxPlayerPoints == -1)
                maxPlayerPoints = pointValue;
        }
        playerPoints = maxPlayerPoints;

        if (maxDealerPoints == maxPlayerPoints) {
            System.out.println("DRAW! " + maxDealerPoints + " = " + maxPlayerPoints);
            gameResult = "Draw";
        } else if (21 - maxDealerPoints < 21 - maxPlayerPoints && maxDealerPoints <= 21) {
            System.out.println("Dealer won! " + maxDealerPoints + " > " + maxPlayerPoints);
            gameResult = "Dealer won";
        } else {
            System.out.println("Player won! " + maxDealerPoints + " < " + maxPlayerPoints);
            gameResult = "Player won";
        }
    }
}