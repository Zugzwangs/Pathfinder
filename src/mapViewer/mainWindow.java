/*
               LICENCE PUBLIQUE RIEN À BRANLOUZER
                     Version 1, Mars 2009

Copyright (C) 2014 Zugzwang

La copie et la distribution de copies exactes de cette licence sont
autorisées, et toute modification est permise à condition de changer
le nom de la licence. 

        CONDITIONS DE COPIE, DISTRIBUTON ET MODIFICATION
              DE LA LICENCE PUBLIQUE RIEN À BRANLOUZER

 0. Faites ce que vous voulez, j’en ai RIEN À BRANLOUZER.
 */

package mapViewer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import pathfinder.*;

/**
 * 
*/
public class mainWindow extends JFrame {
    
    //menu components references
    JMenuBar menuBar;
    JMenu menuFile;
    JMenu menuSettings;
    JMenu menuEditor;
    JMenuItem itemOpen;   
    JMenuItem itemExit;
    JMenu itemAlgo;
    JMenuItem itemOptions;
    JRadioButtonMenuItem itemAStar;
    JRadioButtonMenuItem itemDStarLite;
    JRadioButtonMenuItem itemAStarJPS;
    JMenuItem itemSave; 
    JMenuItem itemLoadInEditor;
    JMenuItem itemCreateNewMap;
    
    
    //popup menu
    MapPopUpMenu mapPopUpMenu;
                
    //containers components references
    JTabbedPane mainTabs;   //Top level container
    JPanel searchTab;       //First tab container
    JPanel editorTab;       //Second tab container
    JPanel mapPanel;        //Left part of first tab
    JPanel controlPanel;    //Right part of first tab
    JPanel mapEditorPanel;  //Left part of second tab
    JPanel controlPanel2;   //Right part of second tab 
    JScrollPane searchScrollArea;
    JScrollPane editorScrollArea;
    
    //controls components references (first tab)
    JTextField startXField;
    JTextField startYField;
    JTextField endXField;
    JTextField endYField;
    JButton startButton;
    JButton loadButton;
    JLabel pathFindingStats;
    JLabel elapsedTimeLabel;

    //controls components references (second tab)    
    JButton emptyBlocks;
    JButton wallBlocks;
    JButton waterBlocks;
    JButton saveMap;
    JButton loadMap;
    JButton clearMap;
    JButton newMap;
    
    //constantes
    private static final int DEFAULT_MAP_SIZE = 50;
    private static final int MAX_MAP_SIZE = 3000;
    
    //data references
    Map map;
    AStar pathFinder;    
    Dimension start;
    Dimension end;
    private int gridWidth;
    private int gridHeight;
    private int iterations;
    
    public mainWindow(){
        
        //init fields
        gridWidth = 0;
        gridHeight = 0;   
        start = null;
        end = null;   
        iterations = 1;
        //run setup methodes
        buildMenus();
        buildWindow();
        setupMap();
        
        ///////////////////////////
        setStart(15, 15);
        setEnd(36, 27);
        //////////////////////////
        //finally show the main window
        this.setVisible(true);
    }
    
    private void setupMap(){
        
        //we build an empty map       
        map = new Map(DEFAULT_MAP_SIZE, DEFAULT_MAP_SIZE); 
        buildEmptyMap(DEFAULT_MAP_SIZE, DEFAULT_MAP_SIZE);
        pathFinder = new AStar(map);        
    }

    public final void setStart(int x, int y){

        CasePanel tempCase;
            
        if ( map.isFreeCase(x, y) )
            {             
            //unset previous start
            if ( start != null )
                {
                tempCase = (CasePanel)mapPanel.getComponent(start.height*map.getWidth()+start.width);                     
                tempCase.setValue(Map.CASE_FREE);
                }
            //set new start
            start = new Dimension(x, y);
            startXField.setText("" + start.width);
            startYField.setText("" + start.height);
            
            tempCase = (CasePanel)mapPanel.getComponent(start.height*map.getWidth()+start.width);
            tempCase.setValue(Map.CASE_START);     
            }
    }
    
