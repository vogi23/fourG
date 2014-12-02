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
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vogi23
 */
public class RemoteEnemy extends Enemy implements IEnemy {
    
    private DiscoverServer dserver;
    private JoinServer jserver;
    
    private InetAddress enemyAddr;
    private int enemyPort;
    
    public RemoteEnemy(GameModel gamemodel, GameController gamecontroller){
        super(gamemodel, gamecontroller);
        
    }
    
    /**
     * Send Move to remote Host
     * 
     * @param m 
     */
    @Override
    public void receiveMove(Move m) {
        throw new UnsupportedOperationException("Not supported yet.2"); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void searchEnemys(){
        try(DatagramSocket c = new DatagramSocket()) {
            c.setBroadcast(true);
            
            byte[] sendData = "DISCOVER_FOURG_SERVER".getBytes();

            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 4241);
                c.send(sendPacket);
                System.out.println(">>> Request packet sent to: 255.255.255.255 (DEFAULT)");

            } catch (Exception e) {
                 System.err.println(e.getMessage());
            }
            System.out.println("discover sent");
            
            //Wait for a response
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);
            System.out.println(">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
            
            String message = new String(receivePacket.getData()).trim();
            System.out.println("MSG from Server: "+message);
            if (message.equals("DISCOVER_FOURG_SERVER_RESPONSE")) {
                // Add to available JoinServers List
                System.out.println("Do you want to Join a fourG Game on: "+receivePacket.getAddress()+receivePacket.getPort());
                gameC.offerGame(new GameOffer(receivePacket.getAddress(), 4241));
                
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public void listenUDP(){
         dserver = new DiscoverServer(); // Answering UDP Broadcast requests
    }
    
    public void listenTCP(){
         jserver = new JoinServer(this);
    }
    
    public void enemyFound(InetAddress adr, int port){
        dserver.setStopFlag(true);
        
    }
    
    public void connect(GameOffer o){
        // Connect to Remote. Then call gamecontroller.enemyReady();
        enemyAddr = o.getAddress();
        
        System.out.println("Sucher: connect");
        try(Socket client = new Socket(enemyAddr, 4241)){
            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out.println("GO");
            out.flush();
            String line;
            System.out.println("Sucher: wait");
            while((line = in.readLine()) != null){
                
            }
            System.out.println("Sucher: response");
        }catch(IOException ex){
        
        }
        
        System.out.println("Sucher: enemyReady");
        gameC.enemyReady();
    }
}
