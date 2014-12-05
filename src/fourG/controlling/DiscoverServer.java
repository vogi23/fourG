/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.controlling;

import fourG.base.RemoteEnemy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author tgdvoch5
 */
public class DiscoverServer implements Runnable{
    
    private Thread thread = null;
    
    private final int port = 4240;
    private RemoteEnemy enemy;
    private DatagramSocket socket;
    
    private boolean stopFlag = false;
    
    public DiscoverServer(RemoteEnemy e){
        this.start();
        this.enemy = e;
    }
    
    @Override
    public void run(){
        
         try{
            synchronized(enemy.getConsoleLockObject()){
                socket = new DatagramSocket(port);
                System.out.println("DiscoverServer started");
            }
            socket.setBroadcast(true);
            
             
            while(!stopFlag){
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                synchronized(enemy.getConsoleLockObject()){
                    System.out.println("DiscoverServer waiting for packets");
                }
                socket.receive(packet);
                
                String message = new String(packet.getData()).trim();
                
                synchronized(enemy.getConsoleLockObject()){
                    System.out.println("DiscoverServer Packet received! "+message);
                    if(message.equals("DISCOVER_FOURG_ENEMY")){
                        byte[] sendBuf = "FOURG_ENEMY_HELLO".getBytes();
                        DatagramPacket sendpacket = new DatagramPacket(sendBuf, sendBuf.length, packet.getAddress(), packet.getPort());
                        socket.send(sendpacket);
                        System.out.println("DiscoverServer Packet beantwortet");

                    }else{
                        System.out.println("DiscoverServer Packet ungültig");
                    }
                }
            }
            
            socket.close();
        }
        catch(Exception e){
            synchronized(enemy.getConsoleLockObject()){
                System.err.println(e.getMessage());
            }
        }
    }
    
    public void setStopFlag(boolean b){
         stopFlag = b;
    }
    
    public void start(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }
}
