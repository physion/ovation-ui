/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.importer;

import javax.swing.JTextField;
import org.openide.util.ChangeSupport;

/**
 *
 * @author jackie
 */
public class ResponseDetailsPanel extends javax.swing.JPanel {

    private ChangeSupport cs;
    private int responseNumber;
    private String url;
    private String uti;
    private double[] samplingRates;
    private String[] samplingRateUnits;
    private long[] shape;
    private String[] dimensionLabels;
    private String units;
    /**
     * Creates new form ResponseDetailsPanel
     */
    public ResponseDetailsPanel(ChangeSupport cs, int rNum) {
        this.cs = cs;
        responseNumber = rNum;
        initComponents();
    }

    @Override
    public String getName() {
        return "Response " + (responseNumber +1) + ": Metadata";
    }
    
    public void setUnits(String units)
    {   
        if (units == null)
        {
            this.unitsTextField.setEditable(true);
            return;
        }
        this.units = units;
        this.unitsTextField.setText(units);
        this.unitsTextField.setEditable(false);
    }
    
    public void setURL(String url)
    {   
        if (url == null)
        {
            this.urlTextField1.setEditable(true);
            return;
        }
        this.url = url;
        this.urlTextField1.setText(url);
        this.urlTextField1.setEditable(false);
    }
    
    public void setShape(long[] shape)
    {   
        if (shape == null)
        {
            this.shapeTextField.setEditable(true);
            return;
        }
        this.shape = shape;
        this.shapeTextField.setText(convertFromLongArray(shape));
        this.shapeTextField.setEditable(false);
    }
    
    public void setSamplingRates(double[] samplingRates)
    {   
        if (samplingRates == null)
        {
            this.samplingRateTextField.setEditable(true);
            return;
        }
        this.samplingRates = samplingRates;
        this.samplingRateTextField.setText(convertFromDoubleArray(samplingRates));
        this.samplingRateTextField.setEditable(false);
    }
    
    public void setSamplingRateUnits(String[] samplingUnits)
    {   
        if (samplingUnits == null)
        {
            this.samplingUnitTextField1.setEditable(true);
            return;
        }
        this.samplingRateUnits = samplingUnits;
        this.samplingUnitTextField1.setText(convertFromStringArray(samplingRateUnits));
        this.samplingUnitTextField1.setEditable(false);
    }
    
    public void setDimensionLabels(String[] dimensionLabels)
    {   
        if (dimensionLabels == null)
        {
            this.dimensionLabelTextField.setEditable(true);
            return;
        }
        this.dimensionLabels = dimensionLabels;
        this.dimensionLabelTextField.setText(convertFromStringArray(dimensionLabels));
        this.dimensionLabelTextField.setEditable(true);
    }
    
    public void setUTI(String uti)
    {   
        if (uti == null)
        {
            this.utiTextField.setEditable(true);
            return;
        }
        this.uti = uti;
        this.utiTextField.setText(uti);
        this.utiTextField.setEditable(true);
    }
    
    public String getUnits()
    {
        return units;
    }
    
    public String getUTI()
    {
        return uti;
    }
    
    public String getURL()
    {
        return url;
    }
    
    public String[] getSamplingRateUnits()
    {
        return samplingRateUnits;
    }
    
    public String[] getDimensionLabels()
    {
        return dimensionLabels;
    }
    
    public long[] getShape()
    {
        return shape;
    }
    
    public double[] getSamplingRates()
    {
        return samplingRates;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        urlTextField1 = new javax.swing.JTextField();
        unitsTextField = new javax.swing.JTextField();
        shapeTextField = new javax.swing.JTextField();
        dimensionLabelTextField = new javax.swing.JTextField();
        samplingRateTextField = new javax.swing.JTextField();
        samplingUnitTextField1 = new javax.swing.JTextField();
        utiTextField = new javax.swing.JTextField();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.jLabel3.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.jLabel4.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.jLabel5.text")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.jLabel6.text")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.jLabel7.text")); // NOI18N

