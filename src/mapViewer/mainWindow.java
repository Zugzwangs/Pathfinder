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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import pathfinder.*;
//import java.awt.event.ActionListener;

/**
 * 
*/
public class mainWindow extends JFrame {
    
    // containers components references
    JTabbedPane mainTabs;   //Top level container
    JPanel searchTab;       //First tab container
    JPanel editorTab;       //Second tab container
    JPanel mapPanel;        //Left part of first tab
    JPanel controlPanel;    //Right part of first tab
    JPanel mapEditorPanel;  //Left part of second tab
    JPanel controlPanel2;   //Right part of second tab 
    
    // Controls components references
    JTextField startXField;
    JTextField startYField;
    JTextField endXField;
    JTextField endYField;
    JButton startButton;  
    JButton emptyBlocks;
    JButton wallBlocks;
    JButton saveMap;   
    
    // data references
    Map map;
    AStar pathFinder;
    private int pen;
    
    public mainWindow(){
        buildWindow();
        setupMap();
        buildTestMap();
        refreshMapView();
        pathFinder = new AStar(map);               
        this.setVisible(true);
        pen = 0;
    }
    
    private void setupMap(){
        map = new Map(70, 70);
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
        GridBagConstraints c_mapEditor = new GridBagConstraints();
        mapEditorPanel.setLayout(mapEditorLayout);      
        //on crée notre damier en remplissant le mapLayout
        for(int i=1; i<=60; i++)
            {
            for (int j=1; j<=60; j++)
                {
                CasePanel tempLabel = new CasePanel();
                c_mapEditor.gridx = i;
                c_mapEditor.gridy = j;
                mapEditorPanel.add(tempLabel, c_mapEditor);   
                }
            }
        
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
                // setup dimensions
                //int componentNumber = mapEditorPanel.getComponentCount();                
                int map_width = 50;
                int map_height = 50;
                // write the header
                fw.write(f.getName() + "\t");  // name
                fw.write(map_width   + "\t");  // width
                fw.write(map_height  + "\n");  // height                
                // write datas into the body
                for (int j=0; j<map_height; j++)
                    {
                    for (int i=0; i<map_width; i++)
                        {
                        if ( mapEditorPanel.getComponent(j*map_width+i) instanceof CasePanel )
                            {
                            CasePanel tempCase = (CasePanel)mapEditorPanel.getComponent(j*map_width+i);
                            switch ( tempCase.getValue() )
                                {
                                case 1:
                                    {
                                    fw.write("1");                                    
                                    break;
                                    }
                                case 2:
                                    {
                                    fw.write("0");                                    
                                    break;
                                    }
                                default :
                                    System.out.println(mapEditorPanel.getComponent(i).getBackground().toString());
                                    fw.write("?");
                                }
                            }
                        }
                    fw.write("\n");
                    }
                }
            finally
                {
                // release the writer
                fw.close();
                }
            }  
        catch (IOException e) 
            {
            e.printStackTrace();
            return;
            }
    }    

    public void loadMap(String mapFilePath){
        // open in real-only mod the file
        //???
        // read header (name, width, height) and rebuild the Map Object
        int loadedWidth = 12;
        int loadedHeight = 12;
        String loadedName = "dfd";
        pathFinder.flushCurrentMap();
        map = new Map(loadedWidth, loadedHeight, loadedName);
        // read map's datas from the file and set the Map Object
        int i = 0;
        while ( i<loadedWidth*loadedHeight /* && !EOF() */ )
            {
            map.setCaseValue(i%loadedWidth, i/loadedWidth, 0);
            i++;
            }
    }
    
    public void setPen(int _penValue){
        this.pen = _penValue;
    }
    
    public void finalize() throws Throwable {
        try {
            System.out.println("on quitte la fenetre principale");
        } finally {
            super.finalize();
        }
    }

}
