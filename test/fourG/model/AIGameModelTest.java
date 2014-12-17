/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourG.model;

import fourG.base.Move;
import fourG.base.Player;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lts
 */
public class AIGameModelTest {
    
    public AIGameModelTest() {
    }

    @Test
    public void testCreateModel(){
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(1, 0, Player.Blue);
        board.setCell(2, 0, Player.Red);
        board.setCell(3, 0, Player.Red);
        board.setCell(4, 0, Player.Blue);
        AIGameModel gModel = new AIGameModel(board);
    }

    /**
     * Test of getPossibleMoves method, of class AIGameModel.
     */
    @Test
    public void testGetPossibleMoves() {
        System.out.println("getPossibleMoves");
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(0, 1, Player.Blue);
        board.setCell(0, 2, Player.Blue);
        board.setCell(0, 3, Player.Blue);
        board.setCell(0, 4, Player.Blue);
        board.setCell(0, 5, Player.Blue);
        AIGameModel instance = new AIGameModel(board);
        ArrayList<Integer> expResult = new ArrayList<>() ;
        expResult.add(1);
        expResult.add(2);
        expResult.add(3);
        expResult.add(4);
        expResult.add(5);
        expResult.add(6);
        ArrayList<Integer> result = instance.getPossibleMoves();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWinner method, of class AIGameModel.
     */
    @Test
    public void testGetHorizontalWinner() {
        System.out.println("getHorizontalWinner");
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(1, 0, Player.Blue);
        board.setCell(2, 0, Player.Blue);
        board.setCell(3, 0, Player.Blue);
        AIGameModel instance = new AIGameModel(board);
        Player expResult = Player.Blue;
        Player result = instance.getWinner();
        assertEquals(expResult, result);

        board = new GameBoard(7, 6);
        board.setCell(1, 0, Player.Blue);
        board.setCell(2, 0, Player.Blue);
        board.setCell(3, 0, Player.Blue);
        board.setCell(4, 0, Player.Blue);
        instance = new AIGameModel(board);
        result = instance.getWinner();
        assertEquals(expResult, result);

        board = new GameBoard(7, 6);
        board.setCell(2, 0, Player.Blue);
        board.setCell(3, 0, Player.Blue);
        board.setCell(4, 0, Player.Blue);
        board.setCell(5, 0, Player.Blue);
        instance = new AIGameModel(board);
        result = instance.getWinner();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetVerticalWinner() {
        System.out.println("getVerticalWinner");
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(0, 1, Player.Blue);
        board.setCell(0, 2, Player.Blue);
        board.setCell(0, 3, Player.Blue);
        AIGameModel instance = new AIGameModel(board);
        Player expResult = Player.Blue;
        Player result = instance.getWinner();
        assertEquals(expResult, result);

        board = new GameBoard(7, 6);
        board.setCell(1, 1, Player.Blue);
        board.setCell(1, 2, Player.Blue);
        board.setCell(1, 3, Player.Blue);
        board.setCell(1, 4, Player.Blue);
        instance = new AIGameModel(board);
        result = instance.getWinner();
        assertEquals(expResult, result);

        board = new GameBoard(7, 6);
        board.setCell(2, 2, Player.Blue);
        board.setCell(2, 3, Player.Blue);
        board.setCell(2, 4, Player.Blue);
        board.setCell(2, 5, Player.Blue);
        instance = new AIGameModel(board);
        result = instance.getWinner();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetForwardDiagonalWinner() {
        System.out.println("testGetForwardDiagonalWinner");
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(1, 1, Player.Blue);
        board.setCell(2, 2, Player.Blue);
        board.setCell(3, 3, Player.Blue);
        AIGameModel instance = new AIGameModel(board);
        Player expResult = Player.Blue;
        Player result = instance.getWinner();
        assertEquals(expResult, result);

        board = new GameBoard(7, 6);
        board.setCell(1, 1, Player.Blue);
        board.setCell(2, 2, Player.Blue);
        board.setCell(3, 3, Player.Blue);
        board.setCell(4, 4, Player.Blue);
        instance = new AIGameModel(board);
        result = instance.getWinner();
        assertEquals(expResult, result);

        board = new GameBoard(7, 6);
        board.setCell(0, 2, Player.Blue);
        board.setCell(1, 3, Player.Blue);
        board.setCell(2, 4, Player.Blue);
        board.setCell(3, 5, Player.Blue);
        instance = new AIGameModel(board);
        result = instance.getWinner();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetBackDiagonalWinner() {
        System.out.println("testGetBackDiagonalWinner");
        GameBoard board = new GameBoard(7, 6);
        board.setCell(6, 0, Player.Blue);
        board.setCell(5, 1, Player.Blue);
        board.setCell(4, 2, Player.Blue);
        board.setCell(3, 3, Player.Blue);
        AIGameModel instance = new AIGameModel(board);
        Player expResult = Player.Blue;
        Player result = instance.getWinner();
        assertEquals(expResult, result);

        board = new GameBoard(7, 6);
        board.setCell(3, 1, Player.Blue);
        board.setCell(2, 2, Player.Blue);
        board.setCell(1, 3, Player.Blue);
        board.setCell(0, 4, Player.Blue);
        instance = new AIGameModel(board);
        result = instance.getWinner();
        assertEquals(expResult, result);

        board = new GameBoard(7, 6);
        board.setCell(4, 2, Player.Blue);
        board.setCell(3, 3, Player.Blue);
        board.setCell(2, 4, Player.Blue);
        board.setCell(1, 5, Player.Blue);
        instance = new AIGameModel(board);
        result = instance.getWinner();
        assertEquals(expResult, result);
    }

    @Test
    public void test4ColumnAccross1onTop(){
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(0, 1, Player.Blue);
        board.setCell(0, 2, Player.Red);
        board.setCell(0, 3, Player.Red);
        board.setCell(0, 4, Player.Red);
        board.setCell(0, 5, Player.Blue);

        board.setCell(1, 0, Player.Blue);
        board.setCell(1, 1, Player.Blue);
        board.setCell(1, 2, Player.Blue);

        AIGameModel instance = new AIGameModel(board);
        Player expResult = Player.None;
        Player result = instance.getWinner();
        assertEquals(expResult, result);
    }

    @Test
    public void test4ColumnAccross2onTop(){
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(0, 1, Player.Blue);
        board.setCell(0, 2, Player.Red);
        board.setCell(0, 3, Player.Red);
        board.setCell(0, 4, Player.Blue);
        board.setCell(0, 5, Player.Blue);

        board.setCell(1, 0, Player.Blue);
        board.setCell(1, 1, Player.Blue);

        AIGameModel instance = new AIGameModel(board);
        Player expResult = Player.None;
        Player result = instance.getWinner();
        assertEquals(expResult, result);
    }

    @Test
    public void test4ColumnAccross3onTop(){
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(0, 1, Player.Blue);
        board.setCell(0, 2, Player.Red);
        board.setCell(0, 3, Player.Blue);
        board.setCell(0, 4, Player.Blue);
        board.setCell(0, 5, Player.Blue);

        board.setCell(1, 0, Player.Blue);

        AIGameModel instance = new AIGameModel(board);
        Player expResult = Player.None;
        Player result = instance.getWinner();
        assertEquals(expResult, result);
    }

    @Test
    public void test5ColumnAccross2onTop(){
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(0, 1, Player.Blue);
        board.setCell(0, 2, Player.Red);
        board.setCell(0, 3, Player.Red);
        board.setCell(0, 4, Player.Blue);
        board.setCell(0, 5, Player.Blue);

        board.setCell(1, 0, Player.Blue);
        board.setCell(1, 1, Player.Blue);
        board.setCell(1, 2, Player.Blue);

        AIGameModel instance = new AIGameModel(board);
        Player expResult = Player.None;
        Player result = instance.getWinner();
        assertEquals(expResult, result);
    }

    @Test
    public void test5ColumnAccross3onTop(){
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(0, 1, Player.Blue);
        board.setCell(0, 2, Player.Red);
        board.setCell(0, 3, Player.Blue);
        board.setCell(0, 4, Player.Blue);
        board.setCell(0, 5, Player.Blue);

        board.setCell(1, 0, Player.Blue);
        board.setCell(1, 1, Player.Blue);
        AIGameModel instance = new AIGameModel(board);
        Player expResult = Player.None;
        Player result = instance.getWinner();
        assertEquals(expResult, result);
    }

    @Test
    public void test6ColumnAccross3onTop(){
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(0, 1, Player.Blue);
        board.setCell(0, 2, Player.Red);
        board.setCell(0, 3, Player.Blue);
        board.setCell(0, 4, Player.Blue);
        board.setCell(0, 5, Player.Blue);

        board.setCell(1, 0, Player.Blue);
        board.setCell(1, 1, Player.Blue);
        board.setCell(1, 2, Player.Blue);
        board.setCell(1, 3, Player.Red);
        board.setCell(1, 4, Player.Red);
        board.setCell(1, 5, Player.Red);

        board.setCell(2, 0, Player.Red);
        board.setCell(2, 1, Player.Red);
        board.setCell(2, 2, Player.Red);


        AIGameModel instance = new AIGameModel(board);
        Player expResult = Player.None;
        Player result = instance.getWinner();
        assertEquals(expResult, result);
    }

	@Test
	public void testMoveUnmove() {
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue);
        board.setCell(0, 1, Player.Blue);
        board.setCell(0, 2, Player.Red);
        board.setCell(0, 3, Player.Blue);
		AIGameModel instance = new AIGameModel(board);
		long expected = instance.getBlueBoard();
		Move move = new Move(3);
		instance.processMove(move);
		long moveProcessed = instance.getBlueBoard();
		instance.undoMove(move);
		long actual = instance.getBlueBoard();

		assertEquals(expected, actual);
		assertNotSame(expected, moveProcessed);
	}
 }
