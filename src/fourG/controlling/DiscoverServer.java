/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.controlling;

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
    private DatagramSocket socket;
    
    private boolean stopFlag = false;
    
    public DiscoverServer(){
        this.start();
    }
    
    @Override
    public void run(){
        
         System.out.println("DiscoverServer started");
         try{
            socket = new DatagramSocket(port);
            socket.setBroadcast(true);
            
             
            while(!stopFlag){
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
                
                String message = new String(packet.getData()).trim();
                
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
        catch(Exception e){
            System.err.println(e.getMessage());
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
