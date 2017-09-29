/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.java2;


import ie.gmit.java2.gui.MainWindow;

import org.slf4j.*;

/**
 *
 * @author user
 */

public class TextAnalyser {
    public static final Logger LOG = LoggerFactory.getLogger(TextAnalyser.class);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

         /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            /*for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }*/
            //Set the system look and feel
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
         /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            //Open my the main window
            new MainWindow().setVisible(true);

        });
    }

    public static void runThread(Runnable task){
        //Create a new thread for looking up the file/opening a file as it may take some time. Or even the http request can time out.
        //Could use swingworker here and use PropertyChangeListener to wait for the file/url check to finish. 
        //I decided to not to as we are not really going to do anything with swing in college
        java.util.concurrent.ExecutorService executor =java.util.concurrent.Executors.newFixedThreadPool(1);
        //Start execution
        executor.submit(task);
        //Shotdown the thread when the task is finished
        executor.shutdown();
    }
}


