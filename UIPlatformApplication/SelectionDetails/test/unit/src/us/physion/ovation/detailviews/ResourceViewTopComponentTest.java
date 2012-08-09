/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.detailviews;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.joda.time.DateTime;
import org.junit.*;
import static org.junit.Assert.*;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import ovation.*;
import ovation.database.DatabaseManager;
import us.physion.ovation.interfaces.IEntityWrapper;
import us.physion.ovation.interfaces.TestEntityWrapper;

/**
 *
 * @author huecotanks
 */
public class ResourceViewTopComponentTest {
    
    public ResourceViewTopComponentTest() {
        
    }
    
    private IResourceWrapper rw1;
    private IResourceWrapper rw2;
    
    private TestEntityWrapper project;
    private TestEntityWrapper source;
    
    static SelectionViewTestManager tm = new SelectionViewTestManager();
    IAuthenticatedDataStoreCoordinator dsc;
    @BeforeClass
    public static void setUpClass() throws Exception {

        File f = new File(tm.getConnectionFile());
        if (!f.exists()) {
            DatabaseManager db = new DatabaseManager();
            String lockServer = System.getProperty("OVATION_LOCK_SERVER");
            if(lockServer == null) {
                lockServer = InetAddress.getLocalHost().getHostName();
            }
            
            int nodeFdId = 0;
            if (System.getProperty("NODE_FDID") != null) {
                nodeFdId = Integer.parseInt(System.getProperty("NODE_FDID"));
            }

            int jobFdId = 4;
            if (System.getProperty("JOB_FDID") != null) {
                jobFdId = Integer.parseInt(System.getProperty("JOB_FDID"));
            }
            
            db.createLocalDatabase(tm.getConnectionFile(), lockServer, nodeFdId + jobFdId);
        }

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        DatabaseManager db = new DatabaseManager();
        db.deleteLocalDatabase(tm.getConnectionFile());
    }

    @Before
    public void setUp() {
        try {
            dsc = tm.setupDatabase();
        } catch (Exception e) {
            tearDown();
            fail(e.getMessage());
        }

        String UNUSED_NAME = "name";
        String UNUSED_PURPOSE = "purpose";
        DateTime UNUSED_START = new DateTime(0);
        byte[] data = {1, 2, 3, 4, 5};
        String uti = "unknown-uti";
        
        DataContext c = dsc.getContext();
        project = new TestEntityWrapper(dsc, c.insertProject(UNUSED_NAME, UNUSED_PURPOSE, UNUSED_START));
        source = new TestEntityWrapper(dsc, c.insertSource("source"));
        Resource r1 = project.getEntity().addResource(uti, "resource 1", data);
        rw1 = new TestResourceWrapper(dsc, r1);
        Resource r2 = source.getEntity().addResource(uti, "resource 2", data);
        rw2 = new TestResourceWrapper(dsc, r2);

    }

    @After
    public void tearDown() {
        tm.tearDownDatabase();
    }
    
    @Test
    public void testUpdateResourcesUpdatesTheEntitiesList()
    {
        Set<IEntityWrapper> entitySet = new HashSet<IEntityWrapper>();
        
        entitySet.add(project);
        entitySet.add(source);
        ResourceViewTopComponent t = new ResourceViewTopComponent();
        assertTrue( t.getResources().isEmpty());
        t.updateResources(entitySet);
        
        Set<String> nameSet = new HashSet();
        for (IResourceWrapper rw :t.getResources()){
            nameSet.add(rw.getName());
        }
        assertTrue(nameSet.contains(rw1.getName()));
        assertTrue(nameSet.contains(rw2.getName()));
        
        entitySet = new HashSet();
        entitySet.add(source);
        t.updateResources(entitySet);
        List<IResourceWrapper> resources = t.getResources();
        assertTrue(resources.get(0).getName().equals(rw2.getName()));
        assertEquals(resources.size(), 1);

    }
    
     @Test
    public void testUpdateResourcesUpdatesTheEditedSet()
    {
        Set<IEntityWrapper> entitySet = new HashSet<IEntityWrapper>();
        
        entitySet.add(project);
        entitySet.add(source);
        ResourceViewTopComponent t = new ResourceViewTopComponent();
        t.updateResources(entitySet);
        
        t.editResource(rw1);
        assertTrue(t.editedSet.contains(rw1));
    }
     
     @Test
     public void testAddNewResource() throws IOException
     {
         Set<IEntityWrapper> entitySet = new HashSet<IEntityWrapper>();
        
        entitySet.add(source);
        ResourceViewTopComponent t = new ResourceViewTopComponent();
        t.updateResources(entitySet);
        File tmp = null;
        try{
             tmp = File.createTempFile("file", ".ext");
             BufferedWriter out = new BufferedWriter(new FileWriter(tmp));
             out.write("Stuff in the file!!");
             out.close();
             t.addResource(entitySet, tmp.getAbsolutePath());
             
             boolean foundResource = false;
             for (IResourceWrapper rw : t.getResources())
             {
                 if (rw.getName().equals(tmp.getName()))
                 {
                     foundResource = true;
                 }
             }
             
             assertNotNull(source.getEntity().getResource(tmp.getName()));
             assertTrue(foundResource);
         }
        finally{
            tmp.delete();
        }
     }
     
    @Test
    public void testDeactivateSyncButtonWhenTheresNothingToSync()
    {
        Set<IEntityWrapper> entitySet = new HashSet<IEntityWrapper>();
        
        entitySet.add(project);
        entitySet.add(source);
        ResourceViewTopComponent t = new ResourceViewTopComponent();
        t.updateResources(entitySet);
        assertFalse(t.saveButtonIsEnabled());
        
        t.editResource(rw1);
        assertTrue(t.saveButtonIsEnabled());
        
        t.removeResources(new IResourceWrapper[]{rw1}, entitySet);
        assertFalse(t.saveButtonIsEnabled());    
        assertEquals(t.getResources().size(), 1);
    } 
    
    
}
