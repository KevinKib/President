/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president;

import president.Cards.Packs.PlayedCards;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;
import president.Cards.Card;

/**
 *
 * @author Kévin
 */
public abstract class Player {
    
    private Restricter restricter;
    private String name;
    private ArrayList<Card> cardList;
    private Title title;
    private boolean turnPassed;
    private Integer gameRank;
    private Integer points;
    private Score score;
    
    
    public Player(String name, Restricter restricter) {
        this.restricter = restricter;
        this.name = name;
        this.cardList = new ArrayList<>();
        this.title = Title.Neutral;
        this.turnPassed = false;
        this.gameRank = null;
        this.points = 0;
        this.score = new Score(this);
    }
    
    
    public void addCard(Card card) {
        this.cardList.add(card);
    }
    
    
    public void removeCard(Card card) {
        this.cardList.remove(card);
    }
    
    
    public void removeCards(PlayedCards cards) {
        for (int i = 0; i < cards.getCount(); i++) {
            this.cardList.remove(cards.getCard(i));
        }
    }
    
    
    public void emptyCardList() {
        this.cardList.clear();
    }
    
    
    public boolean hasCards() {
        return !this.cardList.isEmpty();
    }
    
    
    /**
     * Returns the amount of cards the player possesses.
     * @return An amount of cards.
     */
    public Integer cardCount() {
        return this.cardList.size();
    }
    
    
    public void sendBestCardsToPlayer(Player player, Integer amount) {
        
        for (int i = 0; i < amount; i++) {
            Card bestCard = null;
        
            for (Card card : this.cardList) {

                if (bestCard == null) {
                    bestCard = card;
                }
                else if (bestCard.getValue() < card.getValue()) {
                    bestCard = card;
                }
            }

            this.sendCard(player, bestCard);
        }
        
    }
    
    
    public void sendCard(Player player, Card card) {
        this.removeCard(card);
        player.addCard(card);
    }
    
    
    private void sendCards(Player player, ArrayList<Card> cards) {
        for (Card card : cards) {
            this.sendCard(player, card);
        }
    }
    
    
    public void sendChosenCardsToPlayer(Player player, Integer expectedNbCards) {
        
        ArrayList<Card> chosenCards = this.chooseCardsToSendToPlayer(expectedNbCards);
        if (chosenCards != null) {
            chosenCards.removeIf(Predicate.isEqual(null));
            if (expectedNbCards == chosenCards.size()) {
                this.sendCards(player, chosenCards);
            }
            else {
                this.sendRandomCards(player, expectedNbCards); 
            }
        }
        else {
            this.sendRandomCards(player, expectedNbCards); 
        }
        
        
    }
    
    
    private void sendRandomCards(Player player, Integer expectedNbCards) {
        System.err.println("Invalid cards. Random cards will be sent.");
        int addedCards = 0;
        ArrayList<Card> randomCards = new ArrayList<>();

        while(addedCards < expectedNbCards) {

            Random r = new Random();
            int low = 0;
            int high = cardList.size();
            Card card = this.cardList.get(r.nextInt(high-low) + low);

            if (!randomCards.contains(card)) {
                randomCards.add(card);
                addedCards++;
            }

        }
        
        this.sendCards(player, randomCards);
        
    }

    
    public void updatePoints(ArrayList<Player> playerList) {
        this.score.updatePoints(playerList);
    }
    
    
    protected ArrayList<Card> getCardList() {
        return cardList;
    }
    
    
    @Override
    public String toString() {
        return name;
    }
    
    
    public abstract PlayedCards chooseCardsToPlay();
    
    
    public abstract ArrayList<Card> chooseCardsToSendToPlayer(Integer amount);

    // ---------------
    
    public String getName() {
        return name;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public boolean isTurnPassed() {
        return turnPassed;
    }

    public void setTurnPassed(boolean turnPassed) {
        this.turnPassed = turnPassed;
    }

    public Integer getGameRank() {
        return gameRank;
    }

    public void setGameRank(Integer gameRank) {
        this.gameRank = gameRank;
    }
    
    protected Integer getNbCards() {
        return this.cardList.size();
    }

    public Restricter getRestricter() {
        return restricter;
    }

    public Score getScore() {
        return score;
    }
    
}
