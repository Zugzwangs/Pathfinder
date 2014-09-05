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
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author BELIN
 */
public final class CasePanel extends JPanel{

private int value;
private static int currentPen = 0;
private Dimension index;

    public CasePanel(){
       super();
       this.setSize(16, 16);
       this.setBorder( BorderFactory.createLineBorder(Color.BLACK, 1) );
       this.setValue(1);
    }

    public CasePanel(int _value){
       super();
       this.setSize(16, 16);
       this.setBorder( BorderFactory.createLineBorder(Color.BLACK, 1) );
       this.setValue(_value);
    }
    
    public CasePanel(CasePanelMouseListener listener){
       super();
       this.setSize(16, 16);
       this.setBorder( BorderFactory.createLineBorder(Color.BLACK, 1) );
       this.setValue(1);
       this.addMouseListener( listener );
    }    
    
    public CasePanel(int _value, CasePanelMouseListener listener){
       super();
       this.setSize(16, 16);
       this.setBorder( BorderFactory.createLineBorder(Color.BLACK, 1) );
       this.setValue(_value);
       this.addMouseListener( listener );
    }  

    public Dimension getIndex() {
        return index;
    }

    public void setIndex(Dimension index) {
        this.index = index;
    }    
    
    public static int getCurrentPen() {
        return currentPen;
    }

    public static void setCurrentPen(int pen) {
        if ( 0 <= pen && pen < 10)
            CasePanel.currentPen = pen;
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
            case 8:
                {
                this.value = value;
                this.setBackground(Color.BLUE);
                break;
                }
            case 9:
                {
                this.value = value;
                this.setBackground(Color.RED);
                break;
                }            
            default:
                {
                this.value = 1;
                this.setBackground(Color.WHITE);
                }
            }
    }
    
    public void setValueWithCurrentPen(){
        setValue(currentPen);
    }
}
