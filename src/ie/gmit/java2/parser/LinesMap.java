/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.java2.parser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author user
 */
public class LinesMap extends HashMap<Integer,ArrayList<String>>{

    
    /**
    * Returns a deep clone of the object
    * @return LinesMap
    */
    public LinesMap deepClone(){
        //super.clone();
        //Create a new HashMap
        LinesMap tmp = new LinesMap();
        //Make a copy of our current HahsMap this is for keeping private the conent of the current HashMap
        this.entrySet().forEach((Entry<Integer, ArrayList<String>> e) -> {
            tmp.put(e.getKey(), new ArrayList<>(e.getValue()));
        });
        //Return a clone of our lines
	return tmp;
    }

}
