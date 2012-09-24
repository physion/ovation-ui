/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.query;

import com.physion.ebuilder.expression.ExpressionTree;
import java.util.ArrayList;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import us.physion.ovation.interfaces.ConnectionProvider;
import us.physion.ovation.interfaces.ExpressionTreeProvider;
import us.physion.ovation.interfaces.QueryListener;

@ServiceProvider(service=ExpressionTreeProvider.class)
/**
 *
 * @author jackie
 */
public class QueryProvider implements ExpressionTreeProvider{

    private ExpressionTree et;
    private ArrayList<QueryListener> listeners = new ArrayList<QueryListener>();
    
    protected synchronized void setExpressionTree(ExpressionTree tree)
    {
        et = tree;
    }
    
    protected synchronized ArrayList<QueryListener> getListeners()
    {
        return listeners;
    }
    
    @Override
    public synchronized ExpressionTree getExpressionTree() {
        return et;
    }

    @Override
    public synchronized void addQueryListener(QueryListener ql) {
        listeners.add(ql);
    }

    @Override
    public synchronized void removeQueryListener(QueryListener ql) {
        listeners.remove(ql);
    }
 
}
