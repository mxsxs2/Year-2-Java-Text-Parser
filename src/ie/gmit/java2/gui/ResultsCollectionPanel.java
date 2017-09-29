/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.java2.gui;

import ie.gmit.java2.parser.Parser;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author user
 */
@SuppressWarnings("serial")
public class ResultsCollectionPanel extends javax.swing.JPanel {

    /**
     * Creates new form Results
     * @param lines
     * @param parser
     */
    public ResultsCollectionPanel(ie.gmit.java2.parser.LinesMap lines, Parser parser) {
        //Initialize components
        initComponents();
        //initialize the tabs.
        this.initializeTabs(lines,parser);
    }
    
    /**
     * Create the new tabs add them to the tabbed pane and register an event listener to them
     *Both the lines and the parser are already a clone of the original ones so we can safely pass them to other classes.
     *At this way they will get the same copies originally
     * @param lines - ie.gmit.java2.parser.LinesMap
     * @param parser - ie.gmit.java2.parser.Parser
     */ 
    private void initializeTabs(ie.gmit.java2.parser.LinesMap lines,Parser parser){
        //Create Statistics Panel
        this.STP=new StatisticsPanel(lines.deepClone(), parser);
        //Create Search Panel
        this.SP=new SearchPanel(lines.deepClone());       
        //Create Modify Panel
        this.MP=new ModifyPanel(lines.deepClone());
        //Create Save Panel
        this.SVP=new SavePanel(lines.deepClone(), parser);
        //Add the statistics panel
                
        this.jTabbedPaneCollection.addTab("Statistics", this.STP);
        //Add the search tab 
        this.jTabbedPaneCollection.addTab("Search", this.SP);
        //Add the modify tab 
        this.jTabbedPaneCollection.addTab("Modify", this.MP);
        //Add the save tab 
        this.jTabbedPaneCollection.addTab("Save", this.SVP);

        //Add the change listener to the tabs
        this.jTabbedPaneCollection.addChangeListener((ChangeEvent e) -> {
            //Get the current tab
            CanReturnProcessedContent tab=(CanReturnProcessedContent)jTabbedPaneCollection.getSelectedComponent();
            //Set the lines in the current tab form the modifier tab
            //If you comment the below line the you can see all the tabs are working on their own "lines" set.
            //This clearly demonstrates the power of encapsulation and the deep copy of an element
            tab.setProcessorContent(MP.getProcessorContent());
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPaneResults = new javax.swing.JLayeredPane();

        jTabbedPaneCollection = new javax.swing.JTabbedPane();

        jLayeredPaneResults.setMaximumSize(new java.awt.Dimension(413, 218));
        jLayeredPaneResults.setMinimumSize(new java.awt.Dimension(413, 218));

        jLayeredPaneResults.setLayer(jTabbedPaneCollection, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPaneResultsLayout = new javax.swing.GroupLayout(jLayeredPaneResults);
        jLayeredPaneResults.setLayout(jLayeredPaneResultsLayout);
        jLayeredPaneResultsLayout.setHorizontalGroup(
            jLayeredPaneResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneResultsLayout.createSequentialGroup()
                .addComponent(jTabbedPaneCollection, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jLayeredPaneResultsLayout.setVerticalGroup(
            jLayeredPaneResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneResultsLayout.createSequentialGroup()
                .addComponent(jTabbedPaneCollection, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneResults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPaneResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

   
    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed

    }//GEN-LAST:event_jButtonRemoveActionPerformed

    /**
     * Set the loading time
     * @param timeInSec - long
     */
    public void setLoadingTime(long timeInSec){
    	this.STP.setLoadingTime(((float)timeInSec/(float)1000)+"s");
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPaneResults;
    private javax.swing.JTabbedPane jTabbedPaneCollection;
    // End of variables declaration//GEN-END:variables
    
    //Define the tabs
    private ModifyPanel     MP;
    private SearchPanel     SP;
    private SavePanel       SVP;
    private StatisticsPanel STP;
}
