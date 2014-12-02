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
    private ModelState state;
    
    private int width = 5;
    private int height = 4;
    
    public GameModel(ModelState s){
        observers = new ArrayList();
        state = s;
    }
    
    public void setWidth(int i){
        width = i;
    }
    public void setHeight(int i){
        height = i;
    }
            
    
    @Override
    public boolean processMove(Move m) {
        throw new UnsupportedOperationException("Not supported yet.3"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isGameover() {
        throw new UnsupportedOperationException("Not supported yet.4"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getWinner() {
        throw new UnsupportedOperationException("Not supported yet.5"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet.6"); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void addModelObserver(IModelObserver obs){
        
    }
    
    @Override
    public void addGameOffer(GameOffer o) {
        throw new UnsupportedOperationException("Not supported yet.7"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setState(ModelState s) {
        state = s;
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
