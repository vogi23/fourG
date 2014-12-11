/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourG.base;

import fourG.controlling.IGameControlUpdates;
import fourG.controlling.InvalidMoveException;
import fourG.model.GameBoard;
import fourG.model.IGameModelInformations;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramon
 */
public class RandomEnemy extends Enemy {
    private final Random random;
    public RandomEnemy(IGameModelInformations gamemodel, IGameControlUpdates gamecontroller) {
        super(gamemodel, gamecontroller);
        random = new Random();
        
        gameC.enemyReady();
    }

    @Override
    public void receiveMove(Move m) {
        Move move = new Move(random.nextInt(7));
        GameBoard board = super.gameM.getBoard();
        while(!board.isMoveValid(move)) {
                        move = new Move(random.nextInt(7));
        }
        try {
            super.gameC.receiveMove(move);
        } catch (InvalidMoveException ex) {
            Logger.getLogger(RandomEnemy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
