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
    private int y;
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
    
   public void setYPosition(int pY){
       y=pY;
   }
    public int getYPosition(int pY){
       return y;
   }
    @Override
    public String toString(){
        return "{Column: "+this.column+", Player: "+this.player+"}";
    }
}
