package fourG.base;

import fourG.base.Player;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public class Move implements Serializable{
    
    private int column;
    private Player player = Player.None;
            
    public Move(int col){
        this.column = col;
    }
    
    public int getColumn(){
        return this.column;
    }
    
    public void setPlayer(Player p){
        player = p;
    }
    
    public Player getPlayer(){
        return player;
    }
}
