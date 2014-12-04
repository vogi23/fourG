package fourG.view;


import fourG.controlling.MgmtController;
import fourG.controlling.GameController;
import fourG.model.IGameModelInformations;
import fourG.model.IModelObserver;
import java.awt.GridLayout;
import java.awt.event.*;
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
    
    private int numberOfRows = 8;
    
    JFrame gui;
    JPanel informationPanel = new JPanel();
    JLabel yourName = new JLabel();
    JLabel opponentName = new JLabel();
    JLabel yourIP = new JLabel();
    JLabel opponentIP = new JLabel();
    JPanel gamePanel = new JPanel();
    JLabel fourGInterface = new JLabel(); // für Darstellung des Interfaces, Mouse-Event
    
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
        
            
    public GameView(MgmtController mgmt, GameController game, IGameModelInformations model){
        super("fourG");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.mgmt = mgmt;
        this.game = game;
        this.model = model;
        
        createMenu();
        createInterface();
        pack();
        setVisible(true);
        
    }
    
    private void createMenu(){
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
                       // mgmt.initLoadedGame(File f);
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

    private void createInterface(){
        setLayout(new GridLayout(1, 2));
        add(informationPanel);
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.PAGE_AXIS));
        informationPanel.add(yourName);
        informationPanel.add(yourIP);
        informationPanel.add(opponentName);
        informationPanel.add(opponentIP);
        add(gamePanel);
        gamePanel.add(fourGInterface);
        fourGInterface.addMouseListener(
                new MouseListener(){
                     @Override
                     public void mouseClicked(MouseEvent e){
                         fourGAction();
                     }
                });
    }
    
    private void onFileExit() {
        System.exit(10);
    }
    
    private int fourGAction(){
        return getX();
    }
    
    @Override
    public void update() {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
