/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package president;

import java.util.ArrayList;

/**
 *
 * @author Kévin
 */
public class Score {
    
    private Player player;
    
    // Normal score : nbPlayers - rank.
    private Integer normal;
    
    // Elo ranking score.
    private double elo;
    private double newElo;
    
    // Zero sum score :
    // +1 in case of win against another player,
    // -1 in case of loss against another player.
    // Formula : (nbPlayers - 1) - (gameRank - 1)*2
    private Integer zeroSum;
    
    
    public Score(Player player) {
        
        this.player = player;
        this.normal = 0;
        this.elo = 1500;
        this.newElo = elo;
        this.zeroSum = 0;
        
    }
    
    
    public void updatePoints(ArrayList<Player> playerList) {
        double K = 10;
        
        int nbPlayers = playerList.size();
        this.normal += nbPlayers - this.player.getGameRank();
        this.zeroSum += (nbPlayers - 1) - (this.player.getGameRank() - 1)*2;
        
        double eloTransaction = 0;
        
        for (Player player : playerList) {
            
            double opponentElo = player.getScore().getPreciseElo();
            double D = this.elo - opponentElo;
            //if (D > 400) D = 400;
            //if (D < -400) D = -400;
            //System.out.println(D);
            double expectedResult = 1/(1+Math.pow(10, -D/400));
            double gameResult;
            
            if (this.player.getGameRank() < player.getGameRank()) {
                gameResult = 1;
            }
            else if (this.player.getGameRank() > player.getGameRank()) {
                gameResult = 0;
            }
            else {
                gameResult = 0.5;
            }
            
            // En+1 = En + K * (W - p(D))
            eloTransaction += K * (gameResult - expectedResult);
            
        }
        
        this.newElo = this.elo + eloTransaction;
    }
    
    public void updateElo() {
        this.elo = this.newElo;
    }

    
    public Integer getNormal() {
        return normal;
    }

    
    public double getPreciseElo() {
        return elo;
    }
    
    public Integer getElo() {
        return (int)Math.round(elo);
    }

    
    public Integer getZeroSum() {
        return zeroSum;
    }
    
    
    
}
