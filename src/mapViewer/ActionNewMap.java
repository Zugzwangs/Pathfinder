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
import javax.swing.AbstractAction;

/**
 *
 * @author BELIN
 */
public class ActionNewMap extends AbstractAction {

private mainWindow myMainWindow;
    
    public ActionNewMap(mainWindow W, String t){
        super(t);
        myMainWindow = W;
    }    

    @Override
    public void actionPerformed(ActionEvent e){
        //TODO show a dialog who ask for map's size
        myMainWindow.buildEmptyGrid(60, 30);
    }    
}