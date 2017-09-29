/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.java2.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public abstract class Parser implements Parseable,Cloneable{
    private final LinesMap lines =new LinesMap();
    private String[] encoding={
    		"UTF-8",
			"UTF-16",
			"ISO-8859-1",
			"GB2312",
			"Windows-1251",
			"Windows-1252",
			"Shift JIS",
			"GBK",
			"Windows-1256",
			"ISO-8859-2",
			"EUC-JP",
			"ISO-8859-15",
			"ISO-8859-9",
			"Windows-1250",
			"Windows-1254",
			"EUC-KR",
			"Big5",
			"Windows-874",
			"US-ASCII",
			"TIS-620",
			"ISO-8859-7",
			"Windows-1255"};
    
    /**
     * The function returns the character encodings
     * @return String[]
     */
    public String[] getEncodings(){
    	return this.encoding;
    }
    
    /**
     * Function to add a new line to the container
     * @param line - ArrayList
     */
    public void addLine(ArrayList<String> line){
        //Add the new line
        this.lines.put(this.lines.size(), line);
    }
   

    //This method should be overwritten in one of the child classes to provide specific messages
    
    @Override
    public String getErrorMessage(){
        return "The path is not avalibale or does not exist.";
    }
    
    @Override
    public LinesMap getParsedLines() {
        //Return a clone of our lines
	return this.lines.deepClone();
    }

   
    
    //The method cannot be private because the subclasses using it
    /**
     * Method to store the lines in a container from the buffered reader.
     * The method will filter every line then it will try to separate the words if a regex was set.
     * @param br - BufferedReader
     * @param separatorRegex - String
     * @throws IOException 
     */
    protected void storeLines(BufferedReader br, String separatorRegex, boolean filterLines) throws IOException{
    	//Stream through the lines returns 579944(filter after split) words or 579781(filter before split)
    	//Check if the buffer is empty        
        if(br.ready()){
            br.lines().forEach((line)->{   
                    //check if there is any regex
                if(separatorRegex.equals("")!=true){ 
                    //Remove extra characters like '.,!-?' but leave the ' as that can make different words. Also leave space for splitting
                	if(filterLines) line=line.replaceAll("[^a-zA-Z0-9\' ]","");

                    //Separate the line by the given regex then convert the result to list and finally add to our lines list
                    ArrayList<String> words=new ArrayList<>(Arrays.asList(line.split(separatorRegex)));


                    //Remove extra characters like '.,!-?' but leave the ' as that can make different words by mapping the replaceAll to each word
                    /*words.forEach((word) -> {
                        java.util.Collections.replaceAll(words,word,word.replaceAll("[^a-zA-Z0-9\']", ""));
                    });*/
                    //Add the words to the line
                    this.addLine(words); 
                }else{
                    //Add the line to a temporary list
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add(line);
                    //Add the temporary list with the line to the main list
                    this.addLine(tmp);
                }
            });
        }
        //Close the buffered reader
        br.close();        
    }
    
    //This method should be overridden by the child classes
    //I need it here as otherwise I get a runtime error because of casting the child classes
    @Override
    public String getContentType(){
        return "";
    }
    
    
    //Clones the current parser object
    @Override
    public Parser clone() throws CloneNotSupportedException{
        try {
            //Return the clone of this object
            return (Parser)super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.clone();
    }
    
    
}
