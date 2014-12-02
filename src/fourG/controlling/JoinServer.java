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

/**
 *
 * @author tgdvoch5
 */
public class JoinServer implements Runnable{
    
    private Thread thread = null;
    
    private final int port = 4241;
    private RemoteEnemy e;
    
    public JoinServer(RemoteEnemy e){
        this.e = e;
        this.start();
    }
    
    @Override
    public void run(){
        System.out.println("JoinServer:started");
        boolean enemyFound = false;
        try(ServerSocket listen = new ServerSocket(port)){
            while(!enemyFound){
                System.out.println("JoinServer: waiting for request");
                Socket client = listen.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String msg = in.readLine();
                
                System.out.println("JoinServer: received "+msg);
                
                String response = "NOGO";
                if(msg.equals("GO")){
                    response = "GO";
                    enemyFound = true;
                }
                System.out.println("JoinServer: Antwort "+response);
                out.println(response);
                out.flush();       
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
