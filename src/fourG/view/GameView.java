package fourG.view;


import fourG.controlling.MgmtController;
import fourG.controlling.GameController;
import fourG.model.IModelObserver;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public class GameView extends JFrame implements IModelObserver {
    
    private MgmtController mgmt;
    private GameController game;
    
    public GameView(MgmtController mgmt, GameController game){
        this.mgmt = mgmt;
        this.game = game;
    }
    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
