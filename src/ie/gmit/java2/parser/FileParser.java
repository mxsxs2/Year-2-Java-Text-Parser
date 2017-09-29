/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.java2.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;

/**
 *
 * @author user
 */
public class FileParser extends Parser{
    //variable for the file
    private File parsedFile;
    //variable for the buffered reader
    private BufferedReader br;
    //file error message
    private String fileErrorMessage="";
    
    @Override
    public void setSource(Object Object) {
        //Check if it is a file 
        if(Object instanceof File){
            //Cast it save it
            this.parsedFile = (File)Object;
        }else{
            //if not a file then cast to string and create a file
            this.parsedFile = new File((String)Object);
        }   
    }
    
    @Override
    public String getErrorMessage(){
        //Return the error message
        return this.fileErrorMessage;
    }
    
    @Override
    public String getSourceName(){
        //Return the name of the file
        return this.parsedFile.getName();
    }
    
    @Override
    public long getSizeofSource(){
        return this.parsedFile.length();
    }
    
    @Override
    public boolean availableSource() {
        //Check if the file exists and if it is actually a file
        if(this.parsedFile.exists() && this.parsedFile.canRead()) return this.parsedFile.isFile();
        //Set error message
        this.fileErrorMessage="The file does not exists or cannot read it.";
        //If not the return false;
        return false;
    }

    @Override
    public boolean bufferContent(String separatorRegex, boolean filterLines) {
        //Check if the file is available
        if(this.availableSource()){  
            try {        
                //Read in to buffer
                //this.br = java.nio.file.Files.newBufferedReader(this.parsedFile.toPath(), Charset.forName("UTF-16"));
                //this.br = new BufferedReader(new InputStreamReader(new FileInputStream(this.parsedFile)));
            	//Try to decode the file into a buffered reader
            	this.decodeFile(0);
            	//Return false if the buffered reader is still null
            	if(this.br==null) return false;
            	//Store the lines
                this.storeLines(this.br, separatorRegex,filterLines);
                return true;
            }catch (IOException ex) {
                    //Do nothing the file will be found anyways. We check this before.
                    ie.gmit.java2.TextAnalyser.LOG.debug(ex.getMessage(), ex);
                    
            }
        }
        return false;
    }
    
    /**
     * Auto detect the encoding of the file
     * @param charsetIndex
     */
    protected void decodeFile(int charsetIndex) throws IOException{
    	//Declare the encodings to try
    	String[] enc=this.getEncodings(); 	
    	try{
    		//Create a buffered reader with a given charset
    		BufferedReader br=Files.newBufferedReader(this.parsedFile.toPath(), Charset.forName(enc[charsetIndex]));
    		//Temporary line holder
    		String line;
    		//Try to read the first line
    		if((line=br.readLine()) != null){
    			//Close the stream
    			br.close();
    			//If the line could be read then "rewind" the buffered reader and return it
        		this.br=Files.newBufferedReader(this.parsedFile.toPath(), Charset.forName(enc[charsetIndex]));
        	}
    	}catch(MalformedInputException ex){
    		//If an exception was thrown (for example because of the wrong charset)
    		//Check if there is any more charset is available for testing
    		if(enc.length>++charsetIndex){
    			//Try to decode again with a different encoding
        		this.decodeFile(charsetIndex);
        	}
    	}
    }
    
    
    @Override
    public String getContentType(){
        //Set the base type
        String type="Not determinable";
        try {
            //Try to get the content type
            String t=java.nio.file.Files.probeContentType(this.parsedFile.toPath());
            //If we have a content type the override the type variable
            if(t!=null) type=t;
        } catch (IOException ex) {
            //Do nothing the file will be found anyways. We check this before.
            ie.gmit.java2.TextAnalyser.LOG.debug(ex.getMessage(), ex);
        }
        return type;
    }

}
