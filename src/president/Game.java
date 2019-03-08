/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president;

import president.Cards.Packs.PlayedCards;
import president.Cards.Packs.CardSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import president.Cards.Card;
import president.Cards.Packs.CardPile;

/**
 *
 * @author Kévin
 */
public class Game {
    
    /**
     * Number of players in the current game.
     */
    private Integer nbPlayers;
    /**
     * List of the different players that take part in the game.
     */
    private ArrayList<Player> playerList;
    /**
     * Set of cards where players pick their cards from.
     */
    private CardSet cardSet;
    /**
     * Pile of cards that's being played upon during the game.
     */
    private CardPile cardPile;
    /**
     * Current player of the turn.
     */
    private Player currentPlayer;
    /**
     * Index of the current player.
     */
    private Integer currentIndex;
    /**
     * Whether the game is in a "shut up" state or not.
     */
    private boolean shutUp;
    /**
     * Whether the current player is allowed to play again or not.
     */
    private boolean replay;
    /**
     * Number of player that finished the game.
     */
    private Integer nbFinishedPlayers;
    /**
     * Number of players that had a wrong finish (i.e that finished with a 2).
     */
    private Integer nbMistakenPlayers;
    /**
     * Restricter used for AIs.
     */
    private Restricter restricter;
    
    public Game(ArrayList<Player> playerList) {
        this.playerList = playerList;
        this.nbPlayers = playerList.size();
        this.cardSet = new CardSet();
        this.cardPile = new CardPile();
        this.currentPlayer = null;
        this.currentIndex = 0;
        this.shutUp = false;
        this.replay = false;
        this.nbFinishedPlayers = 0;
        this.nbMistakenPlayers = 0;
        this.restricter = new Restricter(this, cardPile);
    }
    
    
    // --------------
    
    
    /**
     * Simulates a game of President.
     * 
     * Déroulement d'une partie :
        
        1. Tout le monde démarre neutre
        2. Les 52 cartes du paquet sont divisées en parts égales selon
           tous les joueurs
           (les cartes extra sont données au hasard)
        3. S'il y a des rôles (président/trouduc) ils échangent leurs cartes
        4. Le jeu commence - le trouduc commence a jouer (ou au hasard)
        5. Le joueur place soit :
           - 1 carte simple
           - 2 cartes de même valeur (double)
           - 3 cartes de même valeur (triple)
           - 4 cartes de même valeur (cause une révolution, + tard)
        6. Le prochain joueur doit placer une carte de valeur supérieure à celle
           déjà placée.
           - S'il place un 2 : le tas est remis à zéro
           - S'il place la même carte : le suivant doit replacer la même carte,
             sinon il ne peut jouer
           - Si la même carte est placée 4 fois : le tas est remis à zéro
           - S'il place une carte et que personne ne peut jouer : le tas est
             remis à zéro
        7. Un joueur n'ayant plus de cartes dans son jeu gagne.
           - Cependant : s'il finit par un 2, il perd.
        
        
        CHOSES A IMPLEMENTER :
        X Cartes extra données au hasard
        X Le trouduc commence a jouer
        X Si on finit par un 2, on perd
        - Révolution
     */
    public void play() {
        
        this.setupGame();
        this.distributeCards();
        this.exchangeCards();
        while (this.isPlaying()) {
            this.turn();
        }
        this.defineNewTitles();
        this.displayResults();
        this.addPoints();
        
    }
    
    /**
     * Setups the different paramaters and variables of the game.
     */
    private void setupGame() {
        this.nbPlayers = playerList.size();
        this.nbMistakenPlayers = 0;
        this.cardPile.clear();
        
        // The lowest dirt will be the first player to play, otherwise it's random.
        Player lowestDirt = this.findPlayerWithTitle(Title.LowestDirt);
        if (lowestDirt == null) {
            Random r = new Random();
            int low = 0;
            int high = nbPlayers;
            this.currentIndex = r.nextInt(high-low)+low;
        }
        else {
            this.currentIndex = this.getIndex(lowestDirt);
        }
        this.shutUp = false;
        this.replay = false;
        this.nbFinishedPlayers = 0;
        
        for (Player player : playerList) {
            player.setGameRank(null);
            player.setTurnPassed(false);
        }
    }
    
