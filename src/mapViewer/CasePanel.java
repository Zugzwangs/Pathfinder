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
import java.awt.Color;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author BELIN
 */
public final class CasePanel extends JPanel{

private int value;

    public CasePanel(){
       super();
       this.setSize(20, 20);
       this.setBorder( BorderFactory.createLineBorder(Color.BLACK, 1) );
       this.setValue(1);
       this.addMouseListener( new CasePanelMouseListener() );
    }

    public CasePanel(int _value){
       super();
       this.setSize(20, 20);
       this.setBorder( BorderFactory.createLineBorder(Color.BLACK, 1) );
       this.setValue(_value);
       this.addMouseListener( new CasePanelMouseListener() );
    }
    
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        
        switch( value )
            {
            case 0:
                {
                this.value = value;
                this.setBackground(Color.BLACK);
                break;
                }
            case 1:
                {
                this.value = value;
                this.setBackground(Color.WHITE);
                break;
                }
            case 2:
                {
                this.value = value;
                this.setBackground(Color.CYAN);
                break;
                }
            default:
                {
                this.value = 1;
                this.setBackground(Color.WHITE);
                }
            }
    }
}
