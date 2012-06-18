/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physionconsulting.ovation.browser;

import java.util.HashMap;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//us.physionconsulting.ovation.browser//SourceBrowser//EN",
autostore = false)
@TopComponent.Description(preferredID = "SourceBrowserTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "us.physionconsulting.ovation.browser.SourceBrowserTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_SourceBrowserAction",
preferredID = "SourceBrowserTopComponent")
@Messages({
    "CTL_SourceBrowserAction=Source Navigator",
    "CTL_SourceBrowserTopComponent=Source Navigator",
    "HINT_SourceBrowserTopComponent=Browse your Ovation Dataset starting from the Source hierarchy"
})
public final class SourceBrowserTopComponent extends TopComponent implements ExplorerManager.Provider{

    private ExplorerManager em = new ExplorerManager();
    private HashMap browserMap = new HashMap<String, Node>();
    public SourceBrowserTopComponent() {
        initComponents();
        setName(Bundle.CTL_SourceBrowserTopComponent());
        setToolTipText(Bundle.HINT_SourceBrowserTopComponent());
        
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
        
        BrowserUtilities.initBrowser((BeanTreeView)jScrollPane1, em, browserMap, false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane1 = new BeanTreeView();
        queryButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(SourceBrowserTopComponent.class, "SourceBrowserTopComponent.jButton1.text")); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        org.openide.awt.Mnemonics.setLocalizedText(queryButton, org.openide.util.NbBundle.getMessage(SourceBrowserTopComponent.class, "SourceBrowserTopComponent.queryButton.text")); // NOI18N
        queryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queryButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(queryButton)
                .addGap(0, 319, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(queryButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void queryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queryButtonActionPerformed
        BrowserUtilities.queryActionPerformed((BeanTreeView)jScrollPane1, em, browserMap, false);
    }//GEN-LAST:event_queryButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton queryButton;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }
}
