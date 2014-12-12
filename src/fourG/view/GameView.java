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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

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
    JPanel gamePanel = new JPanel();
    JPanel fourGInterface = new JPanel();
    JPanel availableServers;
    JLabel yourName = new JLabel();
    JLabel opponentName = new JLabel();
    JLabel whoIsPlaying = new JLabel();
    JLabel yourColor = new JLabel();

    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    JMenuItem menuFileNewLocal = new JMenuItem("New Local Game");
    JMenuItem menuFileNewLocalRandom = new JMenuItem("Game against Computer: Easy");
    JMenuItem menuFileNewLocalKI = new JMenuItem("Game against Computer: Hard");
    JMenuItem menuFileNewOnline = new JMenuItem("New Online Game");
    JMenuItem menuFileJoinOnline = new JMenuItem("Join Online Game");
    JMenuItem menuFileSave = new JMenuItem("Save Game");
    JMenuItem menuFileLoadLocal = new JMenuItem("Load Local Game");
    JMenuItem menuFileExit = new JMenuItem("Exit");
    JFileChooser loadedGameChooser = new JFileChooser();
    JFileChooser saveGameChooser = new JFileChooser();

    public GameView(MgmtController mgmt, GameController game, IGameModelInformations model) {
        super("fourG");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.mgmt = mgmt;
        this.game = game;
        this.model = model;

        createMenu();
        createInterface();
        pack();
        setVisible(true);
        setResizable(false);
        insertOwnInformation();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawInterface(); //To change body of generated methods, choose Tools | Templates.
    }

    private void createMenu() {
        menuFile.add(menuFileNewLocalKI);
        menuFile.addSeparator();
        menuFileNewLocalKI.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mgmt.initLocalGame(true);
                    }
                });
        menuFile.add(menuFileNewLocalRandom);
        menuFileNewLocalRandom.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mgmt.initLocalGame(false);
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
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
    }

    private void createInterface() {
        setLayout(new GridLayout(1, 2));
        add(informationPanel);
        informationPanel.setLayout(null);
        informationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        yourName.setBounds(5, 5, 400, 25);
        yourColor.setBounds(5, 30, 400, 25);
        opponentName.setBounds(5, 65, 400, 25);
        whoIsPlaying.setBounds(5, 125, 400, 25);

        informationPanel.add(yourName);
        informationPanel.add(yourColor);
        informationPanel.add(opponentName);
        informationPanel.add(whoIsPlaying);

        // Panel for searching servers
        availableServers = new JPanel();
        availableServers.setLayout(null);
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
                        calculateRow(xPosition);
                    }
                });
    }

    private void insertOwnInformation() {
        try {
            String name = InetAddress.getLocalHost().toString();
            yourName.setText(name);
        } catch (UnknownHostException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        yourColor.setText("Sie sind Farbe: " + game.getMyColor().toString());
    }

    private void insertInformation() {
        whoIsPlaying.setText("Spieler mit Farbe " + model.getCurrentPlayer().toString() + " ist am Zug.");
        opponentName.setText(game.getEnemyName());
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
        System.out.println("GUI.drawCircle " + lastMove);
        int xPos = (lastMove.getXPosition() * 100);
        int yPos = (lastMove.getYPosition() * 100);
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

    private void printGameOffers() {
        clearGameOffer();
        ArrayList<GameOffer> gameoffers = model.getGameOffers();
        Iterator<GameOffer> i = gameoffers.iterator();
        while (i.hasNext()) {
            GameOffer go = i.next();
            GameOfferPanel offer;
            synchronized (availableServers.getTreeLock()) {
                offer = new GameOfferPanel(go, availableServers.getComponentCount());
            }
            availableServers.add(offer);
            offer.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    GameOffer o = ((GameOfferPanel) e.getSource()).getOffer();
                    game.joinGame(o);
                }
            });

            repaint();
        }
    }

    private void clearGameOffer() {
        availableServers.removeAll();
    }

    @Override
    public void update() {
        switch (model.getState()) {
            case Playing:
                insertInformation();
                drawCircle();
                break;
            case SearchOnlineGames:
                System.out.println("printgameoffers");
                printGameOffers();
                break;
        }
    }
}
