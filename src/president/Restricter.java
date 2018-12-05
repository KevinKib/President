/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president;

import president.Cards.Packs.CardPile;
import president.Cards.Packs.PlayedCards;

/**
 * Object that's given to the AIs in order to restrict the informations that the
 * AIs can get from the current game going on.
 * For example, AIs are not allowed to see the other player's hand. If we gave
 * them the game, they might be allowed to, which would permit them to cheat.
 * @author Kévin
 */
public class Restricter {
    
    private Game game;
    private CardPile cardPile;
    
    public Restricter(Game game, CardPile cardPile) {
        this.game = game;
        this.cardPile = cardPile;
    }
    
    public boolean isShutUp() {
        return this.game.isShutUp();
    }
    
    public PlayedCards getPileLastCards() {
        return this.cardPile.getLastCards();
    }
    
    public Integer getPileStreakValue() {
        return this.cardPile.getStreakValue();
    }
    
    public Integer getPileValue() {
        return this.cardPile.getValue();
    }
    
    public Integer getPileCount() {
        return this.cardPile.getCount();
    }
    
    public boolean isPileEmpty() {
        return this.cardPile.isEmpty();
    }
    
    public Integer getBiggestCardValue() {
        return 15;
    }
    
    public Integer getLowestCardValue() {
        return 3;
    }
    
}
