/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourG.base;

import fourG.controlling.IGameControlUpdates;
import fourG.model.GameOffer;
import org.junit.Assert;

/**
 *
 * @author lts
 */
public class TestGameControlUpdates implements IGameControlUpdates {
    private final int expected;

    public TestGameControlUpdates(int move) {
        this.expected = move;
    }

    @Override
    public void enemyReady() {
    }
    
    @Override
    public boolean receiveMove(Move m) {
        int acutal = m.getXPosition();
        Assert.assertEquals(expected, acutal);

        return true;
    }

    @Override
    public void offerGame(GameOffer o) {
    }

    @Override
    public Object getConsoleLockObject() {
        return new Object();
    }

	@Override
	public void setEnemy(IEnemy e) {
	}
    
}
