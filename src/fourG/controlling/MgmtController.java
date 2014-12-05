package fourG.controlling;


import fourG.base.LocalEnemy;
import fourG.base.Player;
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
        
        // Create Controller
        this.gameC = new GameController();
        
        // Create Model
        gameM = new GameModel();
        this.gameC.setModel(gameM);
        
        // Create View
        this.gameV = new GameView(this, this.gameC, gameM);
        gameM.addModelObserver(this.gameV);
    }
    
    /**
     * Load a saved game.
     * 
     * Usually called from the GameView
     */
    public void initLoadedGame(File f){
        //...
    }
        
    /**
     * Start a local game against the computer
     * 
     * Usually called from the GameView
     */
    public void initLocalGame(){
        
        gameM.setState(ModelState.PreparingEnemy);
        
        // Create Enemy
        gameC.setEnemy(new LocalEnemy(this.gameM,this.gameC));
    }
    
    /**
     * Create a game and wait for remote users to join you
     * 
     * Usually called from the GameView
     */
    public void initCreateOnlineGame(){
        
        gameM.setState(ModelState.WaitingForRemoteJoin);
        
        // Create Enemy
        RemoteEnemy e = new RemoteEnemy(this.gameM,this.gameC);
        gameC.setEnemy(e);
        e.listenForDiscoveryRequests();
        e.listenForJoiningRequests();
    }
    
    /**
     * Look for games online and join them
     * 
     * Usually called from the GameView
     */
    public void initJoinOnlineGame(){
        
        // Set my color to Blue (Enemy will start with first move)
        gameC.setMyColor(Player.Blue);
        
        gameM.setState(ModelState.SearchOnlineGames);
        
        // Create Enemy
        RemoteEnemy e = new RemoteEnemy(this.gameM,this.gameC);
        gameC.setEnemy(e);
        e.discoverEnemysOnNetwork();
    }
    
    public void initSaveGame(){
        //...
    }
}