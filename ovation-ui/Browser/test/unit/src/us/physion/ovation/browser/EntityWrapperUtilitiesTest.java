/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.browser;

import us.physion.ovation.browser.QueryChildren;
import us.physion.ovation.browser.EntityWrapperUtilities;
import java.io.File;
import java.net.InetAddress;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import junit.framework.TestCase;
import org.apache.log4j.Level;
import org.junit.*;
import static org.junit.Assert.*;
import org.openide.ErrorManager;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import ovation.*;
import ovation.database.DatabaseManager;
import ovation.test.TestManager;
import us.physion.ovation.interfaces.OvationTestCase;
import us.physion.ovation.interfaces.IEntityWrapper;

/**
 *
 * @author jackie
 */
public class EntityWrapperUtilitiesTest extends OvationTestCase{

    ExplorerManager em;
    Map<String, Node> treeMap;
    static TestManager mgr = new BrowserTestManager();
    public EntityWrapperUtilitiesTest() {
        setTestManager(mgr); //this is because there are static and non-static methods that need to use the test manager
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        Ovation.enableLogging(LogLevel.ALL);
        AccessController.doPrivileged(new PrivilegedAction<Boolean>() {

            public Boolean run() {
                OvationTestCase.setUpDatabase(mgr, 2);
                return true;
            }
        });
        
    }
    
    @Before
    public void setUp() {
        dsc = setUpTest();

        treeMap = new HashMap<String, Node>();
        em = new ExplorerManager();
    }
    
    
    @After
    public void tearDown()
    {
        tearDownTest();
    }
    
     @AfterClass
    public static void tearDownClass() throws Exception {
        OvationTestCase.tearDownDatabase(mgr);
    }


    @Test
    public void testCreateNodeForNodeThatAlreadyExists() {
        //TODO
    }

    @Test
    public void testQuerySetsProjectViewRootNodeAppropriately() {
        em = new ExplorerManager();
        em.setRootContext(new AbstractNode(new QueryChildren(true)));
        Iterator<IEntityBase> itr = dsc.getContext().query(Experiment.class, "true");

        Set s = new HashSet<ExplorerManager>();
        s.add(em);
        EntityWrapperUtilities.createNodesFromQuery(s, itr);

        Node[] projects = em.getRootContext().getChildren().getNodes(true);
        Set<String> projectSet = new HashSet<String>();
        for (Project p : dsc.getContext().getProjects()) {
            projectSet.add(p.getURIString());
        }

        for (Node n : projects) {
            IEntityWrapper ew = n.getLookup().lookup(IEntityWrapper.class);
            assertTrue(projectSet.contains(ew.getURI()));
            projectSet.remove(ew.getURI());
        }
        assertTrue(projectSet.isEmpty());
    }

    @Test
    public void testQuerySetsSourceViewRootNodeAppropriately() {
        ExplorerManager em = new ExplorerManager();
        em.setRootContext(new AbstractNode(new QueryChildren(false)));
        Iterator<IEntityBase> itr = dsc.getContext().query(Experiment.class, "true");

        Set mgrSet = new HashSet<ExplorerManager>();
        mgrSet.add(em);
        EntityWrapperUtilities.createNodesFromQuery(mgrSet, itr);

        Node[] sources = em.getRootContext().getChildren().getNodes(true);
        Set<String> sourcesSet = new HashSet<String>();

        for (Source s : dsc.getContext().getSources()) {
            if (s.getParent() == null) {
                sourcesSet.add(s.getURIString());
            }
        }
        for (Node n : sources) {
            IEntityWrapper ew = n.getLookup().lookup(IEntityWrapper.class);
            assertTrue(sourcesSet.contains(ew.getURI()));
            sourcesSet.remove(ew.getURI());
        }
        assertTrue(sourcesSet.isEmpty());
    }

