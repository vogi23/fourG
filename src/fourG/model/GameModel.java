package fourG.model;


import fourG.base.Player;
import fourG.base.Move;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tgdvoch5
 */
public class GameModel implements IGameModelModifications, IGameModelInformations {
    
    private transient ArrayList<IModelObserver> observers;
    
    public GameModel(int width, int height){
        observers = new ArrayList();
    }
    
    @Override
    public boolean processMove(Move m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isGameover() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getWinner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void addModelObserver(IModelObserver obs){
        
    }
    
    /**
     * Informiert alle IModelObservers (bsp. GameView), dass das GameModel geupdated wurde.
     */
    private void broadcastUpdate(){
        for(IModelObserver o : observers){
            o.update();
        }
    }
}
