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
    public Object getConsoleLockObject(){
        return consoleLock;
    }
    
    public void setModel(IGameModelModifications m){
        this.model = m;
    }
    
    public void setEnemy(IEnemy e){
        this.enemy = e;
    }
    
    public void setMyColor(Player color){
        iAm = color;
    }
    
    public void enemyReady(){
        synchronized(consoleLock){
            System.out.println("GameController: enemy Ready -> startgame");
        }
        startGame();
    }
    
    private void startGame(){
        model.setState(ModelState.Playing);
        
        
        synchronized(consoleLock){ 
            System.out.println("FOR TESTING: sumbit 3 Moves on random Columns every 500ms");
        }
        Move m = new Move(1);
        m.setPlayer(Player.Red);
        
        for(int i = 0; i< 3; i++){
            makeMove(m);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Will be called From any UI to process a Move.
     * 
     * @param m 
     * @return  boolean
     */
    @Override
    public boolean makeMove(Move m){
        
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
        Player enemycolor = Player.Red;
        if(iAm == Player.Red){
            enemycolor = Player.Blue;
        }
        m.setPlayer(enemycolor);
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
        model.addGameOffer(o);
        joinGame(o); // auto join first GameOffer for TESTING! Normally joinGame would be called from GameView;
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
}
