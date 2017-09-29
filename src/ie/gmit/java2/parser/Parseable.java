/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.java2.parser;


/**
 *
 * @author user
 */
public interface Parseable{
     /**
     * Sets the source file 
     * @param Object type of Object 
     */
    public void setSource(Object Object);
    
    /**
     * Check if the source is available
     * @return boolean
     */
    public boolean availableSource();
    
    /**
     * Get source error message
     * @return String
     */
    public String getErrorMessage();
   
    /*
     * Get the name of the source
     * @return String
     */
    public String getSourceName();
    
    /**
     * Get size of source
     * @return long size of content in bytes
     */
    public long getSizeofSource();
    
    /**
     * Buffer the parsed content by a regex.
     * If the regex is an empty String then the line will not be separated
     * @param separatorRegex
     * @return boolean
     */
    public boolean bufferContent(String separatorRegex,boolean filterLines);
    
    /**
     * Get Content Type
     * @return String
     */
    public String getContentType();

    /**
     * Get the parsed lines
     * @return LinesMap
     */
    public LinesMap getParsedLines();
}
