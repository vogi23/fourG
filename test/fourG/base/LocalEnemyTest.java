/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourG.base;

import com.sun.prism.impl.PrismTrace;
import fourG.controlling.IGameControlUpdates;
import fourG.model.GameBoard;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author lts
 */
public class LocalEnemyTest {
    

    private IGameControlUpdates controlUpdates;

    public LocalEnemyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @Test
    public void winNextMoveHorizontally() {
        GameBoard board = createHorizontalWinNextBoard();
        controlUpdates = new TestGameControlUpdates(4);
        TestGameModelInformations gm = new TestGameModelInformations(board);
        LocalEnemy enemy = new LocalEnemy(gm, controlUpdates);
        Move dummyMove = new Move(1);
        enemy.receiveMove(dummyMove);
    }

    @Test
    public void preventDefeatHorizontally() {
        GameBoard board = createHorizontalDefeatBoard();
        controlUpdates = new TestGameControlUpdates(4);
        TestGameModelInformations gm = new TestGameModelInformations(board);
        LocalEnemy enemy = new LocalEnemy(gm, controlUpdates);
        Move dummyMove = new Move(1);
        enemy.receiveMove(dummyMove);
    }

    @Test
    public void preventDefeatVertically() {
        GameBoard board = createVerticalDefeatBoard();
        controlUpdates = new TestGameControlUpdates(5);
        TestGameModelInformations gm = new TestGameModelInformations(board);
        LocalEnemy enemy = new LocalEnemy(gm, controlUpdates);
        Move dummyMove = new Move(1);
        enemy.receiveMove(dummyMove);
    }

    @Test
    public void winIn2Moves() {
        GameBoard board = createWinIn2MovesBoard();
        controlUpdates = new TestGameControlUpdates(3);
        TestGameModelInformations gm = new TestGameModelInformations(board);
        LocalEnemy enemy = new LocalEnemy(gm, controlUpdates);
        Move dummyMove = new Move(1);
        enemy.receiveMove(dummyMove);
    }

    /**
     * Test of receiveMove method, of class LocalEnemy.
     */
    @Test
    public void testReceiveMove() {
        System.out.println("receiveMove");
        Move m = null;
        LocalEnemy instance = null;
        instance.receiveMove(m);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnemyName method, of class LocalEnemy.
     */
    @Test
    public void testGetEnemyName() {
        System.out.println("getEnemyName");
        LocalEnemy instance = null;
        String expResult = "";
        String result = instance.getEnemyName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private GameBoard createHorizontalDefeatBoard() {
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue); // own disc
        board.setCell(1, 0, Player.Red); // opponent disc
        board.setCell(2, 0, Player.Red); // opponent disc
        board.setCell(3, 0, Player.Red); // opponent disc
        board.setCell(4, 0, Player.None); // no disc
        board.setCell(5, 0, Player.Blue); // own disc

        return board;
    }

    private GameBoard createWinIn2MovesBoard() {
        GameBoard board = new GameBoard(7, 6);
        board.setCell(2, 0, Player.Blue); 
        board.setCell(4, 0, Player.Blue);
        board.setCell(6, 0, Player.Red);
        board.setCell(2, 1, Player.Red);
        board.setCell(4, 1, Player.Red);

        return board;
    }

    private GameBoard createVerticalDefeatBoard() {
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Blue); 
        board.setCell(5, 0, Player.Blue);
        board.setCell(5, 1, Player.Red);
        board.setCell(5, 2, Player.Red);
        board.setCell(5, 3, Player.Red);

        return board;
    }

    private GameBoard createHorizontalWinNextBoard() {
        GameBoard board = new GameBoard(7, 6);
        board.setCell(0, 0, Player.Red);
        board.setCell(4, 0, Player.Blue);
        board.setCell(4, 1, Player.Blue);
        board.setCell(4, 2, Player.Blue);
        board.setCell(3, 0, Player.Red);
        board.setCell(3, 1, Player.Red);

        return board;
    }
}
