package fourG.view;

import fourG.controlling.MgmtController;
import fourG.controlling.GameController;
import fourG.model.IGameModelInformations;
import fourG.model.IModelObserver;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import javax.swing.*;

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
    private int xSize;
    private int ySize;
    private int gamePanelHeight;
    private int gamePanelWidth;

    JFrame gui;
    JPanel informationPanel = new JPanel();
    JLabel yourName = new JLabel("I'm The Hero");
    JLabel opponentName = new JLabel("Looser");
    JLabel yourIP = new JLabel("Loaclhost");
    JLabel opponentIP = new JLabel("256.256.256.256");
    JPanel gamePanel = new JPanel();
    JPanel fourGInterface = new GPanel(gamePanelWidth, gamePanelHeight, game); // Graphics Container

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
        Toolkit tk = Toolkit.getDefaultToolkit();
        xSize = ((int) tk.getScreenSize().getWidth());
        ySize = ((int) tk.getScreenSize().getHeight());
        int gameHeight = (int) (Math.round(ySize * 0.5));
        int gameWidth = (int) (Math.round(xSize * 0.8));
        setPreferredSize(new Dimension(gameWidth, gameHeight));

        this.mgmt = mgmt;
        this.game = game;
        this.model = model;

        createMenu();
        createInterface();
        pack();
        setVisible(true);
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
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        informationPanel.add(yourName);
        informationPanel.add(yourIP);
        informationPanel.add(opponentName);
        informationPanel.add(opponentIP);
        add(gamePanel);
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        gamePanel.add(fourGInterface, BorderLayout.CENTER);
        gamePanelHeight = (int) (Math.round(ySize * 1));
        gamePanelWidth = (int) (Math.round((xSize / 2) * 1));
        fourGInterface.setPreferredSize(new Dimension(700, 600));
        fourGInterface.setBorder(BorderFactory.createLineBorder(Color.red));
        fourGInterface.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public void update() {
        fourGInterface.repaint();
    }
}
