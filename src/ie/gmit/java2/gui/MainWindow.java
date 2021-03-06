/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.java2.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author user
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form Menu
     */
    public MainWindow() {
        //Get the dimensions of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //Build layout
        initComponents();
        //Set the window to center of the screen
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        this.jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jButtonExit = new javax.swing.JButton();
        jButtonParseAgain = new javax.swing.JButton();
        jLayeredPaneMain = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(521, 394));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Text Analyser");

        jButtonExit.setMnemonic('x');
        jButtonExit.setText("Exit");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });

        jButtonParseAgain.setMnemonic('P');
        jButtonParseAgain.setText("Parse Again");
        jButtonParseAgain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonParseAgainActionPerformed(evt);
            }
        });

        jLayeredPaneMain.setMaximumSize(new java.awt.Dimension(501, 301));
        jLayeredPaneMain.setMinimumSize(new java.awt.Dimension(501, 301));
        jLayeredPaneMain.setPreferredSize(new java.awt.Dimension(496, 306));
        //Add the menu JPane to the layer after creation
        this.jLayeredPaneMain.add(new MenuPanel(this.jLayeredPaneMain));
        jLayeredPaneMain.setLayout(new javax.swing.OverlayLayout(jLayeredPaneMain));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jLayeredPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonParseAgain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonExit)))
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addComponent(jLayeredPaneMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonExit)
                    .addComponent(jButtonParseAgain))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Get confirmation from the user before the window closes.
     * @param evt - java.awt.event.WindowEvent
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
         //Show confirmation dialog
        int close=JOptionPane.showConfirmDialog(null, "Would you like to close the application?", "Exit",JOptionPane.YES_NO_OPTION);
        //If yes was choose
        if(close==JOptionPane.YES_OPTION){
            //Exit the application
                System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * Add the action listener to the parse again button
     * @param evt - java.awt.event.ActionEvent
     */ 
    private void jButtonParseAgainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonParseAgainActionPerformed
        //clear the content of the main layer
        this.jLayeredPaneMain.removeAll();
        //Create a new menu and add it
        this.jLayeredPaneMain.add(new MenuPanel(this.jLayeredPaneMain));
        //Force the parent to get rid of old "painting"
        this.jLayeredPaneMain.repaint();
        //Re-validate the contents of the layer
        this.jLayeredPaneMain.revalidate();
    }//GEN-LAST:event_jButtonParseAgainActionPerformed
    
    /**
     * Add the click event to the exit button
     * @param evt - java.awt.event.ActionEvent
     */ 
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        //Dispatch the window closing event so the confirmation dialog will come up
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jButtonExitActionPerformed

    

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonParseAgain;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPaneMain;
    // End of variables declaration//GEN-END:variables

}
