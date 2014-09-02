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
public class ActionSetEnd extends AbstractAction {

private mainWindow mainWindow;
    
    public ActionSetEnd(mainWindow W, String t){
        super(t);
        mainWindow = W;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("set end !");        
        //mainWindow.setEnd(x, y);
    }
}
