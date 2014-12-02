package fourG.base;


import fourG.controlling.MgmtController;
import fourG.view.GameView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public class TestGameJoiner {
    
    public static void main(String[] args){
        
        MgmtController mgmt = new MgmtController();
        mgmt.searchOnlineGame();
        
    }
}
