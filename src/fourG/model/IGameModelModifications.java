package fourG.model;

import fourG.base.Move;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tgdvoch5
 */
public interface IGameModelModifications extends IGameModelInformations{
    public boolean processMove(Move m);
    
}
