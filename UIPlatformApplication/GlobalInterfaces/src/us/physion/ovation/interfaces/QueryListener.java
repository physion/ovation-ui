package us.physion.ovation.interfaces;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.Callable;
import javax.swing.SwingUtilities;

//TODO: call it something other than a listner
public class QueryListener {//implements PropertyChangeListener{

    private Runnable toRun;

    public QueryListener(Runnable r)
    {
        toRun = r;
    }
    
    public void run()
    {
        toRun.run();
    }

    /*@Override
	public void propertyChange(PropertyChangeEvent pce) {
        if (pce.getPropertyName().equals("ovation.queryChanged"))
	    {
		toRun.run();
	    }
    }*/

}