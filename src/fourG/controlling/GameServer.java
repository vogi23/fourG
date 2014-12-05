/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.controlling;

import fourG.base.Move;
import fourG.base.RemoteEnemy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vogi23
 */
public class GameServer implements Runnable{
    
    private Thread thread = null;
    
    private RemoteEnemy enemy;
    private IGameControlUpdates gameC;
    
    private boolean stopFlag = false;
    
    public GameServer(IGameControlUpdates gameC, RemoteEnemy e){
        this.gameC = gameC;
        this.enemy = e;
        this.start();
    }
    
    @Override
    public void run(){
        ServerSocket listen;
        try{
            synchronized(enemy.getConsoleLockObject()){
                listen = new ServerSocket(enemy.getReceivingPort());
                System.out.println("GameServer: started");
            }
            
            while(!stopFlag){
                synchronized(enemy.gameserverLoadingWait){
                    enemy.gameserverLoaded = true;
                    enemy.gameserverLoadingWait.notifyAll();
                }
                synchronized(enemy.getConsoleLockObject()){
                    System.out.println("GameServer: waiting for moves (Port "+enemy.getReceivingPort()+")");
                }
                Socket client = listen.accept();
                ObjectInputStream moveIn = new ObjectInputStream(client.getInputStream());
                PrintWriter confirmationOut = new PrintWriter(client.getOutputStream());
                Object m = new Object();
                try{
                     m = moveIn.readObject();
                }catch(ClassNotFoundException cnfe){
                    synchronized(enemy.getConsoleLockObject()){
                         System.out.println("GameServer: received unknown class object"+m.getClass());
                    }
                    continue;
                }finally{
                    if(m.getClass() != Move.class){
                        synchronized(enemy.getConsoleLockObject()){
                            confirmationOut.println("Remote GameServer: received object not of type MOVE");
                        }
                        continue;
                    }
                }
                
                synchronized(enemy.getConsoleLockObject()){
                    System.out.println("GameServer: received move: "+m);
                }
                try{
                    gameC.receiveMove((Move) m);
                }catch(InvalidMoveException ime){
                    confirmationOut.println("Remote GameServer: received MOVE could not be processed (invalid)");
                    confirmationOut.flush();
                    System.err.println("GameServer: received invalid move from enemy"+m);
                    continue;
                }
                
                synchronized(enemy.getConsoleLockObject()){
                    confirmationOut.println("MOVE_PROCESSED");
                    confirmationOut.flush();
                    System.out.println("GameServer: confirmed the receiving and successful processing of move"+m);
                }
            }
            
            listen.close();
        }catch(IOException ex){
            synchronized(enemy.getConsoleLockObject()){
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public void start(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }
}
