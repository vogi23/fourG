/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.controlling;

import fourG.base.RemoteEnemy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tgdvoch5
 */
public class JoinServer implements Runnable{
    
    private Thread thread = null;
    
    private final int port = 4241;
    private RemoteEnemy enemy;
    private ServerSocket listen;
    
    public JoinServer(RemoteEnemy e){
        this.enemy = e;
        this.start();
    }
    
    @Override
    public void run(){
        boolean enemyFound = false;
        try{
            synchronized(enemy.getConsoleLockObject()){
                listen = new ServerSocket(port);
                System.out.println("JoinServer:started");
            }
            while(!enemyFound){
                synchronized(enemy.getConsoleLockObject()){
                    System.out.println("JoinServer: waiting for request");
                }
                Socket client = listen.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String msg = in.readLine();
                
                String response = "NO_FUCK_YOU";
                if(msg.equals("CAN_I_PLAY")){
                    response = "YES_LETS_PLAY";
                    enemyFound = true;
                }
                
                if(enemyFound){
                    // Waiting for GameServer to startup completly
                    synchronized(enemy.gameserverLoadingWait){
                        while(!enemy.gameserverLoaded){
                            try {
                                enemy.gameserverLoadingWait.wait();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(JoinServer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                
                // Send message to joiner
                synchronized(enemy.getConsoleLockObject()){
                    System.out.println("JoinServer: received "+msg);
                    out.println(response);
                    out.flush();       
                    System.out.println("JoinServer: Antwort sent "+response);
                }
                
                if(!enemyFound){
                    continue;
                }
                
                enemy.enemyFound(client.getInetAddress());
            }
            
            listen.close();
        }catch(IOException ex){
            synchronized(enemy.getConsoleLockObject()){
                System.err.println("x2 "+ex.getMessage());
            }
        }
    }
    
    public void start(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }
    
    public void interrupt(){
        try {
            listen.close();
        } catch (IOException ex) {
            Logger.getLogger(JoinServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        thread.interrupt();
    }
}
