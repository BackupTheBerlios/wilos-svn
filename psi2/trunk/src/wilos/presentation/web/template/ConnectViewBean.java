package wilos.presentation.web.template;

import javax.faces.context.FacesContext;

import wilos.business.services.presentation.web.WebSessionService;


/**
 * TODO Method description
 */
public class ConnectViewBean {

    private ConnectContentBean selectedPanel;
    
    private WebSessionService webSessionService;

    /**
     * Gets the currently selected content panel.
     *
     * @return currently selected content panel.
     */
    public ConnectContentBean getSelectedPanel() {
        return selectedPanel;
    }

    /**
     * Sets the selected panel to the specified panel.
     *
     * @param selectedPanel a none null page content bean.
     */
    public void setSelectedPanel(ConnectContentBean selectedPanel) {
        if (selectedPanel != null && selectedPanel.isPageContent()) {
            this.selectedPanel = selectedPanel;
        }
    }
    
    
    /**
     * TODO Method description
     *
     * @param connected
     * @return
     */
    public String connected(boolean _connected, String _applicationRole){
    	ConnectContentBean connectContent = new ConnectContentBean();
    	FacesContext context = FacesContext.getCurrentInstance();
    	
    	if(_connected)
    	{
    		
    		connectContent.setTemplateName("connected");
    		connectContent.setTemplateNameActions(_applicationRole);
    		if(_applicationRole == "participant_role")
    			connectContent.setTemplateNameMenu("tree_group");
    		else
    			connectContent.setTemplateNameMenu("no_tree_group");
    	}
    	else
    	{
    		connectContent.setTemplateName("not_connected");
    		connectContent.setTemplateNameActions("none");
    		connectContent.setTemplateNameMenu("no_tree_group");
    		this.webSessionService.cleanSesssion();
    	}
    	connectContent.setNavigationSelection(this);
    	this.selectedPanel = connectContent;
    	return "";
    }
}
