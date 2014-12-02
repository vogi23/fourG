/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.model;

import java.net.InetAddress;

/**
 *
 * @author tgdvoch5
 */
public class GameOffer {
    
    private InetAddress adr;
    private int port;
    private int width;
    private int height;
    
    private String name;
    
    public GameOffer(InetAddress adr, int port){
        this.adr = adr;
        this.port = port;
        width = 5;
        height = 4;
        name = "Anonym";
    }
    
    public InetAddress getAddress(){
        return adr;
    }
    
    public int getPort(){
        return port;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String s){
        name = s;
    }
    
    public int getWidth(){
        return width;
    }
    public void setWidth(int i){
        width = i;
    }
    
    public int getHeight(){
        return height;
    }
    public void setHeight(int i){
        height = i;
    }
}
