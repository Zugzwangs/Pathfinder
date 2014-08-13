/*
               LICENCE PUBLIQUE RIEN À BRANLER
                     Version 1, Mars 2009

Copyright (C) 2014 Zugzwang

La copie et la distribution de copies exactes de cette licence sont
autorisées, et toute modification est permise à condition de changer
le nom de la licence. 

        CONDITIONS DE COPIE, DISTRIBUTON ET MODIFICATION
              DE LA LICENCE PUBLIQUE RIEN À BRANLER

 0. Faites ce que vous voulez, j’en ai RIEN À BRANLER.
 */
package pathfinder;
import java.util.ArrayList;

public class BinaryHeap {
    
    public BinaryHeap(){
        myList = new ArrayList(7);
        Height = 1;
        LastNodeIndex = 0;
    }
    
    public void build(){}
    public void insert(Node newNode)
        {
        myList.add(newNode);
        LastNodeIndex++;
        percolateDown(LastNodeIndex);
        }
    
    public Node extract() 
        {
        Node root = (Node) myList.get(0);
        myList.set(0, null);
        return root;
        }
    public void increase() {}
    public void decrease() {}
    public void clear() {}
    
    private void percolateUp(int index) {}
    private void percolateDown(int index) {}
    private ArrayList myList;
    private int Height;
    private int LastNodeIndex;
}