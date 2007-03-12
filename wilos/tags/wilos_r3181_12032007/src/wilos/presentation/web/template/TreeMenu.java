/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/
package wilos.presentation.web.template;

import com.icesoft.faces.component.tree.Tree;

import javax.faces.context.FacesContext;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * TODO Method description
 * <p>The TreeNavigation class is the backing bean for the showcase navigation
 * tree on the left hand side of the application. Each node in the tree is made
 * up of a PageContent which is responsible for the navigation action when a
 * tree node is selected.</p>
 * <p/>
 * <p>When the Tree component binding takes place the tree nodes are initialized
 * and the tree is built.  Any addition to the tree navigation must be made to
 * this class.</p>
 *
 * @since 0.3.0
 */
public class TreeMenu {

    // binding to component
    private Tree treeComponent;

    // bound to components value attribute
    private DefaultTreeModel model;

    // root node of tree, for delayed construction
    private DefaultMutableTreeNode rootTreeNode;

    // map of all navigation backing beans.
    private MenuBean navigationBean;

    // initialization flag
    private boolean isInitiated;

    /**
     * Default constructor of the tree.  The root node of the tree is created at
     * this point.
     */
    public TreeMenu() {
        // build root node so that children can be attached
        PageContentBean rootObject = new PageContentBean();
        rootObject.setMenuDisplayText("wilos");
        rootObject.setMenuContentTitle("wilos");
        rootObject.setTemplateName("wilos");
        rootObject.setNavigationSelection(navigationBean);
        rootObject.setPageContent(true);
        rootTreeNode = new DefaultMutableTreeNode(rootObject);
        rootObject.setWrapper(rootTreeNode);
        model = new DefaultTreeModel(rootTreeNode);
    }
    
    /**
     * Utility method to build the entire navigation tree.
     */
    private void init() {
        // set init flag
        isInitiated = true;

        if (rootTreeNode != null) {
            // get the navigation bean from the faces context
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Object navigationObject =  
            	facesContext.getApplication().createValueBinding("#{menu}").getValue(facesContext);

            if (navigationObject != null && navigationObject instanceof MenuBean) {
                // set bean callback for root
                PageContentBean branchObject = (PageContentBean) rootTreeNode.getUserObject();

                // assign the initialized navigation bean, so that we can enable panel navigation
                navigationBean = (MenuBean) navigationObject;

                // set this node as the default page to view
                navigationBean.setSelectedPanel((PageContentBean) rootTreeNode.getUserObject());
                branchObject.setNavigationSelection(navigationBean);

                /**
                 * Generate the backing bean for each tree node and put it all together
                 */               
                
                // component menu -> Tree
                /*
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("subscribe");
                branchObject.setMenuContentTitle("subscribe");
                branchObject.setTemplateName("subscribe");
                */
                
                branchObject.setNavigationSelection(navigationBean);
                DefaultMutableTreeNode leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                //branchObject.setLeaf(true);
                // finally add the new custom component branch
                rootTreeNode.add(leafNode);
            }

        }

    }

    /**
     * Gets the default tree model.  This model is needed for the value
     * attribute of the tree component.
     *
     * @return default tree model used by the navigation tree
     */
    public DefaultTreeModel getModel() {
        return model;
    }

    /**
     * Sets the default tree model.
     *
     * @param model new default tree model
     */
    public void setModel(DefaultTreeModel model) {
        this.model = model;
    }

    /**
     * Gets the tree component binding.
     *
     * @return tree component binding
     */
    public Tree getTreeComponent() {
        return treeComponent;
    }

    /**
     * Sets the tree component binding.
     *
     * @param treeComponent tree component to bind to
     */
    public void setTreeComponent(Tree treeComponent) {
        this.treeComponent = treeComponent;
        if (!isInitiated) {
            init();
        }
    }
}