package ie.gmit.java2.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Processor implements Searchable {
            //Create the container
            private LinesMap lines = new LinesMap(); 
            //timestamp for last modification
            private long lastModifiedTime;
            
            /**
             * Return a copy of the last modified lastModifiedTime
             * @return long - Time  
             */ 
            public long getLastModifiedTime(){
                long t=this.lastModifiedTime;
                return t;
            }
            
            @Override
	    public boolean contains(String s) {
	        //Go through the lines and check if they contains the string 
                return this.lines.entrySet().stream().anyMatch((l)->l.getValue().contains(s));
	    }

	    @Override
	    public int[] count() {
	        //declare the base size
	        int words=0;
	        //Go trough the lines and get the size of each line then sum-them together
	        words = this.lines.entrySet().stream().map((l) -> l.getValue().size()).reduce(words, Integer::sum);
	        //Return the size
	        return new int[]{this.lines.size(),words};
	    }
	    
	    @Override
	    public int getNumberOfWordsInLine(int index){
	        //Try to get the line and the size of it
                //Check if the key exists
                if(this.lines.containsKey(index)){
                    //Get the size of the line
                    return this.lines.get(index).size();
                }
	        //Return 0 if the line does not exists
	        return 0;
	    }

	    @Override
	    public int countOccurrences(String s) {
	        //Decide the base occurrences
	        int occurances =0;
	        //Loop through the map
	        for (HashMap.Entry<Integer,ArrayList<String>> e : this.lines.entrySet()) {
	           //Get The array list
	            occurances = e.getValue().stream()                             //Get a parallel stream
	                                     .filter((word) -> (word.equals(s)))   //Filter down to the given word
	                                     .map((item) -> 1)                     //Each count as one
	                                     .reduce(occurances, Integer::sum);    //Add together the items
	        }
	        //return the occurrences
	        return occurances;
	    }

	    @Override
	    public int[] getFirstIndex(String s) {
	       //Loop the lines
	        for(HashMap.Entry<Integer,ArrayList<String>> e: this.lines.entrySet()){
	            //Check if the word exists in the line
	            int i=e.getValue().indexOf(s);
	            //If it does
	            if(i!=-1){
	                //Return the line and the index
	                return new int[]{e.getKey()+1,i};
	            }
	        }
	        //Return -1 -1 as it does not exists
	        return new int[]{-1,-1};
	    }

	    @Override
	    public int[] getLastIndex(String s) {
	        int[] index ={-1,-1};
                //Loop the lines
                this.lines.entrySet().forEach((e) -> {
                    //Check if the word exists in the line
                    int i = e.getValue().lastIndexOf(s);
                    //If it does
                    if (i!=-1) {
                        //Overwrite the index values
                        index[0]=e.getKey();
                        index[1]=i;
                    }
                });
	        //Return the indexes
	        return index;
	    }

	    @Override
	    public int[][] getAllIndices(String s) {
	        List<int[]> index =new ArrayList<>();
                //Loop the lines
                this.lines.entrySet().forEach((e) -> {
                    //Check if the word exists in the line
                    int linei=0;
                    //Go through the words in the line
                    for(String word : e.getValue()){
                        //If the word is the same as s
                        if(word.equals(s)){
                            //Add the indexes to the list
                            index.add(new int[]{e.getKey(),linei});
                        }
                        linei++;
                    }
                });
	        //Return the indexes
	        return (int[][])index.toArray(new int[index.size()][index.size()]);
	    }

	    @Override
	    public void delete(String s) {
	        //Go through the hash map as a stream
	        this.lines.forEach((k,v)->{
	            //Delete all
	            v.removeAll(java.util.Collections.singleton(s));
	        });
                //Set the timetsamp
                this.setTimestamp();
	    }

	    @Override
	    public void delete(int[] index) {
	        //Try to get the line and the size of it
                //Check if the key exists
                if(this.lines.containsKey(index[0])){
                    //Get the line then delete the word
	            this.lines.get(index[0]).remove(index[1]);
                    //Set the timetsamp
                    this.setTimestamp();
                }
	    }
	    
	    @Override
	    public void replace(String from,String to, boolean first,boolean last,boolean all){
	        //If want to replace all
	        if(all){
	            //Go through the lines
	            this.lines.forEach((k,line) -> {
	                //Replace all of the "from" to "to" in the line
	                java.util.Collections.replaceAll(line,from,to);
	            });
	        }else{
	            //If want to replace first
	            if(first){
	                //Loop the lines
	                for(HashMap.Entry<Integer,ArrayList<String>> e: this.lines.entrySet()){
	                    //Check if the line has the word
	                    int i=e.getValue().indexOf(from);
	                    //If it does
	                    if(i>-1){
	                        //Replace the word
	                        e.getValue().set(i,to);
	                        //Exit loop as we wanted only the first one
	                        break;
	                    }
	                }
	            }
	            //If want to replace last
	            if(last){
	                int lastLine=-1;
	                int lastWord=-1;
	                //Loop the lines
	                for(int i=0; i<this.lines.size(); i++){
	                    //Check if the line has the word
	                    int li=this.lines.get(i).indexOf(from);
	                    //If it does
	                    if(li>-1){
	                        //Set the last line and word
	                        lastLine=i;
	                        lastWord=li;
	                    }
	                }
	                //If there was a match
	                if(lastWord>-1 && lastLine>-1){
	                    //Change the word at index
	                    this.lines.get(lastLine).set(lastWord, to);
	                }
	            }
	            
	        }
                
                //Set the timetsamp
                this.setTimestamp();
	    }
	    
	    @Override
	    public LinesMap getLines() {
	        /*//Create a new HashMap
                HashMap<Integer,ArrayList<String>> tmp = new HashMap<>();
                //Make a copy of our current HahsMap this is for keeping private the current HashMap
                this.lines.entrySet().forEach((e) -> {
                    tmp.put(e.getKey(), new ArrayList<>(e.getValue()));
                });*/
                //Return a clone of our lines
	        return this.lines.deepClone();
	    }
	    
	    @Override
	    public void setLines(LinesMap lines) {
	        //Return a clone of our lines
	    	this.lines=lines;
	    }
            
            /**
             * Sets the current time to the internal holder
             */
            private void setTimestamp(){
                this.lastModifiedTime=System.currentTimeMillis();
            }
}
