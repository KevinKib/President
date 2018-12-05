/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president.Cards.Packs;

import java.util.ArrayList;
import java.util.Collections;
import president.Cards.Card;
import president.Cards.CardType;
import president.Cards.Suit;

/**
 *
 * @author Kévin
 */
public class CardSet {
    
    private ArrayList<Card> cardList;
    
    public CardSet() {
        this.cardList = new ArrayList<>();
        
        /* Init all 52 cards
           (beware: adding a new card type will mess the number of cards)
        */
        for (CardType cardType : CardType.values()) {
            for (Suit suit : Suit.values()) {
                Card newCard = CardFactory.get().createCard(cardType, suit);
                this.cardList.add(newCard);
            }
        }
        
        Collections.shuffle(this.cardList);
        
    }

    public ArrayList<Card> getCards() {
        return cardList;
    }
    
    
    
    
}
