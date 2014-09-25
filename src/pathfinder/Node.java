/*
               LICENCE PUBLIQUE RIEN À BRANLOUZER
                     Version 1, Mars 2009

Copyright (C) 2014 Zugzwang

La copie et la distribution de copies exactes de cette licence sont
autorisées, et toute modification est permise à condition de changer
le nom de la licence. 

        CONDITIONS DE COPIE, DISTRIBUTON ET MODIFICATION
              DE LA LICENCE PUBLIQUE RIEN À BRANLOUZER

 0. Faites ce que vous voulez, j’en ai RIEN À BRANLOUZER.
 */

package pathfinder;
import java.awt.Dimension;

public class Node extends Object
{
    //constantes
    public static final int UNEXPLORED = 0;    
    public static final int IN_OPEN_LIST = 1;
    public static final int IN_CLOSED_LIST = 2;

    //datas
    public int cost;
    public int heuristic;
    public int key;
    public int status;
    public int X;
    public int Y;
    //links
    public Node daddy;

    public Node() {
        cost = 0;
        heuristic = 0;
        key = cost+heuristic;
        X = 0;
        Y = 0;
        daddy = null;
        status = UNEXPLORED;
    }
    
    public Node(int c) {
        cost = c;
        heuristic = 0;
        key = cost+heuristic;
        X = 0;
        Y = 0;
        daddy = null;
        status = UNEXPLORED;
    }

    public Node(int c, int h) {
        cost = c;
        heuristic = h;
        key = cost+heuristic;        
        X = 0;
        Y = 0;
        daddy = null;
        status = UNEXPLORED;        
    }

    public Node(int c, int h, Dimension d) {
        cost = c;
        heuristic = h;
        key = cost+heuristic;        
        X = d.width;
        Y = d.height;
        daddy = null;
        status = UNEXPLORED;        
    }

    public Node(int c, int h, Dimension d, Node parent) {
        cost = c;
        heuristic = h;
        key = cost+heuristic;        
        X = d.width;
        Y = d.height;
        daddy = parent;
        status = UNEXPLORED;        
    }    

    public Node(int c, int h, Dimension d, Node parent, int s) {
        cost = c;
        heuristic = h;
        key = cost+heuristic;        
        X = d.width;
        Y = d.height;
        daddy = parent;
        status = s;        
    } 
        
    public Dimension getCoord(){
        return( new Dimension(X, Y) );
    }
    
    @Override
    public String toString(){
        return ( "cost=" + cost + "  heuristic=" + heuristic + " key=" + key + "  X=" + X + "  Y=" + Y + "\n" );
    }  
    
    //maybe useless
    public boolean equals(Node obj) {
        return (this.X==obj.X && this.Y==obj.Y);
    }
/*
    private Dimension Dimension(int X, int Y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
}
