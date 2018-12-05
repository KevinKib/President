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
import president.InvalidCardException;
import president.Player;
import president.Restricter;

/**
 *
 * @author Kévin
 */
public class AI_Basic extends Player {

    private Integer pileValue;
    private Integer pileCount;
    
    // Cards that will be returned at the end.
    private PlayedCards playedCards;
    
    // Boolean that says if playable cards were found.
    private boolean foundCards;
    
    // Cards that will be played and added to the playedCards object.
    private ArrayList<Card> playedCardsList;
    
    
    public AI_Basic(String name, Restricter restricter) {
        super(name, restricter);
        this.pileValue = this.getRestricter().getPileValue();
        this.pileCount = this.getRestricter().getPileCount();
        playedCards = new PlayedCards();
        foundCards = false;
        playedCardsList = new ArrayList<>();
    }
    
    @Override
    public PlayedCards chooseCardsToPlay() {
        /*
        Tries to always play cards when it's capable of doing so.
        It plays the lowest card that's above the threshold.
        */
        
        this.pileValue = this.getRestricter().getPileValue();
        this.pileCount = this.getRestricter().getPileCount();
        playedCards = new PlayedCards();
        foundCards = false;
        playedCardsList = new ArrayList<>();
        
        
        // Card index that will be checked. Starts at the pile value
        // (no point in looking at the cards below).
        int currentCardIndex = pileValue;
        
        // If there's no card in the pile, , we set the first card index to the
        // lowest possible card value.
        if (currentCardIndex == 0) {
            currentCardIndex = this.getRestricter().getLowestCardValue();
        }
        
        // Amount of cards that were found with the current card index.
        int nbCorrectCards = 0;

        
        while (currentCardIndex <= this.getRestricter().getBiggestCardValue() && !foundCards) {
           
            nbCorrectCards = 0;
            
            for (Card card : this.getCardList()) {
                if (card.getValue() == currentCardIndex) {
                    playedCardsList.add(card);
                    nbCorrectCards++;
                }
            }
            
            // Rules out the case where 0 cards were found, in which case
            // the play will always be invalid.
            if (nbCorrectCards >= pileCount && nbCorrectCards > 0) {
                foundCards = true;
            }
            else {
                playedCardsList.clear();
                
                // The current card index goes up, so we evaluate the next one.
                currentCardIndex++;
            }

        }
        
        if (foundCards) {
            int nbAddedCards = 0;
            try {
                if (pileCount == 0) {
                    // No cards on the pile : play all the cards that were found.
                    while (nbAddedCards < nbCorrectCards) {
                        playedCards.addCard(playedCardsList.get(nbAddedCards));
                        nbAddedCards++;
                    }
                }
                else {
                    while (nbAddedCards < pileCount) {
                        playedCards.addCard(playedCardsList.get(nbAddedCards));
                        nbAddedCards++;
                    }
                }
            } catch (InvalidCardException e) {
                System.err.println(e);
            }
        }
        else {
            playedCards = null;
        }
        
        return playedCards;
    }

    @Override
    public ArrayList<Card> chooseCardsToSendToPlayer(Integer amount) {
        // Sends the worst cards, not caring which constitute a pair.
        
        ArrayList<Card> sentCards = new ArrayList<>();
        ArrayList<Card> tempCardList = this.getCardList();
        int i = 0;
        
        while (i < amount) {
            Card worstCard = null;
            
            for (Card card : tempCardList) {
                if (worstCard == null || card.getValue() > worstCard.getValue()) {
                    worstCard = card;
                }
            }
            
            sentCards.add(worstCard);
            tempCardList.remove(worstCard);
            i++;
            
        }  
        
        return sentCards;
    }
    
    
}
