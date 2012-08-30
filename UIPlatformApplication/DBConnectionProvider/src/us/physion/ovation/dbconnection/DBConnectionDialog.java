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
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import org.netbeans.api.autoupdate.*;
import org.netbeans.api.autoupdate.OperationContainer.OperationInfo;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import ovation.*;
import us.physion.ovation.interfaces.*;

/**
 *
 * @author huecotanks
 */
public class DBConnectionDialog extends javax.swing.JDialog {

    ConnectionHistoryProvider prefs;
    DefaultComboBoxModel connectionComboBoxModel;
    IAuthenticatedDataStoreCoordinator dsc;
    Boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
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
                    cancelled = true;
                }
                dispose();
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

    private void showErrors(final Exception e) {
             
        EventQueueUtilities.runOnEDT(new Runnable() {

            @Override
            public void run() {
               // DBConnectionDialog.this.setVisible(true);
                errorScrollPane.setVisible(true);
                DBConnectionDialog.this.pack();
                if (e instanceof UserAuthenticationException) {
                    errorTextArea.setText("**Error: Username and password combination was not found.");
                } else {
                    errorTextArea.setText("**Error: " + e.getLocalizedMessage());
                }
                errorTextArea.setForeground(Color.RED);
            }
        });
        
    }
    
    private void disposeOnEDT() {
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
        //this.setVisible(false);
        
        //run this on a thread that's not the event queue thread
        Runnable r = new Runnable(){

            @Override
            public void run() {
                DataContext c = getContextFromConnectionFile(connectionFile, username, password);
                if (c == null) {
                    return;
                }

                boolean authenticated = false;
                try {
                    authenticated = c.authenticateUser(username, password);
                } catch (Exception ex) {
                    showErrors(ex);
                    return;
                }
                if (!authenticated) {
                    showErrors(new UserAuthenticationException());
                    return;
                }
                dsc = c.getAuthenticatedDataStoreCoordinator();
                cancelled = false;
                
                disposeOnEDT();
            }
            
        };
        
        EventQueueUtilities.runOffEDT(r);
       
    }//GEN-LAST:event_connectAction

    private void cancelAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAction
        cancelled = true;
        dispose();      
    }//GEN-LAST:event_cancelAction

    protected DataContext getContextFromConnectionFile(String connectionFile, String username, String password)
    {
        DataContext c = null;
        try {
            c = DataStoreCoordinator.coordinatorWithConnectionFile(connectionFile).getContext();
        } catch (SchemaVersionException ex2)
        {
            new ErrorDialog("Schema Exception").showDialog();

            int databaseVersion = ex2.getDatabaseSchemaNumber();
            int apiVersion = ex2.getAPISchemaNumber();
            boolean success = shouldRunUpdater(databaseVersion, apiVersion); //ask the user if they want to run the upgrader
            if (success)
            {
                Collection<? extends UpdateInfo> updates = Lookup.getDefault().lookupAll(UpdateInfo.class);
                List<UpdateInfo> versions = new LinkedList<UpdateInfo>();
                for (UpdateInfo u : updates)
                {
                    int updateVersion = u.getSchemaVersion();
                    if (updateVersion > databaseVersion && updateVersion <= apiVersion)
                    {
                        versions.add(u);
                    }
                }
                Collections.sort(versions, new UpdateComparator());
                UpdaterInProgressDialog uiUpdater = new UpdaterInProgressDialog();
                UpgradeTool tool = new UpgradeTool(versions, connectionFile, username, password, uiUpdater);
                uiUpdater.setUpgradeTool(tool);
                try{
                    new ErrorDialog("Running updater").showDialog();
                    success = runUpdater(tool, uiUpdater, true);
                    if (success)
                        new ErrorDialog("Success").showDialog();
                    else{
                        new ErrorDialog("Failure, but no exception? Weird").showDialog();
                    }
                } catch (Exception e)
                {
                    cancelled = true;
                    showErrors(e);
                    return c;
                }
            }
            if (success)
            {
                new ErrorDialog("Getting context again").showDialog();
                
                try {
                    c = DataStoreCoordinator.coordinatorWithConnectionFile(connectionFile).getContext();
                } catch (DatabaseOpenException ex) {
                    showErrors(ex);
                    return c;
                } catch (DatabaseNotFoundException ex) {
                    showErrors(ex);
                    return c;
                }
            }
            else{
                cancelled = true;
                return c;
            }
        }
        catch (DatabaseIsUpgradingException ex)
        {
            DBIsUpgradingDialog d = new DBIsUpgradingDialog();
            d.showDialog();
            if (d.forceUpgrade())
            {
                try {
                    DataStoreCoordinator.coordinatorWithConnectionFile(connectionFile).removeUpgradeLock("OVATION_UPGRADE_FLAG");
                } catch (DatabaseOpenException ex1) {
                    showErrors(ex1);
                    return c;
                } catch (DatabaseNotFoundException ex1) {
                    showErrors(ex1);
                    return c;
                }
               return getContextFromConnectionFile(connectionFile, username, password);
            }
                    
        }
        catch (Exception ex) {
            showErrors(ex);
            return c;
        }
        return c;
    }
    
    protected boolean shouldRunUpdater(int databaseVersion, int apiVersion)
    {
        return shouldRunUpdater(databaseVersion, apiVersion, true, null);
    }
    //dependency injection, for testing
    protected boolean shouldRunUpdater(int databaseVersion, int apiVersion, boolean showDialogs, ShouldRunUpdaterDialog shouldRun)
    {
        if (databaseVersion <0 || apiVersion <0 )
        {
            showErrors(new RuntimeException("Invalid database schema version (" + databaseVersion + ") or api schema version (" + apiVersion + ")"));
            return false;
        }
        
        if (databaseVersion > apiVersion)
        {
            if (showDialogs)
            {
                InstallLatestVersionDialog installVersionDialog = new InstallLatestVersionDialog();
                installVersionDialog.showDialog();
            }
            return true;
        }
        else if (databaseVersion < apiVersion)
        {
            if (shouldRun == null)
            {
                shouldRun = new ShouldRunUpdaterDialog();
            }
            if (showDialogs)
            {
                shouldRun.showDialog();
            }
            return !shouldRun.isCancelled();
        }
        return false;
    }
    
    protected boolean runUpdater(final IUpgradeDB tool, UpdaterInProgressDialog inProgress, boolean showDialogs)
    {
        if (showDialogs)
        {
            inProgress.showDialog();
        }
        try{
            tool.start();
        } catch (DatabaseIsUpgradingException e)
        {
            inProgress.cancel();
            //TODO: pop up a dialog here
            
        } catch (Exception e)
        {
            inProgress.cancel();
            throw new RuntimeException(e.getLocalizedMessage());
        }
        if (inProgress.isCancelled())
        {
            return false;
        }
        else{
            return true;
        }
    }
    
   
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

    protected void showDialog() {
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    
    class UpdateComparator implements Comparator<UpdateInfo>
    {
        @Override
        public int compare(UpdateInfo t, UpdateInfo t1) {
            return t.getSchemaVersion() - t1.getSchemaVersion();
        }
    }
}
