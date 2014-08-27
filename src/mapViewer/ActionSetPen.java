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
public class ActionSetPen extends AbstractAction {

private mainWindow fenetre;
private int penValue;
    
    public ActionSetPen(mainWindow W, String t, int _penValue){
        super(t);
        this.fenetre = W;
        this.penValue = _penValue;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //fenetre.monCallBack(_penValue);
    }
}
