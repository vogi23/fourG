package fourG.base;


import fourG.controlling.MgmtController;
import fourG.view.GameView;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public class TestGameServer {
    
    public static void main(String[] args){
        
        MgmtController mgmt = new MgmtController();
        mgmt.initCreateOnlineGame();
    }
}
