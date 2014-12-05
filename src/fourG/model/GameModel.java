package fourG.model;


import fourG.base.Player;
import fourG.base.Move;
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
public class GameModel implements IGameModelModifications, IGameModelInformations {
    
    private transient HashSet<IModelObserver> observers;
    private ModelState state;
    GameBoard gameBoard;
    
    private Player nextPlayer;
    private Player currentPlayer;
    private Player winner;
    private int row;            //Zeile, waagerecht
    private int colum;          //Spalte senkrecht
    private int yCurrent;
    private int xCurrent;
    private final int winValue=4;
    //private GameBoard board;                  //CHANGE it
    private ArrayList<GameOffer> gameoffers;    //CHECK it
    
    public GameModel(){
        this(6,7,Player.Red);
    }
    public GameModel(int pWidth,int pHeight,Player pFirstPlayer){
        //observers = new ArrayList
        row=pWidth;
        colum=pHeight;
        gameBoard=new GameBoard(row,colum);
        nextPlayer=pFirstPlayer;
        observers = new HashSet<IModelObserver>();
        gameoffers = new ArrayList<GameOffer>();
        state = ModelState.Playing;                          //CHECK it
        winner=Player.None;
    }
    

    
    @Override
    public boolean processMove(Move m) {
        //returnVar, true if move is valid
        //Validate Move
        gameBoard.printArray();
        if(m==null){                                        //MoveObject empty
            return false;           
        }
        if(m.getPlayer()!=nextPlayer){                   //wrong Player
            return false;
        }
        if(gameBoard.getCell(m.getColumn(),colum-1)!=Player.None){  //Field used
            return false;
        }
        //-----Move is valid----
        //Get y Position (colum)
        int counter=0;
        while(gameBoard.getCell(m.getColumn(),counter)!=Player.None){
            counter++;
        }
        xCurrent=m.getColumn();
        yCurrent=counter;
        //insert Disc

        gameBoard.setCell(xCurrent, yCurrent, m.getPlayer());
        //change Member
        //ADD CODE HERE, change other Members like counter
        if(m.getPlayer()==Player.Red){
            nextPlayer=Player.Blue;
            currentPlayer=Player.Red;
        }
        else{
            nextPlayer=Player.Red;
            currentPlayer=Player.Blue;
        }
        return true;    
    }

    @Override
    public Move getLastMove() {
        Move dummy=new Move(xCurrent);
        dummy.setPlayer(currentPlayer);
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
            while(counter<winValue-1 && gameBoard.getCell(xCurrent,yCurrent-counterDown)==currentPlayer){
                counterDown++;
                counter++;
            }
            if(counter>=winValue-1){
                winner=currentPlayer;
                return true;        //y Win
            }
        }
        
        //check x
        counter=0;
        int counterLeft=1;
        int counterRight=1;
        while(counter<winValue-1 && 0<=xCurrent-counterLeft && gameBoard.getCell(xCurrent-counterLeft,yCurrent)==currentPlayer){
                counterLeft++;
                counter++;
        }
        while(counter<winValue-1 && row>xCurrent+counterRight && gameBoard.getCell(xCurrent+counterRight,yCurrent)==currentPlayer){
                counterRight++;
                counter++;
        }
        if(counter>=winValue-1){
                winner=currentPlayer;
                return true;        //x Win
        }
        
        //check diag (direction right)
        state=ModelState.GameOver;         //CHECK IT
        return false;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void addModelObserver(IModelObserver obs){
        observers.add(obs);
    }
    
    @Override
    public void addGameOffer(GameOffer o) {
        if(o == null){
            return;
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
