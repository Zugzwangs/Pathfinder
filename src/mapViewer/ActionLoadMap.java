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
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

/**
 *
 */
public class ActionLoadMap extends AbstractAction {

private mainWindow myMainWindow;
    
    public ActionLoadMap(mainWindow W, String t){
        super(t);
        myMainWindow = W;
    }

    @Override
    public void actionPerformed(ActionEvent e){

    File f = null;
    File currentFolder = null;
        
        try {
            currentFolder = new File(".").getCanonicalFile();
            System.out.println("Répertoire courant : " + currentFolder);
            } 
        catch(IOException ex) {
            ex.printStackTrace();
            return;
        }
         
        // open the "choose a file" in working directory or home if is null
        JFileChooser fileChooser = new JFileChooser(currentFolder);
        int choice = fileChooser.showOpenDialog(null);
         
        // if user choose a file, callback appropriate treatment
        if (choice == JFileChooser.APPROVE_OPTION)
            {
            f = fileChooser.getSelectedFile();        
            myMainWindow.loadMap(f);
            }       
    }    
}