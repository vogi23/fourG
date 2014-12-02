package fourG.controlling;

import fourG.base.Move;
import fourG.base.IEnemy;
import fourG.base.Player;
import fourG.base.RemoteEnemy;
import fourG.model.GameOffer;
import fourG.model.IGameModelModifications;
import fourG.model.ModelState;

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
    private Player iAm = Player.None;         // Color (Player identification) of this Application
    
    public GameController(){
        
    }
    
    
    
    public void setModel(IGameModelModifications m){
        this.model = m;
    }
    
    public void setEnemy(IEnemy e){
        this.enemy = e;
    }
    
    public void setMyColer(Player color){
        iAm = color;
    }
    
    public void enemyReady(){
        startGame();
    }
    
    private void startGame(){
        
        model.setState(ModelState.Playing);
    }
    
    /**
     * Will be called From any UI to process a Move.
     * 
     * @param m 
     */
    @Override
    public boolean makeMove(Move m){
        
        // Process move. return false if invalid.
        if(!model.processMove(m)){
            return false;
        }
        
        // Send valid move to enemy
        enemy.receiveMove(m);
                
        // Check if game is over
        if(model.isGameover()){
            //TODO kill enemy (close Sockets ....)
        }
        return true;
    }
    
    /**
     * Inform GameController about a move from an enemy
     * 
     * @param m 
     */
    @Override
    public void receiveMove(Move m) throws InvalidMoveException {
        
        // Process move. return false if invalid.
        if(!model.processMove(m)){
            throw new InvalidMoveException();
        }
        
        // Check if game is over
        if(model.isGameover()){
            //TODO  kill enemy (close Sockets ....)
        }
    }
    
    @Override
    public void offerGame(GameOffer o){
        //model.addGameOffer(o);
        joinGame(o);
    }

    @Override
    public void searchEnemys() {
        if(RemoteEnemy.class.isInstance(enemy)){
            ((RemoteEnemy) enemy).searchEnemys();
        }
    }

    @Override
    public void joinGame(GameOffer o) {
        System.out.println("join Game");
        if(RemoteEnemy.class.isInstance(enemy)){
        System.out.println("join Game2");
            ((RemoteEnemy) enemy).connect(o);
        }
    }
    
}
