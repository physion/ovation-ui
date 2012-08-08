/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.browser;

import java.util.List;
import ovation.IEntityBase;
import ovation.User;
import us.physion.ovation.browser.EntityWrapper;

/**
 *
 * @author huecotanks
 */
public class PerUserEntityWrapper extends EntityWrapper {

    List<EntityWrapper> children;

    public PerUserEntityWrapper(String username, String uri, List<EntityWrapper> children) {
        super(username, User.class, uri);
        this.children = children;
    }

    protected PerUserEntityWrapper(String username, String uri) {
        super(username, User.class, uri);
    }

    public List<EntityWrapper> getChildren() {
        return children;
    }
}
