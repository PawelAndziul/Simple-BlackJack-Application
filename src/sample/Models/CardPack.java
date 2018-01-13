package sample.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardPack {
    List<Card> cardList = new ArrayList<Card>();

    public CardPack()
    {
        generatePack();
    }

    private void generatePack()
    {
        String[] symbolArray = {"Heart", "Spade", "Diamond", "Star"};
        String[] cardValues = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};

        for (String symbol : symbolArray)
        {
            for (int i=2; i<=14; i++)
            {
                cardList.add(new Card(symbol, cardValues[i-2], i));
            }
        }
        System.out.println(cardList.size());
    }

    public Card pickCard()
    {
        if (cardList.size() == 0)
            return null;

        int random = new Random().nextInt(cardList.size());
        Card pickedCard = cardList.get(random);
        cardList.remove(random);

        return pickedCard;
    }

    public int getNumberOfCards()
    {
        return cardList.size();
    }
}
