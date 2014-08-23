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
import java.util.ArrayList;
import java.util.Collections;

/**
 * data structure a binary min-heap implemented on top of an ArrayList
 * utiliser des opérations de décalage de bits pour le calcul des indices ?
 * @author jr
 */
public class BinaryHeap {

private ArrayList<Node> myList;
private int LastNodeIndex;

    public BinaryHeap(){
        myList = new ArrayList<Node>();
        myList.add( new Node(-1) );
        LastNodeIndex = 0;
    }

    public void clear(){
        myList.clear();
        myList.add( new Node(-1) );        
        LastNodeIndex = 0;
    }
    
    public void insert(Node newNode){
        //add to the list then reorder the min property
        myList.add(newNode);
        LastNodeIndex++;
        percolateUp(LastNodeIndex);
        }
    
    public Node extract(){
        // return null if no node to be extracted
        if (1 == myList.size())
            return null;
        // swap the root with last element, then copy deported root and delete it from myList
        Collections.swap(myList, 1, LastNodeIndex);
        Node root = myList.get(LastNodeIndex);
        myList.remove(LastNodeIndex);
        LastNodeIndex--;
        // and percolate down the wrong root to restore heap property
        percolateDown(1);
        return root;
        }
    
    private void percolateUp(int index){
        // swap element at index with it's parent while is smaller
        while( index>1 && myList.get(index).key<myList.get(index/2).key )
            {
            Collections.swap(myList, index, index/2);
            index = index/2;
            }
    }
    
    private void percolateDown(int index){
        // swap element at index with it's smallest child while is bigger
        while( index*2<=LastNodeIndex ) //while at least one son exist
            {
            //CASE TWO SONS
            if ( index*2+1 <= LastNodeIndex ) 
                {//first son is smallest
                if ( myList.get(index*2).key<myList.get(index*2+1).key )
                    {//first son is smallest
                    if ( myList.get(index).key>myList.get(index*2).key )
                        {
                        Collections.swap(myList, index, index*2);
                        index = index*2;
                        }
                    else
                        return;
                    }
                else
                    {//second son is smallest
                    if ( myList.get(index).key>myList.get(index*2+1).key )
                        {
                        Collections.swap(myList, index, index*2+1);
                        index = index*2+1;
                        }
                    else
                        return;
                    }
                }
            //CASE ONE SON
            else
                {
                if( myList.get(index).key>myList.get(index*2).key ) //if only son and smaller =>swap
                    {
                    Collections.swap(myList, index, index*2);
                    index = index*2;
                    }
                else
                    return;
                }
            }        
    }

    public void alterKey(int index, int value){
        //index 0 is forbidden, beware that percolateDown(0) goes infinite loop
        if ( index <= 0)
            return;
        
        if ( value > myList.get(index).key )
            {
            myList.get(index).key = value;
            percolateDown(index);
            }
        else
            {
            myList.get(index).key = value;                
            percolateUp(index);
            }
    }
    
    public void build()    {}
    
    public String toString(){
        String S = new String("BinaryHeap content is :\n");
        for (int i=1; i<=LastNodeIndex; i++) 
            S += "node n°" + i + " = " + myList.get(i).toString();// + "\n";
        
        return S;
    }
}