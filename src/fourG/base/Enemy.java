/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.base;

import fourG.controlling.IGameControlInteractions;
import fourG.model.GameModel;
import fourG.model.IGameModelInformations;

/**
 *
 * @author tgdvoch5
 */
public class Enemy {
    protected IGameModelInformations gameM;
    protected IGameControlInteractions gameC;
    
    public Enemy(IGameModelInformations gamemodel, IGameControlInteractions gamecontroller){
        this.gameM = gamemodel;
        this.gameC = gamecontroller;
    }
}
