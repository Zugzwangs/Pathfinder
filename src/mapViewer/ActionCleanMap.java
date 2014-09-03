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
 * @author BELIN
 */
public class ActionCleanMap  extends AbstractAction {

private mainWindow myMainWindow;
    
    public ActionCleanMap(mainWindow W, String t){
        super(t);
        myMainWindow = W;
    }    

    @Override
    public void actionPerformed(ActionEvent e){
        myMainWindow.cleanGrid();
    }    
}