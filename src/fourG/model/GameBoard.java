/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.model;

import fourG.base.Move;
import fourG.base.Player;

/**
 *
 * @author vogi23
 */
public class GameBoard {
    
    private int x;          //y, Koordinate
    private int y;          //x Koordinate
    private Player [][] myPlayField;
    
    public GameBoard(int width, int height){
        this.x = width;
        this.y = height;
        initGameBoard();
    }
    
    private void initGameBoard(){
        //init Array
        myPlayField=new Player[x][y];
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                myPlayField[i][j]=Player.None;
            }
        }
    }
    
    public void setCell(int pX, int pY, Player color){
        myPlayField[pX][pY]=color;
    }
    
    public Player getCell(int pX, int pY){
        return myPlayField[pX][pY];
    }
    
    public int getWidth(){
        return x;
    }
    
    public int getHeight(){
        return y;
    }
    
    public boolean isMoveValid(Move move) {
        return myPlayField[move.getXPosition()][y-1] == Player.None;
    }
    
    public void printArray(){
        String shortPlayCol="";
         for(int i=y-1;i>=0;i--){
            for(int j=x-1;j>=0;j--){
                if(myPlayField[j][i]==Player.None){
                    shortPlayCol="[/]";
                }
                else if(myPlayField[j][i]==Player.Red){
                    shortPlayCol="[R]";
                }
                else if(myPlayField[j][i]==Player.Blue){
                    shortPlayCol="[B]";
                }
                System.out.print(shortPlayCol);
            }
         System.out.println();
        }
         System.out.println();
         System.out.println();
    }
}
