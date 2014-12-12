package fourG.controlling;


import fourG.base.LocalEnemy;
import fourG.base.Player;
import fourG.base.RandomEnemy;
import fourG.base.RemoteEnemy;
import fourG.model.GameModel;
import fourG.model.ModelState;
import fourG.view.GameView;
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tgdvoch5
 */
public class MgmtController {
    
    private GameModel gameM;
    private GameController gameC;
    private GameView gameV;
    
    public MgmtController(){
        reInitGame();
    }
    
    /**
     * Inits new GameModel, Controller and View
     */
    private void reInitGame(){
        
        resetView();
        resetController();
        
        // Create Controller
        this.gameC = new GameController();
        
        // Create Model
        this.gameM = new GameModel();
        this.gameC.setModel(gameM);
        
        // Create View
        this.gameV = new GameView(this, this.gameC, gameM);
        gameM.addModelObserver(this.gameV);
    }
    
    /**
     * Check if there is already a GameView open. If TRUE, close it.
     */
    private void resetView(){
        if(this.gameV != null){
            this.gameV.dispose();
        }
    }
    
    /**
     * Check if there is already a GameController open. If TRUE, close all open Network Connections.
     */
    private void resetController(){
        if(this.gameC != null){
            gameC.kill();
        }
    }
    
    /**
     * Load a saved game.
     * 
     * Usually called from the GameView
     */
    public void initLoadedGame(File f){
        reInitGame();
        //...
    }
        
    /**
     * Start a local game against the computer
     * 
     * Usually called from the GameView
     */
    public void initLocalGame(boolean ki){
        reInitGame();
        
        gameM.setState(ModelState.PreparingEnemy);
        
        // Create Enemy
        if(ki == true){
            new LocalEnemy(this.gameM,this.gameC);
        }else{
            new RandomEnemy(this.gameM,this.gameC);
        }
    }
    
    /**
     * Create a game and wait for remote users to join you
     * 
     * Usually called from the GameView
     */
    public void initCreateOnlineGame(){
        reInitGame();
        
        gameM.setState(ModelState.WaitingForRemoteJoin);
        
        // Create Enemy
        RemoteEnemy e = new RemoteEnemy(this.gameM,this.gameC);
        e.listenForDiscoveryRequests();
        e.listenForJoiningRequests();
    }
    
    /**
     * Look for games online and join them
     * 
     * Usually called from the GameView
     */
    public void initJoinOnlineGame(){
        reInitGame();
        
        // Set my color to Blue (Enemy will start with first move)
        gameC.setMyColor(Player.Blue);
        
        gameM.setState(ModelState.SearchOnlineGames);
        
        // Create Enemy
        RemoteEnemy e = new RemoteEnemy(this.gameM,this.gameC);
        e.discoverEnemysOnNetwork();
    }
    
    public void initSaveGame(){
        reInitGame();
        //...
    }
}