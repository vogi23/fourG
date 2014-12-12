/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourG.model;

import fourG.base.Move;
import fourG.base.Player;
import java.util.ArrayList;

/**
 *
 * @author lts
 */
public class AIGameModel extends GameModel {
    private long boardBlue;
    private long boardRed;
    private Integer[] discsPerColumn;
    private Player currentPlayer;
    private final Integer width;
    private final Integer height;

    public AIGameModel(GameBoard board) {
        width = board.getWidth();
        height = board.getHeight();
        boardBlue = 0;
        boardRed = 0;
        discsPerColumn = new Integer[width];
        for (int i = 0; i < width; i++) {
            discsPerColumn[i] = 0;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board.getCell(j, i) != Player.None) {
                    currentPlayer = board.getCell(j, i);
                    processMove(new Move(j));
                }
            }
        }
    }

	public ArrayList<Integer> getPossibleMoves() {
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            if (isMoveValid(i)) {
                possibleMoves.add(i);
            }
        }

        return possibleMoves;
	}

    @Override
    public boolean processMove(Move m) {
        int col = m.getXPosition();
        long pos = ((long)1 << (discsPerColumn[col] + 7 * col));
        if (currentPlayer == Player.Blue) {
            boardBlue ^= pos;
        } else if (currentPlayer == Player.Red) {
            boardRed ^= pos;
        }
        discsPerColumn[col]++;
        changePlayer();

        return true;
    }

    @Override
    public Player getWinner() {
        boolean draw = true;
        for (int i = 0; i < width; i++) {
            if (isMoveValid(i)) {
                draw = false;
                break;
            }
        }
        if (draw) {
            return Player.None; // TODO: Player.Draw
        }

        long b = boardBlue & (boardBlue >> 6);
        if ((b & (b >> 2 * 6)) > 0) { // check \
            return Player.Blue;
        }
        b = boardBlue & (boardBlue >> 7);
        if ((b & (b >> 2 * 7)) > 0) { // check -
            return Player.Blue;
        }
        b = boardBlue & (boardBlue >> 8);
        if ((b & (b >> 2 * 8)) > 0) { // check /
            return Player.Blue;
        }
        b = boardBlue & (boardBlue >> 1);
        if ((b & (b >> 2)) > 0) { // check |
            return Player.Blue;
        }

        // test red
        b = boardRed & (boardRed >> 6);
        if ((b & (b >> 2 * 6)) > 0) { // check \
            return Player.Red;
        }
        b = boardRed & (boardRed >> 7);
        if ((b & (b >> 2 * 7)) > 0) { // check -
            return Player.Red;
        }
        b = boardRed & (boardRed >> 8);
        if ((b & (b >> 2 * 8)) > 0) { // check /
            return Player.Red;
        }
        b = boardRed & (boardRed >> 1);
        if ((b & (b >> 2)) > 0) { // check |
            return Player.Red;
        }
        
        return Player.None;
    }



    private void changePlayer() {
        currentPlayer = currentPlayer == Player.Blue ? Player.Red : Player.Blue;
    }

    private boolean isMoveValid(int i) {
        return discsPerColumn[i] < height;
    }

    
	
}
