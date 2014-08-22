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
import static java.lang.Math.abs;

/**
 * A* implementation
 */
public class AStar {

// const value used to test behavior
// these value may need to be deported somewhere else later
// need to design a TieBreaker Object ?
private int COST_STRAIGHT = 10;
private int COST_DIAG = 14;
// ********************************************************
    
//datas A* needs to know about the map
private Map map;
private Dimension start;
private Dimension end;

// data structures used by A* research
BinaryHeap open_list;
BinaryHeap closed_list;

    public AStar(Map currentMap){
        map = currentMap;
        bufferingDataFromMap();
        open_list = new BinaryHeap();
        closed_list = new BinaryHeap();
    }
    
    public void flushCurrentMap(){
        map = null;
        start = null;
        end = null;
    }
    
    private void cleanPreviousSearch(){
        open_list.clear();
        closed_list.clear();
    }
    
    public boolean findPath(){
        cleanPreviousSearch();
        
        //code for test purpose only
        System.out.println("********* begin debug *********");
        //add starting point to open list
        Node startNode = new Node(0, Manhattan(start, end), start);
        open_list.insert( startNode );
        adjacentNodes(startNode);
        
        int i=0;
        while( i<1000 )
            {
            closed_list.insert(open_list.extract());
            i++;
            }
        System.out.println("********* end debug *********");        
        //code for test purpose only    
        
        return true;
    }
    
    private int Manhattan(Dimension A, Dimension B){
        return COST_STRAIGHT*( abs(A.width-B.width) + abs(A.height-B.height) );
    } 
    
    private void adjacentNodes(Node n){
        
    }
            
    private boolean bufferingDataFromMap(){
        start = map.getStartPos();
        end = map.getEndPos();
        if ( (start.height >=0 && start.width>=0) && (end.height >=0 && end.width>=0) ) 
            return true;
        else 
            return false;
    }
    
    
}
