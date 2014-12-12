/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.view;

import fourG.model.GameOffer;
import java.awt.Color;
import java.net.InetAddress;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author TGDVOCH5
 */
public class GameOfferPanel extends JPanel{
    private GameOffer go;
    private JLabel label;
            
    public GameOfferPanel(GameOffer go, int y){
        this.go = go;
        this.label = new JLabel();
        this.label.setText(go.getAddress().toString());
        this.add(label);
        
        setBounds(0, y*26,400, 25);
        
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.green);
        
        repaint();
    }
    
    public GameOffer getOffer(){
        return go;
    }
}
