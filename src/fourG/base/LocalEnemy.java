package fourG.base;

import fourG.controlling.GameController;
import fourG.model.GameModel;
import fourG.model.IGameModelInformations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public class LocalEnemy extends Enemy implements IEnemy{
   
    public LocalEnemy(GameModel gamemodel, GameController gamecontroller){
        super(gamemodel, gamecontroller);
        
    }
    
    /**
     * Send Move to KI
     * 
     * @param m 
     */
    @Override
    public void receiveMove(Move m) {
        
        // Move already processed by local GameModel. KI can access directly local GameModel and react with a move
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