    @Test
    public void testQuerySetsExperimentNodesAppropriatelyInSourceView() {
        ExplorerManager em = new ExplorerManager();
        em.setRootContext(new AbstractNode(new QueryChildren(false)));
        Iterator<IEntityBase> itr = dsc.getContext().query(Experiment.class, "true");

        Set mgrSet = new HashSet<ExplorerManager>();
        mgrSet.add(em);
        EntityWrapperUtilities.createNodesFromQuery(mgrSet, itr);
        while (itr.hasNext()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        ArrayList<Node> sourceChildren = new ArrayList();
        for (Node n : em.getRootContext().getChildren().getNodes(true)) {
            Node[] nodes = n.getChildren().getNodes(true);
            for (Node child : nodes) {
                sourceChildren.add(child);
            }
        }
        Set<String> entitySet = new HashSet<String>();

        for (Source s : dsc.getContext().getSources()) {
            if (s.getParent() == null) {
                for (Experiment e : s.getExperiments()) {
                    entitySet.add(e.getURIString());
                }
            }
        }
        for (Node n : sourceChildren) {
            IEntityWrapper ew = n.getLookup().lookup(IEntityWrapper.class);
            if (ew.getType().isAssignableFrom(Experiment.class)) {
                assertTrue(entitySet.contains(ew.getURI()));
                entitySet.remove(ew.getURI());
            }
        }
        assertTrue(entitySet.isEmpty());
    }

    @Test
    public void testQuerySetsExperimentNodesAppropriatelyInProjectView() {
        ExplorerManager em = new ExplorerManager();
        em.setRootContext(new AbstractNode(new QueryChildren(true)));
        Iterator<IEntityBase> itr = dsc.getContext().query(Experiment.class, "true");

        Set mgrSet = new HashSet<ExplorerManager>();
        mgrSet.add(em);
        EntityWrapperUtilities.createNodesFromQuery(mgrSet, itr);

        ArrayList<Node> projectChildren = new ArrayList();
        for (Node n : em.getRootContext().getChildren().getNodes(true)) {
            for (Node child : n.getChildren().getNodes(true)) {
                projectChildren.add(child);
            }
        }
        Set<String> entitySet = new HashSet<String>();
        for (Project p : dsc.getContext().getProjects()) {
            for (Experiment e : p.getExperiments()) {
                entitySet.add(e.getURIString());
            }
        }
        for (Node n : projectChildren) {
            IEntityWrapper ew = n.getLookup().lookup(IEntityWrapper.class);
            if (ew.getType().isAssignableFrom(Experiment.class)) {
                assertTrue(entitySet.contains(ew.getURI()));
                entitySet.remove(ew.getURI());
            } else {
                fail("Project node's child was something other than an Experment");
            }
        }
        assertTrue(entitySet.isEmpty());
    }

    @Test
    public void testQuerySetsAnalysisRecordNodesAppropriatelyInProjectView() {
        ExplorerManager em = new ExplorerManager();
        em.setRootContext(new AbstractNode(new QueryChildren(true)));
        Iterator<IEntityBase> itr = dsc.getContext().query(AnalysisRecord.class, "true");

        Set mgrSet = new HashSet<ExplorerManager>();
        mgrSet.add(em);
        EntityWrapperUtilities.createNodesFromQuery(mgrSet, itr);

        ArrayList<Node> analysisRecordNodes = new ArrayList();
        for (Node n : em.getRootContext().getChildren().getNodes(true)) {
            for (Node child : n.getChildren().getNodes(true)) {
                for (Node grandChild : child.getChildren().getNodes(true)) {
                    analysisRecordNodes.add(grandChild);
                }
            }
        }
        Set<String> entitySet = new HashSet<String>();
        Iterator<User> userItr = dsc.getContext().getUsersIterator();
        while (userItr.hasNext()) {
            User user = userItr.next();
            for (Project p : dsc.getContext().getProjects()) {
                for (AnalysisRecord e : p.getAnalysisRecords(user.getUsername())) {
                    entitySet.add(e.getURIString());
                }
            }
        }

        for (Node n : analysisRecordNodes) {
            IEntityWrapper ew = n.getLookup().lookup(IEntityWrapper.class);
            if (ew.getType().isAssignableFrom(Experiment.class)) {
                assertTrue(entitySet.contains(ew.getURI()));
                entitySet.remove(ew.getURI());
            } else {
                fail("Project node's child was something other than an Experment");
            }
        }
        assertTrue(entitySet.isEmpty());
    }

    //Manual test
    @Test
    public void testPerformanceOnManyChildrenNodes() {
    }

    @Test
    public void testQueryListenerCancelsIteration() {
    }
}