    public final void setEnd(int x, int y){

        CasePanel tempCase;       
        
        if ( map.isFreeCase(x, y) )
            {
            //unset previous end
            if (end != null )
                {
                tempCase = (CasePanel)mapPanel.getComponent(end.height*map.getWidth()+end.width);                    
                tempCase.setValue(Map.CASE_FREE);
                }
            //set new start        
        end = new Dimension(x, y);
        endXField.setText("" + end.width);
        endYField.setText("" + end.height); 
        
        tempCase = (CasePanel)mapPanel.getComponent(end.height*map.getWidth()+end.width);          
        tempCase.setValue(Map.CASE_END);  
        }
    } 
      
    private void buildMenus(){
        
        //instanciate and setup
        menuBar      = new JMenuBar();
        menuFile     = new JMenu("File");
        menuSettings = new JMenu("Settings");
        menuEditor   = new JMenu("Editor");
        
        itemOpen = new JMenuItem("Open map");
        itemOpen.setMnemonic(KeyEvent.VK_O);
        itemOpen.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK) );
        itemOpen.addActionListener( new ActionLoadMap(this, "Load Map") );
        
        itemExit = new JMenuItem("Exit");
        itemExit.setMnemonic(KeyEvent.VK_Q);
        itemExit.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_Q, ActionEvent.CTRL_MASK) );
        itemExit.setToolTipText("Exit application");
        itemExit.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event){
                                        System.exit(0);}
                                    });

        itemLoadInEditor = new JMenuItem("Load map in editor");
        itemLoadInEditor.setMnemonic(KeyEvent.VK_L);
        itemLoadInEditor.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_L, ActionEvent.CTRL_MASK) );
        itemLoadInEditor.addActionListener( new ActionLoadMapForEditing(this, "load map") );
            
        itemSave = new JMenuItem("Save map ...");
        itemSave.setMnemonic(KeyEvent.VK_S);
        itemSave.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK) );
        itemSave.addActionListener( new ActionSaveFile(this, "save map") );
        
        itemCreateNewMap = new JMenuItem("Create a new blank map");
        itemCreateNewMap.setMnemonic(KeyEvent.VK_N);
        itemCreateNewMap.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK) );
        itemCreateNewMap.setToolTipText("Create a new blank map");
        //TODO create ActionNewMap !!!
        itemCreateNewMap.addActionListener( new ActionNewMap(this, "save map") );
                
        itemOptions = new JMenuItem("Options");
        itemOptions.setMnemonic(KeyEvent.VK_P);
        itemOptions.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_P, ActionEvent.CTRL_MASK) );        
        itemOptions.setToolTipText("advanced options");
        
        itemAlgo = new JMenu("Algorithm");
        itemAlgo.setToolTipText("choose wich algorithm you want to perform the path finding");
        
        itemAStar = new JRadioButtonMenuItem("A*");
        itemAStar.setToolTipText("classical algo");

        itemAStarJPS = new JRadioButtonMenuItem("A* + JPS");
        itemAStarJPS.setToolTipText("Optimized A*");
        
        itemDStarLite = new JRadioButtonMenuItem("D*-Lite");
        itemDStarLite.setToolTipText("some words ...");
                
        // we also add item relative to the algo choice in a group 
        ButtonGroup AlgoGroup = new ButtonGroup();
        AlgoGroup.add(itemAStar);        
        AlgoGroup.add(itemDStarLite);
        AlgoGroup.add(itemAStarJPS);        
                
        //agregate elements
        menuFile.add(itemOpen);        
        menuFile.addSeparator();
        menuFile.add(itemExit);
        menuEditor.add(itemSave);
        menuEditor.add(itemLoadInEditor);
        menuEditor.add(itemCreateNewMap);        
        itemAlgo.add(itemAStar); 
        itemAlgo.add(itemAStarJPS);        
        itemAlgo.add(itemDStarLite);
        menuSettings.add(itemOptions);
        menuSettings.add(itemAlgo);
        menuBar.add(menuFile);
        menuBar.add(menuEditor);
        menuBar.add(menuSettings);          
        this.setJMenuBar(menuBar);
        
        // pop up menu
        mapPopUpMenu = new MapPopUpMenu(this);
    }
    
    private void buildWindow(){
        
        //setup de la géométrie du layout et du comportement de la main window
        this.setTitle("Pathfinding Viewer");
        this.setSize(1000, 900);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //creation et insertion du tab panel
        mainTabs = new JTabbedPane();
        this.add(mainTabs);
       
        ////////////////////////////////////////////////////////////////////////        
        //creation et insertion du premier onglet                   ////////////
        ////////////////////////////////////////////////////////////////////////        
        searchTab = new JPanel();
        GridBagLayout searchTabLayout = new GridBagLayout();  
        GridBagConstraints c = new GridBagConstraints();
        searchTab.setLayout(searchTabLayout);
        mainTabs.addTab("path finding", searchTab);
        
        //conteneur de gauche (contient la map)
        mapPanel = new JPanel();
        searchScrollArea = new JScrollPane(mapPanel);
        mapPanel.setBackground(Color.WHITE);    
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        searchTab.add(searchScrollArea, c);
        GridBagLayout mapLayout = new GridBagLayout();
        mapPanel.setLayout(mapLayout);
        
        //conteneur de droite (contient les autres controles)
        controlPanel = new JPanel();
        controlPanel.setMinimumSize( new Dimension(180, 10) );
        controlPanel.setBackground(Color.LIGHT_GRAY);    
        //c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;    
        c.gridx = 1;
        c.gridy = 0;        
        searchTab.add(controlPanel, c);
        GridBagLayout controlLayout = new GridBagLayout();
        GridBagConstraints c_control = new GridBagConstraints(); 
        controlPanel.setLayout(controlLayout);
        
        // on crée les composants I/O 
        // le point de départ
        JLabel startLocation = new JLabel("start position");
        c_control.insets = new Insets(5, 15, 5, 15); //external margin 
        c_control.gridx = 0;
        c_control.gridy = 0;
        c_control.gridwidth = 2;
        c_control.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(startLocation, c_control);
        startXField = new JTextField();
        startYField = new JTextField();
        c_control.gridx = 0;
        c_control.gridy = 1;
        c_control.weightx = 0.5;
        c_control.gridwidth = 1;
        controlPanel.add(startXField, c_control); 
        c_control.gridx = 1; 
        c_control.gridy = 1;
        c_control.weightx = 0.5;
        c_control.gridwidth = 1;
        controlPanel.add(startYField, c_control);
        // le point d'arrivé
        JLabel endLocation = new JLabel("end position");
        c_control.gridx = 0;       
        c_control.gridy = 2;
        c_control.gridwidth = 2;
        controlPanel.add(endLocation, c_control); 
        endXField = new JTextField();
        endYField = new JTextField();
        c_control.gridy = 3;
        c_control.gridwidth = 1;
        controlPanel.add(endXField, c_control); 
        c_control.gridx = 1; 
        c_control.gridwidth = 1;
        controlPanel.add(endYField, c_control);
        // les bouttons
        startButton = new JButton( new ActionFindPath(this, "FIND PATH") );
        c_control.gridx = 0;
        c_control.gridy = 4;
        c_control.gridwidth = 2;
        controlPanel.add(startButton, c_control);
        loadButton = new JButton( new ActionLoadMap(this, "Load Map") );
        c_control.gridx = 0;
        c_control.gridy = 5;
        controlPanel.add(loadButton, c_control);
        
        //statistics       
        pathFindingStats = new JLabel("total elapsed time (ms):");
        c_control.gridx = 0;
        c_control.gridy = 6;
        controlPanel.add(pathFindingStats, c_control); 
        elapsedTimeLabel = new JLabel("");
        c_control.gridx = 0;
        c_control.gridy = 7;
        controlPanel.add(elapsedTimeLabel, c_control); 

        ////////////////////////////////////////////////////////////////////////
        //creation et insertion du second onglet
        ////////////////////////////////////////////////////////////////////////        
        editorTab = new JPanel();
        GridBagLayout editorTabLayout = new GridBagLayout();  
        GridBagConstraints c2 = new GridBagConstraints();
        editorTab.setLayout(editorTabLayout);
        mainTabs.addTab("map editor", editorTab);
        
        //conteneur de gauche (contient la map)
        mapEditorPanel = new JPanel();
        editorScrollArea = new JScrollPane(mapEditorPanel);        
        mapEditorPanel.setBackground(Color.WHITE);    
        c2.fill = GridBagConstraints.BOTH;
        c2.weightx = 1;
        c2.weighty = 1;
        c2.gridx = 0;
        c2.gridy = 0;
        editorTab.add(editorScrollArea, c2);
        GridBagLayout mapEditorLayout = new GridBagLayout();
        mapEditorPanel.setLayout(mapEditorLayout);      
        // we build a default empty grid for editing
        buildEmptyGrid(50,50);
        
        //conteneur de droite (contient les autres controles)
        controlPanel2 = new JPanel();
        controlPanel2.setMinimumSize( new Dimension(180, 10) );
        controlPanel2.setMaximumSize( new Dimension(180, 1000 ) );
        controlPanel2.setBackground(Color.LIGHT_GRAY);    
        c2.fill = GridBagConstraints.BOTH;
        c2.weightx = 0;    
        c2.gridx = 1;
        c2.gridy = 0;        
        editorTab.add(controlPanel2, c2);
        GridBagLayout controlLayout2 = new GridBagLayout();
        GridBagConstraints c_control2 = new GridBagConstraints(); 
        controlPanel2.setLayout(controlLayout2);
        
        // on construit les controles d'edition de map
        emptyBlocks = new JButton( new ActionSetPen(this, "empty blocks", 1) );
        emptyBlocks.setIcon( new ImageIcon("./pics/icone_1.gif") );
        c_control2.gridx = 0;
        c_control2.gridy = 0;   
        c_control2.gridwidth = 2;
        c_control2.fill = GridBagConstraints.HORIZONTAL;        
        c_control2.insets = new Insets(5, 15, 5, 15); //extern margin         
        controlPanel2.add(emptyBlocks, c_control2);
        
        wallBlocks = new JButton( new ActionSetPen(this, "wall blocks    ", 0) );
        wallBlocks.setIcon( new ImageIcon("./pics/icone_0.gif") );
        c_control2.gridx = 0;
        c_control2.gridy = 1;         
        controlPanel2.add(wallBlocks, c_control2);
        
        waterBlocks = new JButton( new ActionSetPen(this, "water blocks", 2) );
        waterBlocks.setIcon( new ImageIcon("./pics/icone_2.gif") );        
        c_control2.gridx = 0;
        c_control2.gridy = 2;     
        controlPanel2.add(waterBlocks, c_control2);
        
        JPanel stretchor = new JPanel();
        stretchor.setPreferredSize( new Dimension(30, 30) );
        stretchor.setOpaque(false);
        c_control2.gridx = 0;
        c_control2.gridy = 3;        
        controlPanel2.add(stretchor, c_control2);
        
        saveMap = new JButton( new ActionSaveFile(this, "save map") );
        c_control2.gridx = 0;
        c_control2.gridy = 4;
        controlPanel2.add(saveMap, c_control2);
        
        loadMap = new JButton( new ActionLoadMapForEditing(this, "load map") );
        c_control2.gridx = 0;
        c_control2.gridy = 5;
        controlPanel2.add(loadMap, c_control2); 
        
        clearMap = new JButton( new ActionCleanMap(this, "clear map") );
        c_control2.gridx = 0;
        c_control2.gridy = 6;
        controlPanel2.add(clearMap, c_control2); 
        
        newMap = new JButton( new ActionNewMap(this, "new map") );
        c_control2.gridx = 0;
        c_control2.gridy = 7;
        controlPanel2.add(newMap, c_control2); 
    }

    public void runPathFinding(){
    
    long startTime;
    long endTime                        ;
    boolean succes = false;
    
        iterations = 10000;
        startTime = System.currentTimeMillis(); 
        for (int i=1; i<iterations; i++)
            succes = pathFinder.findPath(start, end);
        endTime = System.currentTimeMillis();
        elapsedTimeLabel.setText( Long.toString(endTime-startTime) );
        if ( succes )
            {
            //clean then show the path founded
            reDrawMap();                
            drawPath();
            }
        else
            {
            // fail
            }
    }

    private void drawPath(){
        
        for ( int i=0; i<map.getWidth(); i++)
            {
            for ( int j=0; j<map.getHeight(); j++)
                {
                try{
                    switch( pathFinder.searchStatus[i][j].status )   
                        {
                        case AStar.IN_OPEN_LIST:
                            mapPanel.getComponent(j*map.getWidth() + i).setBackground(Color.ORANGE);
                            break;
                        case AStar.IN_CLOSED_LIST:
                            mapPanel.getComponent(j*map.getWidth() + i).setBackground(Color.YELLOW);
                            break;
                        case AStar.ON_PATH:
                            mapPanel.getComponent(j*map.getWidth() + i).setBackground(Color.GREEN);
                            break;
                        }
                } catch(NullPointerException e){
                    continue;
                }
                }
            }
        //redraw start/end case
        mapPanel.getComponent(start.height*map.getWidth() + start.width).setBackground(Color.BLUE);
        mapPanel.getComponent(end.height*map.getWidth() + end.width).setBackground(Color.RED);
    }

    private void reDrawMap() {
        CasePanel tempCase;
        if (map == null)
            return;
        
        for ( int i=0; i<map.getWidth(); i++)
            {
            for ( int j=0; j<map.getHeight(); j++)
                {
                tempCase = (CasePanel)mapPanel.getComponent(j*map.getWidth() + i);
                tempCase.setValue( map.getCaseValue(i, j) );
                }
            }
        //reset start/end identically only to force redraw 
        setStart(start.width, start.height);
        setEnd(end.width, end.height);
    }
    
    public void loadMap(File f){

        try {
            //create the reader
            FileReader fr = new FileReader(f);
            BufferedReader buff = new BufferedReader(fr);
            try {
                //read header (first line)
                String header;
                if ( (header = buff.readLine()) == null )
                    {
                    //choose anathor exception ?? see more about !
                    throw new IllegalArgumentException("the file is empty. Can't load map.");                    
                    }
                
                //split line to appropriate variables
                String[] splittedHeader = header.split("\t");
                if (splittedHeader.length != 3)
                    {
                    throw new IllegalArgumentException("header not in correct format");
                    }
                String loadedName = splittedHeader[0];
                int loadedWidth   = Integer.parseInt(splittedHeader[1]);
                int loadedHeight  = Integer.parseInt(splittedHeader[2]);
                System.out.println("HEADER : name = " + loadedName + "\t width = " + loadedWidth + "\t height = " + loadedHeight);
                
                //build an empty grid with the appropriate size AND the Map object
                flushMap();     // delete previous map               
                buildEmptyMap(loadedWidth, loadedHeight);
                map = new Map(loadedWidth, loadedHeight);
                pathFinder = new AStar(map);
                
                //read the body line by line and set the map with
                String line;
                int j =0; //track the current line number
                while ( (line = buff.readLine()) != null && j<loadedHeight ) 
                    {
                    for(int i=0; i<line.length()&&i<loadedWidth; i++)
                        {
                        if ( mapPanel.getComponent(j*loadedWidth+i) instanceof CasePanel )
                            {
                            CasePanel tempCase = (CasePanel)mapPanel.getComponent(j*loadedWidth+i);
                            tempCase.setValue( Character.getNumericValue(line.charAt(i)) );
                            map.setCaseValue(i, j, Character.getNumericValue(line.charAt(i)));
                            }
                        }
                    j++;  
                    }
                }
            finally
                {
                //release the reader
                fr.close();
                }
            }        
         catch (IOException e) 
            {
            e.printStackTrace();
            }
    }
    
    public void buildEmptyMap(int w, int h){
        
        // if incorrect size is passed, we do nothing
        if ( w<0 || h<0 ) //TODO define MAX_SIZE constantes
            {
            System.out.println("error in buildEmptyMap(int, int) : arguments bad value !");
            return;
            } 
        
        // create a layout constraint object and build the map        
        GridBagConstraints c_map = new GridBagConstraints();
        for(int j=0; j<h; j++)
            {
            for (int i=0; i<w; i++)
                {
                CasePanel tempLabel = new CasePanel();
                tempLabel.setIndex(new Dimension(i,j));
                tempLabel.setComponentPopupMenu(mapPopUpMenu);
                c_map.gridx = i;
                c_map.gridy = j;
                mapPanel.add(tempLabel, c_map);   
                }
            }
        mapPanel.validate();
    }
    
    public void flushMap(){
        mapPanel.removeAll();
        map = null;
        pathFinder = null;
        mapPanel.validate();
        start = null;
        end = null;    
        startXField.setText("");
        startYField.setText("");
        endXField.setText("");
        endYField.setText("");         
    }
    
    public void saveEditedMap(File f){
        
    //this function may need to run under the AWT Clock ?!
        try {
            //create the writer
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            try {
                //check consistence between dimension and effective components number 
                if ( gridWidth*gridHeight != mapEditorPanel.getComponentCount() )
                    {
                    //something wrong => throw exception or trace and leave or try to do partial job
                    System.out.println("inconsistence between map size and number of CasePanel");
                    fw.close();
                    return;
                    }
                //write the header
                fw.write(f.getName() + "\t");  // name
                fw.write(gridWidth   + "\t");  // width
                fw.write(gridHeight  + "\n");  // height                
                //write datas into the body
                for (int j=0; j<gridHeight; j++)
                    {
                    for (int i=0; i<gridWidth; i++)
                        {
                        if ( mapEditorPanel.getComponent(j*gridWidth+i) instanceof CasePanel )
                            {
                            CasePanel tempCase = (CasePanel)mapEditorPanel.getComponent(j*gridWidth+i);
                            switch ( tempCase.getValue() )
                                {
                                case 0:
                                    {
                                    fw.write("0");                                    
                                    break;
                                    }                                
                                case 1:
                                    {
                                    fw.write("1");                                    
                                    break;
                                    }
                                case 2:
                                    {
                                    fw.write("2");                                    
                                    break;
                                    }
                                default :
                                    fw.write("?");
                                }
                            }
                        }
                    fw.write("\n");
                    }
                }
            finally
                {
                //release the writer
                fw.close();
                }
            }  
        catch (IOException e) 
            {
            e.printStackTrace();
            }
    }    

    public void loadMapForEditing(File f){
        
        try {
            //create the reader
            FileReader fr = new FileReader(f);
            BufferedReader buff = new BufferedReader(fr);
            try {
                //read header (first line)
                String header;
                if ( (header = buff.readLine()) == null )
                    {
                    //choose anathor exception ?? see more about !
                    throw new IllegalArgumentException("the file is empty. Can't load map.");                    
                    }
                
                //split line to appropriate variables
                String[] splittedHeader = header.split("\t");
                if (splittedHeader.length != 3)
                    {
                    throw new IllegalArgumentException("header not in correct format");
                    }
                String loadedName = splittedHeader[0];
                int loadedWidth   = Integer.parseInt(splittedHeader[1]);
                int loadedHeight  = Integer.parseInt(splittedHeader[2]);
                //System.out.println("HEADER : name = " + loadedName + "\t width = " + loadedWidth + "\t height = " + loadedHeight);
                
                //build an empty grid with the appropriate size
                buildEmptyGrid(loadedWidth, loadedHeight);
                
                //read the body line by line and set the grid with
                String line;
                int j =0; //track the current line number
                while ( (line = buff.readLine()) != null ) 
                    {
                    for(int i=0; i<line.length(); i++)
                        {
                        if ( mapEditorPanel.getComponent(j*gridWidth+i) instanceof CasePanel )
                            {
                            CasePanel tempCase = (CasePanel)mapEditorPanel.getComponent(j*gridWidth+i);
                            tempCase.setValue( Character.getNumericValue(line.charAt(i)) );
                            }
                        }
                    j++;
                    //System.out.println(line);  
                    }
                }
            finally
                {
                //release the reader
                fr.close();
                }
            }        
         catch (IOException e) 
            {
            e.printStackTrace();
            }
    }
    
    public void buildEmptyGrid(int w, int h){
        
        // if incorrect size is passed, we do nothing
        if ( w<0 || h<0 || w>MAX_MAP_SIZE || h>MAX_MAP_SIZE )
            {
            System.out.println("error in buildEmptyGrid(int, int) : arguments bad value !");
            return;
            }
        
        // delete previous grid
        flushGrid();
        
        // keep grid dimension
        gridWidth = w;
        gridHeight = h;
        
        // create a layout constraint object and build the new grid
        GridBagConstraints c_mapEditor = new GridBagConstraints();
        for(int j=0; j<h; j++)
            {
            for (int i=0; i<w; i++)
                {
                CasePanel tempLabel = new CasePanel(new CasePanelMouseListener());
                c_mapEditor.gridx = i;
                c_mapEditor.gridy = j;
                mapEditorPanel.add(tempLabel, c_mapEditor);   
                }
            }
        mapEditorPanel.validate();
        mapEditorPanel.repaint();
    }

    public void cleanGrid(){
        buildEmptyGrid(gridWidth, gridHeight);
    }
    
    public void flushGrid(){
        mapEditorPanel.removeAll();
        mapEditorPanel.validate(); // useless ?
    }
    
    public void setPen(int _penValue){
        CasePanel.setCurrentPen(_penValue);
    }
    
    @Override
    public void finalize() throws Throwable {
        try {
            System.out.println("on quitte la fenetre principale");
        } finally {
            super.finalize();
        }
    }

}
