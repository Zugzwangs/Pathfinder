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
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;

/**
 * A* implementation
 */
public class AStar {

// const value used to test behavior
// these value may need to be deported somewhere else later
// need to design a TieBreaker Object ?
private final int COST_STRAIGHT = 10;
private final int COST_DIAG = 14;

private final int UNEXPLORED = 0;
private final int IN_OPEN_LIST = 1;
private final int IN_CLOSED_LIST = 2; 
    
//datas A* needs to know about the map
private Map map;
private int mapWidth;
private int mapHeight;
private Dimension start;
private Dimension end;

// data structures used by A* research
private BinaryHeap open_list;
private ArrayList<Node> closed_list;
public int[][] searchStatus;
public ArrayList<Node> computed_path;

    public AStar(Map currentMap){
        map = currentMap;
        bufferingDataFromMap();
        open_list = new BinaryHeap();
        closed_list = new ArrayList<Node>();
        searchStatus = new int[mapWidth][mapHeight];
        computed_path = new ArrayList<Node>();
    }
    
    private void cleanPreviousSearch(){
        open_list.clear();
        closed_list.clear();
        computed_path.clear();
        for (int i=0; i<mapWidth; i++)
            {
            for (int j=0; j<mapHeight; j++)
                {
                searchStatus[i][j] = 0;
                }
            }
    }
    
    public boolean findPath(){
        cleanPreviousSearch();
        
        System.out.println("********* begin debug *********"); 
        System.out.println( "avant la recherche" );        
        System.out.println( open_list.toString() );
        System.out.println( closed_list.toString() );        
        
        //create starting node and put it to open list
        Node startNode = nodeFactory(start.width, start.height);
        closed_list.add(startNode);
        exploreAdjacentNodes(startNode);
 
        System.out.println( "apres le step 0" );        
        System.out.println( open_list.toString() );
        System.out.println( closed_list.toString() ); 
        
        // init main loop
        int i=1;
        Node tempNode = null;
        // main loop : look for the best node and explore it's next nodes.
        while( i<500 && !isEnd(tempNode) )
            {
            tempNode = open_list.extract();
            if ( tempNode != null )
                {
                closed_list.add(tempNode);            
                exploreAdjacentNodes(tempNode);          
                i++;

                System.out.println( "apres le step " + i );        
                System.out.println( open_list.toString() );
                System.out.println( closed_list.toString() ); 
                }
            else
                {
                //open_list is empty => no path exists
                System.out.println( "can't find path !" );
                return false;
                }
            }
        
        if ( isEnd(tempNode) )
            {
            System.out.println( "pathfinding succefull !" );
            computePath();
            return true;        
            }
        else
            {
            System.out.println( "fail ! too much attemps !" ); 
            return false;
            }
    }
    
    private int Manhattan(Dimension A, Dimension B){
        return COST_STRAIGHT*( abs(A.width-B.width) + abs(A.height-B.height) );
    } 
    
    private int birdFly(Dimension A, Dimension B){
        int min_dist = min( abs(A.width-B.width) , abs(A.height-B.height) );
        int max_dist = max( abs(A.width-B.width) , abs(A.height-B.height) );
        return( COST_DIAG*min_dist + COST_STRAIGHT*(max_dist-min_dist) );       
    }
    
    private int Chebyshev(Dimension A, Dimension B){
        return( COST_STRAIGHT*max( abs(A.width-B.width) , abs(A.height-B.height) ) );
    }
    
    private int someHeuristicMethode(Dimension A, Dimension B){
        //code for new heuristic computation 
        return 0;
    }
    
    private void exploreAdjacentNodes(Node n){
        pushNodetoOpenList( nodeFactory(n, n.X+1, n.Y-1) );
        pushNodetoOpenList( nodeFactory(n, n.X+1, n.Y) );
        pushNodetoOpenList( nodeFactory(n, n.X+1, n.Y+1) );
        pushNodetoOpenList( nodeFactory(n, n.X, n.Y-1) );
        pushNodetoOpenList( nodeFactory(n, n.X, n.Y+1) );
        pushNodetoOpenList( nodeFactory(n, n.X-1, n.Y-1) );
        pushNodetoOpenList( nodeFactory(n, n.X-1, n.Y) );
        pushNodetoOpenList( nodeFactory(n, n.X-1, n.Y+1) );      
    }

    private Node nodeFactory(int x, int y){
        return( new Node(0, Manhattan(new Dimension(x,y), end), new Dimension(x,y)) );
    }

    private Node nodeFactory(Node daddy, int x, int y){
        
        if ( daddy.X != x && daddy.Y != y )
            return( new Node(daddy.cost+COST_DIAG, Manhattan(new Dimension(x,y), end), new Dimension(x,y), daddy) );
        else
            return( new Node(daddy.cost+COST_STRAIGHT, Manhattan(new Dimension(x,y), end), new Dimension(x,y), daddy) );
    }

    private void pushNodetoOpenList(Node n){
        
        // push a node into open_list only if not already in open or closed list
        switch( searchStatus[n.X][n.Y] )
            {
            case UNEXPLORED:
                {
                if ( map.getCaseValue(n.X, n.Y) != Map.CASE_OBSTACLE )
                    {
                    open_list.insert( n );        
                    searchStatus[n.X][n.Y] = IN_OPEN_LIST;                     
                    }    
                }
                
            case IN_OPEN_LIST:
                {
                //the node is already in open list => find it 'n recompute it
                // TODO !!!!!!!!!!!!
                return;
                }
 
            case IN_CLOSED_LIST:
                //don't push the node
            }
    }
    
    private boolean isEnd(Node n){
        if ( n != null )
            return(n.X==end.width && n.Y==end.height);
        else
            return false;
    }
    
    private void computePath(){
        Node tempNode = closed_list.get(closed_list.size()-1);

        while ( tempNode.daddy != null )
            {
            computed_path.add(tempNode);
            tempNode = tempNode.daddy;
            }  
    }
    
    private boolean bufferingDataFromMap(){
        start = map.getStartPos();
        end   = map.getEndPos();
        mapWidth  = map.getWidth();
        mapHeight = map.getHeight();
        if ( (start.height >=0 && start.width>=0) && (end.height >=0 && end.width>=0) ) 
            return true;
        else 
            return false;
    }
     
}
