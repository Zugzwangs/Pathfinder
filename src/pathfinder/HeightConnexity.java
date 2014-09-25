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

package pathfinder;

import java.awt.Dimension;

/**
 *
 * @author BELIN
 */
public class HeightConnexity implements Connexity{

Dimension[] neighbours; //TODO : déplacer cette déclaration dans l'interface ?

    public HeightConnexity(){
        neighbours = new Dimension[8];
    }
    
    @Override
    public Dimension[] getNeighbours(Dimension centerCase){
        int x = centerCase.height;
        int y = centerCase.width;
        
        neighbours[0] = new Dimension(x, y+1);
        neighbours[1] = new Dimension(x, y-1);
        neighbours[2] = new Dimension(x-1, y);
        neighbours[3] = new Dimension(x+1, y);
        neighbours[4] = new Dimension(x+1, y+1);
        neighbours[5] = new Dimension(x+1, y-1);
        neighbours[6] = new Dimension(x-1, y+1);
        neighbours[7] = new Dimension(x-1, y-1);        
        return neighbours;
    }
}
