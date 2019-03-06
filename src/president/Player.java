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
    private PlayedCards lastPlayedCards;
    
    
    public Player(String name, Restricter restricter) {
        this.restricter = restricter;
        this.name = name;
        this.cardList = new ArrayList<>();
        this.title = Title.Neutral;
        this.turnPassed = false;
        this.gameRank = null;
        this.points = 0;
        this.score = new Score(this);
        this.lastPlayedCards = null;
    }
    
    /**
     * Adds a card to the player's deck.
     * @param card Card to add.
     */
    public void addCard(Card card) {
        this.cardList.add(card);
    }
    
    /**
     * Removes a card from the player deck.
     * @param card Card to be removed.
     */
    public void removeCard(Card card) {
        this.cardList.remove(card);
    }
    
    /**
     * Remove more than one card from the player.
     * @param cards Cards to remove.
     */
    public void removeCards(PlayedCards cards) {
        for (int i = 0; i < cards.getCount(); i++) {
            this.cardList.remove(cards.getCard(i));
        }
    }
    
    /**
     * Clears the player's card list.
     */
    public void emptyCardList() {
        this.cardList.clear();
    }
    
    /**
     * Checks if the player has cards.
     * @return True if the player has one card or more.
     */
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
    
    /**
     * Sends the x best cards to another player.
     * @param player Player who will receive the cards.
     * @param amount Amount of cards that will be sent.
     */
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
    
    /**
     * Sends a card to a player.
     * @param player Receiver of the card.
     * @param card Card that will be sent.
     */
    public void sendCard(Player player, Card card) {
        this.removeCard(card);
        player.addCard(card);
    }
    
    /**
     * Sends a certain amount of cards to a player.
     * @param player Receiver of the cards.
     * @param cards Cards that will be sent.
     */
    private void sendCards(Player player, ArrayList<Card> cards) {
        for (Card card : cards) {
            this.sendCard(player, card);
        }
    }
    
    /**
     * Sends some chosen cards to a player.
     * @param player Receiver of the cards.
     * @param expectedNbCards Expected number of cards that need to be sent.
     */
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
    
    /**
     * Sends random cards to a player.
     * @param player Receiver of the cards.
     * @param expectedNbCards Expected number of cards that will be sent.
     */
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

    /**
     * Updates the player score.
     * @param playerList List of player that took part in the game.
     */
    public void updatePoints(ArrayList<Player> playerList) {
        this.score.updatePoints(playerList);
    }
    
    /**
     * Getter on the card list.
     * @return Card list. 
     */
    protected ArrayList<Card> getCardList() {
        return cardList;
    }
    
    /**
     * Returns true if when the player finished playing, his last card wasn't a 2.
     * @return True if the player ended the game correctly.
     */
    public boolean isFinishValid() {
        return !this.hasCards() && !(this.lastPlayedCards.getCount() == 2);
    }
    
    public void updateLastPlayedCards(PlayedCards playedCards) {
        this.lastPlayedCards = playedCards;
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
