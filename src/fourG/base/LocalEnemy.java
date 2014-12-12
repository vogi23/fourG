package fourG.base;

import fourG.controlling.GameController;
import fourG.model.AIGameModel;
import fourG.model.GameModel;
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
	private AIGameModel gameModel;
	private final int MAX_DEPTH = 5;
    private final Player me = Player.Blue;

    public LocalEnemy(GameModel gamemodel, GameController gamecontroller){
        super(gamemodel, gamecontroller);
        
	this.gameModel = new AIGameModel(gamemodel.getBoard());
        gameC.enemyReady();
    }

    /**
     * Send Move to KI
     *
     * @param m
     */
    @Override
    public void receiveMove(Move m) {
        ArrayList<Integer> possibleMoves = gameModel.getPossibleMoves();
        int maxValue = 0;
        int bestMove = -1;

	    for (Integer possibleMove : possibleMoves) {
		    int value = evaluateMove(possibleMove, 0, MAX_DEPTH);
		    if (value > maxValue) {
                bestMove = possibleMove;
                maxValue = value;
		    }
	    }

        super.gameC.receiveMove(new Move(bestMove));
    }

	private int evaluateMove(Integer possibleMove, int value, int maxDepth) {
        Move aMove = new Move(possibleMove);
        boolean processMove = gameModel.processMove(aMove);
        Player winner = gameModel.getWinner();
        if (winner != Player.None) {
            value = winner == me ? value + 1 : value -1;
        } else if (maxDepth > 0 ) {
            ArrayList<Integer> possibleMoves = gameModel.getPossibleMoves();
            for (Integer move : possibleMoves) {
                value = evaluateMove(move, value, --maxDepth);
            }
        }

        return value;
	}
    
    @Override
    public String getEnemyName(){
        return "Schlauer Gegner";
    }
}
