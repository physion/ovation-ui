/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.dbconnection;

import javax.swing.JFrame;
import org.openide.util.Exceptions;
import ovation.DataStoreCoordinator;
import us.physion.ovation.interfaces.EventQueueUtilities;
import us.physion.ovation.interfaces.ModalDialogBase;

/**
 *
 * @author huecotanks
 */
public class ShouldRunUpdaterDialog extends ModalDialogBase implements CancellableDialog{

    /**
     * Creates new form ShouldRunUpdaterDialog
     */
    public ShouldRunUpdaterDialog() {
        super();
        initComponents();
        this.getRootPane().setDefaultButton(cancelButton);
    }
    
    private boolean cancelled = false;

    public boolean isCancelled(){ 
        return cancelled;
    }
    
   
    public void showDialog()
    {
        try {
            EventQueueUtilities.runAndWaitOnEDT(new Runnable(){

                @Override
                public void run() {
                    ShouldRunUpdaterDialog.this.setLocationRelativeTo(null);
                    ShouldRunUpdaterDialog.this.pack();
                    ShouldRunUpdaterDialog.this.setVisible(true);
                }
            });
        } catch (InterruptedException ex) {
            cancelled = true;
            this.disposeOnEDT();
            Exceptions.printStackTrace(ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        outOfDateLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        shouldUpgradeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        outOfDateLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        outOfDateLabel.setText(org.openide.util.NbBundle.getMessage(ShouldRunUpdaterDialog.class, "ShouldRunUpdaterDialog.outOfDateLabel.text")); // NOI18N

        okButton.setText(org.openide.util.NbBundle.getMessage(ShouldRunUpdaterDialog.class, "ShouldRunUpdaterDialog.okButton.text")); // NOI18N
        okButton.setFocusPainted(false);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(org.openide.util.NbBundle.getMessage(ShouldRunUpdaterDialog.class, "ShouldRunUpdaterDialog.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        shouldUpgradeLabel.setText(org.openide.util.NbBundle.getMessage(ShouldRunUpdaterDialog.class, "ShouldRunUpdaterDialog.shouldUpgradeLabel.text")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(162, 162, 162)
                        .add(cancelButton)
                        .add(18, 18, 18)
                        .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(31, 31, 31)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(shouldUpgradeLabel)
                            .add(outOfDateLabel))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(25, 25, 25)
                .add(outOfDateLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(shouldUpgradeLabel)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton)
                    .add(okButton))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        cancel();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    public void cancel()
    {
        cancelled = true;
        dispose();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ShouldRunUpdaterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShouldRunUpdaterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShouldRunUpdaterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShouldRunUpdaterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                ShouldRunUpdaterDialog dialog = new ShouldRunUpdaterDialog();
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel outOfDateLabel;
    private javax.swing.JLabel shouldUpgradeLabel;
    // End of variables declaration//GEN-END:variables
}
