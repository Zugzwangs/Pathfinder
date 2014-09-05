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
 * Classic A* implementation
 */
public class AStar {

// const value used to test behavior
// these value may need to be deported somewhere else later
// need to design a TieBreaker Object ?
public static final int MAX_ITERATIONS = 50000;
public static final int COST_STRAIGHT = 10;
public static final int COST_DIAG = 14;

public static final int UNEXPLORED = 0;
public static final int IN_OPEN_LIST = 1;
public static final int IN_CLOSED_LIST = 2;
public static final int ON_PATH = 3;
    
//datas A* needs to know about the map
private Map map;
private int mapWidth;
private int mapHeight;
private Dimension start;
private Dimension end;

//data structures used by A* research
private BinaryHeap open_list;
public Node[][] searchStatus;

//behaviors
private Heuristic heuristic;

    public AStar(Map currentMap){
        map = currentMap;
        mapWidth  = map.getWidth();
        mapHeight = map.getHeight();        
        open_list = new BinaryHeap();
        heuristic = new Manhattan();
        searchStatus = new Node[mapWidth][mapHeight]; //holds a reference for all nodes.
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
    
    private void cleanPreviousSearch(){
        open_list.clear();
        //closed_list.clear();
        for (int i=0; i<mapWidth; i++)
            {
            for (int j=0; j<mapHeight; j++)
                {
                searchStatus[i][j] = nodeFactory(i, j);
                }
            }
    }
    
    public boolean findPath(Dimension _start, Dimension _end){

        //check if start/end positions are valid.
        start = _start;
        end   = _end;        
        if ( !isRouteValid() )
            {
            System.out.println("route is invalid !");
            return false;
            }
        
        // clean before processing
        cleanPreviousSearch(); 
        
        //create starting node and put it to open list
        Node startNode = nodeFactory(start.width, start.height);
        //closed_list.add(startNode);
        startNode.status = Node.IN_CLOSED_LIST;
        exploreAdjacentNodes(startNode);
        
        // init main loop
        int i=1;
        Node tempNode = null;
        // main loop : look for the best node and explore it's adjacents nodes.
        while( i<MAX_ITERATIONS && !isEnd(tempNode) )
            {
            tempNode = open_list.extract();
            if ( tempNode != null )
                {
                //closed_list.add(tempNode); 
                tempNode.status = Node.IN_CLOSED_LIST;
                exploreAdjacentNodes(tempNode);          
                i++;
                }
            else
                {
                //open_list is empty => no path exists
                System.out.println( "no path founded !" );
                return false;
                }
            }
        
        if ( isEnd(tempNode) )
            {
            //System.out.println( "pathfinding succefull !" );
            computePath();
            return true;        
            }
        else
            {
            System.out.println( "fail ! path too hard to compute !" ); 
            return false;
            }
    }
           
    private void exploreAdjacentNodes(Node n){
        // 4-connexity
        pushNodetoOpenList( nodeFactory(n, n.X+1, n.Y) );
        pushNodetoOpenList( nodeFactory(n, n.X, n.Y-1) );
        pushNodetoOpenList( nodeFactory(n, n.X, n.Y+1) );
        pushNodetoOpenList( nodeFactory(n, n.X-1, n.Y) );
        // 8-connexity
        pushNodetoOpenList( nodeFactory(n, n.X+1, n.Y-1) );
        pushNodetoOpenList( nodeFactory(n, n.X+1, n.Y+1) );        
        pushNodetoOpenList( nodeFactory(n, n.X-1, n.Y-1) );
        pushNodetoOpenList( nodeFactory(n, n.X-1, n.Y+1) );      
    }

    private Node nodeFactory(int x, int y){
        return( new Node(0, heuristic.compute(new Dimension(x,y), end), new Dimension(x,y)) );
    }

    private Node nodeFactory(Node daddy, int x, int y){
        
        if ( daddy.X != x && daddy.Y != y )
            return( new Node(daddy.cost+COST_DIAG, heuristic.compute(new Dimension(x,y), end), new Dimension(x,y), daddy, Node.UNEXPLORED) );
        else
            return( new Node(daddy.cost+COST_STRAIGHT, heuristic.compute(new Dimension(x,y), end), new Dimension(x,y), daddy, Node.UNEXPLORED) );
    }

    private void pushNodetoOpenList(Node n){
        
        try{
            switch( searchStatus[n.X][n.Y].status )
                {
                
                //case where n is a completely new node
                case UNEXPLORED:
                    {
                    //check if this node is a free case
                    if ( map.getCaseValue(n.X, n.Y) != Map.CASE_OBSTACLE )
                        {
                        n.status = Node.IN_OPEN_LIST;                            
                        searchStatus[n.X][n.Y] = n;                         
                        open_list.insert( n );                   
                        }                 
                    break;
                    }
                
                //the node is already in open list : adjust it if we found a less-costed way to reach it
                case IN_OPEN_LIST:
                    {
                    if ( n.cost < searchStatus[n.X][n.Y].cost )
                        {
                        n.status = Node.IN_OPEN_LIST;
                        searchStatus[n.X][n.Y] = n;  
                        }
                    break;
                    }
                
                //don't push the node yet
                case IN_CLOSED_LIST:
                    {
                    if ( n.cost < searchStatus[n.X][n.Y].cost )
                        {
                        n.status = Node.IN_OPEN_LIST;
                        searchStatus[n.X][n.Y] = n;  
                        }                        
                    break;
                    }
                }
        } catch (ArrayIndexOutOfBoundsException e)
            {
            // TODO : maybe try an implementation without error handling 'cause that may be too slow            
            //happens when A* want to explore a node that is out of bound of the map
            //just catch and forgot this shit
            }
    }
    
    private boolean isEnd(Node n){
        if ( n != null )
            // TODO : use equalTo methode from node ?
            return(n.X==end.width && n.Y==end.height);
        else
            return false;
    }
    
    private void computePath(){
        //get the end node
        Node tempNode = searchStatus[end.width][end.height];
        //backtrace the way
        while ( tempNode.daddy != null )
            {
            searchStatus[tempNode.X][tempNode.Y].status = ON_PATH;            
            tempNode = tempNode.daddy;
            }  
    }
    
    private boolean isRouteValid(){

        // does start/end positions are valid
        if ( (start != null && end != null) && (start.height >=0 && start.width>=0) && (end.height >=0 && end.width>=0) ) 
            {
            // does start/end are reachable case
            if ( map.getCaseValue(start) != Map.CASE_OBSTACLE && map.getCaseValue(start) != Map.CASE_OBSTACLE )
                return true;
            else
                return false;
            }
        else 
            return false;
    }
     
}
