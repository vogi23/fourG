package fourG.controlling;


import com.sun.corba.se.impl.io.IIOPInputStream;
import fourG.base.LocalEnemy;
import fourG.base.Player;
import fourG.base.RandomEnemy;
import fourG.base.RemoteEnemy;
import fourG.model.GameModel;
import fourG.model.ModelState;
import fourG.view.GameView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

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
    
    private void reInitGame(){
        reInitGame(null);
    }
    /**
     * Inits new GameModel, Controller and View
     */
    private void reInitGame(GameModel gm){
        
        resetView();
        resetController();
        
        // Create Controller
        this.gameC = new GameController();
        
        // Create/Load Model
        if(gm == null){
            this.gameM = new GameModel();
        }else{
            this.gameM = gm;
        }
        
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
        
        System.out.println(f);
        ObjectInputStream ois;
        GameModel gm = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(f));
            Object o = null;
            try {
                o = ois.readObject();
            } catch (ClassNotFoundException ex) {
                System.out.println("Unknown file1");
                return;
            }
            if(o.getClass() != GameModel.class){
                System.out.println("Unknown file2");
                return;
            }
            gm = (GameModel) o;
        } catch (IOException ex) {
            System.out.println("LoadGame IO Exception: "+ex.getMessage());
            return;
        }
        
        reInitGame(gm);
        
        new RandomEnemy(this.gameM,this.gameC);
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
        JFileChooser c = new JFileChooser();
        // Demonstrate "Save" dialog:
        int rVal = c.showSaveDialog(gameV);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            String file = (c.getSelectedFile().getName());
            String dir = (c.getCurrentDirectory().toString());
            String path = dir + System.getProperty("file.separator")+file;
            System.out.println("Selcted for save: "+path);
            
            File f = new File(path);
            FileOutputStream fos;
            try{
                fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(gameM);
                oos.flush();
                oos.close();
            } catch(FileNotFoundException e){
                System.err.println("File not Found");
            } catch (IOException ex) {
                System.err.println("Save IO Error " + ex.getMessage());
            }
        }
        System.out.println("File saved!:)");
    }
}