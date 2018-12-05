/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president.Cards.Packs;

import java.util.ArrayList;
import president.Cards.Card;
import president.InvalidCardException;

/**
 *
 * @author Kévin
 */
public class PlayedCards {
    
    private ArrayList<Card> cardList;
    
    public PlayedCards() {
        this.cardList = new ArrayList<>();
    }
    
    public void addCard(Card card) throws InvalidCardException {
        
        if (this.cardList.isEmpty()) {
            this.cardList.add(card);
        }
        else if (this.cardList.get(0).getValue().equals(card.getValue()) && !this.cardList.contains(card)) {
            this.cardList.add(card);
        }
        else {
            throw new InvalidCardException();
        }
        
    }
    
    public Card getCard(int index) {
        return this.cardList.get(index);
    }
    
    public Integer getCount() {
        return this.cardList.size();
    }
    
    public Integer getValue() {
        Integer res = 0;
        
        if (this.getCount() != 0) {
            res = this.cardList.get(0).getValue();
        }
        
        return res;
    }
    
    public String getName() {
        String res = "";
        
        if (this.getCount() != 0) {
            res = this.cardList.get(0).getName();
        }
        
        return res;
    }
    
    public String toString() {
        String res = "";
        
        for (Card card : this.cardList) {
            res += card+", ";
        }
        res = res.substring(0, res.length()-2);
        
        return res;
    }
    
}
