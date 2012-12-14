/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.browser.insertion;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import org.joda.time.DateTime;
import org.openide.WizardDescriptor;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import ovation.*;
import us.physion.ovation.browser.moveme.*;
import us.physion.ovation.interfaces.IEntityWrapper;

@ServiceProviders(value={
    @ServiceProvider(service=ExperimentInsertable.class),
    @ServiceProvider(service=EpochGroupInsertable.class)
})
/**
 *
 * @author huecotanks
 */
public class InsertEpochGroup extends InsertEntity implements EpochGroupInsertable, ExperimentInsertable {

    public InsertEpochGroup() {
        putValue(NAME, "Insert Epoch Group");
    }

    public List<WizardDescriptor.Panel<WizardDescriptor>> getPanels()
    {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new InsertAnalysisRecordWizardPanel1());
        panels.add(new InsertAnalysisRecordWizardPanel2());
        panels.add(new InsertAnalysisRecordWizardPanel3()); 
        return panels;
    }

    @Override
    public void wizardFinished(WizardDescriptor wiz, IAuthenticatedDataStoreCoordinator dsc, IEntityWrapper parent)
    {
        if (parent.getType().isAssignableFrom(Experiment.class))
            ((Experiment)parent.getEntity()).insertEpochGroup(((Source)wiz.getProperty("epochGroup.source")),
                    ((String)wiz.getProperty("epochGroup.label")),
                    ((DateTime)wiz.getProperty("epochGroup.start")),
                    ((DateTime)wiz.getProperty("epochGroup.end")));
        else if (parent.getType().isAssignableFrom(EpochGroup.class))
            ((EpochGroup)parent.getEntity()).insertEpochGroup(((Source)wiz.getProperty("epochGroup.source")),
                    ((String)wiz.getProperty("epochGroup.label")),
                    ((DateTime)wiz.getProperty("epochGroup.start")),
                    ((DateTime)wiz.getProperty("epochGroup.end")));
    }
}
