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

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (board.getCell(i, j) != Player.None) {
                    currentPlayer = board.getCell(i, j);
                    processMove(new Move(i));
                }
            }
        }

        currentPlayer = Player.Blue;
        System.out.println("boardBlue " + boardBlue);
        System.out.println("boardRed " + boardRed);
    }

	public ArrayList<Integer> getPossibleMoves() {
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            if (isMoveValid(i)) {
                possibleMoves.add(i);
            }
        }

        return possibleMoves;
	}

    @Override
    public boolean processMove(Move m) {
        int col = m.getXPosition();
        long pos = ((long)1 << (discsPerColumn[col] + height * col));
        if (currentPlayer == Player.Blue) {
            boardBlue ^= pos;
        } else if (currentPlayer == Player.Red) {
            boardRed ^= pos;
        }
        discsPerColumn[col]++;
//        System.out.println("Move for Player " + currentPlayer.name() + " " + m.getXPosition());
//        System.out.println("boardBlue: " + boardBlue);
//        System.out.println("boardRed: " + boardRed);
        changePlayer();

        return true;
    }

    public void undoMove(Move move) {
		changePlayer();
        int col = move.getXPosition();
        discsPerColumn[col]--;
        long pos = ((long)1 << (discsPerColumn[col] + height * col));
        if (currentPlayer == Player.Blue) {
            boardBlue ^= pos;
        } else{
            boardRed ^= pos;
        }
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
            long c = (b & (b >> 2)); // false positive
            c |= 1024819115206086200L; // pattern for false positives
            c ^= 1024819115206086200L;
            if (c > 0) {
                return Player.Blue;
            }
            
//
//            if ((c & ) > 0) { //  && (b & 130894241400L) > 0 && (b & 523576965600L) > 0) {
//                return Player.Blue;
//            }
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
            long c = (b & (b >> 2)); // false positive
            c |= 1024819115206086200L; // pattern for false positives
            c ^= 1024819115206086200L;
            if (c > 0) {
                return Player.Red;
            }
        }
        
        return Player.None;
    }

    public long getBlueBoard() {
        return boardBlue;
    }


    private void changePlayer() {
        currentPlayer = currentPlayer == Player.Blue ? Player.Red : Player.Blue;
    }

    private boolean isMoveValid(int i) {
        return discsPerColumn[i] < height;
    }

    
	
}
