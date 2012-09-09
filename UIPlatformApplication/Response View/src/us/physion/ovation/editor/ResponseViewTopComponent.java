/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.editor;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.display.SingleImagePanel;
import com.pixelmed.display.SourceImage;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.FutureTask;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import ovation.*;
import us.physion.ovation.interfaces.ConnectionProvider;
import org.jfree.data.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.RectangleInsets;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.*;
import us.physion.ovation.interfaces.EventQueueUtilities;
import us.physion.ovation.interfaces.IEntityWrapper;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//us.physion.ovation.editor//ResponseView//EN",
autostore = false)
@TopComponent.Description(preferredID = "ResponseViewTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "us.physion.ovation.editor.ResponseViewTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_ResponseViewAction",
preferredID = "ResponseViewTopComponent")
@Messages({
    "CTL_ResponseViewAction=Response View",
    "CTL_ResponseViewTopComponent=Response Viewer",
    "HINT_ResponseViewTopComponent=This plots the currently selected numeric Response data"
})
public final class ResponseViewTopComponent extends TopComponent {

    Lookup.Result global;
    List<ResponsePanel> responsePanels = new ArrayList<ResponsePanel>();
    ChartTableModel chartModel = new ChartTableModel(responsePanels);
    Lookup l;
    private LookupListener listener = new LookupListener() {

        @Override
        public void resultChanged(LookupEvent le) {

            //TODO: we should have some other Interface for things that can update the tags view
            //then we could get rid of the Library dependancy on the Explorer API
            if (TopComponent.getRegistry().getActivated() instanceof ExplorerManager.Provider) {
                updateEntitySelection();
            }
        }
    };

    public ResponseViewTopComponent() {
        initTopComponent();
    }

    private void initTopComponent() {
        initComponents();
        setName(Bundle.CTL_ResponseViewTopComponent());
        setToolTipText(Bundle.HINT_ResponseViewTopComponent());
        global = Utilities.actionsGlobalContext().lookupResult(IEntityWrapper.class);
        global.addLookupListener(listener);
        jTable1.setDefaultRenderer(ResponsePanel.class, new ResponseCellRenderer());
        jTable1.setVisible(true);
        responseListPane.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        responseListPane = new BeanTreeView();
        jTable1 = new javax.swing.JTable();

        jTable1.setModel(chartModel);
        responseListPane.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(responseListPane, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(responseListPane, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable jTable1;
    private javax.swing.JScrollPane responseListPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
        //responseListPane.setVisible(false);
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

    protected void updateEntitySelection() {
        final Collection<? extends IEntityWrapper> entities = global.allInstances();
        Runnable r = new Runnable() {

            @Override
            public void run() {
                updateEntitySelection(entities);
            }
        };

        EventQueueUtilities.runOffEDT(r);
    }

    protected List<ResponseGroupWrapper> updateEntitySelection(Collection<? extends IEntityWrapper> entities) {
        
        LinkedList<ResponseWrapper> responseList = new LinkedList<ResponseWrapper>();

        for (IEntityWrapper ew : entities) {
            if (ew.getType().isAssignableFrom(Epoch.class)) {
                Epoch epoch = (Epoch) (ew.getEntity());//getEntity gets the context for the given thread
                for (String name : epoch.getResponseNames()) {
                    ResponseWrapper entity = ResponseWrapperFactory.create(epoch.getResponse(name));
                    if (entity != null) {
                        responseList.add(entity);
                    }

                }

            } else if (ew.getType().isAssignableFrom(Response.class) || ew.getType().isAssignableFrom(URLResponse.class)) {
                ResponseWrapper entity = ResponseWrapperFactory.create((Response) (ew.getEntity()));
                if (entity != null) {
                    responseList.add(entity);
                }
            }
        }

        List<ResponseGroupWrapper> responseGroups = new LinkedList<ResponseGroupWrapper>();

        for (ResponseWrapper rw : responseList) {
            boolean added = false;
            for (ResponseGroupWrapper group : responseGroups) {
                if (group.shouldAdd(rw)) {
                    group.add(rw);
                    added = true;
                    break;
                }

            }
            if (!added) {
                responseGroups.add(rw.createGroup());
            }
        }
        EventQueueUtilities.runOnEDT(updateChartRunnable(responseGroups));
        return responseGroups;
    }
    
    protected static void error(String s)
    {
        JDialog d = new JDialog(new JFrame(), true);
        d.setPreferredSize(new Dimension(500, 500));
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        d.setLocationRelativeTo(null);
        JLabel l = new JLabel();
        l.setText(s);
        d.add(l);
        d.setVisible(true);
        
    }

    private Runnable updateChartRunnable(final List<ResponseGroupWrapper> responseGroups) {
        final int height = this.getHeight();
        return new Runnable() {

            @Override
            public void run() {
                
                
                int initialSize = responsePanels.size();
                while (!responsePanels.isEmpty()) {
                    responsePanels.remove(0);
                }
                if (responseGroups.size() < initialSize) {
                    chartModel.fireTableRowsDeleted(responseGroups.size(), initialSize - 1);
                }
                if (responseGroups.size() != 0) {
                    int rowheight = (height / responseGroups.size());
                    if (rowheight >= 1) {
                        jTable1.setRowHeight(rowheight);
                    }
                    for (ResponseGroupWrapper c : responseGroups) {
                        Component p = c.generatePanel();

                        responsePanels.add(new ResponsePanel(p));

                    }
                }
                chartModel.fireTableDataChanged();
            }
        };
    }

    //for testing
    protected ChartTableModel getChartTableModel() {
        return chartModel;
    }
}
