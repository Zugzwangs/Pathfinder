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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import pathfinder.*;

/**
 * 
*/
public class mainWindow extends JFrame {
    
    //containers components references
    JTabbedPane mainTabs;   //Top level container
    JPanel searchTab;       //First tab container
    JPanel editorTab;       //Second tab container
    JPanel mapPanel;        //Left part of first tab
    JPanel controlPanel;    //Right part of first tab
    JPanel mapEditorPanel;  //Left part of second tab
    JPanel controlPanel2;   //Right part of second tab 
    
    //controls components references (first tab)
    JTextField startXField;
    JTextField startYField;
    JTextField endXField;
    JTextField endYField;
    JButton startButton;

    //controls components references (second tab)    
    JButton emptyBlocks;
    JButton wallBlocks;
    JButton saveMap;
    JButton loadMap;
    
    //data references
    Map map;
    AStar pathFinder;
    private int gridWidth;
    private int gridHeight;
    
    public mainWindow(){
        
        //init fields
        gridWidth = 0;
        gridHeight = 0;   
        map = new Map(70, 70);        
        pathFinder = new AStar(map);
        
        //run setup methodes
        buildWindow();
        setupMap();
        buildTestMap();
        refreshMapView();
        
        //finally show the main window
        this.setVisible(true);
    }
    
    private void setupMap(){

    }
    
    private void buildTestMap(){

        for (int i=0; i<8; i++)
            {
            map.setCaseValue(10+i, 4, Map.CASE_OBSTACLE);
            }
        map.setStart(45, 60);
        map.setEnd(12, 2);
    }
    
    private void refreshMapView(){
        
        int bound = mapPanel.getComponentCount();
        for (int i=0; i<bound-1; i++)
            {
            switch( map.getCaseValue(i%70, i/70) )
                {
                case Map.CASE_OBSTACLE: mapPanel.getComponent(i).setBackground(Color.BLACK);  break;
                case Map.CASE_FREE:     mapPanel.getComponent(i).setBackground(Color.WHITE);  break;
                case Map.CASE_START:    mapPanel.getComponent(i).setBackground(Color.BLUE);  break;
                case Map.CASE_END:      mapPanel.getComponent(i).setBackground(Color.RED);  break;    
                }
            }
        
        startXField.setText("" + map.getStartPos().width);
        startYField.setText("" + map.getStartPos().height);

        endXField.setText("" + map.getEndPos().width);
        endYField.setText("" + map.getEndPos().height);
    }
    
