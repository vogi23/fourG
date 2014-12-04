/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.model;

import fourG.base.Player;

/**
 *
 * @author vogi23
 */
public class GameBoard {
    
    private int width;
    private int height;
    
    public GameBoard(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    public void setCell(int x, int y){
        
    }
    
    public Player getCell(){
        
        return Player.None;
    }
}
