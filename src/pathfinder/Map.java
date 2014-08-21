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
import java.awt.Dimension;

/**
 *
 */
public class Map {

    ArrayList<Integer> internalMap;
    int[][] computedPath;
    int width;
    int height;
    Dimension startPos;
    Dimension endPos;
   
    public static final int CASE_OBSTACLE = 0;
    public static final int CASE_FREE  = 1;
    public static final int CASE_SLOW  = 2;
    public static final int CASE_FAST = 3;
    public static final int CASE_START = 8;
    public static final int CASE_END = 9;
    
    public static final int STATUS_PATH = 0;
    public static final int STATUS_IGNORED = 1;
    public static final int STATUS_REJECTED = 2;

    
    public Map(){
        internalMap = new ArrayList(100);
        computedPath = new int[10][10];  
        width  = 10;
        height = 10;
        startPos = new Dimension(-1, -1);
        endPos = new Dimension(-1, -1);        
    } 
    
    public Map(int w, int h){
        width  = w;
        height = h; 
        computedPath = new int[w][h];
        internalMap = new ArrayList<>(w*h);
        for(int i=1; i<w*h; i++)
            internalMap.add(CASE_FREE);

        startPos = new Dimension(-1, -1);
        endPos = new Dimension(-1, -1);  
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void setCaseValue(int x, int y, int value){
        switch(value){
            case CASE_OBSTACLE :
            case CASE_FREE:
            case CASE_SLOW :
            case CASE_FAST:                
                internalMap.set(y*width +x, value);
            }
    }
    
    public int getCaseValue(int x, int y){
        return( (Integer)internalMap.get(y*width +x) );        
    }
    
    public boolean isFreeCase(int x, int y){
        return( (Integer)internalMap.get(y*width +x) == CASE_FREE );
    }
    
    public void setPathStatus(int x, int y, int value){
        computedPath[x][y] = value;
    }
    
    public int getPathStatus(int x, int y){
        return computedPath[x][y];
    }

    public void setStart(int x, int y){

        if ( (x>=0 && x<width) && (y>=0 && y<height) && getCaseValue(x, y) != CASE_OBSTACLE )
            {
            // on reset un précédent start si il existe
            if (startPos.height > -1 && startPos.width > -1)
                {
                internalMap.set(y*width +x, CASE_FREE);
                }
            //set le nouveau start
            internalMap.set(y*width +x, Map.CASE_START); 
            startPos.setSize(x, y);
            }
    }
    
    public void setEnd(int x, int y){
        
        if ( (x>=0 && x<width) && (y>=0 && y<height) && getCaseValue(x, y) != CASE_OBSTACLE )
            {
            // on reset une précédente arrivé si elle existe
            if (endPos.height > -1 && endPos.width > -1)
                {
                internalMap.set(y*width +x, CASE_FREE);
                }
            //set la nouvelle arrivée
            internalMap.set(y*width +x, Map.CASE_END); 
            endPos.setSize(x, y);
            }        
 
    }
}