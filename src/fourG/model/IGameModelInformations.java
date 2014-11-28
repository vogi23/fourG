package fourG.model;

import fourG.base.Player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tgdvoch5
 */
public interface IGameModelInformations {
    public boolean isGameover();
    public Player getWinner();
    public Player getCurrentPlayer();
}
