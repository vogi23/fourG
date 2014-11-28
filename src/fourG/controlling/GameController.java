package fourG.controlling;


import fourG.base.Move;
import fourG.base.IEnemy;
import fourG.base.Player;
import fourG.model.IGameModelModifications;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lueq
 */
public class GameController implements IGameControlInteractions, IGameControlUpdates {
    
    private IGameModelModifications model;
    private IEnemy enemy;
    private Player iAm;         // Color (Player identification) of this Application
    
    public GameController() {
        
    }
    
    public void setModel(IGameModelModifications m){
        this.model = m;
    }
    
    public void setEnemy(IEnemy e){
        this.enemy = e;
    }
    
    /**
     * Will be called From any UI to process a Move.
     * 
     * @param m 
     */
    @Override
    public void makeMove(Move m) {
        
    }

    @Override
    public void receiveMove(Move m) {
        
    }
    
}
