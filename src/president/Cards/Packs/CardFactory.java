/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president.Cards.Packs;

import president.Cards.Card;
import president.Cards.CardType;
import president.Cards.Card_10;
import president.Cards.Card_2;
import president.Cards.Card_3;
import president.Cards.Card_4;
import president.Cards.Card_5;
import president.Cards.Card_6;
import president.Cards.Card_7;
import president.Cards.Card_8;
import president.Cards.Card_9;
import president.Cards.Card_Ace;
import president.Cards.Card_Jack;
import president.Cards.Card_King;
import president.Cards.Card_Queen;
import president.Cards.Suit;

/**
 * Factory design pattern
 * @author Kévin
 */
public class CardFactory {
    
    private static CardFactory instance = null;
    
    private CardFactory() {
        
    }
    
    public Card createCard(String name, Suit suit) {
        Card card = null;
        
        switch(name) {
            case "2": card = new Card_2(suit); break;
            case "3": card = new Card_3(suit); break;
            case "4": card = new Card_4(suit); break;
            case "5": card = new Card_5(suit); break;
            case "6": card = new Card_6(suit); break;
            case "7": card = new Card_7(suit); break;
            case "8": card = new Card_8(suit); break;
            case "9": card = new Card_9(suit); break;
            case "10": card = new Card_10(suit); break;
            case "Jack": card = new Card_Jack(suit); break;
            case "Queen": card = new Card_Queen(suit); break;
            case "King": card = new Card_King(suit); break;
            case "Ace": card = new Card_Ace(suit); break;
        }
        
        return card;
    }
    
    public Card createCard(CardType cardType, Suit suit) {
        Card card = null;
        
        switch(cardType) {
            case Card_2: card = new Card_2(suit); break;
            case Card_3: card = new Card_3(suit); break;
            case Card_4: card = new Card_4(suit); break;
            case Card_5: card = new Card_5(suit); break;
            case Card_6: card = new Card_6(suit); break;
            case Card_7: card = new Card_7(suit); break;
            case Card_8: card = new Card_8(suit); break;
            case Card_9: card = new Card_9(suit); break;
            case Card_10: card = new Card_10(suit); break;
            case Card_Jack: card = new Card_Jack(suit); break;
            case Card_King: card = new Card_Queen(suit); break;
            case Card_Queen: card = new Card_King(suit); break;
            case Card_Ace: card = new Card_Ace(suit); break;
        }
        
        return card;
    }
    
    
    public static CardFactory get() {
        if (instance == null) {
            instance = new CardFactory();
        }
        return instance;
    }
    
    
}
