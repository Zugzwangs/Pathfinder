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
import static java.lang.Integer.max;
import static java.lang.Math.abs;
import static pathfinder.AStar.COST_STRAIGHT;

/**
 *
 * @author jr
 */
public class Manhattan implements Heuristic{
    
    public Manhattan(){
        
    }
    
    @Override
    public int compute(Dimension A, Dimension B)
    {
    return COST_STRAIGHT*( abs(A.width-B.width) + abs(A.height-B.height) );         
    }
}