        urlTextField1.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.urlTextField1.text")); // NOI18N
        urlTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlTextField1ActionPerformed(evt);
            }
        });
        urlTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                urlTextField1KeyReleased(evt);
            }
        });

        unitsTextField.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.unitsTextField.text")); // NOI18N
        unitsTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unitsTextFieldActionPerformed(evt);
            }
        });

        shapeTextField.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.shapeTextField.text")); // NOI18N
        shapeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapeTextFieldActionPerformed(evt);
            }
        });

        dimensionLabelTextField.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.dimensionLabelTextField.text")); // NOI18N
        dimensionLabelTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dimensionLabelTextFieldActionPerformed(evt);
            }
        });
        dimensionLabelTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dimensionLabelTextFieldKeyReleased(evt);
            }
        });

        samplingRateTextField.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.samplingRateTextField.text")); // NOI18N
        samplingRateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                samplingRateTextFieldActionPerformed(evt);
            }
        });

        samplingUnitTextField1.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.samplingUnitTextField1.text")); // NOI18N
        samplingUnitTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                samplingUnitTextField1ActionPerformed(evt);
            }
        });

        utiTextField.setText(org.openide.util.NbBundle.getMessage(ResponseDetailsPanel.class, "ResponseDetailsPanel.utiTextField.text")); // NOI18N
        utiTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utiTextFieldActionPerformed(evt);
            }
        });
        utiTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                utiTextFieldKeyReleased(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel6)
                    .add(jLabel3)
                    .add(jLabel1)
                    .add(jLabel2)
                    .add(jLabel4)
                    .add(jLabel5)
                    .add(jLabel7))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(utiTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                    .add(samplingUnitTextField1)
                    .add(urlTextField1)
                    .add(unitsTextField)
                    .add(shapeTextField)
                    .add(dimensionLabelTextField)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, samplingRateTextField)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(urlTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(unitsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(shapeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(dimensionLabelTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(samplingRateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(samplingUnitTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(utiTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7))
                .addContainerGap(62, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void urlTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlTextField1ActionPerformed

    }//GEN-LAST:event_urlTextField1ActionPerformed

    private void unitsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unitsTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unitsTextFieldActionPerformed

    private void shapeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shapeTextFieldActionPerformed

    private void dimensionLabelTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dimensionLabelTextFieldActionPerformed
        
    }//GEN-LAST:event_dimensionLabelTextFieldActionPerformed

    private void samplingRateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_samplingRateTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_samplingRateTextFieldActionPerformed

    private void samplingUnitTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_samplingUnitTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_samplingUnitTextField1ActionPerformed

    private void utiTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utiTextFieldActionPerformed
        
    }//GEN-LAST:event_utiTextFieldActionPerformed

    private void urlTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_urlTextField1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_urlTextField1KeyReleased

    private void utiTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_utiTextFieldKeyReleased
        String newuti = utiTextField.getText();
        boolean newVarIsEmpty = (newuti == null || newuti.isEmpty());
        boolean oldVarIsEmpty = (uti == null || uti.isEmpty());
        
        uti = newuti;
        if (newVarIsEmpty != oldVarIsEmpty) {
            cs.fireChange();
        }
    }//GEN-LAST:event_utiTextFieldKeyReleased

    private void dimensionLabelTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dimensionLabelTextFieldKeyReleased
        String newDimensionLabels = dimensionLabelTextField.getText();
        boolean newVarIsEmpty = (newDimensionLabels == null || newDimensionLabels.isEmpty());
        boolean oldVarIsEmpty = (dimensionLabels == null || dimensionLabels.length == 0);
        
        String[] newVar = convertToStringArray(newDimensionLabels);
        if (newVar != null) {
            dimensionLabels = newVar;
            if (newVarIsEmpty != oldVarIsEmpty) {
                cs.fireChange();
            }
        }
    }//GEN-LAST:event_dimensionLabelTextFieldKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dimensionLabelTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField samplingRateTextField;
    private javax.swing.JTextField samplingUnitTextField1;
    private javax.swing.JTextField shapeTextField;
    private javax.swing.JTextField unitsTextField;
    private javax.swing.JTextField urlTextField1;
    private javax.swing.JTextField utiTextField;
    // End of variables declaration//GEN-END:variables

    private String[] convertToStringArray(String arr) {
        if (arr.startsWith("{") && arr.endsWith("}")) {
            String[] newS = arr.substring(1, arr.length() - 1).split(",");

            for (int i = 0; i < newS.length; i++) {
                newS[i] = newS[i].trim();
            }
            return newS;
        }
        return null;
    }
    private String convertFromStringArray(String[] arr) {
        String newS = "{";
        for (String s : arr)
        {
            newS += s + ", ";
        }
        if (arr.length != 0)
        {
            newS = newS.substring(0, newS.length() - 2);
        }
        newS += "}";
        return newS;
    }
    
    private double[] convertToDoubleArray(String arr) {
        if (arr.startsWith("{") && arr.endsWith("}")) {
            String[] newS = arr.substring(1, arr.length() - 1).split(",");
            double[] doubles = new double[newS.length];
            
            for (int i = 0; i < newS.length; i++) {
                doubles[i] = Double.valueOf(newS[i].trim());
            }
            return doubles;
        }
        return null;
    }
    private String convertFromDoubleArray(double[] arr) {
        String newS = "{";
        for (double s : arr)
        {
            newS += s + ", ";
        }
        if (arr.length != 0)
        {
            newS = newS.substring(0, newS.length() - 2);
        }
        newS += "}";
        return newS;
    }
    
    private long[] convertToLongArray(String arr) {
        if (arr.startsWith("{") && arr.endsWith("}")) {
            String[] newS = arr.substring(1, arr.length() - 1).split(",");
            long[] longs = new long[newS.length];
            
            for (int i = 0; i < newS.length; i++) {
                longs[i] = Long.valueOf(newS[i].trim());
            }
            return longs;
        }
        return null;
    }
    
    private String convertFromLongArray(long[] arr) {
        String newS = "{";
        for (double s : arr)
        {
            newS += s + ", ";
        }
        if (arr.length != 0)
        {
            newS = newS.substring(0, newS.length() - 2);
        }
        newS += "}";
        return newS;
    }
}
