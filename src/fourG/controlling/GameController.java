package fourG.controlling;

import fourG.base.Move;
import fourG.base.IEnemy;
import fourG.base.Player;
import fourG.base.RemoteEnemy;
import fourG.model.GameOffer;
import fourG.model.IGameModelModifications;
import fourG.model.ModelState;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Player iAm = Player.Red;      // Color (Player identification) of this Application
    
    private final Object consoleLock = new Object();
    
    public GameController(){
        
    }
    
    /**
     * Kill all Dependencys which disturb the initiaion of a complete new game (ex. open Sockets still blocking ports)
     */
    public void kill(){
        if(enemy != null){
            enemy.killSockets();
        }
    }
    
    @Override
    public Player getMyColor(){
        return iAm;
    }
    
    @Override
    public Object getConsoleLockObject(){
        return consoleLock;
    }
    
    public void setModel(IGameModelModifications m){
        this.model = m;
    }
    
    @Override
    public void setEnemy(IEnemy e){
        this.enemy = e;
    }
    
    public void setMyColor(Player color){
        iAm = color;
    }
    
    @Override
    public void enemyReady(){
        synchronized(consoleLock){
            System.out.println("GameController: enemy Ready -> startgame");
        }
        startGame();
    }
    
    private void startGame(){
        model.setState(ModelState.Playing);
    }
    
    /**
     * Will be called From any UI to process a Move.
     * 
     * @param m 
     * @return  boolean
     */
    @Override
    public boolean makeMove(Move m){
        
        if(model.getState() != ModelState.Playing){
            return true;
        }
        
        // Process move. return false if invalid.
        m.setPlayer(iAm);
        synchronized(consoleLock){ 
            System.out.println("GameController makeMove: "+m);
        }
        if(!model.processMove(m)){
            synchronized(consoleLock){ 
                System.out.println("Move is NOT valid");
            }
            return false;
        }
        synchronized(consoleLock){ 
            System.out.println("Move is valid");
        }
        
        // Send valid move to enemy
        enemy.receiveMove(m);
        
        return true;
    }
    
    /**
     * Inform GameController about a move from an enemy
     * 
     * @param m 
     * @return  
     */
    @Override
    public boolean receiveMove(Move m){
        
        if(model.getState() != ModelState.Playing){
            return true;
        }
        
        // Process move. return false if invalid.
        Player enemycolor = Player.Red;
        if(iAm == Player.Red){
            enemycolor = Player.Blue;
        }
        m.setPlayer(enemycolor);
        if(!model.processMove(m)){
            return false;
        }
        
        // Check if game is over
        if(model.isGameover()){
            System.out.println("WIIIIINNNEEEEERRRRRR: "+model.getWinner());
            //TODO  kill enemy (close Sockets ....)
        }
        return true;
    }
    
    @Override
    public void offerGame(GameOffer o){
        model.addGameOffer(o);
    }
   

    @Override
    public void reDiscoverEnemys() {
        if(RemoteEnemy.class.isInstance(enemy)){
            model.setState(ModelState.SearchOnlineGames);
            model.clearGameOffers();
            ((RemoteEnemy) enemy).discoverEnemysOnNetwork();
        }
    }

    @Override
    public void joinGame(GameOffer o) {
        if(RemoteEnemy.class.isInstance(enemy)){
            ((RemoteEnemy) enemy).connectToOnlineGame(o);
        }
    }
    
    @Override
    public String getEnemyName(){
        if(enemy == null){
            return "kein Gegner erstellt";
        }
        return enemy.getEnemyName();
    }
}
