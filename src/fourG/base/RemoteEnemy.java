package fourG.base;

import fourG.controlling.GameController;
import fourG.model.GameModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public class RemoteEnemy extends Enemy implements IEnemy {
    
    public RemoteEnemy(GameModel gamemodel, GameController gamecontroller){
        super(gamemodel, gamecontroller);
        
    }
    
    /**
     * Send Move to remote Host
     * 
     * @param m 
     */
    @Override
    public void receiveMove(Move m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
