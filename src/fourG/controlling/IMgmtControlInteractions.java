package fourG.controlling;

import fourG.base.Move;
import fourG.base.Player;
import fourG.model.GameModel;
import fourG.model.GameOffer;
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public interface IMgmtControlInteractions {
    
    public void initLoadedGame(File f);
    public void initLocalGame(boolean ki);
    public void initCreateOnlineGame();
    public void initJoinOnlineGame();
    public void initSaveGame();
}
