package fourG.base;

import fourG.controlling.GameController;
import fourG.controlling.IGameControlUpdates;
import fourG.model.AIGameModel;
import fourG.model.GameModel;
import fourG.model.IGameModelInformations;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public class LocalEnemy extends Enemy implements IEnemy {
	private final int MAX_DEPTH = 5;
    private final Player me = Player.Blue;
    private AIGameModel gameModel;

    public LocalEnemy(IGameModelInformations gamemodel, IGameControlUpdates gamecontroller){
        super(gamemodel, gamecontroller);
        
        gameC.enemyReady();
    }

    /**
     * Send Move to KI
     *
     * @param m
     */
    @Override
    public void receiveMove(Move m) {
        gameModel = new AIGameModel(super.gameM.getBoard());
        ArrayList<Integer> possibleMoves = gameModel.getPossibleMoves();
        float maxValue = Float.NEGATIVE_INFINITY;
        int bestMove = -1;

        System.out.println("");
        System.out.println("searching best move");

	    for (Integer possibleMove : possibleMoves) {
		    float value = evaluateMove(possibleMove, 1);
            System.out.println("move: " + possibleMove + "; value: " + value);
		    if (value > maxValue) {
                bestMove = possibleMove;
                maxValue = value;
		    }
	    }

        super.gameC.receiveMove(new Move(bestMove));
    }

	private float evaluateMove(Integer possibleMove, int depth) {
        Move aMove = new Move(possibleMove);
        System.out.println("before processMove: " + gameModel.);
        boolean processMove = gameModel.processMove(aMove);
        Player winner = gameModel.getWinner();
        float value = 0;
        if (winner != Player.None) {
            if (winner == Player.Draw) {
                value = 0;
            } else {
                value = winner == me ? 1 : -1;
            }
        } else if (depth <= MAX_DEPTH ) {
            ArrayList<Integer> possibleMoves = gameModel.getPossibleMoves();
            ++depth;
            for (Integer move : possibleMoves) {
                value += evaluateMove(move, depth);
            }
        }

        gameModel.undoMove(aMove);
        return value / depth;
	}
    
    @Override
    public String getEnemyName(){
        return "The Master";
    }
}
