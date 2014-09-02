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
import java.util.ArrayList;

/**
 *
 */
public class Map {

    private int width;
    private int height;    
    private String name;
    ArrayList<Integer> internalMap;
    //Dimension startPos;
    //Dimension endPos;

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
        width  = 10;
        height = 10; 
        internalMap = new ArrayList<Integer>(width*height); 
        name = "unknow";
    } 
    
    public Map(int w, int h){
        width  = w;
        height = h; 
        internalMap = new ArrayList<>(width*height);
        for(int i=0; i<width*height; i++)
            internalMap.add(CASE_FREE);
        name = "unknow";
    }
    
    public Map(int w, int h, String mapName){
        width  = w;
        height = h; 
        internalMap = new ArrayList<>(width*height);
        for(int i=0; i<width*height; i++)
            internalMap.add(CASE_FREE);
        name = mapName;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void setCaseValue(int x, int y, int value){
        try{
            switch(value){
                case CASE_OBSTACLE :
                case CASE_FREE:
                case CASE_SLOW :
                case CASE_FAST:                
                    internalMap.set(y*width +x, value);
                }
        } 
        catch (IndexOutOfBoundsException e){
            System.out.println("internalMap size is " + internalMap.size());
            System.out.println("x = " + x + " y = " + y + "index = " + (y*width +x));
        }
    }
    
    public int getCaseValue(int x, int y){
        return( (Integer)internalMap.get(y*width +x) );        
    }

    public int getCaseValue(Dimension d){
        return( (Integer)internalMap.get(d.height*width +d.width) );        
    }    
    
    public boolean isFreeCase(int x, int y){
        return( (Integer)internalMap.get(y*width +x) == CASE_FREE );
    }
}