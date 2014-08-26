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
import javax.swing.AbstractAction;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

/**
 *
 */
public class ActionSaveFile extends AbstractAction {

private mainWindow myMainWindow;

    public ActionSaveFile(mainWindow W, String t){
        super(t);
        myMainWindow = W;
    }    

    @Override
    public void actionPerformed(ActionEvent e){
        
        File f = null;
        File currentFolder = null;
        
        // get the working directory.
        try { 
            currentFolder = new File(".").getCanonicalFile();
            System.out.println("current folder is : " + currentFolder);
            } 
        catch(IOException ex){
            }
        
        // open the "save to file" dialogue
        JFileChooser fileChooser = new JFileChooser(currentFolder);
        fileChooser.setDialogTitle("Save map to file");
        int choix = fileChooser.showSaveDialog(null);
        
        // if user choose a file, callback appropriate treatment
        if (choix == JFileChooser.APPROVE_OPTION)
            {
            f = fileChooser.getSelectedFile();        
            myMainWindow.saveMap(f);
            }         
        }
}
