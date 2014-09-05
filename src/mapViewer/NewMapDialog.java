/*
 *                LICENCE PUBLIQUE RIEN � BRANLER
 *                      Version 1, Mars 2009
 * 
 * Copyright (C) 2014 Zugzwang
 * La copie et la distribution de copies exactes de cette licence sont
 * autoris�es, et toute modification est permise � condition de changer
 * le nom de la licence. 
 * 
 *         CONDITIONS DE COPIE, DISTRIBUTON ET MODIFICATION
 *               DE LA LICENCE PUBLIQUE RIEN � BRANLER
 * 
 *  0. Faites ce que vous voulez, j�en ai RIEN � BRANLER.
 * 
 */

package mapViewer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jr
 */
public class NewMapDialog extends JDialog{
    //components references
    private JPanel topPanel, bottomPanel, mainPanel;
    private JLabel labelWidth, labelHeight;
    private JTextField textWidth, textHeight;
    private JButton buttonOk, buttonCancel;
    
    //data refs
    private Dimension mapSize;
    
    public NewMapDialog(JFrame f, String title, boolean modal){
        
        super(f, title, modal);
        try {
            mapSize = new Dimension();
            buildDialog();
        }
       catch(Exception e) { 
            e.printStackTrace();  
            }        
        
    }   
    
    public Dimension showDialog(){
        this.setVisible(true);
        return mapSize;
    }
    
    private void buildDialog(){
        
        //instantiate components       
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        mainPanel = new JPanel();
        labelWidth = new JLabel("Width");
        labelHeight = new JLabel("Height");
        textWidth = new JTextField(4);       
        textHeight = new JTextField(4);
        buttonOk = new JButton("OK");
        buttonCancel = new JButton("Cancel");
        
        //setup properties
        textHeight.setMaximumSize( new Dimension(50, 20) );
        textWidth.setMaximumSize( new Dimension(50, 20) ); 
        buttonOk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event){
                                        actionOk();}
                                    });
        buttonCancel.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event){
                                        actionCancel();}
                                    });        
        
        //build hierarchie
        topPanel.add(labelWidth);
        topPanel.add(textWidth);
        topPanel.add(labelHeight);
        topPanel.add(textHeight);
        mainPanel.add(topPanel);
        bottomPanel.add(buttonOk);
        bottomPanel.add(buttonCancel);     
        mainPanel.add(bottomPanel);
        this.getContentPane().add(mainPanel);        
        
        //setup window
        this.setSize( new Dimension(300, 200) );
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    public void actionOk(){
        this.getImput();
        this.setVisible(false);
    }
    
    public void actionCancel(){
        mapSize = null;
        this.setVisible(false);
        //this.dispose();
    }
    
    private void getImput(){
        mapSize.width = Integer.parseInt( textWidth.getText() );
        mapSize.height = Integer.parseInt( textHeight.getText() );        
    }
}
