/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president.AI;

import java.util.ArrayList;
import java.util.Random;
import president.Cards.Card;
import president.Cards.Packs.PlayedCards;
import president.Game;
import president.InvalidCardException;
import president.Player;
import president.Restricter;

/**
 *
 * @author Kévin
 */
public class AI_Random extends Player {

    public AI_Random(String name, Restricter restricter) {
        super(name, restricter);
    }
    
    @Override
    public PlayedCards chooseCardsToPlay() {
        PlayedCards playedCards = new PlayedCards();

        Random r = new Random();
        int low = 0;
        int high = this.getNbCards();
  
        Integer index = r.nextInt(high-low)+low;
        
        try {
            playedCards.addCard(this.getCardList().get(index));
        } catch (InvalidCardException e) {
            System.err.println(e);
        }
        
        for (Card card : this.getCardList()) {
            if (card.getValue().equals(playedCards.getValue()) && card.getSuit() != playedCards.getCard(0).getSuit()) {
                try {
                    playedCards.addCard(card);
                } catch (InvalidCardException e) {
                    System.err.println(e);
                }
            }
        }
        
        return playedCards;
    }

    @Override
    public ArrayList<Card> chooseCardsToSendToPlayer(Integer amount) {
        ArrayList<Card> sentCards = new ArrayList<>();
        int i = 0;
        
        while (i < amount) {
            
            Random r = new Random();
            int low = 0;
            int high = this.cardCount();
            Card card = this.getCardList().get(r.nextInt(high-low) + low);
            
            if (!sentCards.contains(card)) {
                sentCards.add(card);
                i++;
            }
        }  
        return sentCards;
    }
    
    
}
