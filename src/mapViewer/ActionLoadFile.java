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
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

/**
 *
 */
public class ActionLoadFile extends AbstractAction {

private mainWindow fenetre;
    
    public ActionLoadFile(mainWindow W, String t){
        super(t);
        this.fenetre = W;
    }    

    @Override
    public void actionPerformed(ActionEvent e){
        
        String file_path;
        File repertoireCourant = null;
        
        try 
            {
            // obtention du répertoire courant.
            repertoireCourant = new File(".").getCanonicalFile();
            System.out.println("Répertoire courant : " + repertoireCourant);
            } 
        catch(IOException ex) 
            {}
         
        // dialogue dans ce répertoire courant (ou dans "home" s'il y a eu une erreur d'entrée/sortie, auquel
        // cas repertoireCourant vaut null)
        JFileChooser dialogue = new JFileChooser(repertoireCourant);
        dialogue.showOpenDialog(null);
        // TODO USE FILE instead string file path 
        file_path = dialogue.getSelectedFile().getAbsolutePath();
        // récupération du fichier sélectionné
        System.out.println("Fichier choisi : " + file_path);        
        fenetre.loadMap(file_path);
    }    
}
