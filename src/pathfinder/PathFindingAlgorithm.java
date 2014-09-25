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
public interface PathFindingAlgorithm {
    
    public void setConnexity(Connexity connexity);
    
    public void setHeuristic(Heuristic heuristic);
    
    public boolean findPath(Dimension _start, Dimension _end)  ;  
            
}
