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
import pathfinder.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Dimension;
import java.awt.Color; 

import java.awt.event.ActionListener;

/**
 * 
*/
public class mainWindow extends JFrame {
    
    Map map;
    AStar pathFinder;
    JPanel mapPanel;
    JTextField startXField;
    JTextField startYField;
    JTextField endXField;
    JTextField endYField;
    JButton startButton;    
    
    public mainWindow(){
        buildWindow();
        setupMap();
        buildTestMap();
        refreshMapView();
        pathFinder = new AStar(map);        
        //Et enfin, la rendre visible        
        this.setVisible(true);
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
        map.setEnd(4, 11);
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
        //setup de la géométrie et du comportement
        this.setTitle("Pathfinding Viewer");
        this.setSize(1000, 900);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //creation du layout principal
        GridBagLayout mainLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout( mainLayout );

        //conteneur de gauche (contient la map)
        mapPanel = new JPanel();
        mapPanel.setBackground(Color.BLACK);    
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(mapPanel, c);
        //on déclare son layout
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
        JPanel controlPanel = new JPanel();
        controlPanel.setMinimumSize( new Dimension(180, 10) );
        controlPanel.setBackground(Color.LIGHT_GRAY);    
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;    
        c.gridx = 1;
        c.gridy = 0;        
        this.add(controlPanel, c);
        //on déclare son layout
        GridBagLayout controlLayout = new GridBagLayout();
        GridBagConstraints c_control = new GridBagConstraints(); 
        controlPanel.setLayout(controlLayout);
        
        /* on crée les composants I/O */
        
        // le point de départ
        JLabel startLocation = new JLabel("start position");
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
        
    }

    public void runPathFinding(){
        pathFinder.findPath();
    }
          
    public void finalize() throws Throwable {
        try {
            System.out.println("on quitte la fenetre principale");
        } finally {
            super.finalize();
        }
    }

}
