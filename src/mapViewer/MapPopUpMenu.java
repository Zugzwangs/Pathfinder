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

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author BELIN
 */
public class MapPopUpMenu extends JPopupMenu {
    
JMenuItem startItem;
JMenuItem endItem;

    public MapPopUpMenu(mainWindow w){

        startItem = new JMenuItem( new ActionSetStart(w, "set the start here") );
        endItem   = new JMenuItem( new ActionSetEnd(w, "set the end here") );
        add(startItem);
        add(endItem);
    }
    
}