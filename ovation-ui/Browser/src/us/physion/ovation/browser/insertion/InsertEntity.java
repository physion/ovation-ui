/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.browser.insertion;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractAction;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import ovation.IAuthenticatedDataStoreCoordinator;
import us.physion.ovation.browser.moveme.EntityInsertable;
import us.physion.ovation.browser.moveme.ProjectInsertable;
import us.physion.ovation.interfaces.ConnectionProvider;
import us.physion.ovation.interfaces.IEntityWrapper;

/**
 *
 * @author jackie
 */
public class InsertEntity extends AbstractAction implements EntityInsertable{

    @Override
    public void actionPerformed(ActionEvent ae) {
        WizardDescriptor wiz = new WizardDescriptor(new InsertEntityIterator(getPanels()));
        //             // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
        //             // {1} will be replaced by WizardDescriptor.Iterator.name()
        wiz.setTitleFormat(new MessageFormat("{0} ({1})"));
        wiz.setTitle("...dialog title...");
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            wizardFinished(wiz, Lookup.getDefault().lookup(ConnectionProvider.class).getConnection(), null);
        }
    }

    @Override
    public int getPosition() {
        return 100;
    }

    @Override
    public int compareTo(Object t) {
        if (t instanceof EntityInsertable)
        {
            return getPosition() - ((EntityInsertable)t).getPosition();
        }
        return -1;
    }
    
    public IEntityWrapper getEntity()
    {
        Collection<? extends IEntityWrapper> entities = Utilities.actionsGlobalContext().lookupResult(IEntityWrapper.class).allInstances();
        if (entities.size() == 1)
        {
            return entities.iterator().next();
        }
        return null;
    }

    @Override
    public List<Panel<WizardDescriptor>> getPanels() {
        throw new UnsupportedOperationException("Subclasses of InsertEntity should implement the getPanels method");
    }

    @Override
    public void wizardFinished(WizardDescriptor wiz, IAuthenticatedDataStoreCoordinator dsc, IEntityWrapper ew) {
        throw new UnsupportedOperationException("Subclasses of InsertEntity should implement the wizardFinished method");
    }
    
}
