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
    //datas
    public int cost;
    public int heuristic;
    public int key;
    public int X;
    public int Y;
    //links
    public Node daddy;
    // useless links for the moment
    //public Node leftSon;
    //public Node rightSon;

    public Node() {
        cost = 0;
        heuristic = 0;
        key = cost+heuristic;
        X = 0;
        Y = 0;
        daddy = null;
        //leftSon = null;
        //rightSon = null;
    }
    
    public Node(int c) {
        cost = c;
        heuristic = 0;
        key = cost+heuristic;
        X = 0;
        Y = 0;
        daddy = null;
    }

    public Node(int c, int h) {
        cost = c;
        heuristic = h;
        key = cost+heuristic;        
        X = 0;
        Y = 0;
        daddy = null;
    }

    public Node(int c, int h, Dimension d) {
        cost = c;
        heuristic = h;
        key = cost+heuristic;        
        X = d.width;
        Y = d.height;
        daddy = null;
    }

    public Node(int c, int h, Dimension d, Node n) {
        cost = c;
        heuristic = h;
        key = cost+heuristic;        
        X = d.width;
        Y = d.height;
        daddy = n;
    }    
    
    @Override
    public String toString(){
        return ( "cost=" + cost + "  heuristic=" + heuristic + "key=" + key + "  X=" + X + "  Y=" + Y + "\n" );
    }  
    
    public boolean equals(Node obj) {
        return (this.X==obj.X && this.Y==obj.Y);
    }
.
}
