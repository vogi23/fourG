/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourG.base;

import fourG.model.GameBoard;
import fourG.model.GameOffer;
import fourG.model.IGameModelInformations;
import fourG.model.ModelState;
import java.util.ArrayList;

/**
 *
 * @author lts
 */
public class TestGameModelInformations implements IGameModelInformations{
    private final GameBoard board;

    public TestGameModelInformations(GameBoard board) {
        this.board = board;
    }

    @Override
    public GameBoard getBoard() {
        return board;
    }
    
    
    @Override
    public boolean isGameover() {
        return false;
    }

    @Override
    public Player getWinner() {
        return Player.None;
    }

    @Override
    public Player getCurrentPlayer() {
        return Player.None;
    }

    @Override
    public ModelState getState() {
        return ModelState.Playing;
    }

    @Override
    public ArrayList<GameOffer> getGameOffers() {
        return new ArrayList<>();
    }

    @Override
    public Move getLastMove() {
        return new Move(0);
    }

    
}