    /**
     * Distributes the cards.
     */
    private void distributeCards() {
        
        // Reset card lists
        for (Player player : this.playerList) {
            player.emptyCardList();
        }
        
        Integer receivingPlayerIndex = 0;
        Player receivingPlayer;
        
        Integer nbCards = this.cardSet.getCards().size();
        Integer distributedCards = 0;

        // Distribute all cards
        // To change here : randomize the last cards
        for (Card card : this.cardSet.getCards()) {
            
            // Change player
            receivingPlayer = this.playerList.get(receivingPlayerIndex);
            receivingPlayerIndex++;
            if (receivingPlayerIndex == this.playerList.size()) {
                receivingPlayerIndex = 0;
            }
            
            // Give card to player
            receivingPlayer.addCard(card);
            
            distributedCards++;
            
        }
        
    }
    
    /**
     * Exchanges the cards between president roles and dirt roles.
     */
    private void exchangeCards() {
        Player president = null;
        Player vicePresident = null;
        Player dirt = null;
        Player lowestDirt = null;
        
        // Reference titles
        for (Player player : this.playerList) {
            switch (player.getTitle()) {
                case President:
                    president = player;
                    break;
                case VicePresident:
                    vicePresident = player;
                    break;
                case Dirt:
                    dirt = player;
                    break;
                case LowestDirt:
                    lowestDirt = player;
                    break;
            }
        }
        
        boolean presidentExchange = (president != null) && (lowestDirt != null);
        boolean viceExchange = (vicePresident != null) && (dirt != null);
    
        if (presidentExchange) {
            // President and lowest dirt exchange 2 cards
            effectuateCardExchange(president, lowestDirt, 2);
        }
        if (viceExchange) {
            // Vice president and dirt exchange 1 card
            effectuateCardExchange(vicePresident, dirt, 1);
        }
        
    }
    
    /**
     * Effectuates the final card exchange.
     * @param stronger The receiver of the cards.
     * @param weaker The sender of the cards.
     * @param nbCards The amount of cards that will be sent.
     */
    private void effectuateCardExchange(Player stronger, Player weaker, Integer nbCards) {
        weaker.sendBestCardsToPlayer(stronger, nbCards);
        stronger.sendChosenCardsToPlayer(weaker, nbCards);
    }
    
    /**
     * Defines the new titles of the differents player (president / dirt).
     */
    private void defineNewTitles() {
        
        for (Player player : this.playerList) {
            
            if (player.getGameRank() == null) {
                player.setGameRank(nbPlayers-nbMistakenPlayers);
            }
            
            
            if (player.getGameRank().equals(nbPlayers)) {
                player.setTitle(Title.LowestDirt);
            }
            else if (player.getGameRank() == 1) {
                player.setTitle(Title.President);
            }
            else if (player.getGameRank() == 2 && nbPlayers >= 4) {
                player.setTitle(Title.VicePresident);
            }
            else if (player.getGameRank() == nbPlayers-1 && nbPlayers >= 4) {
                player.setTitle(Title.Dirt);
            }
            else {
                player.setTitle(Title.Neutral);
            }
        }
        
    }
    
    /**
     * Displays the results of the game.
     */
    private void displayResults() {
        Console.write("");
        for (Player player : playerList) {
            Console.write(". "+player+" raced rank "+player.getGameRank()+".");
        }
        Console.write("");
    }
     
    private void addPoints() {
        Integer worstRank = this.nbPlayers;
        for (Player player : this.playerList) {
            player.updatePoints(playerList);
        }
        for (Player player : this.playerList) {
            player.getScore().updateElo();
        }
    }
    