    private void buildWindow(){
        
        //setup de la géométrie et du comportement de la Window
        this.setTitle("Pathfinding Viewer");
        this.setSize(1000, 900);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //ajout du top layout
        GridBagLayout mainLayout = new GridBagLayout();
        GridBagConstraints c_main = new GridBagConstraints();
        this.setLayout( mainLayout );
        
        //creation et insertion du tab panel
        mainTabs = new JTabbedPane();
        c_main.fill = GridBagConstraints.BOTH;
        this.add(mainTabs, c_main);

        ////////////////////////////////////////////////////////////////////////        
        //creation et insertion du premier onglet
        ////////////////////////////////////////////////////////////////////////        
        searchTab = new JPanel();
        GridBagLayout searchTabLayout = new GridBagLayout();  
        GridBagConstraints c = new GridBagConstraints();
        searchTab.setLayout(searchTabLayout);
        mainTabs.addTab("path finding", searchTab);
        
        //conteneur de gauche (contient la map)
        mapPanel = new JPanel();
        mapPanel.setBackground(Color.BLACK);    
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        searchTab.add(mapPanel, c);
        GridBagLayout mapLayout = new GridBagLayout();
        GridBagConstraints c_map = new GridBagConstraints();
        mapPanel.setLayout(mapLayout);
        //on crée notre damier en remplissant le mapLayout
        for(int i=1; i<=70; i++)
            {
            for (int j=1; j<=70; j++)
                {
                JPanel tempLabel = new JPanel();
                tempLabel.setBorder( BorderFactory.createLineBorder(Color.BLACK, 1) );
                tempLabel.setBackground(Color.WHITE);
                tempLabel.setSize(20, 20);
                c_map.gridx = i;
                c_map.gridy = j;
                mapPanel.add(tempLabel, c_map);   
                }
            }

        //conteneur de droite (contient les autres controles)
        controlPanel = new JPanel();
        controlPanel.setMinimumSize( new Dimension(180, 10) );
        controlPanel.setBackground(Color.LIGHT_GRAY);    
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;    
        c.gridx = 1;
        c.gridy = 0;        
        searchTab.add(controlPanel, c);
        GridBagLayout controlLayout = new GridBagLayout();
        GridBagConstraints c_control = new GridBagConstraints(); 
        controlPanel.setLayout(controlLayout);
        
        // on crée les composants I/O 
        // le point de départ
        JLabel startLocation = new JLabel("start position");
        c_control.insets = new Insets(5, 15, 5, 15); //extern margin 
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
        mapEditorPanel.setBackground(Color.WHITE);    
        c2.fill = GridBagConstraints.BOTH;
        c2.weightx = 1;
        c2.weighty = 1;
        c2.gridx = 0;
        c2.gridy = 0;
        editorTab.add(mapEditorPanel, c2);
        GridBagLayout mapEditorLayout = new GridBagLayout();
        mapEditorPanel.setLayout(mapEditorLayout);      
        // we build a default empty grid for editing
        buildEmptyGrid(50,50);
        
        //conteneur de droite (contient les autres controles)
        controlPanel2 = new JPanel();
        controlPanel2.setMinimumSize( new Dimension(180, 10) );
        controlPanel2.setBackground(Color.LIGHT_GRAY);    
        c2.fill = GridBagConstraints.BOTH;
        c2.weightx = 0.5;    
        c2.gridx = 1;
        c2.gridy = 0;        
        editorTab.add(controlPanel2, c2);
        GridBagLayout controlLayout2 = new GridBagLayout();
        GridBagConstraints c_control2 = new GridBagConstraints(); 
        controlPanel2.setLayout(controlLayout2);
        // on construit les controles d'edition de map
        emptyBlocks = new JButton( new ActionSetPen(this, "empty blocks", 1) );
        c_control2.gridx = 0;
        c_control2.gridy = 0;         
        controlPanel2.add(emptyBlocks, c_control2);
        wallBlocks = new JButton( new ActionSetPen(this, "wall blocks", 0) );        
        c_control2.gridx = 0;
        c_control2.gridy = 1;         
        controlPanel2.add(wallBlocks, c_control2);
        saveMap = new JButton( new ActionSaveFile(this, "save map") );
        c_control2.gridx = 0;
        c_control2.gridy = 2;
        controlPanel2.add(saveMap, c_control2);
        loadMap = new JButton( new ActionLoadMapForEditing(this, "load map") );
        c_control2.gridx = 0;
        c_control2.gridy = 3;
        controlPanel2.add(loadMap, c_control2);        
    }

    public void runPathFinding(){
        if ( pathFinder.findPath() )
            {
            //show the path founded
            drawPath();
            }
    }

    private void drawPath(){
        
        for ( int i=0; i<70; i++)
            {
            for ( int j=0; j<70; j++)
                {
                switch( map.computedPath[i][j] )   
                    {
                    case 1:
                        mapPanel.getComponent( j*70 + i).setBackground(Color.ORANGE);
                        break;
                    case 2:
                        mapPanel.getComponent( j*70 + i).setBackground(Color.YELLOW);
                        break;
                    }
                }
            }
        for( int i=pathFinder.computed_path.size()-1; i>0; i--)
            {
            int _x = pathFinder.computed_path.get(i).X;
            int _y = pathFinder.computed_path.get(i).Y;
            mapPanel.getComponent( _y*70 + _x).setBackground(Color.GREEN);
            }

    }

    public void saveMap(File f){
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
                System.out.println("HEADER : name = " + loadedName + "\t width = " + loadedWidth + "\t height = " + loadedHeight);
                
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
        if ( w<0 || h<0 )
            {
            System.out.println("error in buildEmptyGrid(int, int) : arguments bad value !");
            return;
            }
        // delete previous grid
        flushGrid();
        // keep grid dimension
        gridWidth = w;
        gridHeight = h;
        // create a layout constraint object
        GridBagConstraints c_mapEditor = new GridBagConstraints();        
        // build the new grid
        for(int j=0; j<h; j++)
            {
            for (int i=0; i<w; i++)
                {
                CasePanel tempLabel = new CasePanel();
                c_mapEditor.gridx = i;
                c_mapEditor.gridy = j;
                mapEditorPanel.add(tempLabel, c_mapEditor);   
                }
            }
        mapEditorPanel.validate();
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
