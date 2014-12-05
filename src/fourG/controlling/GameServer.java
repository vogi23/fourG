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

/**
 *
 * @author vogi23
 */
public class GameServer implements Runnable{
    
    private Thread thread = null;
    
    private final int port = 4241;
    private RemoteEnemy enemy;
    private IGameControlUpdates gameC;
    
    public GameServer(IGameControlUpdates gameC, RemoteEnemy e){
        this.gameC = gameC;
        this.enemy = e;
        this.start();
    }
    
    @Override
    public void run(){
        System.out.println("GameServer:started");
        try(ServerSocket listen = new ServerSocket(4242)){
            while(true){
                System.out.println("GameServer: waiting for moves");
                Socket client = listen.accept();
                ObjectInputStream moveIn = new ObjectInputStream(client.getInputStream());
                PrintWriter confirmationOut = new PrintWriter(client.getOutputStream());
                Object m = new Object();
                try{
                     m = moveIn.readObject();
                }catch(ClassNotFoundException cnfe){
                    System.out.println("GameServer: received unknown class object"+m.getClass());
                    continue;
                }finally{
                    if(m.getClass() != Move.class){
                        confirmationOut.println("Remote GameServer: received object not of type MOVE");
                        continue;
                    }
                }
                
                // TESTING:
                System.out.println("GameServer: received move: "+m);
                
                /*try{
                    gameC.receiveMove((Move) m);
                }catch(InvalidMoveException ime){
                    System.out.println("GameServer: received invalid move from enemy"+m);
                    confirmationOut.println("Remote GameServer: received MOVE could not be processed (invalid)");
                    continue;
                }
                */
                confirmationOut.println("MOVE_PROCESSED");
                confirmationOut.flush();
                System.out.println("GameServer: confirmed receiving and successful processing of move"+m);
            }
        }catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public void start(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }
}