    /**
     * Displays the score of the players.
     */
    public void displayPoints() {
        Console.write("");
        double elosum = 0.0;
        for (Player player : playerList) {
            //write(". "+player+" got "+player.getScore().getPreciseElo()+" elo.");
            elosum += player.getScore().getPreciseElo();
        }
        
        
        elosum /= this.nbPlayers;
        
        
        Console.write("elosum:"+elosum);
        Console.write("");
        for (Player player : playerList) {
            //write(". "+player+" got "+player.getScore().getNormal()+" points.");
            Console.write(". "+player+" got "+player.getScore().getElo()+" elo.");
        }
        
    }
    
    
    // --------------
    
    
    /**
     * Simulates a player turn.
     */
    private void turn() {

        updateCurrentPlayer();
 
        do {
            replay = false;
            
            // Chooses cards to play
            PlayedCards playedCards = this.currentPlayer.chooseCardsToPlay();
            
            try {
                playPlayerCards(playedCards);
                
                for (Player player : this.playerList) {
                    player.setTurnPassed(false);
                }
                
                Console.write(this.currentPlayer+" played "+playedCards+".");
                specialCardProperties(playedCards);
                
                
            } catch (InvalidCardException e) {
                if (!this.shutUp) {
                    this.currentPlayer.setTurnPassed(true);
                    Console.write(this.currentPlayer+" passed his turn.");
                }
                else {
                    Console.write(this.currentPlayer+" got shut up.");
                }
                
                this.shutUp = false;
                replay = false;
                
            } finally {
                
                if (allTurnsPassed()) {
                    resetPile(false);
                }
            }
            
        } while (replay && this.currentPlayer.hasCards());
        
        if (!this.currentPlayer.hasCards()) {
            
            if (this.currentPlayer.isFinishValid()) {
                nbFinishedPlayers++;
                this.currentPlayer.setGameRank(nbFinishedPlayers);
            }
            else {
                this.currentPlayer.setGameRank(nbPlayers-nbMistakenPlayers);
                nbMistakenPlayers++;
                Console.write(this.currentPlayer+" had a wrong finish.");
            }
            Console.write(this.currentPlayer+" finished at rank "+this.currentPlayer.getGameRank()+".");
        }
        
    }
    
    /**
     * Updates the player who will effectuate his turn.
     */
    private void updateCurrentPlayer() {
        do {
            this.currentPlayer = this.playerList.get(this.currentIndex);
            this.currentIndex++;
            if (this.currentIndex >= this.playerList.size()) {
                this.currentIndex = 0;
            }
        } while(!this.currentPlayer.hasCards() && !this.currentPlayer.isTurnPassed());
    }
    
    
    private void playPlayerCards(PlayedCards playedCards) throws InvalidCardException {
        if ((playedCards != null && playedCards.getCount() > 0) && (!shutUp || playedCards.getValue().equals(this.cardPile.getValue()))) {
            this.cardPile.addCard(playedCards);
            this.currentPlayer.removeCards(playedCards);
            this.currentPlayer.updateLastPlayedCards(playedCards);
        }
        else {
            throw new InvalidCardException();
        }
}
    
    
    private void specialCardProperties(PlayedCards playedCards) {
        boolean resetPile = playedCards.getName().equals("2")
                            || cardPile.getStreakValue() >= 4;

        if (resetPile) {
            resetPile(true);
        }
        else if (cardPile.getStreakValue() >= 2 && cardPile.getCount() == 1) {
            this.shutUp = true;
        }
    }
    
    
    private void resetPile(boolean _replay) {
        Console.write("Pile reset.\n");
        this.cardPile.clear();
        replay = _replay;
        this.shutUp = false;
        for (Player player : this.playerList) {
            player.setTurnPassed(false);
        }
    }
    
    /**
     * Finds the player with one specific title, if there's any.
     * @param title Title that we're looking for.
     * @return Player with the dirt title, or null if there's not.
     */
    private Player findPlayerWithTitle(Title title) {
        Player dirt = null;
        for (Player player : playerList) {
            if (player.getTitle() == title) {
                dirt = player;
                break;
            }
        }
        return dirt;
    }
    
    /**
     * Returns the index of a specific player, and null if there's none.
     * @param player Player we want the index of.
     * @return The index of the player, null if there's none.
     */
    private Integer getIndex(Player player) {
        Integer index = null;
        
        for (int i = 0; i < playerList.size(); i++) {
            if (this.playerList.get(i).equals(player)) {
                index = i;
                break;
            }
        }
        
        return index;
    }
    
    // --------------
    
    
    private boolean allTurnsPassed() {
        Integer nbPassedTurns = 0;
        for (Player player : this.playerList) {
            if (player.isTurnPassed() || !player.hasCards()) {
                nbPassedTurns++;
            }
        }
        
        return nbPassedTurns >= this.nbPlayers-1;
    }
    
    
    /**
     * Tells if the game is still going.
     * @return True if the game continues. 
     */
    private boolean isPlaying() {
        int nbPlayersPlaying = 0;
        
        for (Player player : this.playerList) {
            if (player.hasCards()) {
                nbPlayersPlaying++;
            } 
        }
        
        return (nbPlayersPlaying > 1);
    }

    
    public boolean isShutUp() {
        return shutUp;
    }

    
    public Restricter getRestricter() {
        return restricter;
    }
    
}
