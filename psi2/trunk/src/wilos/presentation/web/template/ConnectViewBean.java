package wilos.presentation.web.template;

/**
 * TODO Method description
 */
public class ConnectViewBean {

    private ConnectContentBean selectedPanel;

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
    	if(_connected)
    	{
    		
    		connectContent.setTemplateName("connected");
    		connectContent.setTemplateNameActions(_applicationRole);
    	}
    	else
    	{
    		connectContent.setTemplateName("not_connected");
    		connectContent.setTemplateNameActions("none");
    	}
    	connectContent.setNavigationSelection(this);
    	this.selectedPanel = connectContent;
    	return "";
    }
}
