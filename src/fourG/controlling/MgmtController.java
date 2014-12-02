package fourG.controlling;


import fourG.base.LocalEnemy;
import fourG.base.Player;
import fourG.base.RemoteEnemy;
import fourG.model.GameModel;
import fourG.model.ModelState;
import fourG.view.GameView;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
    
    private GameController gameC;
    private GameView gameV;
    
    private JoinServer server;
    
    public MgmtController(){
        this.gameC = new GameController();
        this.gameV = new GameView(this, this.gameC);
        
    }
    
    public void initLoadedGame(){
        // Set My Color
        gameC.setMyColer(Player.Red);
        
    }
    
    public void initLocalGame(){
        // Set My Color
        gameC.setMyColer(Player.Red);

        // Create GameModel
        GameModel g = new GameModel(ModelState.PreparingEnemy);
        g.addModelObserver(this.gameV);
        gameC.setModel(g);
        
        // Create Enemy
        gameC.setEnemy(new LocalEnemy(g,this.gameC));
    }
    
    public void initOnlineGame(){
        // Set My Color
        gameC.setMyColer(Player.Red);
        
        // Create GameModel
        GameModel g = new GameModel(ModelState.WaitingForRemoteJoin);
        g.addModelObserver(this.gameV);
        gameC.setModel(g);
        
        // Create Enemy
        RemoteEnemy e = new RemoteEnemy(g,this.gameC);
        gameC.setEnemy(e);
        e.listenUDP();
        e.listenTCP();
    }
    
    public void searchOnlineGame(){
        // Set My Color
        gameC.setMyColer(Player.Blue);
        System.out.println("starting");
        
        // Create GameModel
        GameModel g = new GameModel(ModelState.SearchOnlineGames);
        g.addModelObserver(this.gameV);
        gameC.setModel(g);
        
        // Create Enemy
        RemoteEnemy e = new RemoteEnemy(g,this.gameC);
        gameC.setEnemy(e);
        e.searchEnemys();
    }
}
