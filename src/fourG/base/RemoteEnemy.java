package fourG.base;

import fourG.controlling.GameController;
import fourG.controlling.JoinServer;
import fourG.controlling.DiscoverServer;
import fourG.controlling.GameServer;
import fourG.model.GameModel;
import fourG.model.GameOffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static java.lang.System.in;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public class RemoteEnemy extends Enemy {
    
    private DiscoverServer dserver;
    private JoinServer jserver;
    private GameServer gserver;
    
    private InetAddress enemyAddr;
    private int enemySendsOnPort;    // TCP
    private int enemyReceivesOnPort; // TCP
    private int enemyJoinPort = 4241;       // TCP
    private int enemyDiscoverPort = 4240;   // UDP
    
    public final Object gameserverLoadingWait = new Object();
    public boolean gameserverLoaded = false;
    
    public RemoteEnemy(GameModel gamemodel, GameController gamecontroller){
        super(gamemodel, gamecontroller);
    }
    
    @Override
    public void killSockets(){
        if(dserver != null){
            dserver.interrupt();
        }
        
        if(jserver != null){
            jserver.interrupt();
        }
        
        if(gserver != null){
            gserver.interrupt();
        }
    }
    
    public int getReceivingPort(){
        return enemyReceivesOnPort;
    }
    
    
    /**
     * Send Move to remote Host for processing and reacting
     * 
     * @param m 
     */
    @Override
    public void receiveMove(Move m) {
        synchronized(getConsoleLockObject()){
            System.out.println("Try sending my Move over Network to enemy (Port "+enemySendsOnPort+")");
        }
        
        try(Socket client = new Socket(enemyAddr, enemySendsOnPort)){
            
            ObjectOutputStream moveOut = new ObjectOutputStream(client.getOutputStream());
            synchronized(getConsoleLockObject()){
                moveOut.writeObject(m);
                moveOut.flush();
                System.out.println("Move sent over network (Port: "+enemySendsOnPort+"): waiting for confirmation");
            }
            
            BufferedReader confirmation = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = confirmation.readLine();
            synchronized(getConsoleLockObject()){
                System.out.println("Enemy confirmation (move received) received. Msg: "+line);
                if(line.equals("MOVE_PROCESSED")){
                    System.out.println("Move sent and processed by enemy");
                }else{
                    System.err.println("Move could not be processed by enemy. msg: "+line);
                }
            }
        }catch(IOException ex){
            synchronized(getConsoleLockObject()){
                System.err.println("Enemy not reachable anymore");
            }
        }
    }
    
    /**
     * Discover serving remote hosts with a UDP Broadcast and forward resulting GameOffers via GameController.offerGame() to Model.
     * 
     * View will display avaiable GameOffers in GameModel
     */
    public void discoverEnemysOnNetwork(){
        try{
            DatagramSocket client = new DatagramSocket();
            client.setBroadcast(true);
            
            byte[] outData = "DISCOVER_FOURG_ENEMY".getBytes();
            
           
            
             // Broadcast the message over all the network interfaces
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
              NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

              if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
              }

                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null) {
                      continue;
                    }

                    // Send the broadcast package!
                    synchronized(getConsoleLockObject()){
                        try {
                          DatagramPacket sendPacket = new DatagramPacket(outData, outData.length, broadcast, enemyDiscoverPort);
                          client.send(sendPacket);
                        } catch (Exception e) {
                            System.err.println("remoteenemy.java "+e.getMessage());
                        }

                        System.out.println("JOINER Enemy-Discovery-Packet sent to: " + broadcast.getHostAddress() + ":" + enemyDiscoverPort+ "; Interface: " + networkInterface.getDisplayName());
                    }
                }
            }
            
            
            // Receive response
            while(true){
                byte[] recvBuf = new byte[15000];
                DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
                String message;
                client.receive(receivePacket);
                synchronized(getConsoleLockObject()){
                    System.out.println("JOINER Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
                
                    // Filter Message
                    message = new String(receivePacket.getData()).trim();
                    System.out.println("JOINER Broadcast response content: "+message);
                }
                if (message.equals("FOURG_ENEMY_HELLO")) {
                    // Add to available JoinServers List
                    gameC.offerGame(new GameOffer(receivePacket.getAddress(), enemyJoinPort));
                    break; // TESTING
                }
            }
        }catch(Exception e){
            synchronized(getConsoleLockObject()){
                System.err.println(e.getMessage());
            }
        }
    }
    
    /**
     * Setup UDP Server, who reacts on Enemy-Discover Msgs via UDP
     */
    public void listenForDiscoveryRequests(){
         dserver = new DiscoverServer(this); // Answering UDP Broadcast requests
    }
    
    /**
     * Setup TCP Server, who reacts on connection-trys from joining-enemys.
     * 
     * Calls enemyFound after connection to first-coming enemy is made.
     */
    public void listenForJoiningRequests(){
         enemySendsOnPort = 4242;
         enemyReceivesOnPort = 4243;
         gserver = new GameServer(gameC, this);
         
         jserver = new JoinServer(this);
    }
    
    /**
     * Saves connection settings (IP) for active remote enemy.
     * 
     * Settings will be reused to send Moves over Network.
     * 
     * Also diables Discovery- and Joining-Server
     * 
     * @param adr 
     */
    public void enemyFound(InetAddress adr) {
        if(dserver != null){
            dserver.setStopFlag(true);
        }
        enemyAddr = adr;
        
        synchronized(getConsoleLockObject()){
            System.out.println("remoteEnemy is ready For Moves!");
        }
        gameC.enemyReady();
    }
    
    /**
     * 
     * @param o 
     */
    public void connectToOnlineGame(GameOffer o){
        
        enemySendsOnPort = 4243;
        enemyReceivesOnPort = 4242;
        gserver = new GameServer(gameC, this);
        
        // Online searching Connect to Remote. Then call gamecontroller.enemyReady();
        
        try(Socket client = new Socket(o.getAddress(), 4241)){
            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            synchronized(gameserverLoadingWait){
                while(!gameserverLoaded){
                    try {
                        gameserverLoadingWait.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RemoteEnemy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            synchronized(getConsoleLockObject()){
                out.println("CAN_I_PLAY");
                out.flush();
                System.out.println("JOINER sent join req 'CAN_I_PLAY' to:"+o.getAddress());
            }
            
            String line = in.readLine();
            synchronized(getConsoleLockObject()){
                System.out.println("JOINER connection status: "+line);
            }
            
            if(line.equals("YES_LETS_PLAY")){
                enemyFound(o.getAddress()); 
            }
        }catch(IOException ex){
            synchronized(getConsoleLockObject()){
                System.out.println("JOINER  connection status: Server not avaiable anymore");
            }
        }
        
    }
}
