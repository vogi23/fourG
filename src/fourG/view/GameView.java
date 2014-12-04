package fourG.view;


import fourG.controlling.MgmtController;
import fourG.controlling.GameController;
import fourG.model.IGameModelInformations;
import fourG.model.IModelObserver;
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

    
    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    JMenuItem menuFileNewLocal = new JMenuItem("New Local Game");
    JMenuItem menuFileNewOnline = new JMenuItem("New Online Game");
    JMenuItem menuFileJoinOnline = new JMenuItem("Join Online Game");
    JMenuItem menuFileSave = new JMenuItem("Save Game");
    JMenuItem menuFileLoadLocal = new JMenuItem("Load Local Game");
    JMenuItem menuFileExit = new JMenuItem("Exit");
    
    public GameView(MgmtController mgmt, GameController game, IGameModelInformations model){
        super("fourG");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.mgmt = mgmt;
        this.game = game;
        this.model = model;
        
        pack();
        setVisible(true);
        createMenu();
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
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
    }
    
    private void onFileExit() {
        System.exit(10);
    }
    
    @Override
    public void update() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
