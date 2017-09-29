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
public interface Readable{
    //Set path
    public void setSource(Object o);
    //Check if the source is available
    public boolean availableSource();
    //Get source error message
    public String getErrorMessage();
    //Get the name of the source
    public String getSourceName();
    //Get size of source
    public long getSizeofSource();
    //Get the content of source
    public boolean bufferContent(String separatorRegex);
    //Get Content Type
    public String getContentType();
    
}
