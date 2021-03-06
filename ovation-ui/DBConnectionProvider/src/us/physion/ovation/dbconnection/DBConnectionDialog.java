/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.dbconnection;

import com.objy.db.DatabaseNotFoundException;
import com.objy.db.DatabaseOpenException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import org.netbeans.api.autoupdate.*;
import org.netbeans.api.autoupdate.OperationContainer.OperationInfo;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.ErrorManager;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import ovation.*;
import us.physion.ovation.interfaces.*;

/**
 *
 * @author huecotanks
 */
public class DBConnectionDialog extends javax.swing.JDialog implements ConnectionDialog{

    ConnectionHistoryProvider prefs;
    DefaultComboBoxModel connectionComboBoxModel;
    IAuthenticatedDataStoreCoordinator dsc;
    ProgressHandle progressHandle;

    DBConnectionManager manager;
    
    public void setConnectionManager(DBConnectionManager m)
    {
        manager = m;
    }

    public IAuthenticatedDataStoreCoordinator getDataStoreCoordinator() {
        return dsc;
    }

    /**
     * Creates new form DBConnectionDialog
     */
    public DBConnectionDialog(java.awt.Frame parent, boolean modal, ConnectionHistoryProvider prefProvider) {
        super(parent, modal);
        initComponents();

        // Set up default and cancel buttons
        this.getRootPane().setDefaultButton(connectButton);
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "CANCEL");
        getRootPane().getActionMap().put("CANCEL", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAction(e);
            }
        });

        this.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (dsc == null) {
                    manager.cancel();
                }
                disposeOnEDT();
            }
        });

        prefs = prefProvider;
        connectionComboBoxModel = new DefaultComboBoxModel(prefs.getConnectionHistory().toArray(new String[0]));
        connectionFileComboBox.setModel(connectionComboBoxModel);
        errorScrollPane.setVisible(false);
    }

    /**
     * Creates new form DBConnectionDialog
     */
    public DBConnectionDialog() {
        this(new JFrame(), true, new JavaPreferenceProvider(java.util.prefs.Preferences.userNodeForPackage(DBConnectionDialog.class)));
    }

    public void showErrors(final Exception e, DataStoreCoordinator toClose) {
        String message = "";
        try{
            if (toClose != null)
            {
                toClose.close();
            }
        }catch (Exception ex){ 
            message += "\n**Error thrown while closing database connection: " + ex.getLocalizedMessage();
        }
        
        final String otherError = message;
        EventQueueUtilities.runOnEDT(new Runnable() {

            @Override
            public void run() {
                errorScrollPane.setVisible(true);
                if (e instanceof UserAuthenticationException) {
                    errorTextArea.setText("**Error: Username and password combination was not found." + otherError);
                } else {
                    errorTextArea.setText("**Error: " + e.getLocalizedMessage() + otherError);
                }
                errorTextArea.setForeground(Color.RED);
                pack();
                repaint();
            }
        });
    }

    @Override
    public void startConnectionStatusBar()
    {
        if (progressHandle == null)
        {
            progressHandle = ProgressHandleFactory.createHandle("Connecting...");
            progressHandle.start();
        }
    }
    
    public void disposeOnEDT() {
        
        if (progressHandle != null)
        {
            EventQueueUtilities.runOffEDT(new Runnable() {

                @Override
                public void run() {
                    progressHandle.finish();
                }
            });
            
        }
        EventQueueUtilities.runOnEDT(new Runnable() {

            @Override
            public void run() {
                DBConnectionDialog.this.dispose();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        viewModel = new us.physion.ovation.dbconnection.ConnectionDialogModel();
        chooseButton = new javax.swing.JButton();
        connectionFileComboBox = new javax.swing.JComboBox();
        credentialsPanel = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        passwordTextField = new javax.swing.JPasswordField();
        errorScrollPane = new javax.swing.JScrollPane();
        errorTextArea = new javax.swing.JTextArea();
        connectionLabel = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(DBConnectionDialog.class, "DBConnectionDialog.title")); // NOI18N
        setFocusCycleRoot(false);
        setResizable(false);

        chooseButton.setText(org.openide.util.NbBundle.getMessage(DBConnectionDialog.class, "DBConnectionDialog.chooseButton.text")); // NOI18N
        chooseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseButtonActionPerformed(evt);
            }
        });

        connectionFileComboBox.setEditable(true);
        connectionFileComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<your connection file here>" }));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, viewModel, org.jdesktop.beansbinding.ELProperty.create("${connection}"), connectionFileComboBox, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        connectionFileComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                connectionFileComboBoxItemStateChanged(evt);
            }
        });

        credentialsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        usernameLabel.setText(org.openide.util.NbBundle.getMessage(DBConnectionDialog.class, "DBConnectionDialog.usernameLabel.text")); // NOI18N

        passwordLabel.setText(org.openide.util.NbBundle.getMessage(DBConnectionDialog.class, "DBConnectionDialog.passwordLabel.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, viewModel, org.jdesktop.beansbinding.ELProperty.create("${username}"), usernameTextField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, viewModel, org.jdesktop.beansbinding.ELProperty.create("${password}"), passwordTextField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout credentialsPanelLayout = new org.jdesktop.layout.GroupLayout(credentialsPanel);
        credentialsPanel.setLayout(credentialsPanelLayout);
        credentialsPanelLayout.setHorizontalGroup(
            credentialsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(credentialsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(credentialsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(passwordLabel)
                    .add(usernameLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(credentialsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(usernameTextField)
                    .add(passwordTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE))
                .addContainerGap())
        );
        credentialsPanelLayout.setVerticalGroup(
            credentialsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(credentialsPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(credentialsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(usernameLabel)
                    .add(usernameTextField))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(credentialsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(passwordLabel)
                    .add(passwordTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        errorTextArea.setColumns(20);
        errorTextArea.setRows(5);
        errorScrollPane.setViewportView(errorTextArea);

        connectionLabel.setText(org.openide.util.NbBundle.getMessage(DBConnectionDialog.class, "DBConnectionDialog.connectionLabel.text")); // NOI18N

        connectButton.setText(org.openide.util.NbBundle.getMessage(DBConnectionDialog.class, "DBConnectionDialog.connectButton.text")); // NOI18N
        connectButton.setToolTipText(org.openide.util.NbBundle.getMessage(DBConnectionDialog.class, "DBConnectionDialog.connectButton.toolTipText")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, viewModel, org.jdesktop.beansbinding.ELProperty.create("${complete}"), connectButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectAction(evt);
            }
        });

        cancelButton.setText(org.openide.util.NbBundle.getMessage(DBConnectionDialog.class, "DBConnectionDialog.cancelButton.text")); // NOI18N
        cancelButton.setToolTipText(org.openide.util.NbBundle.getMessage(DBConnectionDialog.class, "DBConnectionDialog.cancelButton.toolTipText")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAction(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(1, 1, 1)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(cancelButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(connectButton))
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(errorScrollPane)
                                .add(credentialsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(layout.createSequentialGroup()
                        .add(connectionLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(connectionFileComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 392, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chooseButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(connectionFileComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(chooseButton)
                    .add(connectionLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(credentialsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(errorScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(connectButton)
                    .add(cancelButton))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chooseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new BootFileFilter();
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(new JPanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //add to preferences
            String path = chooser.getSelectedFile().getAbsolutePath();
            connectionComboBoxModel.insertElementAt(path, 0);
            //connectionFileComboBox.addItem(path);
            connectionFileComboBox.setSelectedItem(path);
        }
    }//GEN-LAST:event_chooseButtonActionPerformed

    private void connectAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectAction
        
        //check the database schema version --scheam version logic could be simplified if we could add arbitrary metadata to UpgradeElements
        //if an update is required, search for all UpdateElements with the same specification number
        //run them in order -- determined either by spec number or some file
        //
        
        /*List<UpdateElement> toEnable = new LinkedList<UpdateElement>();
        List<UpdateElement> toDisable = new LinkedList<UpdateElement>();
        List<UpdateUnit> units = UpdateManager.getDefault().getUpdateUnits();
        for (UpdateUnit unit : units)
        {
            UpdateElement u = unit.getInstalled();
            System.out.println(u.getCodeName());
            if (u.getCodeName().equals("us.physion.ovation.update"))
            {
                if (u.isEnabled())
                {
                    Double.parseDouble(u.getSpecificationVersion());
                    System.out.println("Found update module");
                    //toDisable.add(u);
                }else{
                    Double.parseDouble(u.getSpecificationVersion());
                    System.out.println("Found update module");
                    toEnable.add(u);
                }
                
            }
        }
      
        for (UpdateElement enable : toEnable) {
            OperationContainer enabler = OperationContainer.createForEnable();

            enabler.add(enable);
            ProgressHandle ph = ProgressHandleFactory.createHandle("Enabling Updater");
            try {
                ((OperationSupport) enabler.getSupport()).doOperation(ph);
            } catch (OperationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
        for (UpdateElement disable : toDisable)
        {
            OperationContainer disabler = OperationContainer.createForDisable();

            disabler.add(disable);
            ProgressHandle ph = ProgressHandleFactory.createHandle("Disabling Old Updaters");
            try {
                ((OperationSupport) disabler.getSupport()).doOperation(ph);
            } catch (OperationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        System.out.println(Ovation.getBuildNumber());
        System.out.println("Enabled module...");
        Collection<? extends Updater> updaters = Lookup.getDefault().lookupAll(Updater.class);
        for (Updater up : updaters)
        {
            System.out.println("Found updater");
            up.runUpdate();
        }
        
        System.out.println(Ovation.getBuildNumber());
        */
        
       
        final String username = viewModel.getUsername();
        final String password = viewModel.getPassword();
        final String connectionFile = connectionFileComboBox.getSelectedItem().toString();
        prefs.addConnectionFile(connectionFile);
        
        manager.tryToConnect(username, password, connectionFile);
    }//GEN-LAST:event_connectAction

    private void cancelAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAction
        manager.cancel();
        disposeOnEDT();      
    }//GEN-LAST:event_cancelAction

    private void connectionFileComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_connectionFileComboBoxItemStateChanged
        String path = (String)connectionFileComboBox.getSelectedItem();
        if (path == null || path.isEmpty())
            return;
        File f = new File(path);
        if (!f.exists())
        {
            connectButton.setText("Create DB");
        }else{
            connectButton.setText("Connect");
        }
    }//GEN-LAST:event_connectionFileComboBoxItemStateChanged

    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton chooseButton;
    private javax.swing.JButton connectButton;
    private javax.swing.JComboBox connectionFileComboBox;
    private javax.swing.JLabel connectionLabel;
    private javax.swing.JPanel credentialsPanel;
    private javax.swing.JScrollPane errorScrollPane;
    private javax.swing.JTextArea errorTextArea;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTextField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    private us.physion.ovation.dbconnection.ConnectionDialogModel viewModel;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public void showDialog() {
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
   
}
