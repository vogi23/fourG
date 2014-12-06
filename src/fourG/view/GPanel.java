/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourG.view;

import fourG.controlling.GameController;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 *
 * @author Ramon
 */
public class GPanel extends JPanel {

    private GameController game;

    public GPanel(int gamePanelWidth, int gamePanelHeight, GameController game) {
        this.game = game;
        setSize(new Dimension(gamePanelWidth, gamePanelHeight));

    }

    

    public void drawStuf() {

    }

   // @Override
    //public void paint(Graphics g) {
    //drawBackground();
    //  }
}
