/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourG.base;

import fourG.controlling.IGameControlUpdates;
import fourG.model.IGameModelInformations;
import java.util.Random;

/**
 *
 * @author Ramon
 */
public class RandomEnemy extends Enemy {
    private final Random random;
    public RandomEnemy(IGameModelInformations gamemodel, IGameControlUpdates gamecontroller) {
        super(gamemodel, gamecontroller);
        random = new Random();
    }

    @Override
    public void receiveMove(Move m) {
        Move move = new Move(random.nextInt(7));
        // TODO: send move
//        while (super.gameM.) {
//            move = new Move(random.nextInt(7));
//        }
    }
    
}
