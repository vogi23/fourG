/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourG.model;

import fourG.base.Move;
import fourG.base.Player;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class GameModelTest {
    
    public GameModelTest() {

    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    @Test
    public void testHorizontal(){
        System.out.println("processMove");
        Move mR = new Move(5);
        mR.setPlayer(Player.Red);
        Move mB = new Move(4);
        mB.setPlayer(Player.Blue);
        GameModel myGame = new GameModel();
        boolean expResult = true;
        boolean result;
        
        for(int i=0;i<3;i++){

            checkMove(myGame,mR);
            checkWinner(myGame,false);
            mB = new Move(i);
            mB.setPlayer(Player.Blue);
            checkMove(myGame,mB);
            checkWinner(myGame,false);
        }
        mR = new Move(4);
        mR.setPlayer(Player.Red);
        checkMove(myGame,mR);
        checkWinner(myGame,false);
        mB = new Move(3);
        mB.setPlayer(Player.Blue);
        checkMove(myGame,mB);
        checkWinner(myGame,true);
        checkMove(myGame,mR);
        checkWinner(myGame,false);
    }
    
    public void checkMove(GameModel myGame, Move m){
        boolean expResult=true;
        boolean result= myGame.processMove(m);
        assertEquals(expResult, result);
        if(expResult!=result){
            fail("Move Red konnte nicht eingefügt werden");
        }
        
    }
    
    public void checkWinner(GameModel myGame,boolean expResult){
        boolean result= myGame.isGameover();
        assertEquals(expResult, result);
        if(expResult!=result){
            fail("Move Red konnte nicht eingefügt werden");
        }
    }
   /* @Test
    public void testProcessMove() {
        System.out.println("processMove");
        Move mR = new Move(3,Player.Red,0);
        Move mB = new Move(3,Player.Blue,0);
        GameModel instance = new GameModel();
        boolean expResult = true;
        boolean result = instance.processMove(mR);
        assertEquals(expResult, result);
        fail("Move Red (spez Methode)konnte nicht eingefühgt werden");
        
        result = instance.processMove(mR);
        assertEquals(expResult, result);
        fail("Move Blue (spez Methode) konnte nicht eingefühgt werden");
    }
*/

   /* @Test
    public void testGetLastMove() {
        System.out.println("getLastMove");
        GameModel instance = new GameModel();
        Move expResult = null;
        Move result = instance.getLastMove();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }*/

   /* @Test
    public void testGetBoard() {
        System.out.println("getBoard");
        GameModel instance = new GameModel();
        GameBoard expResult = null;
        GameBoard result = instance.getBoard();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }*/

   /* @Test
    public void testIsGameover() {
        System.out.println("isGameover");
        GameModel instance = new GameModel();
        boolean expResult = false;
        boolean result = instance.isGameover();
        assertEquals(expResult, result);
        fail("Es ist Gameover.");
    }
*/
   /* @Test
    public void testGetWinner() {
        System.out.println("getWinner");
        GameModel instance = new GameModel();
        Player expResult = null;
        Player result = instance.getWinner();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }*/
/*
    @Test
    public void testGetCurrentPlayer() {
        System.out.println("getCurrentPlayer");
        GameModel instance = new GameModel();
        Player expResult = null;
        Player result = instance.getCurrentPlayer();
        assertEquals(expResult, result);
        fail("Der falsche Player");
    }
*/
    /*@Test
    public void testAddModelObserver() {
        System.out.println("addModelObserver");
        IModelObserver obs = null;
        GameModel instance = new GameModel();
        instance.addModelObserver(obs);
        fail("The test case is a prototype.");
    }*/

   /* @Test
    public void testAddGameOffer() {
        System.out.println("addGameOffer");
        GameOffer o = null;
        GameModel instance = new GameModel();
        instance.addGameOffer(o);
        fail("The test case is a prototype.");
    }*/

   /* @Test
    public void testClearGameOffers() {
        System.out.println("clearGameOffers");
        GameModel instance = new GameModel();
        instance.clearGameOffers();
        fail("The test case is a prototype.");
    }*/

   /* @Test
    public void testGetGameOffers() {
        System.out.println("getGameOffers");
        GameModel instance = new GameModel();
        ArrayList<GameOffer> expResult = null;
        ArrayList<GameOffer> result = instance.getGameOffers();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
*/
  /*  @Test
    public void testGetState() {
        System.out.println("getState");
        GameModel instance = new GameModel();
        ModelState expResult = null;
        ModelState result = instance.getState();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }*/

  /*  @Test
    public void testSetState() {
        System.out.println("setState");
        ModelState s = null;
        GameModel instance = new GameModel();
        instance.setState(s);
        fail("The test case is a prototype.");
    }*/
    
}
