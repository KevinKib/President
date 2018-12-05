/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president.AI;

import java.util.ArrayList;
import president.Cards.Card;
import president.Cards.Packs.PlayedCards;
import president.Game;
import president.Player;
import president.Restricter;

/**
 *
 * @author Kévin
 */
public class Human extends Player {

    public Human(String name, Restricter restricter) {
        super(name, restricter);
    }
    
    @Override
    public PlayedCards chooseCardsToPlay() {
        return null;
    }

    @Override
    public ArrayList<Card> chooseCardsToSendToPlayer(Integer amount) {
        return null;
    }
    
    
    
    
    
}
