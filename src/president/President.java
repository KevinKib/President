/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president;


import java.util.ArrayList;
import president.AI.*;

/**
 *
 * @author Kévin
 */
public class President {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ArrayList<Player> playerList = new ArrayList<>();
        Game game = new Game(playerList);
        game.setTextDisplay(true);
        
        
        Player p1 = new AI_Basic("player1", game.getRestricter());
        Player p2 = new AI_Basic("player2", game.getRestricter());
        Player p3 = new AI_Random("random1", game.getRestricter());
        Player p4 = new AI_Random("random2", game.getRestricter());
        
        
        playerList.add(p1);
        playerList.add(p2);
        playerList.add(p3);
        playerList.add(p4);
        
        int nbIterations = 3;
        
        for (int i = 0; i < nbIterations; i++) {
            game.play(); 
        }
        
        game.setTextDisplay(true);
        game.displayPoints();
    }
}
