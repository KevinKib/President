/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president.Cards.Packs;

import java.util.ArrayList;
import president.InvalidCardException;

/**
 * Pile of cards that's being played upon during the game.
 * @author Kévin
 */
public class CardPile {
    
    public ArrayList<PlayedCards> cardList;
    
    public CardPile() {
        this.cardList = new ArrayList<>();
    }
    
    public void addCard(PlayedCards cards) throws InvalidCardException {
        
        if (this.isEmpty()) {
            this.cardList.add(cards);
        }
        else if (this.getLastCards().getValue() <= cards.getValue() && this.getLastCards().getCount().equals(cards.getCount()) ) {
            this.cardList.add(cards);
        }
        else {
            throw new InvalidCardException();
        }
    }
    
    public void clear() {
        this.cardList.clear();
    }
    
    public Integer getStreakValue() {
        Integer res = 0;
        Integer currentIndex = this.cardList.size()-1;
        
        if (!this.cardList.isEmpty()) {
            boolean streak = true;
            
            Integer streakValue = this.cardList.get(currentIndex).getValue();
        
            do {
                
                if (streakValue.equals(this.cardList.get(currentIndex).getValue())) {
                    res += this.cardList.get(currentIndex).getCount();
                    currentIndex--;
                }
                else {
                    streak = false;
                }
                
            } while(streak && currentIndex >=  0);
            
        }
        
        return res;
        
    }
    
    public PlayedCards getLastCards() {
        PlayedCards lastCards = null;
        
        if (!this.cardList.isEmpty()) {
            lastCards = this.cardList.get(this.cardList.size()-1);
        }
        
        return lastCards;
    }
    
    public Integer getValue() {
        Integer res = 0;
        
        if (!this.cardList.isEmpty()) {
            res = this.getLastCards().getValue();
        }
        
        return res;
        
    }

    public Integer getCount() {
        Integer res = 0;
        
        if (!this.cardList.isEmpty()) {
            res = this.getLastCards().getCount();
        }
        
        return res;
    }
    
    public boolean isEmpty() {
        return this.cardList.isEmpty();
    }

    
}
