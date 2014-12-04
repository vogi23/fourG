package fourG.base;

import fourG.controlling.GameController;
import fourG.controlling.JoinServer;
import fourG.controlling.DiscoverServer;
import fourG.model.GameModel;
import fourG.model.GameOffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;

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
    
    private InetAddress enemyAddr;
    private int enemyGamePort = 4242;       // TCP
    private int enemyJoinPort = 4241;       // TCP
    private int enemyDiscoverPort = 4240;   // UDP
    
    public RemoteEnemy(GameModel gamemodel, GameController gamecontroller){
        super(gamemodel, gamecontroller);
        
    }
    
    /**
     * Send Move to remote Host for processing and reacting
     * 
     * @param m 
     */
    @Override
    public void receiveMove(Move m) {
        throw new UnsupportedOperationException("Not supported yet.2"); //To change body of generated methods, choose Tools | Templates.
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
                try {
                  DatagramPacket sendPacket = new DatagramPacket(outData, outData.length, broadcast, enemyDiscoverPort);
                  client.send(sendPacket);
                } catch (Exception e) {
                    System.err.println("remoteenemy.java "+e.getMessage());
                }
                
                System.out.println("JOINER Enemy-Discovery-Packet sent to: " + broadcast.getHostAddress() + ":" + enemyDiscoverPort+ "; Interface: " + networkInterface.getDisplayName());
              }
            }
            
            
            // Receive response
            while(true){
                byte[] recvBuf = new byte[15000];
                DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
                client.receive(receivePacket);
                System.out.println("JOINER Broadcast response from server: " + receivePacket.getAddress().getHostAddress());

                // Filter Message
                String message = new String(receivePacket.getData()).trim();
                System.out.println("JOINER Broadcast response content: "+message);
                if (message.equals("FOURG_ENEMY_HELLO")) {
                    // Add to available JoinServers List
                    //gameC.offerGame(new GameOffer(receivePacket.getAddress(), enemyJoinPort));
                }
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Setup UDP Server, who reacts on Enemy-Discover Msgs via UDP
     */
    public void listenForDiscoveryRequests(){
         dserver = new DiscoverServer(); // Answering UDP Broadcast requests
    }
    
    /**
     * Setup TCP Server, who reacts on connection-trys from joining-enemys.
     * 
     * Calls enemyFound after connection to first-coming enemy is made.
     */
    public void listenForJoiningRequests(){
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
     * @param port 
     */
    public void enemyFound(InetAddress adr){
        dserver.setStopFlag(true);
        enemyAddr = adr;
    }
    
    /**
     * 
     * @param o 
     */
    public void connectToOnlineGame(GameOffer o){
        // Online searching Connect to Remote. Then call gamecontroller.enemyReady();
        
        System.out.println("JOINER try to connect to online game");
        try(Socket client = new Socket(o.getAddress(), 4241)){
            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out.println("CAN_I_PLAY");
            out.flush();
            String line = in.readLine();
            System.out.println("JOINER connection status: "+line);
            if(line.equals("YES_LETS_PLAY_ON_PORT_4242")){
                enemyAddr = o.getAddress();
                gameC.enemyReady();
                System.out.println("JOINER enemyReady!");
            }else{
                System.out.println("JOINER Server refused my join!");
            }
        }catch(IOException ex){
            System.out.println("JOINER connection status: Server not avaiable anymore");
        
        }
        
    }
}
