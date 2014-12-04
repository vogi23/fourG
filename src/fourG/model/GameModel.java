package fourG.model;


import fourG.base.Player;
import fourG.base.Move;
import java.util.ArrayList;
import java.util.HashSet;

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
    
    private transient HashSet<IModelObserver> observers;
    private ModelState state;
    
    private int width = 5;
    private int height = 4;
    private GameBoard board;
    
    private ArrayList<GameOffer> gameoffers;
    
    public GameModel(ModelState s){
        observers = new HashSet<IModelObserver>();
        gameoffers = new ArrayList<GameOffer>();
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
    public Move getLastMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameBoard getBoard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean isGameover() {
        return state == ModelState.GameOver;
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
        observers.add(obs);
    }
    
    @Override
    public void addGameOffer(GameOffer o) {
        if(o == null){
            return;
        }
        gameoffers.add(o);
        broadcastUpdate();
    }
    
    @Override
    public void clearGameOffers() {
        gameoffers.clear();
        broadcastUpdate();
    }
    
    @Override
    public ArrayList<GameOffer> getGameOffers() {
        return gameoffers;
    }

    @Override
    public ModelState getState() {
        return state;
    }
    @Override
    public void setState(ModelState s) {
        if(state == s){
            // State did NOT change
            return;
        }
        state = s;
        broadcastUpdate();
    }
    
    /**
     * Informiert alle IModelObservers (bsp. GameView), dass das GameModel aktualisiert wurde.
     */
    private void broadcastUpdate(){
        for(IModelObserver o : observers){
            o.update();
        }
    }
}
