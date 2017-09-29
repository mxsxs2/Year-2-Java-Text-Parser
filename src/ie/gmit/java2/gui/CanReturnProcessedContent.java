/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.java2.gui;

/**
 *
 * @author user
 * This interface is to making sure the integrity of each panel in the results collection 
 */
public interface CanReturnProcessedContent {
    /**
     *Set the content of the processor
     * @param Lines - LinesMap
     */ 
    void setProcessorContent(ie.gmit.java2.parser.LinesMap Lines);
    
    /**
     *Get the content of the processor
     * @return LinesMap
     */ 
    ie.gmit.java2.parser.LinesMap getProcessorContent();
    
    /**
     * Return the last modified time of the content of the processor
     * @return long
     */ 
    long getLastModifiedTime();
}
