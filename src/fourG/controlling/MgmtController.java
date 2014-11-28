package fourG.controlling;


import fourG.base.LocalEnemy;
import fourG.base.RemoteEnemy;
import fourG.model.GameModel;
import fourG.view.GameView;

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
    
    public MgmtController(){
        this.gameC = new GameController();
        this.gameV = new GameView(this, this.gameC);
    }
    
    public void initLocalGame(){
        // Create GameModel
        GameModel g = new GameModel(5,4);
        g.addModelObserver(this.gameV);
        gameC.setModel(g);
        
        // Create Enemy
        gameC.setEnemy(new LocalEnemy(g,this.gameC));
    }
    
    public void initOnlineGame(){
        // Create GameModel
        GameModel g = new GameModel(5,4);
        g.addModelObserver(this.gameV);
        gameC.setModel(g);
        
        // Create Enemy
        gameC.setEnemy(new RemoteEnemy(g,this.gameC));
    }
    
    public void initLoadedGame(){
        //...
    }
}
