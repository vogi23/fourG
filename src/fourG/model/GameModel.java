package fourG.model;


import fourG.base.Player;
import fourG.base.Move;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tgdvoch5
 */
public class GameModel implements Serializable, IGameModelModifications, IGameModelInformations {
    
    //Playdata
    private Player nextPlayer;
    private Player lastPlayer;
    private Player winner;
    private int yCurrent;
    private int xCurrent;
    private int moveCounter;
    private int maxInserts;
    
    //Size of gameBoard and 
    private int row;            //Zeile, waagerecht
    private int colum;          //Spalte senkrecht
    private final int winValue=4;
    
    //Classes
    private transient HashSet<IModelObserver> observers;
    private transient ArrayList<GameOffer> gameoffers;
    private ModelState state;
    private GameBoard gameBoard;
    
    
    
    public GameModel(){
        this(7,6);              //Standart size
    }
    public GameModel(int pWidth,int pHeight){

        //Init gameBoardData
        row=pWidth;
        colum=pHeight;
        
        //Init Playdata
        nextPlayer=Player.Red;
        lastPlayer=Player.None;
        winner=Player.None;
        yCurrent=-1;
        xCurrent=-1;
        moveCounter=0;
        maxInserts=pWidth*pHeight;
        //Init Objects
        observers = new HashSet<IModelObserver>();
        gameoffers = new ArrayList<GameOffer>();
        state = ModelState.Home;                          //CHECK it
        gameBoard=new GameBoard(row,colum);
    }
     
    @Override
    public boolean processMove(Move m) {                //returnVar, true if move is valid
        //Validate Move
        gameBoard.printArray();
        if(m==null){                                     //MoveObject empty
            return false;           
        }
        if(m.getPlayer()!=nextPlayer){                   //wrong Player
            return false;
        }
        if(row<=m.getXPosition() || m.getXPosition()<0){
            return false;
        }
        if(gameBoard.getCell(m.getXPosition(),colum-1)!=Player.None){  //Field used
            return false;
        }
        if(state!=ModelState.Playing){
            if(state==ModelState.GameOver){
                System.out.println("Player "+lastPlayer+" hat bereits Gewonnen!");
            }
            else{
                System.out.println("Der State ist nicht Playing, er ist: "+state);
            }
            return false;
        }
        //-----Move is valid----
        //Get y Position (colum)
        int counter=0;
        while(gameBoard.getCell(m.getXPosition(),counter)!=Player.None){
            counter++;
        }
        xCurrent=m.getXPosition();
        yCurrent=counter;
        //insert Disc

        gameBoard.setCell(xCurrent, yCurrent, m.getPlayer());
        //change Member
        // change Members like counter
        moveCounter++;
        if(m.getPlayer()==Player.Red){
            nextPlayer=Player.Blue;
            lastPlayer=Player.Red;
        }
        else{
            nextPlayer=Player.Red;
            lastPlayer=Player.Blue;
        }
        broadcastUpdate();
        if (isGameover()){
            System.out.println("Der Player "+lastPlayer+" hat gewonnen!------" );
            
        }
        return true;    
    }

    @Override
    public Move getLastMove() {
        Move dummy=new Move(xCurrent);
        dummy.setPlayer(lastPlayer);
        dummy.setYPosition(colum-(yCurrent+1));
        return dummy;
    }

    @Override
    public GameBoard getBoard() {
        return gameBoard;
    }
    
@Override
    public boolean isGameover() {
        //false not Gameover
        int counter=0;
        int counterDown=1;
        //check y
        if(yCurrent>=winValue-1){
            while(counter<winValue-1 && gameBoard.getCell(xCurrent,yCurrent-counterDown)==lastPlayer){
                counterDown++;
                counter++;
            }
            if(counter>=winValue-1){
                winner=lastPlayer;
                state=ModelState.GameOver;  
                return true;        //y Win
            }
        }
        
        //check x
        counter=0;
        int counterLeft=1;
        int counterRight=1;
        while(counter<winValue-1 && 0<=xCurrent-counterLeft && gameBoard.getCell(xCurrent-counterLeft,yCurrent)==lastPlayer){
                counterLeft++;
                counter++;
        }
        while(counter<winValue-1 && row>xCurrent+counterRight && gameBoard.getCell(xCurrent+counterRight,yCurrent)==lastPlayer){
                counterRight++;
                counter++;
        }
        if(counter>=winValue-1){
                winner=lastPlayer;
                state=ModelState.GameOver;  
                return true;        //x Win
        }
        
        //check diag (direction rightUp to leftDown)   
        counter=0;
        counterLeft=1;
        counterRight=1;
        while(counter<winValue-1 && 0<=xCurrent-counterLeft && colum>yCurrent+counterLeft && gameBoard.getCell(xCurrent-counterLeft,yCurrent+counterLeft)==lastPlayer){
                counterLeft++;
                counter++;
        }
        while(counter<winValue-1 && row>xCurrent+counterRight && 0<=yCurrent-counterRight && gameBoard.getCell(xCurrent+counterRight,yCurrent-counterRight)==lastPlayer){
                counterRight++;
                counter++;
        }
        if(counter>=winValue-1){
                winner=lastPlayer;
                state=ModelState.GameOver;  
                return true;        //x Win
        }
        
        //check diag (direction leftUp to rigthDown)   
        counter=0;
        counterLeft=1;
        counterRight=1;
        while(counter<winValue-1 && 0<=xCurrent-counterLeft && 0<=yCurrent-counterLeft && gameBoard.getCell(xCurrent-counterLeft,yCurrent-counterLeft)==lastPlayer){
                counterLeft++;
                counter++;
        }
        while(counter<winValue-1 && row>xCurrent+counterRight && colum>yCurrent+counterRight && gameBoard.getCell(xCurrent+counterRight,yCurrent+counterRight)==lastPlayer){
                counterRight++;
                counter++;
        }
        if(counter>=winValue-1){
                winner=lastPlayer;
                state=ModelState.GameOver;  
                return true;        //x Win
        }
        //--------No Winner--------
        //check draw
        if(moveCounter>=maxInserts){
                winner=Player.Draw;
                state=ModelState.GameOver;  
                return true;        //x Win
        }
        return false;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public Player getCurrentPlayer() {
        return nextPlayer;
    }
    
    public void addModelObserver(IModelObserver obs){
        if(observers == null){
            observers = new HashSet<>();
        }
        observers.add(obs);
    }
    
    @Override
    public void addGameOffer(GameOffer o) {
        if(o == null){
            return;
        }
        if(gameoffers == null){
            gameoffers = new ArrayList<>();
        }
        gameoffers.add(o);
        broadcastUpdate();
    }
    
    @Override
    public void clearGameOffers() {
        gameoffers.clear();
        broadcastUpdate();
    }
    
    @Override
    public ArrayList<GameOffer> getGameOffers() {
        return gameoffers;
    }

    @Override
    public ModelState getState() {
        return state;
    }
    
    @Override
    public void setState(ModelState s) {
        if(state == s){
            // State did NOT change
            return;
        }
        state = s;
        broadcastUpdate();
    }
    
    /**
     * Informiert alle IModelObservers (bsp. GameView), dass das GameModel aktualisiert wurde.
     */
    private void broadcastUpdate(){
        for(IModelObserver o : observers){
            o.update();
        }
    }
}
