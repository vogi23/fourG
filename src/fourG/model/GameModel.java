package fourG.model;


import fourG.base.Player;
import fourG.base.Move;
import java.util.ArrayList;

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
    
    //private transient ArrayList<IModelObserver> observers;

    private Player [][] myPlayField;
    private Player nextPlayer;
    private Player currentPlayer;
    private int row;            //Zeile, waagerecht
    private int colum;          //Spalte senkrecht
    private int yCurrent;
    private int xCurrent;
    private final int winValue=4;
    
    public GameModel(){
        this(6,7,Player.Red);
    }
    
    public GameModel(int pWidth,int pHeight,Player pFirstPlayer){
        //observers = new ArrayList
        row=pWidth;
        colum=pHeight;
        myPlayField=new Player[row][colum];
        nextPlayer=pFirstPlayer;
        //init Array
        for(int i=0;i<pWidth;i++){
            for(int j=0;j<pHeight;j++){
                myPlayField[i][j]=Player.None;
            }
        }
    }
    
    @Override
    public boolean processMove(Move m) {
        //returnVar, true if move is valid
        //Validate Move
        if(m==null){                                        //MoveObject empty
            return false;           
        }
        if(m.getPlayer()!=nextPlayer){                   //wrong Player
            return false;
        }
        if(myPlayField[m.getRow()][colum-1]!=Player.None){  //Field used
            return false;
        }
        //-----Move is valid----
        //Get y Position (colum)
        int counter=0;
        while(myPlayField[m.getRow()][counter]!=Player.None){
            counter++;
        }
        xCurrent=m.getRow();
        yCurrent=counter;
        //insert Disc
        myPlayField[xCurrent][yCurrent]=m.getPlayer();
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
    public boolean isGameover() {
        //false not Gameover
        //check y
        if(yCurrent>winValue-1){
            int counter=0;
            while(counter<winValue && myPlayField[xCurrent][yCurrent-counter]==currentPlayer){
                counter++;
            }
            if(counter>=winValue){
                return true;        //y Win
            }
        }
        
        //check x
           /* if(yCurrent>winValue-1){
            int leftCounter=0;
            while(counter<winValue && myPlayField[xCurrent][yCurrent-counter]==currentPlayer){
                counter++;
            }
            if(counter>=winValue){
                return true;        //y Win
            
        }*/
        return false;
    }

    @Override
    public Player getWinner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void addModelObserver(IModelObserver obs){
        
    }
    
    
    /**
     * Informiert alle IModelObservers (bsp. GameView), dass das GameModel geupdated wurde.
     */
   /* private void broadcastUpdate(){
        for(IModelObserver o : observers){
            o.update();
        }
    }
    */
    /*private void insertDisc(Player pPlayer){
        
    }*/
}
