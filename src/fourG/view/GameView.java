package fourG.view;

import fourG.base.Move;
import fourG.base.Player;
import fourG.controlling.IGameControlInteractions;
import fourG.controlling.IMgmtControlInteractions;
import fourG.model.GameBoard;
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
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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

    private IMgmtControlInteractions mgmt;
    private IGameControlInteractions game;
    private IGameModelInformations model;
    private int gamePanelHeight = 599;
    private int gamePanelWidth = 699;

    JFrame gui;
    JPanel informationPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JPanel fourGInterface = new JPanel();
    JPanel availableServers = new JPanel();
    JLabel yourName = new JLabel();
    JLabel opponentName = new JLabel();
    JLabel whoIsPlaying = new JLabel();
    JLabel yourColor = new JLabel();
    JOptionPane abortGame = new JOptionPane();

    Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    Border blackLineBorder = BorderFactory.createLineBorder(Color.black, 2);
    Border blueLineBorder = BorderFactory.createLineBorder(Color.blue, 10);
    TitledBorder availableServerTitle = BorderFactory.createTitledBorder(etchedBorder, "Verfügbare Spielserver");


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

    public GameView(IMgmtControlInteractions mgmt, IGameControlInteractions game, IGameModelInformations model) {
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
        drawCircle();
    }

    private void createMenu() {
        menuFile.add(menuFileNewLocalKI);
        menuFile.addSeparator();
        menuFileNewLocalKI.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int response = JOptionPane.showConfirmDialog(gui, "Aktuelles Spiel wird abgeborchen und ein neues Spiel gegen schweren Computer wird gestartet", "Spiel gegen schweren Gegner starten", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            mgmt.initLocalGame(true);
                        }
                    }
                });
        menuFile.add(menuFileNewLocalRandom);
        menuFileNewLocalRandom.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int response = JOptionPane.showConfirmDialog(gui, "Aktuelles Spiel wird abgeborchen und ein neues Spiel gegen leichten Computer wird gestartet", "Spiel gegen einfachen Gegner starten", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            mgmt.initLocalGame(false);
                        }
                    }
                });
        menuFile.addSeparator();
        menuFile.add(menuFileNewOnline);
        menuFileNewOnline.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int response = JOptionPane.showConfirmDialog(gui, "Aktuelles Spiel wird abgeborchen und ein neues Online-Spiel wird gestartet", "Online-Spiel starten", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            mgmt.initCreateOnlineGame();
                        }
                    }
                });
        menuFile.addSeparator();
        menuFile.add(menuFileJoinOnline);
        menuFileJoinOnline.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int response = JOptionPane.showConfirmDialog(gui, "Aktuelles Spiel wird abgeborchen. Sie können aus einem Online-Spiel beitreten", "Online-Spiel beitreten", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            mgmt.initJoinOnlineGame();
                        }
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
                        int response = JOptionPane.showConfirmDialog(gui, "Aktuelles Spiel wird abgeborchen. Sie können ein gespeichertes Spiel starten", "Gespeichertes Spiel weiterführen", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            File f = getFileName();
                            mgmt.initLoadedGame(f);
                        }
                    }
                });
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuFileExit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int response = JOptionPane.showConfirmDialog(gui, "Sie verlassen das Spiel nun", "Auf Wiedersehen", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            onFileExit();
                        }
                    }
                });
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
    }

    private void createInterface() {
        setLayout(new GridLayout(1, 2));
        add(informationPanel);
        informationPanel.setLayout(null);
        informationPanel.setBorder(blackLineBorder);
        yourName.setBounds(30, 5, 400, 25);
        yourColor.setBounds(30, 30, 400, 25);
        opponentName.setBounds(30, 65, 400, 25);
        whoIsPlaying.setBounds(30, 125, 400, 25);

        informationPanel.add(yourName);
        informationPanel.add(yourColor);
        informationPanel.add(opponentName);
        informationPanel.add(whoIsPlaying);
        whoIsPlaying.setBorder(etchedBorder);

        // Panel for searching servers
        availableServers.setLayout(null);
        availableServers.setBounds(30, 200, 500, 300);
        availableServers.setBorder(availableServerTitle);

        informationPanel.add(availableServers);
        
        add(gamePanel);
        gamePanel.setBorder(blackLineBorder);
        gamePanel.add(fourGInterface, BorderLayout.CENTER);
        fourGInterface.setPreferredSize(new Dimension(gamePanelWidth, gamePanelHeight));
        fourGInterface.setBorder(blueLineBorder);
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
        opponentName.setText("Sie spielen gegen: " + game.getEnemyName());
    }

    private void onFileExit() {
        System.exit(10);
    }

    private File getFileName() {
        int r = loadedGameChooser.showOpenDialog(this);
        File s = null;
        if (r == JFileChooser.APPROVE_OPTION) {
            s = loadedGameChooser.getSelectedFile();
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
        GameBoard b = model.getBoard();
        for (int i = 0; i < b.getWidth(); i++) {
            for (int j = 0; j < b.getHeight(); j++) {
                Player p = b.getCell(i, j);
                if (p == Player.None) {
                    continue;
                }
                // DrawBoard
                g.setColor(Color.red);
                if (p == Player.Blue) {
                    g.setColor(Color.blue);
                }
                int xPos = (i * 100);
                int yPos = ((b.getHeight() - j - 1) * 100);
                g.drawOval(xPos + 25, yPos + 25, 50, 50);
                g.fillOval(xPos + 25, yPos + 25, 50, 50);
            }
        }

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
                repaint();
                break;
            case SearchOnlineGames:
                System.out.println("printgameoffers");
                printGameOffers();
                break;
        }
    }
}
