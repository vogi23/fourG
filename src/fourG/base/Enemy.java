/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.base;

import fourG.controlling.IGameControlUpdates;
import fourG.model.GameModel;
import fourG.model.IGameModelInformations;

/**
 *
 * @author vogi23
 */
public class Enemy {
    protected IGameModelInformations gameM;
    protected IGameControlUpdates gameC;
    
    public Enemy(IGameModelInformations gamemodel, IGameControlUpdates gamecontroller){
        this.gameM = gamemodel;
        this.gameC = gamecontroller;
    }
}
