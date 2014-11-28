package fourG.base;

import fourG.base.Player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public class Move {
    
    private int row;
    private Player player;
    private int playerCount;
            
    public Move(int row, Player player, int count){
        this.row = row;
        this.player = player;
        this.playerCount = count;
    }
    
    public int getRow(){
        return this.row;
    }
    
    public Player getPlayer(){
        return this.player;
    }
    
    public int getPlayerCount(){
        return this.playerCount;
    }
}
