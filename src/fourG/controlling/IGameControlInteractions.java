package fourG.controlling;

import fourG.base.Move;
import fourG.base.Player;
import fourG.model.GameOffer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public interface IGameControlInteractions {
    
    public boolean makeMove(Move m);
    public void reDiscoverEnemys();
    public void joinGame(GameOffer o);
    public Player getMyColor();
    
    public String getEnemyName();
    public Object getConsoleLockObject();
}
