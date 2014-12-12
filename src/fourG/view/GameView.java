package fourG.view;

import fourG.base.Move;
import fourG.controlling.MgmtController;
import fourG.controlling.GameController;
import fourG.model.GameOffer;
import fourG.model.IGameModelInformations;
import fourG.model.IModelObserver;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.awt.Graphics;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.Iterator;
>>>>>>> e8e7183ad288ef80a1cea5616b3c0bab630d24c7

/**
 * To-Do-List: - Farbe des Spielers auslesen - yPosition des Moves richtig
 * einzeichnen - Hintergrund bei Erstellung des Interfaces einrichten - Update
 * implementieren - Spielfeld skaliert mit Faktor 100 --> Anpassung der
 * Spielfeldgrösse damit theoretisch möglich --> Rundungsfehler bei Typecasting
 * zu int für Array genutzt
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vogi23
 */
public class GameView extends JFrame implements IModelObserver {

    private MgmtController mgmt;
    private GameController game;
    private IGameModelInformations model;
    private int gamePanelHeight = 599;
    private int gamePanelWidth = 699;

    JFrame gui;
    JPanel informationPanel = new JPanel();
    JLabel yourName = new JLabel("I'm The Hero");
    JLabel opponentName = new JLabel("Looser");
    JLabel yourIP = new JLabel("Loaclhost");
    JLabel opponentIP = new JLabel("256.256.256.256");
    JPanel gamePanel = new JPanel();
    JPanel fourGInterface;
    JPanel availableServers;
    
    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    JMenuItem menuFileNewLocal = new JMenuItem("New Local Game");
    JMenuItem menuFileNewOnline = new JMenuItem("New Online Game");
    JMenuItem menuFileJoinOnline = new JMenuItem("Join Online Game");
    JMenuItem menuFileSave = new JMenuItem("Save Game");
    JMenuItem menuFileLoadLocal = new JMenuItem("Load Local Game");
    JMenuItem menuFileExit = new JMenuItem("Exit");
    JMenu menuOptions = new JMenu("Options");
    JMenuItem setOptions = new JMenuItem("Set Options");
    JFileChooser loadedGameChooser = new JFileChooser();
    JFileChooser saveGameChooser = new JFileChooser();

    public GameView(MgmtController mgmt, GameController game, IGameModelInformations model) {
        super("fourG");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.mgmt = mgmt;
        this.game = game;
        this.model = model;

        createMenu();
        fourGInterface = new JPanel();
        createInterface();
        pack();
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawInterface(); //To change body of generated methods, choose Tools | Templates.
    }

    private void createMenu() {
        menuFile.add(menuFileNewLocal);
        menuFileNewLocal.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mgmt.initLocalGame();
                    }
                });
        menuFile.addSeparator();
        menuFile.add(menuFileNewOnline);
        menuFileNewOnline.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mgmt.initCreateOnlineGame();
                    }
                });
        menuFile.addSeparator();
        menuFile.add(menuFileJoinOnline);
        menuFileJoinOnline.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mgmt.initJoinOnlineGame();
                    }
                });
        menuFile.addSeparator();
        menuFile.add(menuFileSave);
        menuFileSave.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mgmt.initSaveGame();
                    }
                });
        menuFile.addSeparator();
        menuFile.add(menuFileLoadLocal);
        menuFileLoadLocal.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String fileName = getFileName();
                        mgmt.initLoadedGame(new File(fileName));
                    }
                });
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuFileExit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onFileExit();
                    }
                });
        menuOptions.add(setOptions);
        menuBar.add(menuFile);
        menuBar.add(menuOptions);
        setJMenuBar(menuBar);
    }

    private void createInterface() {
        setLayout(new GridLayout(1, 2));
        add(informationPanel);
        informationPanel.setLayout(null);
        informationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        yourName.setBounds(5, 5, 400, 25);
        yourIP.setBounds(5, 35, 400, 25);
        opponentName.setBounds(5, 65, 400, 25);
        opponentIP.setBounds(5, 95, 400, 25);
        informationPanel.add(yourName);
        informationPanel.add(yourIP);
        informationPanel.add(opponentName);
        informationPanel.add(opponentIP);
        
        // Panel for searching servers
        this.availableServers = new JPanel();
        availableServers.setBounds(30, 200, 500, 300);
        availableServers.setBackground(Color.red);
        informationPanel.add(availableServers);
        
        
        add(gamePanel);
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        gamePanel.add(fourGInterface, BorderLayout.CENTER);
        fourGInterface.setPreferredSize(new Dimension(gamePanelWidth, gamePanelHeight));
        fourGInterface.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));
        fourGInterface.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int xPosition = e.getX();
                        int yPosition = e.getY();
                        calculateRow(xPosition);
                    }
                });
    }

    private void onFileExit() {
        System.exit(10);
    }

    private String getFileName() {
        int r = loadedGameChooser.showOpenDialog(this);
        String s = "no File!";
        if (r == JFileChooser.APPROVE_OPTION) {
            s = loadedGameChooser.getSelectedFile().getName();
        }
        return s;
    }

    private int calculateRow(int xPosition) {
        int xCurrent = xPosition / 100;
        game.makeMove(new Move(xCurrent));
        return xCurrent;
    }

    private void drawCircle() {
        Graphics g = fourGInterface.getGraphics();
        Move lastMove = model.getLastMove();
        g.setColor(lastMove.getPlayerColor());
        System.out.println("GUI.drawCircle "+lastMove);
        int xPos = (lastMove.getXPosition()*100);
        int yPos = (lastMove.getYPosition()*100);           
        g.drawOval(xPos + 25, yPos + 25, 50, 50);
        g.fillOval(xPos + 25, yPos + 25, 50, 50);
    }

    private void drawInterface() {
        Graphics g = fourGInterface.getGraphics();
        g.setColor(Color.blue);
        for (int i = 0; i <= 7; i++) {
            int yPos = (i * 100);
            g.drawLine(0, yPos, 700, yPos);
        }
        for (int j = 0; j <= 6; j++) {
            int xPos = (j * 100);
            g.drawLine(xPos, 0, xPos, 600);
        }
    }

    @Override
    public void update() {
        // Move lastMove; // For drawCircle
        switch(model.getState()){
            case Playing :
                drawCircle();
            break;
            case SearchOnlineGames :
                System.out.println("printgameoffers");
                printGameOffers();
            break;
        }
        
    }
    
    private void printGameOffers(){
        clearGameOffer(); 
        ArrayList<GameOffer> gameoffers = model.getGameOffers();
        Iterator<GameOffer> i = gameoffers.iterator();
        while(i.hasNext()){
            GameOffer go = i.next();
            GameOfferPanel offer;
            synchronized(availableServers.getTreeLock()){
                offer = new GameOfferPanel(go, availableServers.getComponentCount());
            }
            availableServers.add(offer);
            offer.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    GameOffer o = ((GameOfferPanel) e.getSource()).getOffer();
                    game.joinGame(o);
                    System.out.println("join");
                }
            });
            
            
            repaint();
        }
    }
    
    private void clearGameOffer(){
        availableServers.removeAll();
    }
}
